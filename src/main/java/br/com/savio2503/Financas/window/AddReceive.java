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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import main.java.br.com.savio2503.Financas.tools.Account;
import main.java.br.com.savio2503.Financas.tools.SQLUtil;
import main.java.br.com.savio2503.Financas.util.JMoneyFieldValor;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddReceive extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 351485589830502346L;
	private JPanel contentPane;
	
	private String[] nameAccounts = null;
	private List<Account> accounts = null;
	
	private void setupNames() {
		
		accounts = SQLUtil.getAccounts();
		
		if (accounts.size() == 0) {
			nameAccounts = new String[] {"NAO HA CONTA CADASTRADA"};
		} else {
			nameAccounts = new String[accounts.size() + 1];
			
			nameAccounts[0] = "CONTA NAO SELECIONADA";
			
			for (int i = 0; i < accounts.size(); i++) {
				String aux = accounts.get(i).id + " - " + accounts.get(i).nome;
				nameAccounts[i+1] = aux;
			}
		}
		
	}

	/**
	 * Create the frame.
	 */
	public AddReceive() {
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() / 2) - (384 / 2));
		int y = (int) ((dimension.getHeight() / 2) - (252 / 2));
		
		setupNames();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//setBounds(x, y, 384, 252);
		setBounds(0, 0, 384, 252);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("ADICIONAR DINHEIRO");
		lblTitle.setBounds(140, 11, 200, 14);
		contentPane.add(lblTitle);
		
		JLabel lblConta = new JLabel("SELECIONE A CONTA:");
		lblConta.setBounds(10, 56, 125, 14);
		contentPane.add(lblConta);
		
		JComboBox comboBoxConta = new JComboBox();
		comboBoxConta.setModel(new DefaultComboBoxModel(nameAccounts));
		comboBoxConta.setBounds(145, 52, 200, 22);
		contentPane.add(comboBoxConta);
		
		JLabel lblNewLabel = new JLabel("VALOR A SER ADICIONADO:");
		lblNewLabel.setBounds(10, 115, 160, 14);
		contentPane.add(lblNewLabel);

		JMoneyFieldValor formattedValue = new JMoneyFieldValor();
		formattedValue.setHorizontalAlignment(SwingConstants.RIGHT);
		formattedValue.setBounds(180, 112, 160, 20);
		getContentPane().add(formattedValue);
		
		JButton btnAdicionar = new JButton("ADICIONAR");
		btnAdicionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int pos = comboBoxConta.getSelectedIndex();
				
				if (accounts.size() > 0 && pos > 0) {
					Double valor = 0.0;
					
					if (!formattedValue.getText().isBlank()) {
						String aux = formattedValue.getText();
						
						aux = aux.replace(".", "");
						aux = aux.replace(",", ".");
						
						valor = Double.parseDouble(aux);
					}
					
					int id = accounts.get(comboBoxConta.getSelectedIndex() - 1).id;
					
					if (SQLUtil.addReceipt(id, valor)) {
						System.out.println("Adicionado com sucesso");
					} else {
						System.out.println("Erro ao incrementar");
					}
				}
			}
		});
		btnAdicionar.setBounds(112, 174, 100, 23);
		contentPane.add(btnAdicionar);
	}
}
