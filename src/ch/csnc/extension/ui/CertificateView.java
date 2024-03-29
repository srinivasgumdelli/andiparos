/*
 * Copyright (C) 2010, Compass Security AG
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/copyleft/
 * 
 */

package ch.csnc.extension.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;

public class CertificateView extends JFrame {

	private static final long serialVersionUID = -7284926693579230812L;

	private JScrollPane certificateScrollPane;
	private JTextArea certificateTextArea;
	private JButton closeButton;

	/** Creates new form Certificate */
	public CertificateView(String certificate) {
		initComponents();
		this.certificateTextArea.setText(certificate);
		setVisible(true);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {
		closeButton = new JButton();
		certificateScrollPane = new JScrollPane();
		certificateTextArea = new JTextArea();

		setTitle("Certificate");
		closeButton.setText("Close");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				closeButtonActionPerformed(evt);
			}
		});

		certificateScrollPane.setViewportView(certificateTextArea);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
			GroupLayout.Alignment.LEADING).addGroup(
			GroupLayout.Alignment.TRAILING,
			layout.createSequentialGroup().addContainerGap().addGroup(
				layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(closeButton, GroupLayout.PREFERRED_SIZE,
							93, GroupLayout.PREFERRED_SIZE).addComponent(
							certificateScrollPane, GroupLayout.DEFAULT_SIZE,
							658, Short.MAX_VALUE)).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
			GroupLayout.Alignment.LEADING).addGroup(
			GroupLayout.Alignment.TRAILING,
			layout.createSequentialGroup().addContainerGap().addComponent(
				certificateScrollPane, GroupLayout.DEFAULT_SIZE, 439,
				Short.MAX_VALUE).addPreferredGap(
				LayoutStyle.ComponentPlacement.RELATED).addComponent(
				closeButton).addContainerGap()));
		pack();
	}

	private void closeButtonActionPerformed(ActionEvent evt) {
		setVisible(false);
		dispose();
	}
}
