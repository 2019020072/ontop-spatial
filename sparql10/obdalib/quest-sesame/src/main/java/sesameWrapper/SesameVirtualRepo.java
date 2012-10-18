package sesameWrapper;

import it.unibz.krdb.obda.model.OBDAException;
import it.unibz.krdb.obda.owlrefplatform.core.QuestConstants;
import it.unibz.krdb.obda.owlrefplatform.core.QuestDBConnection;
import it.unibz.krdb.obda.owlrefplatform.questdb.QuestDBVirtualStore;

import java.io.File;
import java.net.URI;

import org.openrdf.repository.RepositoryException;

public class SesameVirtualRepo extends SesameAbstractRepo {

	private QuestDBVirtualStore virtualStore;

	public SesameVirtualRepo(String name, String tboxFile, String obdaFile)
			throws Exception {
		super();

		URI obdaURI = (new File(obdaFile)).toURI();
		this.virtualStore = new QuestDBVirtualStore(name,
				(new File(tboxFile)).toURI(), obdaURI);

	}

	public void initialize() throws RepositoryException {
		super.initialize();
		try {
			virtualStore.getConnection();
		} catch (OBDAException e) {
			e.printStackTrace();
		}
	}

	@Override
	public QuestDBConnection getQuestConnection() throws OBDAException {
		return virtualStore.getConnection();
	}

	@Override
	public boolean isWritable() throws RepositoryException {
		// Checks whether this repository is writable, i.e.
		// if the data contained in this repository can be changed.
		// The writability of the repository is determined by the writability
		// of the Sail that this repository operates on.
		return false;
	}

	public String getType() {
		return QuestConstants.VIRTUAL;
	}

}