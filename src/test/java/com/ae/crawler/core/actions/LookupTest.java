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
	public void testFindMaxScoreOnEvilGemini() throws IOException {
		// given
		String elementId = "make-everything-ok-button";
		String originResourcePath = "./src/test/resources/sample-0-origin.html";
		String evilGeminiResourcePath = "./src/test/resources/sample-1-evil-gemini.html";

		JsoupElementRepository originRepository = new JsoupElementRepository(new File(originResourcePath));
		JsoupElementRepository evilGeminiRepository = new JsoupElementRepository(new File(evilGeminiResourcePath));

		Element element = originRepository.findById(elementId).get();
		List<Element> similarElements = evilGeminiRepository.findByAttributeValue(element);

		ScoringService scoringService = new DefaultScoringService();

		// when finding max score ...
		Integer max = 0;
		Element maxScoreElement = null;

		for (Element similarElement: similarElements){
			Integer score = scoringService.getScore(element, similarElement);
			if (score > max){
				max = score;
				maxScoreElement = similarElement;
			}
		}

		// then
		assertThat(maxScoreElement.attributes()).isNotNull();
		assertThat(maxScoreElement.attributes().get("href")).isEqualTo("#check-and-ok");

	}

	@Test
	public void testFindMaxScoreOnContainerAndClone() throws IOException {
		// given
		String elementId = "make-everything-ok-button";
		String originResourcePath = "./src/test/resources/sample-0-origin.html";
		String containerAndCloneResourcePath = "./src/test/resources/sample-2-container-and-clone.html";

		JsoupElementRepository originRepository = new JsoupElementRepository(new File(originResourcePath));
		JsoupElementRepository containerAndCloneRepository = new JsoupElementRepository(new File(containerAndCloneResourcePath));

		Element element = originRepository.findById(elementId).get();
		List<Element> similarElements = containerAndCloneRepository.findByAttributeValue(element);

		ScoringService scoringService = new DefaultScoringService();

		// when finding max score ...
		Integer max = 0;
		Element maxScoreElement = null;

		for (Element similarElement: similarElements){
			Integer score = scoringService.getScore(element, similarElement);
			if (score > max){
				max = score;
				maxScoreElement = similarElement;
			}
		}

		// then
		assertThat(maxScoreElement.attributes()).isNotNull();
		assertThat(maxScoreElement.attributes().get("class")).isEqualTo("btn test-link-ok");
	}

	@Test
	public void testLookUpAction() throws IOException {
		// given
		String elementId = "make-everything-ok-button";
		String originResourcePath = "./src/test/resources/sample-0-origin.html";
		String theEscapeResourcePath = "./src/test/resources/sample-3-the-escape.html";

		JsoupElementRepository originRepository = new JsoupElementRepository(new File(originResourcePath));
		JsoupElementRepository theScapeRepository = new JsoupElementRepository(new File(theEscapeResourcePath));

		ScoringService scoringService = new DefaultScoringService();
		Lookup lookup = new Lookup();

		// when
		//Optional<String> path = lookup.invoke(elementId);

		// then
		//assertThat(path.isPresent()).isTrue();
	}

}