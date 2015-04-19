/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.create.handler;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class TableNameLimit extends PlainDocument {
			private int limit;
			// optional uppercase conversion
			private boolean toUppercase = false;

			public TableNameLimit(int limit) {
				super();
				this.limit = limit;
			}

			TableNameLimit(int limit, boolean upper) {
				super();
				this.limit = limit;
				toUppercase = upper;
			}

			@Override
			public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
				if (str == null)
					return;

				if ((getLength() + str.length()) <= limit) {
					if (toUppercase)
						str = str.toUpperCase();
					super.insertString(offset, str, attr);
				}
			}
		}