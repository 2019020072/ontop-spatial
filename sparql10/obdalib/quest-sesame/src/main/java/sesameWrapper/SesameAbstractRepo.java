package sesameWrapper;

import it.unibz.krdb.obda.model.OBDAException;
import it.unibz.krdb.obda.owlrefplatform.core.QuestDBConnection;
import it.unibz.krdb.obda.owlrefplatform.questdb.QuestDB;

import java.io.File;

import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.repository.RepositoryException;

public abstract class SesameAbstractRepo implements
		org.openrdf.repository.Repository {

	private RepositoryConnection repoConnection;

	boolean isinitialized = false;
	
	public SesameAbstractRepo() {

	}

	public RepositoryConnection getConnection() throws RepositoryException {
		try {
			return new RepositoryConnection(this, getQuestConnection());
		} catch (OBDAException e) {
			throw new RepositoryException(e);
		}
		// Opens a connection to this repository that can be used for
		// querying and updating the contents of the repository.
		// Created connections need to be closed to make sure that any resources
		// they keep hold of are released.
	}

	public File getDataDir() {
		// TODO Auto-generated method stub
		// Get the directory where data and logging for this repository is
		// stored.
		return null;
	}

	public ValueFactory getValueFactory() {
		// Gets a ValueFactory for this Repository.
		return ValueFactoryImpl.getInstance();
	}

	public void initialize() throws RepositoryException {
		// Initializes this repository.
		try {
			this.repoConnection = new RepositoryConnection(this, getQuestConnection());
		} catch (OBDAException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isInitialized() {
		return isinitialized;
	}

	public abstract QuestDBConnection getQuestConnection() throws OBDAException;

	public boolean isWritable() throws RepositoryException {
		// Checks whether this repository is writable, i.e.
		// if the data contained in this repository can be changed.
		// The writability of the repository is determined by the writability
		// of the Sail that this repository operates on.
		return false;
	}

	public void setDataDir(File arg0) {
		// Set the directory where data and logging for this repository is
		// stored.
	}

	public void shutDown() throws RepositoryException {
		// Shuts the repository down, releasing any resources that it keeps hold
		// of.
		// Once shut down, the repository can no longer be used until it is
		// re-initialized.
		repoConnection.close();
		try {
			// abstractStore.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public abstract String getType();

}