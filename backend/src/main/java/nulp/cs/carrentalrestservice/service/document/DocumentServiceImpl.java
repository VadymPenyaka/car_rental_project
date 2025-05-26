package nulp.cs.carrentalrestservice.service.document;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.mapper.DocumentMapper;
import nulp.cs.carrentalrestservice.model.dto.*;
import nulp.cs.carrentalrestservice.model.enumeration.DocumentType;
import nulp.cs.carrentalrestservice.model.request.PersonalInfoRequest;
import nulp.cs.carrentalrestservice.repository.DocumentRepository;
import nulp.cs.carrentalrestservice.service.order.OrderService;
import nulp.cs.carrentalrestservice.service.person.PersonService;
import nulp.cs.carrentalrestservice.service.person.PersonalInfoService;
import nulp.cs.carrentalrestservice.util.S3Service;
import nulp.cs.carrentalrestservice.util.pdf.RentalAgreementService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final RentalAgreementService rentalAgreementService;
    private final OrderService orderService;
    private final S3Service s3Service;
    private final DigitalSignatureService digitalSignatureService;
    private final PersonService personService;
    private final PersonalInfoService personalInfoService;

    @Override
    public DocumentDTO createDocument(DocumentDTO documentDTO) {
        return documentMapper.documentToDocumentDto(documentRepository
                .save(documentMapper.documentDtoToDocument(documentDTO)));
    }

    @Override
    public void createRentalAgreementDocument(UUID orderId) {
        //add document number to method
        Map<String, String> data = getDataForGeneration(orderId);

        DocumentDTO documentToSave = DocumentDTO.builder()
                .createdAt(LocalDateTime.now())
                .type(DocumentType.AGREEMENT)
                .build();

        UUID documentId = createDocument(documentToSave).getId();

        s3Service.saveDocumentToServer(rentalAgreementService
                .generateDocumentBytes(data), documentId.toString());
    }

    @Override
    public Optional<DocumentDTO> getDocumentInfoById(UUID id) {
        return Optional.ofNullable(documentMapper
                .documentToDocumentDto(documentRepository
                        .findById(id).orElse(null)));
    }


    @Override
    public Optional<byte[]> getDocumentBytesById(UUID id) {
        return Optional.ofNullable(s3Service
                .getFile(id.toString(), "docs"));
    }

    @SneakyThrows
    @Override
    public Optional<byte[]> signAgreement(UUID documentId) {
        UUID personId = personService.getAuthenticatedPerson().getId();
        byte[] bytes = getDocumentBytesById(documentId).orElseThrow(()
                -> new NotFoundException("Document not found!"));
        return Optional.ofNullable(digitalSignatureService.signAgreement(personId, bytes));
    }

    private Map<String, String> getDataForGeneration (UUID orderId) {
        CarOrderDTO order = orderService.getCarOrderByID(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found!"));

        PersonalDataDTO personalInfo = personalInfoService.getCustomerDataById(order.getPerson().getId())
                .orElseThrow(() -> new NotFoundException("Personal data not found!"));

        LocationDTO location = order.getAdmin().getLocation();
        PassportDTO passport = personalInfo.getPassport();
        CarScheduleDTO schedule = order.getSchedule();
        CarDTO car = schedule.getCar();
        CarRegistrationInfoDTO carRegistrationInfo = car.getRegistrationInfo();

        Map<String, String> data = new HashMap<>();
        data.put("contractNumber", "1");
        data.put("city", location.getCity());
        data.put("date", LocalDate.now().toString());
        data.put("lesseeName", passport.getFullName());
        data.put("passportNumber", passport.getDocumentNumber());
        data.put("issuedBy", passport.getIssuedBy());
        data.put("taxId", passport.getTaxIdentificationNumber());
        data.put("phoneNumber", personalInfo.getPerson().getPhoneNumber());

        data.put("carBrandModel",  car.getModel().getBrandName().getName() + " " + car.getModel().getModelName());
        data.put("carYear", car.getModel().getYear().toString());
        data.put("carPlate", carRegistrationInfo.getNumber());
        data.put("carVIN", carRegistrationInfo.getVin());
        data.put("carColor", carRegistrationInfo.getColor());

        data.put("rentalStartDate", schedule.getStartDate().toString());
        data.put("rentalEndDate", schedule.getEndDate().toString());
        data.put("totalAmount", String.valueOf(order.getTotalPrice()));

        return data;
    }
}
