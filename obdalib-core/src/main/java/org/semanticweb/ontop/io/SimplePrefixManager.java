/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.semanticweb.ontop.model.impl.OBDAVocabulary;

public class SimplePrefixManager extends AbstractPrefixManager {

	/**
	 * A simple map containing for each ontology URI the correponding prefix
	 */
	private HashMap<String, String> uriToPrefixMap;

	/**
	 * A simple map containing for each prefix the correponding ontology URI
	 */
	private HashMap<String, String> prefixToURIMap;

	/**
	 * The default constructor. It creates a new instance of the prefix manager
	 */
	public SimplePrefixManager() {
		uriToPrefixMap = new HashMap<String, String>();
		prefixToURIMap = new HashMap<String, String>();
		initKnownPrefixes();
	}

	private void initKnownPrefixes() {
		addPrefix(OBDAVocabulary.PREFIX_RDF, OBDAVocabulary.NS_RDF);
		addPrefix(OBDAVocabulary.PREFIX_RDFS, OBDAVocabulary.NS_RDFS);
		addPrefix(OBDAVocabulary.PREFIX_OWL, OBDAVocabulary.NS_OWL);
		addPrefix(OBDAVocabulary.PREFIX_XSD, OBDAVocabulary.NS_XSD);
		addPrefix(OBDAVocabulary.PREFIX_QUEST, OBDAVocabulary.NS_QUEST);
	}

	@Override
	public void addPrefix(String prefix, String uri) {
		
		if (uri == null) {
			throw new NullPointerException("Prefix name must not be null");
		}
		if (!prefix.endsWith(":")) {
			throw new IllegalArgumentException("Prefix names must end with a colon (:)");
		}
		
		prefixToURIMap.put(prefix, getProperPrefixURI(uri));
		uriToPrefixMap.put(getProperPrefixURI(uri), prefix);
	}

	/**
	 * Returns the corresponding URI definition for the given prefix
	 * 
	 * @param prefix
	 *            the prefix name
	 * @return the corresponding URI definition or null if the prefix is not
	 *         registered
	 */
	public String getURIDefinition(String prefix) {
		return prefixToURIMap.get(prefix);
	}

	/**
	 * Returns the corresponding prefix for the given URI.
	 * 
	 * @param prefix
	 *            the prefix name
	 * @return the corresponding prefix or null if the URI is not registered
	 */
	public String getPrefix(String uri) {
		return uriToPrefixMap.get(uri);
	}

	/**
	 * Returns a map with all registered prefixes and the corresponding URI
	 * 
	 * @return a hash map
	 */
	public Map<String, String> getPrefixMap() {
		return Collections.unmodifiableMap(prefixToURIMap);
	}

	/**
	 * Checks if the prefix manager stores the prefix name.
	 * 
	 * @param prefix
	 *            The prefix name to check.
	 */
	public boolean contains(String prefix) {
		Set<String> prefixes = prefixToURIMap.keySet();
		return prefixes.contains(prefix);
	}

	@Override
	public void clear() {
		prefixToURIMap.clear();
		uriToPrefixMap.clear();
	}

	@Override
	public List<String> getNamespaceList() {
		ArrayList<String> namespaceList = new ArrayList<String>();
		for (String uri : getPrefixMap().values()) {
			namespaceList.add(uri);
		}
		Collections.sort(namespaceList, Collections.reverseOrder());
		return namespaceList;
	}
}
