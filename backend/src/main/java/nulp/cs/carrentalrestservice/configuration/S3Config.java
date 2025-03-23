package nulp.cs.carrentalrestservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.endpoints.S3EndpointProvider;

import java.net.URI;

@Configuration
public class S3Config {

    @Value("${do.space.key}")
    private String doSpaceKey;

    @Value("${do.space.secret}")
    private String doSpaceSecret;

    @Value("${do.space.endpoint}")
    private String doSpaceEndpoint;

    @Value("${do.space.region}")
    private String doSpaceRegion;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .credentialsProvider(
                        StaticCredentialsProvider.create(AwsBasicCredentials.create(doSpaceKey, doSpaceSecret))
                )
                .region(Region.of(doSpaceRegion))
                .endpointOverride(URI.create(doSpaceEndpoint)) // Важливий момент для DO Spaces
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true) // DigitalOcean використовує path-style доступ
                        .build()
                )
                .build();
    }
}
