package myProject.workout.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class AxiomClient {

    private final RestTemplate rest = new RestTemplate();

    private final String dataset = "render";
    private final String token = "xaat-a9f93cd1-9baa-4ab8-873e-64f4764f5917";

    public void sendLog(Map<String, Object> log) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(log, headers);

        rest.postForEntity(
                "https://api.axiom.co/v1/datasets/" + dataset + "/ingest",
                entity,
                String.class
        );
    }
}

