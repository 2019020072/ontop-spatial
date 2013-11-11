/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.protege4.views;

import it.unibz.krdb.obda.model.OBDAModel;
import it.unibz.krdb.obda.model.impl.OBDAModelImpl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;
import org.semanticweb.ontop.protege4.core.OBDAModelManager;
import org.semanticweb.ontop.protege4.core.OBDAModelManagerListener;
import org.semanticweb.ontop.protege4.panels.DatasourceSelector;
import org.semanticweb.ontop.protege4.panels.MappingAssistantPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MappingAssistantView extends AbstractOWLViewComponent implements OBDAModelManagerListener {

	private static final long serialVersionUID = 1L;

	private DatasourceSelector datasourceSelector;

	private OBDAModelManager apic;

	private static final Logger log = LoggerFactory.getLogger(SQLQueryInterfaceView.class);
	
	@Override
	protected void disposeOWLView() {
		apic.removeListener(this);
	}

	@Override
	protected void initialiseOWLView() throws Exception {

		apic = (OBDAModelManager) getOWLEditorKit().get(OBDAModelImpl.class.getName());
		apic.addListener(this);

		OBDAModel dsController = apic.getActiveOBDAModel();

		MappingAssistantPanel queryPanel = new MappingAssistantPanel(dsController);
		datasourceSelector = new DatasourceSelector(dsController);
		datasourceSelector.addDatasourceListListener(queryPanel);

		JPanel selectorPanel = new JPanel();
		selectorPanel.setLayout(new GridBagLayout());

		JLabel label = new JLabel("Select datasource: ");

		label.setFont(new Font("Dialog", Font.BOLD, 12));
		label.setForeground(new Color(53, 113, 163));

		GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		selectorPanel.add(label, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		selectorPanel.add(datasourceSelector, gridBagConstraints);

		selectorPanel.setBorder(new TitledBorder("Datasource selection"));
		queryPanel.setBorder(new TitledBorder("SQL Query Editor"));

		setLayout(new BorderLayout());
		add(queryPanel, BorderLayout.CENTER);
		add(selectorPanel, BorderLayout.NORTH);

		log.debug("SQL Query view initialized");
	}

	@Override
	public void activeOntologyChanged() {
		datasourceSelector.setDatasourceController(apic.getActiveOBDAModel());
	}
}
