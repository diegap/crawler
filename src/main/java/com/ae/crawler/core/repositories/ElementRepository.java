package com.ae.crawler.core.repositories;

import java.util.List;
import java.util.Optional;

import org.jsoup.nodes.Element;

public interface ElementRepository {
	Optional<Element> findById(String elementId);

	List<Element> findByAttributeValue(Element element);
}
