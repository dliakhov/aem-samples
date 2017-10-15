package com.aem.samples.core.search.impl;

import com.aem.samples.core.search.ContentFinder;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryBuilderContentFinder implements ContentFinder {

    private QueryBuilder queryBuilder;
    private Session session;

    public QueryBuilderContentFinder(QueryBuilder queryBuilder, Session session) {
        this.queryBuilder = queryBuilder;
        this.session = session;
    }

    @Override
    public List<String> searchByPath(String path, String text) throws RepositoryException {
        List<String> resultPaths = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("path", path);
        map.put("fulltext", text);
        Query query = queryBuilder.createQuery(PredicateGroup.create(map), session);
        query.setStart(0);
        query.setHitsPerPage(20);
        SearchResult result = query.getResult();
        for (Hit hit : result.getHits()) {
            resultPaths.add(hit.getPath());
        }
        return resultPaths;
    }
}
