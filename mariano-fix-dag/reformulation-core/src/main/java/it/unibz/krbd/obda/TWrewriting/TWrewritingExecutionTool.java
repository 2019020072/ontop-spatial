package it.unibz.krbd.obda.TWrewriting;

import it.unibz.krdb.obda.io.DataManager;
import it.unibz.krdb.obda.model.OBDADataFactory;
import it.unibz.krdb.obda.model.OBDADataSource;
import it.unibz.krdb.obda.model.OBDAModel;
import it.unibz.krdb.obda.model.OBDAStatement;
import it.unibz.krdb.obda.model.impl.OBDADataFactoryImpl;
import it.unibz.krdb.obda.model.impl.RDBMSourceParameterConstants;
import it.unibz.krdb.obda.owlapi.OBDAOWLReasonerFactory;
import it.unibz.krdb.obda.owlapi.ReformulationPlatformPreferences;
import it.unibz.krdb.obda.owlrefplatform.core.QuestOWL;
import it.unibz.krdb.obda.owlrefplatform.core.QuestTechniqueWrapper;
import it.unibz.krdb.obda.owlrefplatform.core.reformulation.TreeWitnessReformulator;
import it.unibz.krdb.obda.querymanager.QueryControllerEntity;
import it.unibz.krdb.obda.querymanager.QueryControllerQuery;
import it.unibz.krdb.sql.JDBCConnectionManager;

import java.io.File;
import java.net.URI;
import java.sql.Connection;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TWrewritingExecutionTool {

	public static Logger	log	= LoggerFactory.getLogger(TWrewritingExecutionTool.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String owlfile = "src/test/resources/test/ontologies/treewitnessrewriting/BBK-new.owl";
		String obdafile = "src/test/resources/test/ontologies/treewitnessrewriting/BBK-new.obda";

		// Loading the OWL file
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology;
		try {
			ontology = manager.loadOntologyFromPhysicalURI((new File(owlfile)).toURI());

			// Loading the OBDA data (note, the obda file must be in the same
			// folder as the owl file
			OBDADataFactory obdafac = OBDADataFactoryImpl.getInstance();
			OBDAModel obdamodel = obdafac.getOBDAModel();

			
			/* Setting up a dummy datasource */
			String driver = "org.h2.Driver";
            String url = "jdbc:h2:mem:aboxdump";
            String username = "sa";
            String password = "";
            Connection connection;

            OBDADataFactory fac = OBDADataFactoryImpl.getInstance();
            OBDADataSource source = fac.getDataSource(URI.create("http://www.obda.org/ABOXDUMP"));
            source.setParameter(RDBMSourceParameterConstants.DATABASE_DRIVER, driver);
            source.setParameter(RDBMSourceParameterConstants.DATABASE_PASSWORD, password);
            source.setParameter(RDBMSourceParameterConstants.DATABASE_URL, url);
            source.setParameter(RDBMSourceParameterConstants.DATABASE_USERNAME, username);
            source.setParameter(RDBMSourceParameterConstants.IS_IN_MEMORY, "true");
            source.setParameter(RDBMSourceParameterConstants.USE_DATASOURCE_FOR_ABOXDUMP, "true");

            connection = JDBCConnectionManager.getJDBCConnectionManager().getConnection(source);
            
            obdamodel.addSource(source);
            
            
			
			
			DataManager ioManager = new DataManager(obdamodel);
			ioManager.loadOBDADataFromURI(new File(obdafile).toURI(), ontology.getURI(), obdamodel.getPrefixManager());

			// Creating a new instance of a Quest reasoner
			OBDAOWLReasonerFactory factory = new TWOBDAPlatformFactoryImpl();

			ReformulationPlatformPreferences p = new ReformulationPlatformPreferences();
			p.setCurrentValueOf(ReformulationPlatformPreferences.ABOX_MODE, "material");
//			p.setCurrentValueOf(ReformulationPlatformPreferences.DATA_LOCATION, "inmemory");

//			factory.setOBDAController(obdamodel);
			factory.setPreferenceHolder(p);

			QuestOWL reasoner = (QuestOWL) factory.createReasoner(manager);
			reasoner.setPreferences(p);

			reasoner.loadOntologies(manager.getOntologies());
			reasoner.loadOBDAModel(obdamodel);

			// Loading a set of configurations for the reasoner and giving them
			// Properties properties = new Properties();
			// properties.load(new FileInputStream(configFile));

			// One time classification call.
			reasoner.classify();
			
			TreeWitnessReformulator ref = new TreeWitnessReformulator();
			ref.setTBox(reasoner.getOntology());
			
			((QuestTechniqueWrapper)reasoner.getTechniqueWrapper()).setRewriter(ref);

			// Now we are ready for querying

			
			for (QueryControllerEntity entity : obdamodel.getQueryController().getElements()) {
				if (!(entity instanceof QueryControllerQuery)) {
					continue;
				}
				QueryControllerQuery query = (QueryControllerQuery)entity;
				String sparqlquery = query.getQuery();
				String id = query.getID();
				log.info("##################  Executing query: {}", id);
				
				
				OBDAStatement st = reasoner.getStatement();
				
				long start = System.currentTimeMillis();				
				String rewriting = st.getRewriting(sparqlquery);
				long end = System.currentTimeMillis();
				log.debug(rewriting);
				log.info("Total time for rewriting: {}", end-start);
				
				
				
			
				
				
			}
			

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

}
