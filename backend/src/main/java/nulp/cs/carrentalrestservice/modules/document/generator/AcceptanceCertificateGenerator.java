package nulp.cs.carrentalrestservice.modules.document.generator;

import com.itextpdf.text.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AcceptanceCertificateGenerator {

    private final Font titleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    private final Font regularFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

    public void generateCertificate(Document document, Map<String, String> data) throws Exception {
        addHeader(document, data);
        addPartiesInfo(document, data);
        addVehicleDetails(document, data);
        addDocumentsInfo(document, data);
        addTechnicalCondition(document);
        addSignatures(document);
    }

    public void generateReturnCertificate (Document document, Map<String, String> data) throws Exception {
        addHeader(document, data);
        addPartiesInfo(document, data);
        addVehicleDetailsReturn(document, data);
        addDocumentsInfo(document, data);
        addTechnicalCondition(document);
        addSignatures(document);
    }


    private void addHeader(Document document, Map<String, String> data) throws DocumentException {
        Paragraph title = new Paragraph("Acceptance Certificate of Rented Property", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        Paragraph agreementInfo = new Paragraph("To the Rental Agreement No. " + data.get("agreement_number") + " dated " + data.get("agreement_date"), regularFont);
        agreementInfo.setAlignment(Element.ALIGN_CENTER);
        document.add(agreementInfo);
        document.add(Chunk.NEWLINE);

        Paragraph cityAndDate = new Paragraph("City: " + data.get("city") + ", " + data.get("acceptance_date"), regularFont);
        document.add(cityAndDate);
        document.add(Chunk.NEWLINE);
    }

    private void addPartiesInfo(Document document, Map<String, String> data) throws DocumentException {
        String intro = "This Vehicle Handover Act is an integral part of the Car Rental Agreement between:\n" +
                "\n" +
                "Lessor (Company): LLC \"5Cars LLC\"\n" +
                "Represented by: Director Ivanenko Ivan Ivanovych\n" +
                "\n" +
                "Lessee (Individual): " + data.get("lesseeName") + "\n"+
                "Passport / ID : " + data.get("passportNumber") + "\n" +
                "Tax ID:" + data.get("taxId") + "\n";

        document.add(new Paragraph(intro, regularFont));
        document.add(Chunk.NEWLINE);
    }

    private void addVehicleDetails(Document document, Map<String, String> data) throws DocumentException {
        String vehicleInfo = "The Lessor has transferred, and the Lessee has accepted into temporary paid use, " +
                "the property subject to the rental agreement – a vehicle: " +
                data.get("carBrandModel") + " (year of manufacture: " + data.get("carYear") +
                "), license plate number " + data.get("carPlate") + ", VIN " + data.get("carVIN") +
                ", color " + data.get("carColor") +".";
        document.add(new Paragraph(vehicleInfo, regularFont));
        document.add(Chunk.NEWLINE);
    }

    private void addVehicleDetailsReturn (Document document, Map<String, String> data) throws DocumentException {
        String vehicleInfo = "The Lessee has returned to the Lessor the vehicle previously rented under the rental agreement: " +
                data.get("carBrandModel") + " (year of manufacture: " + data.get("carYear") +
                "), license plate number " + data.get("carPlate") + ", VIN " + data.get("carVIN") +
                ", color " + data.get("carColor") + ".";
        document.add(new Paragraph(vehicleInfo, regularFont));
        document.add(Chunk.NEWLINE);
    }

    private void addDocumentsInfo(Document document, Map<String, String> data) throws DocumentException {
        String documentsInfo = "Together with the vehicle, the following documents have been provided:\n" +
                "Certificate of state registration (technical passport) No. " + data.get("registration_number") +
                ", as well as insurance for the vehicle.";
        document.add(new Paragraph(documentsInfo, regularFont));
        document.add(Chunk.NEWLINE);
    }

    private void addTechnicalCondition(Document document) throws DocumentException {
        String technicalCondition = "The vehicle is in good technical and visual condition: clean exterior and interior, " +
                "fully functional lights, engine, and systems. " +
                "Tires are in proper condition. No visible damage, cracks, or malfunctions detected. " +
                "All required tools and safety equipment are present.\n" +
                "A joint visual inspection has been carried out. Photos attached if necessary.";
        document.add(new Paragraph(technicalCondition, regularFont));
        document.add(Chunk.NEWLINE);
    }

    private void addSignatures(Document document) throws DocumentException {
        document.add(new Paragraph("Lessor:", regularFont));
        document.add(new Paragraph("(Signature)", regularFont));
        document.add(Chunk.NEWLINE);

        document.add(new Paragraph("Lessee:", regularFont));
        document.add(new Paragraph("(Signature)", regularFont));
    }
}
