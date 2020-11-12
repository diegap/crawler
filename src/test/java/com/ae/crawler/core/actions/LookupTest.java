package com.ae.crawler.core.actions;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.jsoup.nodes.Element;
import org.junit.Test;

import com.ae.crawler.core.repositories.JsoupElementRepository;
import com.ae.crawler.core.services.DefaultScoringService;
import com.ae.crawler.core.services.ScoringService;

public class LookupTest {

	@Test
	public void testElementIsFoundInDocument() throws IOException {
		// given
		String elementId = "make-everything-ok-button";
		String resourcePath = "./src/test/resources/sample-0-origin.html";

		JsoupElementRepository repository = new JsoupElementRepository(new File(resourcePath));

		// when
		Optional<Element> element = repository.findById(elementId);

		// then
		assertThat(element).isNotEmpty();
	}

	@Test
	public void testElementIsNotFoundInDocument() throws IOException {
		// given
		String elementId = "non-existing-button";
		String resourcePath = "./src/test/resources/sample-0-origin.html";
		JsoupElementRepository repository = new JsoupElementRepository(new File(resourcePath));

		// when
		Optional<Element> element = repository.findById(elementId);

		// then
		assertThat(element).isEmpty();
	}

	@Test
	public void testFindSimilarElements() throws IOException {
		// given
		String elementId = "make-everything-ok-button";
		String originResourcePath = "./src/test/resources/sample-0-origin.html";
		String evilGeminiResourcePath = "./src/test/resources/sample-1-evil-gemini.html";

		JsoupElementRepository originRepository = new JsoupElementRepository(new File(originResourcePath));
		JsoupElementRepository targetRepository = new JsoupElementRepository(new File(evilGeminiResourcePath));

		Optional<Element> element = originRepository.findById(elementId);

		// when
		List<Element> similarElements = targetRepository.findByAttributeValue(element.get());

		// then
		assertThat(similarElements).isNotEmpty();
	}

	@Test
	public void testScoreSimilarElement() throws IOException {
		// given
		String elementId = "make-everything-ok-button";
		String similarElementId = "custom-mod";
		String originResourcePath = "./src/test/resources/sample-0-origin.html";
		String customModResourcePath = "./src/test/resources/sample-0-origin-mod.html";;
		JsoupElementRepository originRepository = new JsoupElementRepository(new File(originResourcePath));
		JsoupElementRepository targetRepository = new JsoupElementRepository(new File(customModResourcePath));

		Element element = originRepository.findById(elementId).get();
		Element similarElement = targetRepository.findById(similarElementId).get();

		ScoringService scoringService = new DefaultScoringService();

		// when
		Integer score = scoringService.getScore(element, similarElement);

		// then
		assertThat(score).isEqualTo(2);
	}

	@Test
	public void testFindMaxScoreOnEvilGemini() {

	}

	@Test
	public void testFindMaxScoreOnContainerAndClone() {

	}

}