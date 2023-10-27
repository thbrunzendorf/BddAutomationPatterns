package bddautomationpatterns.geekpizza;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.TemplateModel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;

import static freemarker.template.Configuration.VERSION_2_3_27;

@Configuration
public class CustomFreeMarkerConfig implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof FreeMarkerConfigurer) {
            FreeMarkerConfigurer configurer = (FreeMarkerConfigurer) bean;
            Map<String, Object> sharedVariables = new HashMap<>();

            BeansWrapperBuilder builder = new BeansWrapperBuilder(VERSION_2_3_27);
            builder.setUseModelCache(true);

            BeansWrapper wrapper = builder.build();
            try {
                sharedVariables.put("authenticationService", wrapper.getStaticModels().get("bddautomationpatterns.geekpizza.service.AuthenticationService"));
                sharedVariables.put("defaultDataService", wrapper.getStaticModels().get("bddautomationpatterns.geekpizza.service.DefaultDataService"));
            }
            catch (Exception ex){
                //nop
            }

            configurer.setFreemarkerVariables(sharedVariables);
        }
        return bean;
    }
}
