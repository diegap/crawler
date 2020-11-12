package com.ae.crawler.core.repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class JsoupElementRepository {

	private static final String CHARSET_NAME = "utf8";
	private final Document document;

	public JsoupElementRepository(File resource) throws IOException {
		document = Jsoup.parse(resource, CHARSET_NAME, resource.getAbsolutePath());
	}

	public Optional<Element> findById(String elementId) {
		return Optional.ofNullable(document.getElementById(elementId));
	}

	public List<Element> findByAttributeValue(Element element) {
		return new ArrayList<>();
	}
}
