package com.aem.samples.core.injector;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.spi.DisposalCallbackRegistry;
import org.apache.sling.models.spi.Injector;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;

@Component(service = RequestParameterInjector.class,
        property = {
            Constants.SERVICE_RANKING + "=" + Integer.MAX_VALUE
})
public class RequestParameterInjector implements Injector {
    @Override
    public String getName() {
        return "request-parameter";
    }

    @Override
    public Object getValue(Object adaptable, String fieldName, Type type,
                           AnnotatedElement annotatedElement,
                           DisposalCallbackRegistry disposalCallbackRegistry) {
        if (adaptable instanceof SlingHttpServletRequest) {
            SlingHttpServletRequest request = (SlingHttpServletRequest)adaptable;
            if (type instanceof Class<?>) {
                Class<?> fieldClass = (Class<?>) type;
                return getValue(request, fieldName, fieldClass);
            }
        }
        return null;
    }

    private Object getValue(SlingHttpServletRequest request, String fieldName, Class<?> type) {
        String parameter = request.getParameter(fieldName);
        if (StringUtils.isBlank(fieldName)) {
            return null;
        }
        if (type.equals(String.class)) {
            return parameter;
        } else if (type.equals(Integer.class)) {
            return Integer.parseInt(parameter);
        }
        return null;
    }
}
