package bddautomationpatterns.geekpizza.support;

import org.springframework.http.HttpStatus;

import java.util.function.Function;

public class WebApiActionAttempt {

    public static <TInput, TResult> ActionAttempt<TInput, TResult> create(Function<TInput, WebApiResponse<TResult>> webApiAction, HttpStatus expectedStatus){
        return new ActionAttempt<>(i -> {
            WebApiResponse<TResult> response = webApiAction.apply(i);
            response.assertStatus(expectedStatus);
            return response.responseBody();
        });
    }

    public static <TInput> CommandActionAttempt<TInput> createCommand(Function<TInput, StatusOnlyWebApiResponse> webApiAction, HttpStatus expectedStatus){
        return new CommandActionAttempt<>(i -> {
            StatusOnlyWebApiResponse response = webApiAction.apply(i);
            response.assertStatus(expectedStatus);
        });
    }
}
