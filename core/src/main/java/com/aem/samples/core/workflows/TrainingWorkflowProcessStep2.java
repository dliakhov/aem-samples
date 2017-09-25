package com.aem.samples.core.workflows;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = WorkflowProcess.class,
        property = {
                "process.label=Training Workflow Process Step 2",
                "service.pid=com.aem.samples.core.workflows.TrainingWorkflowProcessStep2"
        })
public class TrainingWorkflowProcessStep2 implements WorkflowProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingWorkflowProcessStep2.class);

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
        LOGGER.info("Training workflow step 2");
        String message = String.valueOf(workItem.getWorkflowData().getMetaDataMap().get("interstep.message"));
        LOGGER.info("Message: " + message);
    }

}
