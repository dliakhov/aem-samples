package com.aem.samples.core.search.impl;

import com.aem.samples.core.search.ContentFinder;
import org.apache.sling.api.resource.ResourceResolver;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;
import java.util.ArrayList;
import java.util.List;

public class QueryManagerContentFinder implements ContentFinder {

    private QueryManager queryManager;

    public QueryManagerContentFinder(ResourceResolver resourceResolver) throws RepositoryException {
        this.queryManager = resourceResolver.adaptTo(Session.class).getWorkspace().getQueryManager();
    }

    @Override
    public List<String> searchByPath(String path, String text) throws RepositoryException {
        Query query = queryManager.createQuery("SELECT * FROM [nt:base] AS s WHERE ISDESCENDANTNODE([" + path + "]) AND CONTAINS(s.*, '" + text + "')", Query.JCR_SQL2);
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
