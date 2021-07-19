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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import main.java.br.com.savio2503.Financas.tools.Card;
import main.java.br.com.savio2503.Financas.tools.Cost;
import main.java.br.com.savio2503.Financas.tools.SQLUtil;
import main.java.br.com.savio2503.Financas.util.DateLabelFormatter;
import main.java.br.com.savio2503.Financas.util.JMoneyFieldValor;

public class SeeFatura extends JFrame {

	private JPanel contentPane;
	private JLabel lblTitle, lblValor, lblDescricao;
	private JComboBox comboBox;
	private JButton btnPagar;
	private JMoneyFieldValor formattedValue;
	private JDatePickerImpl datePicker;
	
	private String[] nameCards = null;
	
	private List<Card> cards = null;
	
	private void setupNames() {
		
		cards = SQLUtil.getCards();
		
		if (cards.size() == 0) {
			nameCards = new String[] {"NAO HA CARTAO CADASTRADO"};
		} else {
			nameCards = new String[cards.size() + 1];
			
			nameCards[0] = "SELECIONE O CARTAO";
			
			for (int i = 0; i < cards.size(); i++) {
				String aux = cards.get(i).id + " - " + cards.get(i).nome;
				nameCards[i+1] = aux;
			}
		}
		
	}

	/**
	 * Create the frame.
	 */
	public SeeFatura() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() / 2) - (634 / 2));
		int y = (int) ((dimension.getHeight() / 2) - (187 / 2));
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(x, y, 634, 187);
//		setBounds(100, 100, 634, 187);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setupNames();
		
		lblTitle = new JLabel("FATURAS");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 11, 596, 14);
		contentPane.add(lblTitle);
		
		UtilDateModel model = new UtilDateModel();
		Properties properties = new Properties();
		properties.put("text.today", "Today");
		properties.put("text.month", "Month");
		properties.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(225, 51, 196, 30);
//		datePicker.getJFormattedTextField().setText(LocalDate.now().toString());
		getContentPane().add(datePicker);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(nameCards));
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				if (cards.size() > 0 && comboBox.getSelectedIndex() > 0 && !datePicker.getJFormattedTextField().getText().isBlank()) {
					
					buscarFatura();
				}
				
			}
		});
		comboBox.setBounds(10, 55, 203, 22);
		contentPane.add(comboBox);
		
		lblValor = new JLabel("VALOR:");
		lblValor.setBounds(433, 55, 46, 14);
		contentPane.add(lblValor);
		
		formattedValue = new JMoneyFieldValor();
		formattedValue.setHorizontalAlignment(SwingConstants.RIGHT);
		formattedValue.setBounds(483, 52, 101, 20);
		formattedValue.setEditable(false);
		getContentPane().add(formattedValue);
		
		btnPagar = new JButton("PAGAR");
		btnPagar.setBounds(362, 108, 89, 23);
		contentPane.add(btnPagar);
		
		lblDescricao = new JLabel("DESCRICAO");
		lblDescricao.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescricao.setBounds(10, 112, 334, 14);
		contentPane.add(lblDescricao);
	}
	
	private String buscarFatura() {
		
		Card card = cards.get(comboBox.getSelectedIndex() - 1);
		int diaFechamento = card.fechamento;
		int diaVencimento = card.vencimento;

		Calendar dtSelect = Calendar.getInstance();
		dtSelect.setTime((Date) datePicker.getModel().getValue());
		
		//data de agora
		Calendar calFechamento = Calendar.getInstance();
		calFechamento.set(Calendar.DAY_OF_MONTH, diaFechamento);
		
		Calendar calVencimento = Calendar.getInstance();
		calVencimento.set(Calendar.DAY_OF_MONTH, diaVencimento);
		
		try {
			
//			1 = depois, -1 = antes

			if (dtSelect.compareTo(calFechamento) == 1 && dtSelect.compareTo(calVencimento) == -1) { //entre o fechamento e vencimento
				
				Calendar monthPass = Calendar.getInstance();
				
				int month = calFechamento.get(Calendar.MONTH);
				
				
				if (month == 0) {
					
					int year = calFechamento.get(Calendar.YEAR);
					
					month = 11;
					year -= 1;
					
					monthPass.set(Calendar.MONTH, month);
					monthPass.set(Calendar.YEAR, year);
					
				} else {
					
					month -= 1;
					monthPass.set(Calendar.MONTH, month);
				}
				
				monthPass.set(Calendar.DAY_OF_MONTH, diaFechamento);
				
				java.sql.Date pass = new java.sql.Date(monthPass.getTimeInMillis());
				java.sql.Date fech = new java.sql.Date(calFechamento.getTimeInMillis());
				
				System.out.println("p: " + monthPass.getTime());
				System.out.println("n: " + calFechamento.getTime());
				
				List<Cost> costs = SQLUtil.getCostMonth(pass, fech);
				
				if (costs.size() == 0) {
					lblDescricao.setText("NAO HA CONTA PARA PAGAR NESSE MES");
				} else {
					
					Double valor = 0.0;
					
					for (Cost cost : costs) {
						valor += cost.valor;
					}
					
					lblDescricao.setText("VALOR A PAGAR: R$ " + valor);
					formattedValue.setText(String.format("%.2f", valor));
				}
				
			} else { //antes do fechamento
				lblDescricao.setText("FATURA AINDA NAO FOI FECHADA");
			} 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
		
	}
}
