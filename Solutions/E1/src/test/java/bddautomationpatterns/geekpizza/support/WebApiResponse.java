package bddautomationpatterns.geekpizza.support;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

public class WebApiResponse<TData>  {
    ResponseEntity<TData> responseEntity;

    public WebApiResponse(ResponseEntity<TData> responseEntity) {
        this.responseEntity = responseEntity;
    }

    public HttpStatus httpStatus() {
        return responseEntity.getStatusCode();
    }

    public String responseMessage(){
        return Arrays.stream(responseEntity.toString().split(",")).map(s -> s.replace("\"", ""))
                .filter(s -> s.startsWith("status:") || s.startsWith("error:") || s.startsWith("message:"))
                .reduce((s1, s2) -> s1 + "," + s2).orElse(responseEntity.toString());
    }

    public TData responseBody(){
        return responseEntity.getBody();
    }

    public void assertStatus(HttpStatus expectedStatus) {
        if (expectedStatus != httpStatus())
            Assertions.fail(String.format("expected:%s, got:%s, response: %s", expectedStatus.toString(), httpStatus().toString(), responseMessage()));
    }
}
