package bddautomationpatterns.geekpizza.support;

import java.util.function.Consumer;

public class CommandActionAttempt<TInput> extends ActionAttempt<TInput, Object> {

    public CommandActionAttempt(Consumer<TInput> action) {
        super(i -> {
            action.accept(i);
            return null;
        });
    }
}
