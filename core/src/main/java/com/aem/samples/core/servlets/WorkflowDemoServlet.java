package com.aem.samples.core.servlets;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowService;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.Workflow;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.model.WorkflowModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component(service = Servlet.class,
        immediate = true,
        property = {
            "sling.servlet.resourceTypes=sling/servlet/default",
            "sling.servlet.extensions=abc"})
public class WorkflowDemoServlet extends SlingSafeMethodsServlet {

    @Reference
    private WorkflowService workflowService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html; charset=utf-8");

        PrintWriter writer = response.getWriter();

        Workflow workflow = null;
        try {
            Session session = request.getResourceResolver().adaptTo(Session.class);
            WorkflowSession workflowSession = workflowService.getWorkflowSession(session);
            WorkflowModel model = workflowSession.getModel("/etc/workflow/models/aem-training-model/jcr:content/model");
            String resourcePath = request.getRequestPathInfo().getResourcePath();
            WorkflowData workflowData = workflowSession.newWorkflowData("JCR_PATH", resourcePath);
            workflow = workflowSession.startWorkflow(model, workflowData);
        } catch (WorkflowException e) {
            throw new ServletException();
        }
        writer.write("<p>Workflow Launched: " + workflow.getId() + "</p>");
        writer.flush();
        writer.close();
    }

}
