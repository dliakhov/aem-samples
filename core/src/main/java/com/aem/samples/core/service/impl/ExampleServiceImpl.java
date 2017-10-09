package com.aem.samples.core.service.impl;

import com.aem.samples.core.service.ExampleService;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.HashMap;
import java.util.Map;

@Component(service = ExampleService.class)
public class ExampleServiceImpl implements ExampleService {

    public static final String MY_SYSTEM_USER = "mySystemUser";
    @Reference
    private SlingRepository slingRepository;

    @Reference
    public ResourceResolverFactory rrFactory;

    @Override
    public String getSystemUser() throws RepositoryException, LoginException {
        Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.SUBSERVICE, MY_SYSTEM_USER);
        ResourceResolver serviceResourceResolver = rrFactory.getServiceResourceResolver(param);
        // Do some work w your service resource resolver
        return serviceResourceResolver.getUserID();
    }

}
