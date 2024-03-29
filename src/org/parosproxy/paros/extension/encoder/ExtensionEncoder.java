/*
 *
 * Paros and its related class files.
 * 
 * Paros is an HTTP/HTTPS proxy for assessing web application security.
 * Copyright (C) 2003-2004 Chinotec Technologies Company
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the Clarified Artistic License
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Clarified Artistic License for more details.
 * 
 * You should have received a copy of the Clarified Artistic License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.parosproxy.paros.extension.encoder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import org.parosproxy.paros.extension.ExtensionAdaptor;
import org.parosproxy.paros.extension.ExtensionHook;

public class ExtensionEncoder extends ExtensionAdaptor {

	private EncoderDialog encoderDialog = null;
	private JMenuItem menuItemEncoder = null;

	public ExtensionEncoder() {
		super();
		initialize();
	}

	private void initialize() {
		this.setName("ExtensionEncoder");
	}

	private EncoderDialog getEncoderDialog() {
		if (encoderDialog == null) {
			encoderDialog = new EncoderDialog(getView().getMainFrame(), false);
			encoderDialog.setView(getView());
			encoderDialog.setSize(480, 360);
		}
		return encoderDialog;
	}

	private JMenuItem getMenuItemEncoder() {
		if (menuItemEncoder == null) {
			menuItemEncoder = new JMenuItem();
			menuItemEncoder.setText("En-/Decoder");
			menuItemEncoder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getEncoderDialog().setVisible(true);
				}
			});
		}
		return menuItemEncoder;
	}

	public void hook(ExtensionHook extensionHook) {
		super.hook(extensionHook);
		if (getView() != null) {
			extensionHook.getHookMenu().addToolsMenuItem(getMenuItemEncoder());
		}
	}
}
