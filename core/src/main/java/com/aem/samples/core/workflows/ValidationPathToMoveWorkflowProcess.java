package com.aem.samples.core.workflows;

import com.day.cq.wcm.api.Page;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.Collections;

@Component(service = WorkflowProcess.class,
property = {
        "process.label=Move page",
        "service.pid=com.aem.samples.core.workflows.ValidationPathToMoveWorkflowProcess"
})
public class ValidationPathToMoveWorkflowProcess implements WorkflowProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationPathToMoveWorkflowProcess.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
        LOGGER.info("Move page");
        String pathToMove = (String)workItem.getWorkflowData().getMetaDataMap().get("pathToMove");
        LOGGER.info("Path value: " + pathToMove);

        String pagePath = (String)workItem.getWorkflowData().getPayload();
        ResourceResolver resourceResolver = null;
        try {
            resourceResolver = getResourceResolver(workflowSession.getSession());
            Page page = resourceResolver.resolve(pagePath).adaptTo(Page.class);
        } catch (LoginException e) {
            LOGGER.error("LoginException happens: " + e.getMessage());
        }
        if (isPathValid(pathToMove, pagePath)) {
            LOGGER.error("Page is valid");
        } else {
            LOGGER.info("Page is not valid");
        }

    }

    private boolean isPathValid(String pathToMove, String pathPage) {
        return (StringUtils.isNotBlank(pathToMove) && pathToMove.equals(pathPage));
    }

    private ResourceResolver getResourceResolver(Session session) throws LoginException {
        return resourceResolverFactory.getResourceResolver(Collections.<String, Object>singletonMap(JcrResourceConstants.AUTHENTICATION_INFO_SESSION,
                session));
    }

}

