package bddautomationpatterns.geekpizza.support;

public abstract class PrerequisiteBase {
    public abstract boolean isFulfilled();
    protected abstract void fulfill();
    public boolean ensure()
    {
        if (isFulfilled()) {
            System.out.printf("Skip ensuring prerequisite '%s', because it is already fulfilled.%n", getClass().getSimpleName());
            return false;
        }
        System.out.printf("Fulfilling prerequisite '%s'...%n", getClass().getSimpleName());
        fulfill();
        return true;
    }
}
