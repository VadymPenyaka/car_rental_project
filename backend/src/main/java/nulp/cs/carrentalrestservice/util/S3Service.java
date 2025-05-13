package nulp.cs.carrentalrestservice.util;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Client s3Client;

    @SneakyThrows
    public void savePictureToServer(MultipartFile multipartFile, String key) {
        String contentType = multipartFile.getContentType();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket("cars")
                .key(key)
                .contentType(contentType)
                .acl("public-read")
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));
    }

    @SneakyThrows
    public void saveDocumentToServer(byte[] fileContent, String key) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket("docs")
                .key(key)
                .contentType("application/pdf")
                .acl("public-read")
                .build();

        s3Client.putObject(
                putObjectRequest,
                RequestBody.fromBytes(fileContent)
        );
    }


    public void deleteFileFromServer(String key) {
        s3Client.deleteObject(builder -> builder
                .bucket("cars")
                .key(key)
                .build()
        );
    }

}
