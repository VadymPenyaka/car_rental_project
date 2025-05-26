package nulp.cs.carrentalrestservice.util.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RentalAgreementService {
    private final Font titleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    private final Font regularFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
    private final Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

    @SneakyThrows
    public byte[] generateDocumentBytes(Map<String, String> data) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream); // Прив’язка до пам’яті
        document.open();


        addHeader(document, data);
        addIntro(document, data);
        addSection1(document, data);
        addSection2(document);
        addSection3(document, data);
        addSection4(document);
        addSection5(document);
        addSection6(document, data);

        document.close();

        return outputStream.toByteArray();
    }



    private void addHeader(Document document, Map<String, String> data) throws DocumentException {
        Paragraph title = new Paragraph("CAR RENTAL AGREEMENT No. " + data.get("contractNumber"), titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph cityDate = new Paragraph("City: " + data.get("city") + ", dated \"" + data.get("date"), regularFont);
        cityDate.setAlignment(Element.ALIGN_CENTER);
        document.add(cityDate);
        document.add(Chunk.NEWLINE);
    }

    private void addIntro(Document document, Map<String, String> data) throws DocumentException {
        String intro = "LLC \"5Cars LLC\" represented by Director Ivanenko Ivan Ivanovych, " +
                "acting on the basis of the Charter (hereinafter referred to as the “Lessor”), " +
                "on the other hand, citizen of Ukraine " + data.get("lesseeName") + ", passport "+
                 "No. "+data.get("passportNumber") + ", issued by: " + data.get("issuedBy") + ", " +
                "Tax ID: " + data.get("taxId") +", (hereinafter referred to as the “Lessee“), " +
                "on the other hand (hereinafter referred to together as the “Parties”, " +
                "and each separately as the “Party”) have concluded this Vehicle Lease Agreement " +
                "(hereinafter referred to as the “Agreement”) on the following.";

        document.add(new Paragraph(intro, regularFont));
        document.add(Chunk.NEWLINE);

    }

    private void addSection1(Document document, Map<String, String> data) throws DocumentException {
        document.add(new Paragraph("1. SUBJECT OF THE AGREEMENT. GENERAL TERMS", boldFont));
        document.add(new Paragraph("1.1. The Lessor shall lease to the Lessee the vehicle – " +
                data.get("carBrandModel") + " (year of manufacture: " + data.get("carYear") +
                "), license plate number " + data.get("carPlate") + ", VIN " + data.get("carVIN") +
                ", color " + data.get("carColor") +".", regularFont));
        document.add(new Paragraph("1.2. The Vehicle and all required technical documentation shall be delivered to the Lessee within 3 (three) days of signing this Agreement, as confirmed by a handover act.", regularFont));
        document.add(new Paragraph("1.3. The Lessee shall have the right to use the Vehicle from " +
                data.get("rentalStartDate") + " to " + data.get("rentalEndDate") + ", inclusive.", regularFont));
        document.add(new Paragraph("1.4. Insurance of the Vehicle is provided by the Lessor. The Lessee’s liability to third parties is governed by the laws of Ukraine.", regularFont));
        document.add(new Paragraph("1.5. Purpose of rental: personal or business use not prohibited by law.", regularFont));
        document.add(Chunk.NEWLINE);
    }

    private void addSection2(Document document) throws DocumentException {
        document.add(new Paragraph("2. RIGHTS AND OBLIGATIONS OF THE PARTIES", boldFont));
        document.add(new Paragraph("2.1. The Lessee agrees to:\n" +
                "• use the Vehicle in accordance with its intended purpose;\n" +
                "• maintain the technical condition of the Vehicle;\n" +
                "• bear operational costs (fuel, washing, fines, etc.);\n" +
                "• not transfer the Vehicle to third parties without written consent from the Lessor;\n" +
                "• return the Vehicle on the end date of the rental period in accordance with the return act.", regularFont));
        document.add(new Paragraph("2.2. The Lessor agrees to:\n" +
                "• provide the Vehicle in good technical condition;\n" +
                "• provide all necessary documents for the Vehicle;\n" +
                "• cover major repairs (except in cases of damage caused by the Lessee’s fault or road accidents).", regularFont));
        document.add(Chunk.NEWLINE);
    }

    private void addSection3(Document document, Map<String, String> data) throws DocumentException {
        document.add(new Paragraph("3. RENTAL FEE", boldFont));
        document.add(new Paragraph("3.1. The total rental amount for the full rental period is " + data.get("totalAmount") + "$.", regularFont));
        document.add(new Paragraph("3.2. The rental fee is paid in full, in advance, by bank transfer to the Lessor’s account prior to the transfer of the Vehicle to the Lessee.", regularFont));
        document.add(Chunk.NEWLINE);
    }

    private void addSection4(Document document) throws DocumentException {
        document.add(new Paragraph("4. LIABILITY AND DISPUTE RESOLUTION", boldFont));
        document.add(new Paragraph("4.1. In case of breach of any terms of this Agreement, the responsible Party shall be liable in accordance with the laws of Ukraine.", regularFont));
        document.add(new Paragraph("4.2. Any disputes arising in connection with this Agreement shall be resolved through negotiations, and if no agreement is reached – in court under the applicable laws of Ukraine.", regularFont));
        document.add(Chunk.NEWLINE);
    }

    private void addSection5(Document document) throws DocumentException {
        document.add(new Paragraph("5. TERM OF THE AGREEMENT", boldFont));
        document.add(new Paragraph("5.1. This Agreement becomes effective upon signing by both Parties and remains valid until full completion of obligations.", regularFont));
        document.add(new Paragraph("5.2. The Agreement is terminated automatically after the rental period ends and the Vehicle is returned, unless otherwise agreed by the Parties.", regularFont));
        document.add(Chunk.NEWLINE);
    }

    private void addSection6(Document document, Map<String, String> data) throws DocumentException {
        document.add(new Paragraph("8. DETAILS OF THE PARTIES", boldFont));
        document.add(Chunk.NEWLINE);

        Paragraph lessorDetails = new Paragraph("LESSOR:\n" +
                "LLC \"5Cars LLC\"\n" +
                "Company Code (EDRPOU): 42750412\n" +
                "Legal Address: 10 Hryhoriia Skovorody St., Kyiv, Ukraine\n" +
                "Phone: +380-095-89-15-290\n" +
                "IBAN: UA123456789012345678901234567\n" +
                "Director: Ivanenko Ivan Ivanovych", regularFont);
        document.add(lessorDetails);
        document.add(Chunk.NEWLINE);

        Paragraph lesseeDetails = new Paragraph("LESSEE:\n" +
                "Full Name: " + data.get("lesseeName") + "\n" +
                "Passport: No. " + data.get("passportNumber") + "\n" +
                "Issued by: " + data.get("issuedBy") + "\n" +
                "Tax ID: " + data.get("taxId") + "\n" +
                "Phone: " + data.get("phoneNumber"), regularFont);
        document.add(lesseeDetails);
        document.add(Chunk.NEWLINE);

        Image logo;
        try {
            logo = Image.getInstance("https://carrental.fra1.digitaloceanspaces.com/docs/%20sign.jpeg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logo.scaleToFit(100, 100);

        PdfPTable signTable = new PdfPTable(2);
        signTable.setWidthPercentage(80);
        signTable.setWidths(new float[]{2, 2});

        PdfPCell lessorLabelCell = new PdfPCell(new Paragraph("LESSOR:", regularFont));
        lessorLabelCell.setBorder(Rectangle.NO_BORDER);
        lessorLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        signTable.addCell(lessorLabelCell);

        PdfPCell lesseeLabelCell = new PdfPCell(new Paragraph("LESSEE:", regularFont));
        lesseeLabelCell.setBorder(Rectangle.NO_BORDER);
        lesseeLabelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        signTable.addCell(lesseeLabelCell);

        PdfPCell lessorSignCell = new PdfPCell();
        lessorSignCell.setBorder(Rectangle.NO_BORDER);
        lessorSignCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        lessorSignCell.addElement(logo);
        signTable.addCell(lessorSignCell);

        PdfPCell lesseeSignCell = new PdfPCell();
        lesseeSignCell.setBorder(Rectangle.NO_BORDER);
        lesseeSignCell.setMinimumHeight(100);
        signTable.addCell(lesseeSignCell);

        document.add(signTable);
        document.add(Chunk.NEWLINE);
    }

}
