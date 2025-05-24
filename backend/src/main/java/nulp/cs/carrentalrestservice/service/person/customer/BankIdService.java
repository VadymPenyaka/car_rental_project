package nulp.cs.carrentalrestservice.service.person.customer;

import nulp.cs.carrentalrestservice.model.request.CustomerFullInfoRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.UUID;

@Service
public class BankIdService {
    @Value("${bank.id.secret_key}")
    private String key;

    @Value("${bank.id.url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public CustomerFullInfoRequest getDataFromApi () {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", key);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        String url = "/info/get";
        ResponseEntity<CustomerFullInfoRequest> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                CustomerFullInfoRequest.class
        );

        return response.getBody();
    }

    public byte[] signAgreement(UUID personId, byte[] file) throws IOException {
        String url = baseUrl + "/sign?personId=" + personId.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", key);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(file) {
            @Override
            public String getFilename() {
                return "file";
            }
        });

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                byte[].class
        );

        return response.getBody();
    }
}
