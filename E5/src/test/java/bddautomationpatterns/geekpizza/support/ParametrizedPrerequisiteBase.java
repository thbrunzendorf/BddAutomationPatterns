package bddautomationpatterns.geekpizza.support;

import java.util.HashMap;

public abstract class ParametrizedPrerequisiteBase<TParam, TPrerequisite extends PrerequisiteBase> {

    private final HashMap<TParam, TPrerequisite> prerequisites = new HashMap<>();

    protected abstract TPrerequisite createPrerequisite(TParam param);

    public boolean ensure(TParam param)
    {
        return getPrerequisite(param).ensure();
    }

    public TPrerequisite getPrerequisite(TParam param){
        if (!prerequisites.containsKey(param)){
            prerequisites.put(param, createPrerequisite(param));
        }
        return prerequisites.get(param);
    }
}
