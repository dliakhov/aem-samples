package com.aem.samples.core.models;

import com.aem.samples.core.injector.RequestParameter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;
import java.util.ArrayList;
import java.util.List;

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

    @RequestParameter(optional = true)
    private String text;

    private QueryManager queryManager;

    @PostConstruct
    private void init() throws RepositoryException {
        queryManager = request.getResourceResolver().adaptTo(Session.class).getWorkspace().getQueryManager();
    }

    @Self
    private Resource resource;

    public String getNameTextProperty() {
        return NAME_TEXT_PROPERTY;
    }

    public String getText() {
        return text;
    }

    public List<String> getResult() throws RepositoryException {
        Query query = queryManager.createQuery("SELECT * FROM [nt:base] AS s WHERE ISDESCENDANTNODE([" + searchPath + "]) AND CONTAINS(s.*, '" + text + "')", Query.JCR_SQL2);
        QueryResult execute = query.execute();
        RowIterator rows = execute.getRows();
        List<String> resultList = new ArrayList<>();
        while (rows.hasNext()) {
            Row row = rows.nextRow();
            resultList.add(row.getPath());
        }
        return resultList;
    }

}
