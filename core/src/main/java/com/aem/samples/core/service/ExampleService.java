package com.aem.samples.core.service;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;

import javax.jcr.RepositoryException;

public interface ExampleService {

    String getSystemUser() throws RepositoryException, LoginException;

}
