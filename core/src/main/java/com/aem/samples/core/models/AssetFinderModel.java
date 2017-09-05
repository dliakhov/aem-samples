package com.aem.samples.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import javax.annotation.PostConstruct;

@Model(adaptables = SlingHttpServletRequest.class)
public class AssetFinderModel {

    private final static String NAME_TEXT_PROPERTY = "text";

    @Self
    private SlingHttpServletRequest request;
    private String text;

    public String getNameTextProperty() {
        return NAME_TEXT_PROPERTY;
    }

    @PostConstruct
    protected void init() {
        RequestParameter requestParameter = request.getRequestParameter(NAME_TEXT_PROPERTY);
        if (requestParameter == null) {
            return;
        }
        String text = requestParameter.getString();
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
