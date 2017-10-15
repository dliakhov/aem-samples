package com.aem.samples.core.models;

import com.aem.samples.core.injector.RequestParameter;
import com.aem.samples.core.search.ContentFinder;
import com.aem.samples.core.search.impl.QueryBuilderContentFinder;
import com.aem.samples.core.search.impl.QueryManagerContentFinder;
import com.day.cq.search.QueryBuilder;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
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

    @RequestParameter(optional = true)
    private String text;

    @SlingObject
    private ResourceResolver resourceResolver;

    @OSGiService
    private QueryBuilder queryBuilder;

    private ContentFinder contentFinder;

    @PostConstruct
    private void init() throws RepositoryException {
        if (SearchEngine.QUERY_BUILDER.equals(searchEngine)) {
            contentFinder = new QueryBuilderContentFinder(queryBuilder, resourceResolver.adaptTo(Session.class));
        } else if (SearchEngine.QUERY_MANAGER.equals(searchEngine)) {
            contentFinder = new QueryManagerContentFinder(resourceResolver);
        }
    }

    public String getNameTextProperty() {
        return NAME_TEXT_PROPERTY;
    }

    public String getText() {
        return text;
    }

    public List<String> getResult() throws RepositoryException {
        return contentFinder.searchByPath(searchPath, text);
    }

    private static final class SearchEngine {
        public static final String QUERY_MANAGER = "queryManager";
        public static final String QUERY_BUILDER = "queryBuilder";
    }

}
