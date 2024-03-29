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
package org.parosproxy.paros.core.scanner.plugin;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.parosproxy.paros.core.scanner.AbstractAppParamPlugin;
import org.parosproxy.paros.core.scanner.Alert;
import org.parosproxy.paros.core.scanner.Category;
import org.parosproxy.paros.network.HttpMessage;

/**
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class TestMiscInjectionCRLF extends AbstractAppParamPlugin {

	private static Random staticRandomGenerator = new Random();
	private String randomString = "Tamper=" + Long.toString(Math.abs(staticRandomGenerator.nextLong()));
	private String cookieTamper1 = "Set-cookie: " + randomString;
	private String cookieTamper2a = "any\r\nSet-cookie: " + randomString;
	private String cookieTamper2b = "any?\r\nSet-cookie: " + randomString;
	private String cookieTamper3a = "any\nSet-cookie: " + randomString;
	private String cookieTamper3b = "any?\nSet-cookie: " + randomString;
	private String cookieTamper4a = "any\r\nSet-cookie: " + randomString + "\r\n";
	private String cookieTamper4b = "any?\r\nSet-cookie: " + randomString + "\r\n";

	// should not be changed to static as Global may not be ready
	private String[] PARAM_LIST = { cookieTamper1, cookieTamper2a,
			cookieTamper2b, cookieTamper3a, cookieTamper3b, cookieTamper4a,
			cookieTamper4b };

	private Pattern patternCookieTamper = Pattern.compile("\\nSet-cookie: "
			+ randomString, PATTERN_PARAM);

	public int getId() {
		return 60000;
	}

	public String getName() {
		return "CRLF Injection";
	}

	public String[] getDependency() {
		return null;
	}

	public String getDescription() {
		String msg = "Cookie can be set via CRLF injection. It may also be possible to set arbitrary "
			+ "HTTP response header. In addition, by carefully crafting the injected response using "
			+ "cross-site scripting, a cache poisoning vulnerability may also exist.";
		return msg;
	}

	public int getCategory() {
		return Category.MISC;
	}

	public String getSolution() {
		String msg = "Type check the submitted parameter carefully. Do not allow CRLF to be injected "
			+ "by filtering CRLF.";
		return msg;
	}

	public String getReference() {
		String msg = "<ul><li>http://www.watchfire.com/resources/HTTPResponseSplitting.pdf</li>"
				+ "<li>http://www.securityfocus.com/bid/9804</li></ul>";
		return msg;
	}

	public void init() {

	}

	public void scan(HttpMessage msg, String param, String value) {
		String bingoQuery = null;

		// loop parameters
		for (int i = 0; i < PARAM_LIST.length; i++) {
			msg = getNewMsg();
			bingoQuery = setParameter(msg, param, PARAM_LIST[i]);
			try {
				sendAndReceive(msg, false);
				if (checkResult(msg, bingoQuery)) {
					return;
				}

			} catch (Exception e) {
				
			}

		}

	}

	private boolean checkResult(HttpMessage msg, String query) {
		Matcher matcher = patternCookieTamper.matcher(msg.getResponseHeader().toString());
		if (matcher.find()) {
			bingo(Alert.RISK_MEDIUM, Alert.WARNING, null, query, "", msg);
			return true;
		}

		return false;
	}

}
