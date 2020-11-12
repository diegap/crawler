package com.ae.crawler.core.services;

import org.jsoup.nodes.Element;

public interface ScoringService {
	Integer getScore(Element element, Element similarElement);
}
