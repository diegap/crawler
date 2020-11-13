package com.ae.crawler.core.repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupElementRepository implements ElementRepository {

	private static final String CHARSET_NAME = "utf8";
	private final Document document;

	public JsoupElementRepository(File resource) throws IOException {
		document = Jsoup.parse(resource, CHARSET_NAME, resource.getAbsolutePath());
	}

	@Override
	public Optional<Element> findById(String elementId) {
		return Optional.ofNullable(document.getElementById(elementId));
	}

	@Override
	public List<Element> findByAttributeValue(Element element) {
		List<Element> matchingElements = new ArrayList<>();

		for (Map.Entry<String, String> entry : getAttributes(element).entrySet()) {
			Elements elementsByAttributeValue = document.getElementsByAttributeValue(entry.getKey(), entry.getValue());
			matchingElements.addAll(elementsByAttributeValue);
		}

		return matchingElements;
	}

	private Map<String, String> getAttributes(Element element) {
		return element.attributes().asList().stream().collect(Collectors.toMap(Attribute::getKey, Attribute::getValue));
	}
}
