package com.aem.samples.core.search;

import javax.jcr.RepositoryException;
import java.util.List;

public interface ContentFinder {

    List<String> searchByPath(String path, String text) throws RepositoryException;

}
