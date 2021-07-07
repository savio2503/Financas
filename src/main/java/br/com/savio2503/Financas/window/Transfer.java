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
package main.java.br.com.savio2503.Financas.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.br.com.savio2503.Financas.tools.Account;
import main.java.br.com.savio2503.Financas.tools.SQLUtil;
import main.java.br.com.savio2503.Financas.util.JMoneyFieldValor;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Transfer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1618360718272302188L;
	private JPanel contentPane;
	private String[] nameAccounts = null;
	private List<Account> accounts = null;
	
	private void setupNames() {
		
		accounts = SQLUtil.getAccounts();
		
		if (accounts.size() > 0) {
			
			nameAccounts = new String[accounts.size()+1];
			
			nameAccounts[0] = "SELECIONE A CONTA";
			
			for (int i = 0; i < accounts.size(); i++) {
				
				nameAccounts[i+1] = accounts.get(i).id + " - " + accounts.get(i).nome;
			}
		} else {
			
			nameAccounts = new String[]{"NAO HA CONTA"};
		}
	}

	/**
	 * Create the frame.
	 */
	public Transfer() {
		
		setupNames();
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() / 2) - (277 / 2));
		int y = (int) ((dimension.getHeight() / 2) - (417 / 2));
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(x, y, 300, 270);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("TRANSFERIR DINHEIRO");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 11, 260, 14);
		contentPane.add(lblTitle);
		
		JLabel lblFonte = new JLabel("FONTE:");
		lblFonte.setBounds(10, 53, 60, 14);
		contentPane.add(lblFonte);
		
		JLabel lblDestino = new JLabel("DESTINO:");
		lblDestino.setBounds(10, 100, 60, 14);
		contentPane.add(lblDestino);
		
		JComboBox CBFonte = new JComboBox();
		CBFonte.setModel(new DefaultComboBoxModel(nameAccounts));
		CBFonte.setBounds(80, 48, 190, 25);
		contentPane.add(CBFonte);
		
		JComboBox CBDestino = new JComboBox();
		CBDestino.setModel(new DefaultComboBoxModel(nameAccounts));
		CBDestino.setBounds(80, 95, 190, 25);
		contentPane.add(CBDestino);
		
		JLabel lblValor = new JLabel("VALOR:");
		lblValor.setBounds(10, 149, 60, 16);
		contentPane.add(lblValor);
		
		JMoneyFieldValor formattedValue = new JMoneyFieldValor();
		formattedValue.setHorizontalAlignment(SwingConstants.RIGHT);
		formattedValue.setBounds(80, 145, 190, 20);
		getContentPane().add(formattedValue);
		
		JButton btnTransferir = new JButton("TRANSFERIR");
		btnTransferir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (accounts.size() > 0) {
					
					int idFonte = CBFonte.getSelectedIndex();
					int idDesti = CBDestino.getSelectedIndex();
					
					if (idFonte == 0 || idDesti == 0) {
						System.out.println("Selecione as contas");
					} else if (idFonte == idDesti) {
						System.out.println("Selecione contas diferentes");
					} else {
						
						Double valor = 0.0;
						
						if (!formattedValue.getText().isBlank()) {
							String aux = formattedValue.getText();
							
							aux = aux.replace(".", "");
							aux = aux.replace(",", ".");
							
							valor = Double.parseDouble(aux);
						}
						
						if (SQLUtil.transferMoney(idFonte, idDesti, valor)) {
							System.out.println("Transferencia realizada com sucesso");
						} else {
							System.out.println("problemas no sql");
						}
					}
				}
			}
		});
		btnTransferir.setBounds(87, 193, 120, 26);
		contentPane.add(btnTransferir);
	}
}
