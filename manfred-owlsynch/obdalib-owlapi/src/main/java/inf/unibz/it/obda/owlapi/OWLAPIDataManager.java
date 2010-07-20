package inf.unibz.it.obda.owlapi;

import inf.unibz.it.dl.assertion.Assertion;
import inf.unibz.it.dl.codec.xml.AssertionXMLCodec;
import inf.unibz.it.obda.api.controller.AssertionController;
import inf.unibz.it.obda.api.controller.DatasourcesController;
import inf.unibz.it.obda.api.controller.MappingController;
import inf.unibz.it.obda.api.controller.QueryController;
import inf.unibz.it.obda.api.io.DataManager;
import inf.unibz.it.obda.api.io.PrefixManager;
import inf.unibz.it.obda.constraints.AbstractConstraintAssertionController;
import inf.unibz.it.obda.dependencies.AbstractDependencyAssertionController;
import inf.unibz.it.obda.domain.DataSource;
import inf.unibz.it.utils.io.FileUtils;
import inf.unibz.it.utils.xml.XMLUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class OWLAPIDataManager extends DataManager {

	private Map<String,String> prefixMap = null;
	
	public OWLAPIDataManager(DatasourcesController dscontroller,
			MappingController mapcontroller, QueryController queryController, PrefixManager man) {
		super(dscontroller, mapcontroller, queryController, man);
		prefixMap = new HashMap<String, String>();
	}
	
public void loadOBDADataFromURI(URI obdaFileURI) {
		
		File obdaFile = new File(obdaFileURI);
		if (obdaFile == null) {
			System.err.println("OBDAPluging. OBDA file not found.");
			return;
		}
		if (!obdaFile.exists()) {
			return;
		}
		if (!obdaFile.canRead()) {
			System.err.print("WARNING: can't read the OBDA file:" + obdaFile.toString());
		}
		Document doc = null;
		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(obdaFile);
			doc.getDocumentElement().normalize();

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		Element root = doc.getDocumentElement(); // OBDA
		if (root.getNodeName() != "OBDA") {
			System.err.println("WARNING: obda info file should start with tag <OBDA>");
			return;
		}
		NamedNodeMap att = root.getAttributes();
		for(int i=0;i<att.getLength();i++){
			Node n = att.item(i);
			String name = n.getNodeName();
			String value = n.getNodeValue();
			if(name.endsWith("xml:base")){
				prefixMap.put("base", value);
			}else if (name.equals("version")){
				prefixMap.put(name, value);
			}else if (name.equals("xmlns")){
				prefixMap.put(name, value);
			}else if (value.endsWith(".owl#")){
				String[] aux = name.split(":");
				prefixMap.put(aux[1], value);
			}
			if(name.startsWith("xmlns:") && !value.startsWith(".owl#")){
				String[] aux = name.split(":");
				prefixMap.put(aux[1], value);				
			}
		}
		
		super.loadOBDADataFromURI(obdaFileURI);
	}
	
	/***************************************************************************
	 * Saves all the OBDA data of the project to the specified file. If
	 * useTempFile is true, the mechanism will first attempt to save the data
	 * into a temporary file. If this is successful it will attempt to replace
	 * the specified file with the newly created temporarely file.
	 * 
	 * If useTempFile is false, the procedure will attempt to directly save all
	 * the data to the file.
	 */
	public void saveOBDAData(URI obdaFileURI, boolean useTempFile) throws IOException, ParserConfigurationException {

		File tempFile = null;
		File obdaFile = new File(obdaFileURI);

		if (useTempFile) {
			tempFile = new File(obdaFileURI.getPath() + ".tmp");
			if (tempFile.exists()) {
				boolean result = tempFile.delete();
				if (!result) {
					throw new IOException("Error deleting temporary file: " + tempFile.toString());
				}
			}
		}

		if (useTempFile) {
			saveOBDAData(tempFile.toURI());
		} else {
			saveOBDAData(obdaFileURI);
			return;
		}

		if (useTempFile) {
			if (obdaFile.exists()) {
				boolean result = obdaFile.delete();
				if (!result) {
					throw new IOException("Error deleting default file: " + obdaFileURI.toString());
				}
			}
			try {
				FileUtils.copy(tempFile.toString(), obdaFileURI.toString());
			} catch (IOException e) {
				System.err.println("WARNING: error while copying temp file.");
			}
			tempFile.delete();
		}
	}

	public void saveOBDAData(URI fileuri) throws ParserConfigurationException, FileNotFoundException, IOException {
		File file = new File(fileuri);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		Element root = doc.createElement("OBDA");
//		root.setAttribute("version", prefixMap.get("version"));
		Set<String> set = prefixMap.keySet();
		Iterator<String> sit = set.iterator();
		while(sit.hasNext()){
			String key = sit.next();
			root.setAttribute(key, prefixMap.get(key));
		}
		doc.appendChild(root);

		mapcontroller.dumpMappingsToXML(root);
		dscontroller.dumpDatasourcesToXML(root);

		Element dom_queries = queryController.toDOM(root);
		root.appendChild(dom_queries);

		/***********************************************************************
		 * Appending data of the registred controllers
		 */

		Set<Class<Assertion>> assertionClasses = assertionControllers.keySet();
		for (Iterator<Class<Assertion>> assClassIt = assertionClasses.iterator(); assClassIt.hasNext();) {
			Class<Assertion> assertionClass = assClassIt.next();
			AssertionXMLCodec<Assertion> xmlCodec = assertionXMLCodecs.get(assertionClass);
			AssertionController<Assertion> controller = assertionControllers.get(assertionClass);
			if (controller instanceof AbstractDependencyAssertionController) {
				AbstractDependencyAssertionController depcon = (AbstractDependencyAssertionController) controller;
				HashMap<URI, DataSource> sources = dscontroller.getAllSources();
				Set<URI> ds = sources.keySet();
				Iterator<URI> it = ds.iterator();
				while (it.hasNext()) {
					URI dsName = it.next();
					Collection<Assertion> assertions = depcon.getAssertionsForDataSource(dsName);
					if (assertions != null && assertions.size() > 0) {
						Element controllerElement = doc.createElement(depcon.getElementTag());
						controllerElement.setAttribute("datasource_uri", dsName.toString());
						for (Assertion assertion : assertions) {
							Element assertionElement = xmlCodec.encode(assertion);
							doc.adoptNode(assertionElement);
							controllerElement.appendChild(assertionElement);
						}
						root.appendChild(controllerElement);
					}
				}
			} else if (controller instanceof AbstractConstraintAssertionController) {
				AbstractConstraintAssertionController constcon = (AbstractConstraintAssertionController) controller;
				HashMap<URI, DataSource> sources = dscontroller.getAllSources();
				Set<URI> ds = sources.keySet();
				Iterator<URI> it = ds.iterator();
				while (it.hasNext()) {
					URI dsName = it.next();
					Collection<Assertion> assertions = constcon.getAssertionsForDataSource(dsName);
					if (assertions != null && assertions.size() > 0) {
						Element controllerElement = doc.createElement(constcon.getElementTag());
						controllerElement.setAttribute("datasource_uri", dsName.toString());
						for (Assertion assertion : assertions) {
							Element assertionElement = xmlCodec.encode(assertion);
							doc.adoptNode(assertionElement);
							controllerElement.appendChild(assertionElement);
						}
						root.appendChild(controllerElement);
					}
				}

			} else {
				Collection<Assertion> assertions = controller.getAssertions();
				if (assertions.isEmpty())
					continue;
				Element controllerElement = doc.createElement(controller.getElementTag());
				for (Assertion assertion : assertions) {
					Element assertionElement = xmlCodec.encode(assertion);
					doc.adoptNode(assertionElement);
					controllerElement.appendChild(assertionElement);
				}
				root.appendChild(controllerElement);
			}
		}

		// if (doc.getDocumentElement() == null) {
		// System.err.println("WARNING: there was no OBDA data to save");
		// return;
		// }

		XMLUtils.saveDocumentToXMLFile(doc, file.toString());
	}
	
	public Map<String,String> getPrefixMap(){
		return prefixMap;
	}
}
