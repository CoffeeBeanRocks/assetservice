package edu.iu.c322.assetservice.client;

import edu.iu.c322.assetservice.models.License;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class LicenseClient {
    private RestTemplate restTemplate;

    public LicenseClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<License> getOrganization(int licenseId){
        ResponseEntity<License> restExchange =
                restTemplate.exchange(
                        "http://license-service/licensings/{licenseId}",
                        HttpMethod.GET,
                        null, License.class, licenseId);

        return Optional.ofNullable(restExchange.getBody());
    }
}
