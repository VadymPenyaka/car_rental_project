package nulp.cs.carrentalrestservice.controller.admin;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.dto.DocumentDTO;
import nulp.cs.carrentalrestservice.service.document.DocumentService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(DocumentController.BASE_PATH)
public class DocumentController {
    public final static String BASE_PATH = "/api/v1/documents";
    private final DocumentService documentService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getById (@PathVariable UUID id) {
        byte[] pdfContent = documentService.getDocumentBytesById(id)
                .orElseThrow(() -> new NotFoundException("Document not found"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline()
                .filename("document_" + id + ".pdf")
                .build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
    }


    @GetMapping("/{id}/info")
    public DocumentDTO getDocumentInfoById (@PathVariable UUID id) {
        return documentService.getDocumentInfoById(id).orElseThrow(()
                -> new NotFoundException("Document not found"));
    }

    @PostMapping("/agreements/{id}/sign")
    public ResponseEntity<byte[]> signAgreement(@PathVariable UUID id) {
        byte[] signedBytes = documentService.signAgreement(id).orElseThrow(()
                -> new NotFoundException("Document not found"));

        return ResponseEntity.ok()
                .headers(new HttpHeaders() {{
                    setContentType(MediaType.APPLICATION_PDF);
                    setContentDisposition(ContentDisposition.inline()
                            .filename("signed_document_" + id + ".pdf")
                            .build());
                }})
                .body(signedBytes);
    }




}
