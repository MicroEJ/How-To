/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mock.widget;

import java.util.HashMap;
import java.util.Map;

import ej.widget.navigation.page.ClassNameURLResolver;
import ej.widget.navigation.page.Page;
import ej.widget.navigation.page.PageNotFoundException;

/**
 * The direct URL resolver is a class name resolver that put the loaded pages in
 * a cache.
 */
public class DirectURLResolver extends ClassNameURLResolver {

	private final Map<String, Page> pages;

	/**
	 * Creates a direct URL resolver.
	 */
	public DirectURLResolver() {
		this.pages = new HashMap<>();
		// Uncomment this to preload all the pages.
		// resolve(VectorWidgetPage.class.getName());
		// resolve(ImageWidgetPage.class.getName());
		// resolve(PictoWidgetPage.class.getName());
		// resolve(ProgressBarPage.class.getName());
		// resolve(ScrollableListPage.class.getName());
		// resolve(ScrollableTextPage.class.getName());
	}

	@Override
	public Page resolve(String url) throws PageNotFoundException {
		// Search in cached pages.
		Page page = this.pages.get(url);
		if (page == null) {
			page = super.resolve(url);
			this.pages.put(url, page);
		}
		return page;
	}

	@Override
	public boolean isSamePage(String url1, String url2) {
		return false;
	}

}
