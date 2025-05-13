package nulp.cs.carrentalrestservice.util.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.util.Map;

@Component
public class AcceptanceCertificateGenerator {

    private final Font titleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    private final Font regularFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

    public void generateCertificate(String filePath, Map<String, String> data) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        addHeader(document, data);
        addPartiesInfo(document, data);
        addVehicleDetails(document, data);
        addDocumentsInfo(document, data);
        addTechnicalCondition(document);
        addSignatures(document);

        document.close();
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
        String partiesInfo = "The Lessor, " + data.get("lessor_full_name") + " (hereinafter referred to as the \"Lessor\"), " +
                "a private individual, residing at " + data.get("lessor_address") + ", passport series and number " + data.get("lessor_passport_number") +
                ", issued by " + data.get("lessor_issuing_authority") + ", on the one hand, and\n" +
                "the Lessee, " + data.get("lessee_full_name") + " (hereinafter referred to as the \"Lessee\"), " +
                "a private individual, residing at " + data.get("lessee_address") + ", passport series and number " + data.get("lessee_passport_number") +
                ", issued by " + data.get("lessee_issuing_authority") + ", on the other hand, by signing this certificate confirm and acknowledge the following:";
        document.add(new Paragraph(partiesInfo, regularFont));
        document.add(Chunk.NEWLINE);
    }

    private void addVehicleDetails(Document document, Map<String, String> data) throws DocumentException {
        String vehicleInfo = "The Lessor has transferred, and the Lessee has accepted into temporary paid use, the property subject to the rental agreement – a vehicle: " +
                data.get("vehicle_model") + " (" + data.get("vehicle_year") + " year of manufacture), license plate number " +
                data.get("license_plate") + ", VIN code " + data.get("vin_code") + ", color " + data.get("vehicle_color") +
                ", with an estimated value of " + data.get("vehicle_value") + " UAH.";
        document.add(new Paragraph(vehicleInfo, regularFont));
        document.add(Chunk.NEWLINE);
    }

    private void addDocumentsInfo(Document document, Map<String, String> data) throws DocumentException {
        String documentsInfo = "Together with the vehicle, the following documents have been provided:\n" +
                "Certificate of state registration (technical passport) No. " + data.get("registration_number") +
                ", as well as " + data.get("additional_document") + " for the vehicle.";
        document.add(new Paragraph(documentsInfo, regularFont));
        document.add(Chunk.NEWLINE);
    }

    private void addTechnicalCondition(Document document) throws DocumentException {
        String technicalCondition = "The technical condition of the vehicle is satisfactory. The Lessee has no claims regarding the condition of the property.";
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
