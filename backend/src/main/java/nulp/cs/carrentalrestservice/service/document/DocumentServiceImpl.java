package nulp.cs.carrentalrestservice.service.document;

import com.itextpdf.text.pdf.PdfWriter;
import com.stripe.model.TODO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.mapper.DocumentMapper;
import nulp.cs.carrentalrestservice.model.dto.*;
import nulp.cs.carrentalrestservice.model.enumeration.DocumentType;
import nulp.cs.carrentalrestservice.repository.DocumentRepository;
import nulp.cs.carrentalrestservice.service.order.OrderService;
import nulp.cs.carrentalrestservice.util.S3Service;
import nulp.cs.carrentalrestservice.util.pdf.RentalAgreementService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final RentalAgreementService rentalAgreementService;
    private final OrderService orderService;
    private final S3Service s3Service;


    @Override
    public DocumentDTO createDocument(DocumentDTO documentDTO) {
        return documentMapper.documentToDocumentDto(documentRepository
                .save(documentMapper.documentDtoToDocument(documentDTO)));
    }

    @Override
    public void createRentalAgreementDocument(UUID orderId) {
        Map<String, String> data = getDataForGeneration(orderId);

        DocumentDTO documentToSave = DocumentDTO.builder()
                .createdAt(LocalDateTime.now())
                .type(DocumentType.AGREEMENT)
                .filePath("/agreement")
                .build();

        UUID documentId = createDocument(documentToSave).getId();

        s3Service.saveDocumentToServer(rentalAgreementService
                .generateDocumentBytes(data), documentId.toString());
    }

    @Override
    public Optional<DocumentDTO> getDocumentById(UUID id) {
        return Optional.ofNullable(documentMapper
                .documentToDocumentDto(documentRepository
                        .findById(id).orElse(null)));
    }

    @Override
    public Optional<DocumentDTO> updateDocumentById(UUID id, DocumentDTO documentDTO) {
        AtomicReference<Optional<DocumentDTO>> atomicReference = new AtomicReference<>();

        documentRepository.findById(id).ifPresentOrElse ( foundDocument -> {
                foundDocument.setFilePath(documentDTO.getFilePath());
                foundDocument.setType(documentDTO.getType());
                foundDocument.setCreatedAt(documentDTO.getCreatedAt());

                atomicReference.set(Optional.ofNullable(documentMapper
                        .documentToDocumentDto(documentRepository.save(foundDocument))));

            }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    private Map<String, String> getDataForGeneration (UUID orderId) {
        CarOrderDTO order = orderService.getCarOrderByID(orderId).orElseThrow(() -> new NotFoundException("Order not found!"));

        AdminDTO admin = order.getAdmin();
        LocationDTO location = admin.getLocation();
        PassportDTO passport = order.getCustomer().getPassport();
        PersonDTO person = order.getCustomer().getPerson();
        CarScheduleDTO schedule = order.getSchedule();
        CarDTO car = schedule.getCar();
        CarRegistrationInfoDTO carRegistrationInfo = car.getRegistrationInfo();
        Map<String, String> data = new HashMap<>();
//        TODO format pdf to match map
        data.put("contractNumber", "1");
        data.put("city", location.getCity());
        data.put("date", LocalDate.now().toString());
        //TODO add fill name and address
        data.put("lesseeName", person.getSureName() + " " + person.getFirstName());
        data.put("passportNumber", passport.getDocumentNumber());
        data.put("issuedBy", passport.getIssuedBy());
        data.put("taxId", passport.getTaxIdentificationNumber());
        data.put("address", "");
        data.put("phoneNumber", person.getPhoneNumber());

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
