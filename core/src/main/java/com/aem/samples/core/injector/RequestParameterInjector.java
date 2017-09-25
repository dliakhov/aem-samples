package com.aem.samples.core.injector;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.spi.DisposalCallbackRegistry;
import org.apache.sling.models.spi.Injector;
import org.apache.sling.models.spi.injectorspecific.AbstractInjectAnnotationProcessor2;
import org.apache.sling.models.spi.injectorspecific.InjectAnnotationProcessor2;
import org.apache.sling.models.spi.injectorspecific.InjectAnnotationProcessorFactory2;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;

@Component(service = Injector.class,
        property = {
            Constants.SERVICE_RANKING + "=" + Integer.MAX_VALUE
})
public class RequestParameterInjector implements Injector, InjectAnnotationProcessorFactory2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestParameterInjector.class);

    @Override
    public String getName() {
        return "request-parameter";
    }

    @Override
    public Object getValue(Object adaptable, String fieldName, Type type,
                           AnnotatedElement annotatedElement,
                           DisposalCallbackRegistry disposalCallbackRegistry) {
        if (adaptable instanceof SlingHttpServletRequest) {
            LOGGER.debug("RequestParameterInjector#getValue adaptable instanceof SlingHttpServletRequest");
            SlingHttpServletRequest request = (SlingHttpServletRequest)adaptable;
            final RequestParameter annotation = annotatedElement.getAnnotation(RequestParameter.class);
            if (annotation == null) {
                return null;
            }

            LOGGER.debug("RequestParameterInjector#getValue RequestParameter annotation is present");
            if (type instanceof Class<?>) {
                Class<?> fieldClass = (Class<?>) type;
                return getValue(request, annotation, fieldName, fieldClass);
            }
        }
        return null;
    }

    private Object getValue(SlingHttpServletRequest request, RequestParameter annotation, String fieldName, Class<?> type) {
        String parameter;
        if (StringUtils.isNotBlank(annotation.name())) {
            parameter = request.getParameter(annotation.name());
        } else {
            parameter = request.getParameter(fieldName);
        }
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

    @Override
    public InjectAnnotationProcessor2 createAnnotationProcessor(Object o, AnnotatedElement annotatedElement) {
        RequestParameter annotation = annotatedElement.getAnnotation(RequestParameter.class);
        if (annotation != null) {
            return new RequestParameterAnnotationProcessor(annotation);
        }
        return null;
    }

    private static class RequestParameterAnnotationProcessor extends AbstractInjectAnnotationProcessor2 {

        private final RequestParameter annotation;

        RequestParameterAnnotationProcessor(RequestParameter annotation) {
            this.annotation = annotation;
        }

        @Override
        public Boolean isOptional() {
            return annotation.optional();
        }

        @Override
        public String getName() {
            return annotation.name();
        }

    }

}
