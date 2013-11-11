/*
 * Copyright (C) 2009-2013, Free University of Bozen Bolzano
 * This source code is available under the terms of the Affero General Public
 * License v3.
 * 
 * Please see LICENSE.txt for full license terms, including the availability of
 * proprietary exceptions.
 */
package org.semanticweb.ontop.protege4.gui.component;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class AutoSuggestComboBox extends JComboBox {

	private static final long serialVersionUID = 1L;

	private Vector<Object> items;

	private boolean hideFlag = false;

	public AutoSuggestComboBox(Vector<Object> items) {
		super(items);
		this.items = items;
		setFont(new Font("Dialog", 0, 14));
		setEditable(true);
		setSelectedIndex(-1);
		overrideEditorBehavior();
	}

	public void overrideEditorBehavior() {
		if (getEditor().getEditorComponent() instanceof JTextField) {
			final JTextField tf = (JTextField) getEditor().getEditorComponent();
			tf.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							String text = tf.getText();
							if (text.length() == 0) {
								hidePopup();
								setModel(new DefaultComboBoxModel(items), "");
							} else {
								DefaultComboBoxModel m = getSuggestedModel(items, text);
								if (m.getSize() == 0 || hideFlag) {
									hidePopup();
									hideFlag = false;
								} else {
									setModel(m, text);
									showPopup();
								}
							}
							tf.setText(text);
						}
					});
				}
				@Override
				public void keyPressed(KeyEvent e) {
					String text = tf.getText();
					int code = e.getKeyCode();
					if (code == KeyEvent.VK_ESCAPE) {
						hideFlag = true;
					} else if (code == KeyEvent.VK_RIGHT) {
						for (int i = 0; i < items.size(); i++) {
							Object element = items.elementAt(i);
							String value = element.toString();
							if (value.startsWith(text)) {
								if (value.contains(":") && !text.contains(":")) {
									/* if the suggested string contains a prefix label and 
									 * the input text doesn't contain any prefix label then
									 * auto-complete the prefix label. */
									tf.setText(value.substring(0, value.indexOf(":") + 1));
								}
								return;
							}
						}
					} else if (code == KeyEvent.VK_ENTER) {
						tf.transferFocus();
						hideFlag = true;
					}
				}
			});
		}
	}

	private void setModel(DefaultComboBoxModel mdl, String str) {
		setModel(mdl);
		setSelectedIndex(-1);
	}

	private static DefaultComboBoxModel getSuggestedModel(List<Object> items, String searchedText) {
		DefaultComboBoxModel m = new DefaultComboBoxModel();
		for (Object element : items) {
			String value = element.toString();
			if (value.startsWith(searchedText)) {
				m.addElement(element);
			}
		}
		return m;
	}
}
