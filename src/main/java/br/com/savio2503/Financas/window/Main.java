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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.java.br.com.savio2503.Financas.tools.SQLUtil;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.time.LocalDateTime;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Color;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5091151183853352101L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public Main() {
		
		JLabel lblValor = new JLabel("Valor Total: R$ ");
		
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				getTotal(lblValor);
			}
		});
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() / 2) - (500 / 2));
		int y = (int) ((dimension.getHeight() / 2) - (404 / 2));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(x, y, 500, 404);
//		setBounds(100, 100, 500, 404);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		lblValor.setHorizontalAlignment(SwingConstants.CENTER);
		lblValor.setBounds(20, 20, 460, 14);
		contentPane.add(lblValor);
		
		getTotal(lblValor);
		
		JButton btnCusto = new JButton("ADICIONAR CUSTO");
		btnCusto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddCost frame = new AddCost();
				frame.setVisible(true);
			}
		});
		
		JLabel lblCustoMes = new JLabel("Custo no m\u00EAs: R$ ");
		lblCustoMes.setHorizontalAlignment(SwingConstants.CENTER);
		lblCustoMes.setBounds(20, 38, 460, 16);
		contentPane.add(lblCustoMes);
		
		getCustoMes(lblCustoMes);
		
		JLabel lblPrevisto = new JLabel("Valor Previsto R$: ");
		lblPrevisto.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrevisto.setBounds(20, 60, 460, 16);
		contentPane.add(lblPrevisto);
		
		getPrevisto(lblPrevisto);
		btnCusto.setBounds(264, 97, 216, 23);
		contentPane.add(btnCusto);
		
		JButton btnCard = new JButton("ADICIONAR CART\u00C3O");
		btnCard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddCard frame = new AddCard();
				frame.setVisible(true);
			}
		});
		btnCard.setBounds(22, 97, 216, 23);
		contentPane.add(btnCard);
		
		JButton btnAccount = new JButton("ADICIONAR CONTA");
		btnAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddAccount frame = new AddAccount();
				frame.setVisible(true);
			}
		});
		btnAccount.setBounds(22, 131, 216, 23);
		contentPane.add(btnAccount);
		
		JButton btnRecebimento = new JButton("ADICIONAR RECEBIMENTO");
		btnRecebimento.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddReceive frame = new AddReceive();
				frame.setVisible(true);
			}
		});
		btnRecebimento.setBounds(22, 165, 216, 23);
		contentPane.add(btnRecebimento);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBounds(22, 233, 216, 2);
		contentPane.add(separator);
		
		JButton btnModConta = new JButton("CONTA");
		btnModConta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ModConta frame = new ModConta();
				frame.setVisible(true);
			}
		});
		btnModConta.setBounds(21, 265, 217, 23);
		contentPane.add(btnModConta);
		
		JButton btnModCard = new JButton("CART\u00C3O");
		btnModCard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ModCard frame = new ModCard();
				frame.setVisible(true);
			}
		});
		btnModCard.setBounds(22, 299, 216, 23);
		contentPane.add(btnModCard);
		
		JButton btnModCusto = new JButton("CUSTO");
		btnModCusto.setBounds(21, 333, 217, 23);
		contentPane.add(btnModCusto);
		
		JLabel lblModificar = new JLabel("MODIFICAR");
		lblModificar.setHorizontalAlignment(SwingConstants.CENTER);
		lblModificar.setBounds(21, 240, 217, 14);
		contentPane.add(lblModificar);
		
		JButton btnTransferencia = new JButton("TRANSFERIR");
		btnTransferencia.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Transfer frame = new Transfer();
				frame.setVisible(true);
			}
		});
		btnTransferencia.setBounds(22, 199, 216, 23);
		contentPane.add(btnTransferencia);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(250, 97, 5, 260);
		contentPane.add(separator_1);
		
		JButton btnCustoMes = new JButton("VER CUSTOS");
		btnCustoMes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SeeCost frame = new SeeCost();
				frame.setVisible(true);
			}
		});
		btnCustoMes.setBounds(264, 129, 216, 26);
		contentPane.add(btnCustoMes);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(264, 165, 216, 2);
		contentPane.add(separator_2);
		
		JButton btnPagarFatura = new JButton("PAGAR FATURA");
		btnPagarFatura.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SeeFatura frame = new SeeFatura();
				frame.setVisible(true);
			}
		});
		btnPagarFatura.setBounds(264, 178, 216, 26);
		contentPane.add(btnPagarFatura);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(264, 254, 216, 2);
		contentPane.add(separator_3);
		
		JButton btnCompras = new JButton("COMPRAS FUTURAS");
		btnCompras.setBounds(264, 263, 216, 26);
		contentPane.add(btnCompras);
		
		JLabel lblObjetivos = new JLabel("OBJETIVOS");
		lblObjetivos.setHorizontalAlignment(SwingConstants.CENTER);
		lblObjetivos.setBounds(264, 233, 216, 16);
		contentPane.add(lblObjetivos);
		
		JButton btnInvestimentos = new JButton("INVESTIMENTOS");
		btnInvestimentos.setBounds(264, 296, 216, 29);
		contentPane.add(btnInvestimentos);
	}
	
	private double total, custoMes;
	
	private void getCustoMes(JLabel label) {
		//2021-06-26
		LocalDateTime now = LocalDateTime.now();
		
		int year = now.getYear();
		int month = now.getMonthValue();
		
		String datain = String.format("%d-%d-01", year, month);
		String dataout;
		if (month == 12) {
			dataout = String.format("%d-01-01", year+1);
		} else {
			dataout = String.format("%d-%d-01", year, month+1);
		}
		
		Date dtIn = Date.valueOf(datain);
		Date dtOut = Date.valueOf(dataout);
		
		custoMes = SQLUtil.getTotalValueCostMonth(dtIn, dtOut) + SQLUtil.getSumSignature();
		label.setText(String.format("%s %.2f", label.getText(), custoMes));
	}
	
	private void getTotal(JLabel label) {
		
		total = SQLUtil.getTotalValueAccounts();
		
		label.setText(String.format("%s %.2f", label.getText(), total));
	}
	
	private void getPrevisto (JLabel label) {
		label.setText(String.format("%s %.2f", label.getText(), total - custoMes));
	}
}
