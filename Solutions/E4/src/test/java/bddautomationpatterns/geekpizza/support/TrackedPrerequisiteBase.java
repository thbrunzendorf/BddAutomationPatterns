package bddautomationpatterns.geekpizza.support;

public abstract class TrackedPrerequisiteBase extends PrerequisiteBase {
    private boolean isFulfilled = false;

    @Override
    public boolean isFulfilled() { return isFulfilled; }

    public void signalFulfilled()
    {
        isFulfilled = true;
        System.out.printf("Prerequisite '%s' fulfilled.%n", getClass().getSimpleName());
    }

    @Override
    public boolean ensure()
    {
        boolean result = super.ensure();
        if (result)
            signalFulfilled();
        return result;
    }
}
