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

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import main.java.br.com.savio2503.Financas.tools.Account;
import main.java.br.com.savio2503.Financas.tools.SQLUtil;
import main.java.br.com.savio2503.Financas.util.DateLabelFormatter;
import main.java.br.com.savio2503.Financas.util.JMoneyFieldValor;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.List;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;

import javax.swing.JTextField;

public class AddReceive extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 351485589830502346L;
	private JPanel contentPane;
	
	private String[] nameAccounts = null;
	private List<Account> accounts = null;
	private JTextField textDescricao;
	
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
		int x = (int) ((dimension.getWidth() / 2) - (570 / 2));
		int y = (int) ((dimension.getHeight() / 2) - (252 / 2));
		
		setupNames();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(x, y, 570, 252);
//		setBounds(100, 100, 570, 252);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("ADICIONAR DINHEIRO");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 11, 554, 14);
		contentPane.add(lblTitle);
		
		JLabel lblConta = new JLabel("SELECIONE A CONTA:");
		lblConta.setBounds(10, 56, 135, 14);
		contentPane.add(lblConta);
		
		JComboBox comboBoxConta = new JComboBox();
		comboBoxConta.setModel(new DefaultComboBoxModel(nameAccounts));
		comboBoxConta.setBounds(160, 52, 213, 22);
		contentPane.add(comboBoxConta);
		
		JLabel lblNewLabel = new JLabel("VALOR A SER ADICIONADO:");
		lblNewLabel.setBounds(10, 146, 180, 14);
		contentPane.add(lblNewLabel);

		JMoneyFieldValor formattedValue = new JMoneyFieldValor();
		formattedValue.setHorizontalAlignment(SwingConstants.RIGHT);
		formattedValue.setBounds(200, 143, 178, 20);
		getContentPane().add(formattedValue);
		
		UtilDateModel model = new UtilDateModel();
		Properties properties = new Properties();
		properties.put("text.today", "Today");
		properties.put("text.month", "Month");
		properties.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(400, 100, 139, 30);
		getContentPane().add(datePicker);
		
		JLabel lblDescricao = new JLabel("DESCRIÇÃO:");
		lblDescricao.setBounds(10, 103, 82, 14);
		contentPane.add(lblDescricao);
		
		textDescricao = new JTextField();
		textDescricao.setHorizontalAlignment(SwingConstants.CENTER);
		textDescricao.setBounds(98, 100, 260, 20);
		contentPane.add(textDescricao);
		textDescricao.setColumns(10);
		
		JLabel lblData = new JLabel("SELECIONE A DATA");
		lblData.setBounds(400, 72, 139, 16);
		contentPane.add(lblData);
		
		JButton btnAdicionar = new JButton("ADICIONAR");
		btnAdicionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int pos = comboBoxConta.getSelectedIndex();
				String descricao = textDescricao.getText();
				
				if (accounts.size() > 0 && pos > 0 && !descricao.isBlank() && !datePicker.getJFormattedTextField().getText().isBlank()) {
					Double valor = 0.0;
					Date data = Date.valueOf(datePicker.getJFormattedTextField().getText());
					
					if (!formattedValue.getText().isBlank()) {
						String aux = formattedValue.getText();
						
						aux = aux.replace(".", "");
						aux = aux.replace(",", ".");
						
						valor = Double.parseDouble(aux);
					}
					
					int id = accounts.get(comboBoxConta.getSelectedIndex() - 1).id;
					
					if (SQLUtil.addReceipt(id, descricao, valor, data)) {
						System.out.println("Adicionado com sucesso");
						comboBoxConta.setSelectedIndex(0);
						textDescricao.setText("");
						formattedValue.setText("0");
					} else {
						System.out.println("Erro ao incrementar");
					}
				}
			}
		});
		btnAdicionar.setBounds(221, 178, 100, 23);
		contentPane.add(btnAdicionar);
	}
}
