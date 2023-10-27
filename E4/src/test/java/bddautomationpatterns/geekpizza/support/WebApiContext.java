package bddautomationpatterns.geekpizza.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import io.cucumber.spring.ScenarioScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;

import java.lang.reflect.Array;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
@ScenarioScope
public class WebApiContext {

    @Autowired
    protected TestRestTemplate restTemplate;

    public WebApiContext(){
        // initialize cookie manager to be able to manage user login sessions
        CookieHandler.setDefault(new CookieManager());
    }

    public <T> T executeGet(String url, Class<T> responseType) {

        // execute request
        ResponseEntity<T> response = restTemplate.getForEntity(url, responseType);

        // sanity check (the response is a meaningful value, not e.g. 500)
        assertTrue(response.getStatusCode().is2xxSuccessful());

        // parse response
        return response.getBody();
    }

    public <T> List<T> executeGetList(String url, Class<T> responseType) {

        // execute request

        //noinspection rawtypes
        Class arrayClass = Array.newInstance(responseType, 0).getClass();
        @SuppressWarnings("unchecked")
        ResponseEntity<T[]> response = restTemplate.getForEntity(url, arrayClass);

        // sanity check (the response is a meaningful value, not e.g. 500)
        assertTrue(response.getStatusCode().is2xxSuccessful());

        // parse response
        T[] bodyData = response.getBody();
        assert bodyData != null;
        return Arrays.asList(bodyData);
    }

    public <T> StatusOnlyWebApiResponse executePost(String url, T payload){

        return executeInternalStatusOnly(url, HttpMethod.POST, payload);
    }

    public <T> StatusOnlyWebApiResponse executePut(String url, T payload){

        return executeInternalStatusOnly(url, HttpMethod.PUT, payload);
    }

    public <TPayload, TData> WebApiResponse<TData> executePost(String url, TPayload payload, Class<TData> dataClass){

        return executeInternalWithBody(url, HttpMethod.POST, payload, dataClass);
    }

    public <TPayload, TData> WebApiResponse<TData> executePut(String url, TPayload payload, Class<TData> dataClass){

        return executeInternalWithBody(url, HttpMethod.PUT, payload, dataClass);
    }

    public <T> StatusOnlyWebApiResponse executeInternalStatusOnly(String url, HttpMethod httpMethod, T payload){

        ResponseEntity<String> response = executeInternal(url, httpMethod, payload, String.class);
        return new StatusOnlyWebApiResponse(response);
    }

    public <TPayload, TData> WebApiResponse<TData> executeInternalWithBody(String url, HttpMethod httpMethod, TPayload payload, Class<TData> dataClass){

        ResponseEntity<TData> response = executeInternal(url, httpMethod, payload, dataClass);
        return new WebApiResponse<>(response);
    }

    private <TPayload, TData> ResponseEntity<TData> executeInternal(String url, HttpMethod httpMethod, TPayload payload, Class<TData> dataClass) {
        // prepare JSON payload data
        HttpEntity<TPayload> request = new HttpEntity<>(payload);

        // execute request
        ResponseEntity<TData> response = executeForEntity(url, httpMethod, request, dataClass);

        // sanity check (the response is a meaningful value, not e.g. 500)
        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is4xxClientError());

        // return response
        return response;
    }

    private <T> ResponseEntity<T> executeForEntity(String url, HttpMethod httpMethod, @Nullable Object request, Class<T> responseType) throws RestClientException {
        RequestCallback requestCallback = restTemplate.getRestTemplate().httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.getRestTemplate().responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor);
    }
}
