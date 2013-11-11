/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.protege4.views;

import org.semanticweb.ontop.model.impl.OBDAModelImpl;

import java.awt.BorderLayout;

import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;
import org.semanticweb.ontop.protege4.core.OBDAModelManager;
import org.semanticweb.ontop.protege4.core.OBDAModelManagerListener;
import org.semanticweb.ontop.protege4.panels.SavedQueriesPanel;
import org.semanticweb.ontop.protege4.panels.SavedQueriesPanelListener;

public class QueryManagerView extends AbstractOWLViewComponent implements OBDAModelManagerListener {

	private static final long serialVersionUID = 1L;
	
	private SavedQueriesPanel panel;
	
	private OBDAModelManager obdaController;

	@Override
	protected void disposeOWLView() {
		QueryManagerViewsList queryManagerViews = (QueryManagerViewsList) this.getOWLEditorKit().get(QueryManagerViewsList.class.getName());
		if (queryManagerViews == null) {
			return;
		}
		queryManagerViews.remove(this);
		obdaController.removeListener(this);
	}

	@Override
	protected void initialiseOWLView() throws Exception {
		obdaController = (OBDAModelManager) getOWLEditorKit().get(OBDAModelImpl.class.getName());
		obdaController.addListener(this);

		setLayout(new BorderLayout());
		panel = new SavedQueriesPanel(obdaController.getQueryController());

		add(panel, BorderLayout.CENTER);

		registerInEditorKit();
	}

	public void addListener(SavedQueriesPanelListener listener) {
		panel.addQueryManagerListener(listener);
	}

	public void removeListener(SavedQueriesPanelListener listener) {
		panel.removeQueryManagerListener(listener);
	}

	public void registerInEditorKit() {
		QueryManagerViewsList queryManagerViews = (QueryManagerViewsList) this.getOWLEditorKit().get(QueryManagerViewsList.class.getName());
		if (queryManagerViews == null) {
			queryManagerViews = new QueryManagerViewsList();
			getOWLEditorKit().put(QueryManagerViewsList.class.getName(), queryManagerViews);
		}
		QueryInterfaceViewsList queryInterfaceViews = (QueryInterfaceViewsList) this.getOWLEditorKit().get(QueryInterfaceViewsList.class.getName());
		if ((queryInterfaceViews != null) && (!queryInterfaceViews.isEmpty())) {
			for (QueryInterfaceView queryInterfaceView : queryInterfaceViews) {
				this.addListener(queryInterfaceView);
			}
		}
		queryManagerViews.add(this);
	}

	@Override
	public void activeOntologyChanged() {
		// NO-OP
	}
}
