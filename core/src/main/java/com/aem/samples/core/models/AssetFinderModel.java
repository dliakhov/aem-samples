package com.aem.samples.core.models;

import com.aem.samples.core.injector.RequestParameter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;

import javax.inject.Inject;

@Model(adaptables = SlingHttpServletRequest.class)
public class AssetFinderModel {

    private final static String NAME_TEXT_PROPERTY = "text";

    @Self
    private SlingHttpServletRequest request;

    @Inject
    @Via("resource")
    private String searchPath;

    @Inject
    @Via("resource")
    private String searchEngine;

    @RequestParameter
    private String text;

    public String getNameTextProperty() {
        return NAME_TEXT_PROPERTY;
    }

    public String getText() {
        return text;
    }

}
