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

import java.util.List;
import java.util.Properties;

import javax.swing.JFrame;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import main.java.br.com.savio2503.Financas.tools.Account;
import main.java.br.com.savio2503.Financas.tools.Card;
import main.java.br.com.savio2503.Financas.tools.SQLUtil;
import main.java.br.com.savio2503.Financas.util.DateLabelFormatter;
import main.java.br.com.savio2503.Financas.util.JMoneyFieldValor;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.DecimalFormat;

import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

public class AddCost extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1262703599603442799L;
	private JTextField textFieldDescricao;
	private byte[] arquivo;
	
	private String[] nameCards = null;
	private String[] nameAccounts = null;
	
	private List<Card> cards       = null;
	private List<Account> accounts = null;
	
	private void setupNames() {
		
		cards    = SQLUtil.getCards();
		accounts = SQLUtil.getAccounts();
		
		if (cards.size() == 0) {
			nameCards = new String[] {"NAO HA CARTAO CADASTRADO"};
		} else {
			nameCards = new String[cards.size() + 1];
			
			nameCards[0] = "NAO ASSOCIAR";
			
			for (int i = 0; i < cards.size(); i++) {
				String aux = cards.get(i).id + " - " + cards.get(i).nome;
				nameCards[i+1] = aux;
			}
		}
		
		if (accounts.size() == 0) {
			nameAccounts = new String[] {"NAO HA CONTA CADASTRADA"};
		} else {
			nameAccounts = new String[accounts.size() + 1];
			
			nameAccounts[0] = "NAO ASSOCIAR";
			
			for (int i = 0; i < accounts.size(); i++) {
				String aux = accounts.get(i).id + " - " + accounts.get(i).nome;
				nameAccounts[i+1] = aux;
			}
		}
		
	}

	/**
	 * Create the frame.
	 */
	public AddCost() {
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() / 2)  - (500 / 2));
		int y = (int) ((dimension.getHeight() / 2) - (413 / 2));
		
		setupNames();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//setBounds(100, 100, 500, 413);
		setBounds(x, y, 500, 413);
		getContentPane().setLayout(null);
		
		JLabel lbTitle = new JLabel("ADICIONAR CUSTO");
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(12, 11, 456, 14);
		getContentPane().add(lbTitle);
		
		JLabel lblDescricao = new JLabel("DESCRI\u00C7\u00C3O:");
		lblDescricao.setBounds(10, 48, 108, 14);
		getContentPane().add(lblDescricao);
		
		textFieldDescricao = new JTextField();
		textFieldDescricao.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldDescricao.setBounds(118, 46, 320, 20);
		getContentPane().add(textFieldDescricao);
		textFieldDescricao.setColumns(10);
		
		JLabel lblValor = new JLabel("VALOR: R$");
		lblValor.setBounds(10, 99, 80, 14);
		getContentPane().add(lblValor);
		
		JLabel lblParcelas = new JLabel("QUANT. PARCELAS: ");
		lblParcelas.setBounds(293, 99, 123, 14);
		getContentPane().add(lblParcelas);
		
		JLabel lblData = new JLabel("DATA:");
		lblData.setBounds(10, 140, 46, 14);
		getContentPane().add(lblData);
		
		UtilDateModel model = new UtilDateModel();
		Properties properties = new Properties();
		properties.put("text.today", "Today");
		properties.put("text.month", "Month");
		properties.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(60, 139, 139, 30);
		getContentPane().add(datePicker);
		
		JLabel lblComprovante = new JLabel("ADD COMPROVANTE:");
		lblComprovante.setBounds(10, 185, 164, 14);
		getContentPane().add(lblComprovante);
		
		JLabel lblConta = new JLabel("ASSOCIAR A UMA CONTA:");
		lblConta.setBounds(10, 243, 248, 14);
		getContentPane().add(lblConta);
		
		JComboBox comboBoxParcelas = new JComboBox();
		comboBoxParcelas.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		comboBoxParcelas.setSelectedIndex(0);
		comboBoxParcelas.setBounds(418, 95, 50, 22);
		getContentPane().add(comboBoxParcelas);
		
		JMoneyFieldValor formattedValue = new JMoneyFieldValor();
		formattedValue.setHorizontalAlignment(SwingConstants.RIGHT);
		formattedValue.setBounds(95,96,130,20);
		getContentPane().add(formattedValue);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("ASSINATURA?");
		chckbxNewCheckBox.setBounds(359, 136, 129, 23);
		getContentPane().add(chckbxNewCheckBox);

		JComboBox comboBoxCard = new JComboBox();
		JComboBox comboBoxAccount = new JComboBox();
		
		JButton btnAdd = new JButton("ADICIONAR");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//check components
				String descricao = textFieldDescricao.getText();
				// comboBoxParcelas
				int parcelas = Integer.parseInt((String) comboBoxParcelas.getSelectedItem());
				// datePanel
				String dataText = datePicker.getJFormattedTextField().getText();
				
				int conta = 0, cartao = 0;
				
				if (cards.size() > 0 && comboBoxCard.getSelectedIndex() > 0) {
					cartao = cards.get(comboBoxCard.getSelectedIndex() - 1).id;
				}
				
				if (accounts.size() > 0 && comboBoxAccount.getSelectedIndex() > 0) {
					conta = accounts.get(comboBoxAccount.getSelectedIndex() - 1).id;
				}
				
				Double valor = 0.0;
				
				if (!formattedValue.getText().isBlank()) {
					String aux = formattedValue.getText();
					
					aux = aux.replace(".", "");
					aux = aux.replace(",", ".");
					
					valor = Double.parseDouble(aux);
				}
				
				if (!descricao.isBlank() && valor > 0 && !dataText.isBlank() && (conta > 0 || cartao > 0)) {
					
					Date data = Date.valueOf(dataText);
					
					if (saveCost(descricao, valor, parcelas, data, conta, cartao, chckbxNewCheckBox.isSelected()))
						System.out.println("adicionada com sucesso");
				}
				else {
					System.out.println("alguem esta faltando");
				}
				
				System.out.println("des " + descricao + ", value " + valor + ", parcelas " + parcelas + ", data: " + dataText);
				
			}
		});
		btnAdd.setBounds(170, 330, 123, 23);
		getContentPane().add(btnAdd);
		
		JButton btnAnexo = new JButton("SELECIONAR ARQUIVO");
		btnAnexo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String nameFile = getFile();
				
				if (nameFile != null) {
					btnAnexo.setText(nameFile);
				}
			}
		});
		btnAnexo.setBounds(171, 180, 193, 23);
		getContentPane().add(btnAnexo);
		
		JLabel lblCard = new JLabel("ASSOCIAR A UM CART\u00C3O:");
		lblCard.setBounds(10, 290, 236, 14);
		getContentPane().add(lblCard);
		
		
		comboBoxCard.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				if (cards.size() > 0 && comboBoxCard.getSelectedIndex() > 0) {

					comboBoxAccount.setSelectedIndex(0);
				}
				
			}
		});
		comboBoxCard.setModel(new DefaultComboBoxModel(nameCards));
		comboBoxCard.setBounds(234, 286, 236, 22);
		getContentPane().add(comboBoxCard);
		
		
		comboBoxAccount.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				if (accounts.size() > 0 && comboBoxAccount.getSelectedIndex() > 0) {
					
					comboBoxCard.setSelectedIndex(0);
				}
				
			}
		});
		comboBoxAccount.setModel(new DefaultComboBoxModel(nameAccounts));
		comboBoxAccount.setBounds(234, 239, 234, 22);
		getContentPane().add(comboBoxAccount);
	}
	
	private boolean saveCost(String descricao, double valor, int parcelas, Date data, int conta, int cartao, boolean assinatura) {
		
		boolean result = false;
		
		if (parcelas > 1) {
			if (cartao > 0) {
				
				DecimalFormat format = new DecimalFormat("##.00");
				Double valueDivider = valor / parcelas;
				Double valueParcelas = Double.valueOf(format.format(valueDivider).replace(',', '.'));
				
				for (int i = 1; i <= parcelas; i++) {
					
					Date dateAux = processDateParcelas(data, i-1);
					
					System.out.println("month -> " + dateAux.getMonth());
					
					result = SQLUtil.addCost(descricao, valueParcelas, i, dateAux, arquivo, 0, cartao, false);
				}
				
			} else {
				System.out.println("parcelas somente para cartoes");
			}
		} else {
			result = SQLUtil.addCost(descricao, valor, parcelas, data, arquivo, conta, cartao, assinatura);
		}

		return result;
	}
	
	private Date processDateParcelas(Date in, int mesAFrente) {
		
		Long dateProxLong = in.getTime() + (new Long("2678400000") * mesAFrente);
		
		Date resposta = new Date(dateProxLong);
		
		return resposta;
	}
	
	private String getFile() {
		
		String nameFile = null;
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new java.io.File("."));
		fileChooser.setDialogTitle("selecionar");
		
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): " +  fileChooser.getCurrentDirectory());
			System.out.println("getSelectedFile() : " +  fileChooser.getSelectedFile());
			System.out.println("getNameFile() : " +  fileChooser.getSelectedFile().getName());
			
			nameFile = fileChooser.getSelectedFile().getName();
			
			try {
				arquivo = Files.readAllBytes(Paths.get(fileChooser.getSelectedFile().toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return nameFile;
	}
}
