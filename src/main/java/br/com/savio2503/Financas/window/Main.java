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
		int x = (int) ((dimension.getWidth() / 2) - (455 / 2));
		int y = (int) ((dimension.getHeight() / 2) - (374 / 2));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(x, y, 455, 374);
		setBounds(100, 100, 433, 404);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		lblValor.setHorizontalAlignment(SwingConstants.CENTER);
		lblValor.setBounds(11, 13, 182, 14);
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
		btnCusto.setBounds(221, 79, 182, 23);
		contentPane.add(btnCusto);
		
		JButton btnCard = new JButton("ADICIONAR CART\u00C3O");
		btnCard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddCard frame = new AddCard();
				frame.setVisible(true);
			}
		});
		btnCard.setBounds(11, 79, 182, 23);
		contentPane.add(btnCard);
		
		JButton btnAccount = new JButton("ADICIONAR CONTA");
		btnAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddAccount frame = new AddAccount();
				frame.setVisible(true);
			}
		});
		btnAccount.setBounds(11, 113, 182, 23);
		contentPane.add(btnAccount);
		
		JButton btnRecebimento = new JButton("ADICIONAR RECEBIMENTO");
		btnRecebimento.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddReceive frame = new AddReceive();
				frame.setVisible(true);
			}
		});
		btnRecebimento.setBounds(11, 147, 182, 23);
		contentPane.add(btnRecebimento);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(11, 215, 182, 2);
		contentPane.add(separator);
		
		JButton btnModConta = new JButton("CONTA");
		btnModConta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ModConta frame = new ModConta();
				frame.setVisible(true);
			}
		});
		btnModConta.setBounds(10, 247, 182, 23);
		contentPane.add(btnModConta);
		
		JButton btnModCard = new JButton("CART\u00C3O");
		btnModCard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ModCard frame = new ModCard();
				frame.setVisible(true);
			}
		});
		btnModCard.setBounds(11, 281, 182, 23);
		contentPane.add(btnModCard);
		
		JButton btnModCusto = new JButton("CUSTO");
		btnModCusto.setBounds(10, 315, 182, 23);
		contentPane.add(btnModCusto);
		
		JLabel lblNewLabel = new JLabel("MODIFICAR");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 222, 182, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnTransferencia = new JButton("TRANSFERIR");
		btnTransferencia.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Transfer frame = new Transfer();
				frame.setVisible(true);
			}
		});
		btnTransferencia.setBounds(11, 181, 182, 23);
		contentPane.add(btnTransferencia);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(210, 79, 2, 260);
		contentPane.add(separator_1);
		
		JButton btnCustoMes = new JButton("VER CUSTOS");
		btnCustoMes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SeeCost frame = new SeeCost();
				frame.setVisible(true);
			}
		});
		btnCustoMes.setBounds(221, 113, 182, 26);
		contentPane.add(btnCustoMes);
		
		JLabel lblCustoMes = new JLabel("Custo no m\u00EAs: R$ ");
		lblCustoMes.setHorizontalAlignment(SwingConstants.CENTER);
		lblCustoMes.setBounds(221, 23, 182, 16);
		contentPane.add(lblCustoMes);
		
		getCustoMes(lblCustoMes);
		
		JLabel lblPrevisto = new JLabel("Valor Previsto R$: ");
		lblPrevisto.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrevisto.setBounds(21, 38, 172, 16);
		contentPane.add(lblPrevisto);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(221, 147, 182, 2);
		contentPane.add(separator_2);
		
		JButton btnPagarFatura = new JButton("PAGAR FATURA");
		btnPagarFatura.setBounds(221, 160, 182, 26);
		contentPane.add(btnPagarFatura);
		
		getPrevisto(lblPrevisto);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(221, 236, 182, 2);
		contentPane.add(separator_3);
		
		JButton btnTarget = new JButton("OBJETIVOS");
		btnTarget.setBounds(221, 247, 182, 26);
		contentPane.add(btnTarget);
		
		JButton btnFuture = new JButton("FATURA FUTURAS");
		btnFuture.setBounds(221, 198, 182, 26);
		contentPane.add(btnFuture);
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
		
		custoMes = SQLUtil.getTotalValueCostMonth(dtIn, dtOut);
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
