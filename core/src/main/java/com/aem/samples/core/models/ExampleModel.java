package com.aem.samples.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import javax.annotation.PostConstruct;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ExampleModel {

    @ChildResource
    private ValueMap image;

    @PostConstruct
    private void init() {
        if (image != null) {
            System.out.println(image.get("fileReference"));
        } else {
            System.out.println("Image not exists");
        }
    }

}