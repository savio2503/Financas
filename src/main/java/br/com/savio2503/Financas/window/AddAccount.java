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
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddAccount extends JFrame {

	private JPanel contentPane;
	private JTextField textNome;
	private JTextField textValor;

	/**
	 * Create the frame.
	 */
	public AddAccount() {
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() / 2) - (349 / 2));
		int y = (int) ((dimension.getHeight() / 2) - (237 / 2));
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(x, y, 349, 237);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ADICIONAR BANCO");
		lblNewLabel.setBounds(120, 11, 110, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNome = new JLabel("NOME:");
		lblNome.setBounds(10, 50, 46, 14);
		contentPane.add(lblNome);
		
		JLabel lblValor = new JLabel("VALOR ATUAL:      R$");
		lblValor.setBounds(10, 90, 132, 14);
		contentPane.add(lblValor);
		
		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.setBounds(185, 156, 100, 23);
		contentPane.add(btnCancelar);
		
		textNome = new JTextField();
		textNome.setBounds(56, 50, 250, 20);
		contentPane.add(textNome);
		textNome.setColumns(10);

		JMoneyFieldValor formattedValue = new JMoneyFieldValor();
		formattedValue.setHorizontalAlignment(SwingConstants.RIGHT);
		formattedValue.setBounds(131, 87, 175, 20);
		getContentPane().add(formattedValue);
		
		JButton btnAdicionar = new JButton("ADICIONAR");
		btnAdicionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String nome = textNome.getText();
				Double valor = 0.0;
				
				if (!formattedValue.getText().isBlank()) {
					String aux = formattedValue.getText();
					
					aux = aux.replace(".", "");
					aux = aux.replace(",", ".");
					
					valor = Double.parseDouble(aux);
				}
				
				if (!nome.isBlank()) {
					if (SQLUtil.addAccount(nome, valor)) {
						System.out.println("Conta adicionada com sucesso");
					}
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "NOME NAO ESTA PREENCHIDO", "Dialog", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAdicionar.setBounds(35, 156, 107, 23);
		contentPane.add(btnAdicionar);
	}
}
