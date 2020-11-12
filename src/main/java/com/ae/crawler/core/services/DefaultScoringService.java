package com.ae.crawler.core.services;

import java.util.Map;

import org.jsoup.nodes.Element;

public class DefaultScoringService implements ScoringService {
	@Override
	public Integer getScore(Element element, Element similarElement) {
		Integer score = 0;

		for (Map.Entry<String, String> entry : similarElement.attributes()) {
			if (element.attributes().hasKey(entry.getKey()) &&
					element.attributes().get(entry.getKey()).equals(entry.getValue())) {
				score++;
			}
		}
		return score;
	}
}
