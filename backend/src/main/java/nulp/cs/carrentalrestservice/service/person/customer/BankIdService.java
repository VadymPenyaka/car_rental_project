package nulp.cs.carrentalrestservice.service.person.customer;

import nulp.cs.carrentalrestservice.model.request.CustomerFullInfoRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BankIdService {
    @Value("${bank.id.secret_key}")
    private String key;

    @Value("${bank.id.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    public CustomerFullInfoRequest getDataFromApi () {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", key);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<CustomerFullInfoRequest> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                CustomerFullInfoRequest.class
        );

        return response.getBody();
    }
}
