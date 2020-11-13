package com.ae.crawler.core.actions;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.nodes.Element;

import com.ae.crawler.core.repositories.ElementRepository;
import com.ae.crawler.core.services.ScoringService;

public class Lookup {

	private final ElementRepository originElementsRepository;
	private final ElementRepository targetElementsRepository;
	private final ScoringService scoringService;

	public Lookup(ElementRepository originElementsRepository, ElementRepository targetElementsRepository, ScoringService scoringService) {
		this.originElementsRepository = originElementsRepository;
		this.targetElementsRepository = targetElementsRepository;
		this.scoringService = scoringService;
	}

	public Optional<String> invoke(String elementId) {
		return originElementsRepository.findById(elementId).flatMap(element -> {
			List<Element> similarElements = targetElementsRepository.findByAttributeValue(element);
			Integer max = 0;
			Optional<Element> maxScoreElement = Optional.empty();
			for (Element similarElement : similarElements) {
				Integer score = scoringService.getScore(element, similarElement);
				if (score > max) {
					max = score;
					maxScoreElement = Optional.of(similarElement);
				}
			}
			return maxScoreElement;
		}).flatMap(maxScoreElement -> {
			List<String> parents = maxScoreElement.parents().stream().map(p -> p.nodeName() + "[" + p.elementSiblingIndex() + "]")
					.collect(Collectors.toList());
			Collections.reverse(parents);
			return Optional.of(parents.stream().map(p -> "/" + p).collect(Collectors.joining()));
		});
	}

}
