package com.aem.samples.core.servlets;

import com.aem.samples.core.service.ExampleService;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=Simple Demo Servlet",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.resourceTypes=" + "sling/servlet/default",
                "sling.servlet.extensions=" + "resource"
        })
public class ExampleResourceServlet extends SlingSafeMethodsServlet {

    @Reference
    private ExampleService exampleService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String path = request.getParameter("path");
        if (StringUtils.isNotBlank(path)) {
            try {
                String userID = exampleService.getSystemUser();
                response.getOutputStream().println("<p>" + userID + "</p>");
            } catch (RepositoryException e) {
                e.printStackTrace();
            } catch (LoginException e) {
                e.printStackTrace();
            }
        }
    }

}
