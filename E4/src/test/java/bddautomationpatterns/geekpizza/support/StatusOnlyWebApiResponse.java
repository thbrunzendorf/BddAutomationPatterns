package bddautomationpatterns.geekpizza.support;

import org.springframework.http.ResponseEntity;

public class StatusOnlyWebApiResponse extends WebApiResponse<String> {
    public StatusOnlyWebApiResponse(ResponseEntity<String> responseEntity) {
        super(responseEntity);
    }
}
