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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.br.com.savio2503.Financas.tools.SQLUtil;
import main.java.br.com.savio2503.Financas.util.JMoneyFieldValor;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddCard extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AddCard() {
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() / 2) - (464 / 2));
		int y = (int) ((dimension.getHeight() / 2) - (217 / 2));
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(x, y, 464, 217);
//		setBounds(100, 100, 464, 217);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ADICIONAR CART\u00C3O");
		lblNewLabel.setBounds(190, 11, 150, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNome = new JLabel("NOME:");
		lblNome.setBounds(10, 44, 46, 14);
		contentPane.add(lblNome);
		
		JLabel lblLimite = new JLabel("LIMITE:");
		lblLimite.setBounds(233, 44, 46, 14);
		contentPane.add(lblLimite);
		
		JLabel lblVencimento = new JLabel("VENCIMENTO:");
		lblVencimento.setBounds(10, 100, 100, 14);
		contentPane.add(lblVencimento);
		
		JLabel lblFechamento = new JLabel("FECHAMENTO:");
		lblFechamento.setBounds(233, 100, 107, 14);
		contentPane.add(lblFechamento);
		
		JTextField textName = new JTextField();
		textName.setHorizontalAlignment(SwingConstants.CENTER);
		textName.setBounds(60, 41, 150, 20);
		contentPane.add(textName);
		textName.setColumns(10);
		
		JMoneyFieldValor formattedValue = new JMoneyFieldValor();
		formattedValue.setHorizontalAlignment(SwingConstants.RIGHT);
		formattedValue.setBounds(289, 41, 150, 20);
		getContentPane().add(formattedValue);
		
		JComboBox comboBoxVencimento = new JComboBox();
		comboBoxVencimento.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"}));
		comboBoxVencimento.setBounds(122, 97, 72, 22);
		contentPane.add(comboBoxVencimento);
		
		JComboBox comboBoxFechamento = new JComboBox();
		comboBoxFechamento.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"}));
		comboBoxFechamento.setBounds(352, 97, 72, 22);
		contentPane.add(comboBoxFechamento);
		
		JButton btnAdicionar = new JButton("ADICIONAR");
		btnAdicionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String nome = textName.getText();
				int fechamento = Integer.parseInt((String) comboBoxVencimento.getSelectedItem());
				int vencimento = Integer.parseInt((String) comboBoxFechamento.getSelectedItem());
				
				Double limite = 0.0;
				
				if (!formattedValue.getText().isBlank()) {
					String aux = formattedValue.getText();
					
					aux = aux.replace(".", "");
					aux = aux.replace(",", ".");
					
					limite = Double.parseDouble(aux);
				}
				
				if (!nome.isBlank() && SQLUtil.addCard(nome, limite, vencimento, fechamento)) {
					System.out.println("inserido com sucesso");
				}
			}
		});
		btnAdicionar.setBounds(179, 145, 100, 23);
		contentPane.add(btnAdicionar);
	}
}
