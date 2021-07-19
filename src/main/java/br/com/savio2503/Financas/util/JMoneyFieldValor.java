/*-
 * =====LICENSE-START=====
 * Java 11 Application
 * ------
 * Copyright (C) 2020 - 2021 Organization Name
 * ------
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * =====LICENSE-END=====
 */
package main.java.br.com.savio2503.Financas.util;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.SimpleAttributeSet;

public class JMoneyFieldValor extends JFormattedTextField {
	
    private static final long serialVersionUID = -5712106034509737967L;   
    private static final SimpleAttributeSet nullAttribute = new SimpleAttributeSet(); 
    
    
    public JMoneyFieldValor() {
    	this.setHorizontalAlignment(JTextField.CENTER);
    	this.setDocument(new MoneyFieldDocument());
    	this.addFocusListener(new MoneyFieldFocusListener());
    	this.setText("0,00");
    	this.addCaretListener(new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
				// TODO Auto-generated method stub
				if (e.getDot() != getText().length()) {
					getCaret().setDot(getText().length());
				}
			}
		});
    }
    
    private final class MoneyFieldFocusListener extends FocusAdapter {
    	public void focusGained(FocusEvent e) {
    		selectAll();
    	}
    }
    
    private final class MoneyFieldDocument extends PlainDocument {
    	private static final long serialVersionUID = -3802846632709128803L; 
    	
    	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
    		
    		String original = getText(0, getLength());
    		
    		if (original.length() < 16) {
    			
    			StringBuffer mascarado = new StringBuffer();
    			
    			if (a != nullAttribute) {
    				
    				remove(-1, getLength());
    				
    				mascarado.append( removeLeftZero( (original+str).replaceAll("[^0-9]", "")));
    				
    				for (int i = 0; i < mascarado.length(); i++) {
    					
    					if (!Character.isDigit(mascarado.charAt(i))) {
    						
    						mascarado.deleteCharAt(i);
    					}
    				}
    				
    				if (mascarado.length() < 3) {
    					if (mascarado.length() == 0) {
    						mascarado.insert(0,"0");
    						mascarado.insert(0,"0");
                            mascarado.insert(0,",");   
                            mascarado.insert(0,"0");
    					}
    					else if (mascarado.length() == 1) {
                            mascarado.insert(0,"0");   
                            mascarado.insert(0,",");   
                            mascarado.insert(0,"0");
    					}
    					else if (mascarado.length() == 2) {
    						mascarado.insert(0, ",");
    						mascarado.insert(0, "0");
    					}
    				} else {
    					mascarado.insert(mascarado.length() - 2, ",");
    				}
    				
    				if (mascarado.length() > 6) {
    					mascarado.insert(mascarado.length() - 6, '.');
    					
    					if (mascarado.length() > 10) {
    						
    						mascarado.insert(mascarado.length() - 10, '.');
    						
    						if (mascarado.length() > 14) {
    							
    							mascarado.insert(mascarado.length() - 14, '.');
    						}
    					}
    				}
    				
    				super.insertString(0, mascarado.toString(), a);
    			}
    			else {
    				
    				if (original.length() == 0) {
    					
    					super.insertString(0, "0,00", a);
    				}
    			}
    		}
    	}
    	
    	@Override
    	public void remove(int offs, int len) throws BadLocationException {
    		// TODO Auto-generated method stub
    		if (len == getLength()) {
    			super.remove(0,  len);
    			
    			if (offs != -1) {
    				insertString(0, "", nullAttribute);
    			}
    		}
    		else {
    			String original = getText(0, getLength()).substring(0, offs) + getText(0, getLength()).substring(offs + len);
    			super.remove(0, getLength());
    			insertString(0, original, null);
    		}
    	}
    	
    	private String removeLeftZero(String str) {
    		
    		int i = 0;
    		
    		while (i < str.length() && str.charAt(i) == '0') i++;
    		
    		StringBuffer sb = new StringBuffer(str);
    		
    		sb.replace(0, i, "");
    		
    		return sb.toString();
    	}
    }
}
