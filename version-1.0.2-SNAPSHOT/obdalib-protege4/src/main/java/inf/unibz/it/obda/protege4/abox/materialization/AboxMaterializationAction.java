package inf.unibz.it.obda.protege4.abox.materialization;

import inf.unibz.it.obda.owlapi.abox.materialization.AboxMaterializer;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.net.URI;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JOptionPane;

import org.protege.editor.core.ProtegeManager;
import org.protege.editor.core.editorkit.EditorKit;
import org.protege.editor.core.editorkit.EditorKitFactory;
import org.protege.editor.core.ui.action.ProtegeAction;
import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.event.EventType;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLIndividualAxiom;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.owl.util.OWLOntologyMerger;
import org.semanticweb.owl.util.SimpleURIMapper;

public class AboxMaterializationAction extends ProtegeAction{



	/**
	 * 
	 */
	private static final long serialVersionUID = -1211395039869926309L;

	@Override
	public void initialise() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void dispose() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(getEditorKit() instanceof OWLEditorKit && 
				getEditorKit().getModelManager() instanceof OWLModelManager) 
		{
			OWLEditorKit kit = (OWLEditorKit) this.getEditorKit();
			OWLModelManager mm = kit.getOWLModelManager();
			Container cont = this.getWorkspace().getRootPane().getParent();
//			String path = JOptionPane.showInputDialog(cont, "Insert the path of the owl file, where the new ontology should be saved");
			
//			if(path== null || path.equals("")){
//				return;
//			}
//			if(!path.toLowerCase().endsWith("owl")){
//				JOptionPane.showMessageDialog(cont, "You must specify an owl file!");
//			}
			try {	
				OWLOntologyManager owlOntManager = mm.getOWLOntologyManager();
				OWLOntology owl_ont = mm.getActiveOntology();
				String file = owlOntManager.getPhysicalURIForOntology(owl_ont).getPath();
				AboxMaterializer mat = new AboxMaterializer();
				EditorKitFactory kitFactory = kit.getEditorKitFactory();
				EditorKit dlKit = kitFactory.createEditorKit();
				OWLModelManager dlModelManager = (OWLModelManager) dlKit.getModelManager();
				OWLOntologyManager dlOntManager = dlModelManager.getOWLOntologyManager();
				
				String suffix = "with_materialized_Abox";
				String uriStr = owl_ont.getURI().toString();
				if(uriStr.endsWith(".owl")){
					uriStr = uriStr.substring(0, uriStr.length() - 4) + "_" + suffix + ".owl";
				}else{
					uriStr = uriStr + "_" + suffix + ".owl";
				}
				URI uri = URI.create(uriStr);
				
				String physUriStr = owlOntManager.getPhysicalURIForOntology(owl_ont).toString();
				if(physUriStr.endsWith(".owl")){
					physUriStr = physUriStr.substring(0, physUriStr.length() - 4) + "_" + suffix + ".owl";
				}else{
					physUriStr = physUriStr + "_" + suffix + ".owl";
				}
				URI physUri = URI.create(physUriStr);
				dlOntManager.addURIMapper(new SimpleURIMapper(uri,physUri));
				
				Set<OWLAxiom> set = owl_ont.getAxioms();
				OWLOntology new_onto = dlOntManager.createOntology(uri);
				dlOntManager.addAxioms(new_onto, set);
				Set<OWLIndividualAxiom> individuals = mat.materializeAbox(file,owl_ont, owlOntManager);
				dlOntManager.addAxioms(new_onto, individuals);
				dlModelManager.setActiveOntology(new_onto);
				dlModelManager.setDirty(new_onto);
				dlModelManager.fireEvent(EventType.ONTOLOGY_CREATED);
				ProtegeManager.getInstance().getEditorKitManager().addEditorKit(dlKit);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(cont, "ERROR: could not materialize abox.");
				e.printStackTrace();
			}
		}		
	}

}