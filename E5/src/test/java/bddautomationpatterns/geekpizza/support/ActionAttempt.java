package bddautomationpatterns.geekpizza.support;

import org.opentest4j.AssertionFailedError;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class ActionAttempt<TInput, TResult> {
    private final Function<TInput, TResult> action;
    private boolean wasPerformed;
    private TResult result;
    private Throwable error;

    public ActionAttempt(Function<TInput, TResult> action) {
        this.action = action;
    }

    public boolean success(){
        assertPerformed();
        return error == null;
    }

    public String errorMessage(){
        return error == null ? null : error.getMessage();
    }

    public TResult result() {
        return result;
    }

    public Throwable error() {
        return error;
    }

    protected TResult performInternal(TInput input, boolean attemptOnly){
        wasPerformed = true;
        result = null;
        error = null;
        try {
            result = action.apply(input);
        }
        catch (Throwable throwable){
            error = throwable;
            if (!attemptOnly)
                throw throwable;
        }
        return result;
    }

    public void attempt(TInput input){
        performInternal(input, true);
    }

    public TResult perform(TInput input){
        return performInternal(input, false);
    }

    public void assertPerformed(){
        assertTrue(wasPerformed, "the invocation should have been attempted");
    }

    public void assertSuccess() {
        assertPerformed();
        assertTrue(success(), errorMessage() != null ? errorMessage() : "unknown error");
    }

    public void assertFail() {
        assertPerformed();
        assertFalse(success(), "The action should have failed");
    }

    public void assertFailWith(String expectedErrorMessage) {
        assertFail();
        if (errorMessage() == null || !errorMessage().contains(expectedErrorMessage))
            throw new AssertionFailedError("The error message does not contain the expected value", expectedErrorMessage, errorMessage());
    }
}
