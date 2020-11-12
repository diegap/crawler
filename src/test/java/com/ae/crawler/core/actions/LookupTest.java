package com.ae.crawler.core.actions;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.jsoup.nodes.Element;
import org.junit.Test;

import com.ae.crawler.core.repositories.JsoupElementRepository;

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
	public void testElementIsNotFoundInDocument() {

	}

	@Test
	public void testFindSimilarElements() {

	}

}