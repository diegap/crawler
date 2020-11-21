package com.ae.crawler.core.services;

import org.jsoup.nodes.Element;

public class DefaultScoringService implements ScoringService {
	@Override
	public Integer getScore(Element element, Element similarElement) {
		return Math.toIntExact(similarElement.attributes().asList().stream().filter(attribute ->
			element.attributes().hasKey(attribute.getKey()) &&
					element.attributes().get(attribute.getKey()).equals(attribute.getValue())
		).count());
	}
}
