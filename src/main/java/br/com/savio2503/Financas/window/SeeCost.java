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

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import main.java.br.com.savio2503.Financas.tools.Cost;
import main.java.br.com.savio2503.Financas.tools.SQLUtil;
import main.java.br.com.savio2503.Financas.tools.Signature;
import main.java.br.com.savio2503.Financas.util.DateLabelFormatter;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.beans.PropertyChangeListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDateTime;
import java.beans.PropertyChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class SeeCost extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3509162996247030486L;
	private JPanel contentPane;
	
	private int monthNow, yearNow;
	private List<Cost> custos = new ArrayList<Cost>();
	private List<Signature> signatures = SQLUtil.getAllSignature();
	private JTable table;
	JScrollPane scrollPane;
	private String columnNames[] = {"NUMERO",
						            "DESCRICAO",
						            "PARCELAS",
						            "PAGO",
						            "ASSINATURA"};
	
	private void setup() {
		LocalDateTime now = LocalDateTime.now();
		
		monthNow = now.getMonthValue();
		yearNow  = now.getYear();
		
		//------
		String datain = String.format("%d-%d-01", yearNow, monthNow);
		String dataout;
		if (monthNow == 12) {
			dataout = String.format("%d-01-01", yearNow+1);
		} else {
			dataout = String.format("%d-%d-01", yearNow, monthNow+1);
		}
		
		Date dtIn = Date.valueOf(datain);
		Date dtOut = Date.valueOf(dataout);
		
		updateTable(dtIn, dtOut);
		
	}
	
	private void updateTable(Date start, Date finish) {
		
		custos = SQLUtil.getCostMonth(start, finish);

		DefaultTableModel tableModel = new DefaultTableModel();
		
		tableModel.setColumnIdentifiers(columnNames);
		
		tableModel.setNumRows(0);
		
		int i = 1;
		
		if (signatures.size() > 0) {
			for (Signature sign : signatures) {
				
				Object[] data = new Object[5];
				
				data[0] = i;
				data[1] = sign.descricao;
				data[2] = "----";
				data[3] = "----";
				data[4] = "true";
				
				tableModel.addRow(data);
				i++;
			}
		}
			
		if (custos.size() > 0) {
			
			for (Cost aux : custos) {
				
				Object[] data = new Object[5];
				
				data[0] = i;
				data[1] = aux.descricao;
				data[2] = aux.parcelas;
				data[3] = aux.isFinish;
				data[4] = aux.assinatura;
				
				tableModel.addRow(data);
				i++;
			}
			
		} 
		
		table.setModel(tableModel);
		
		tableModel.fireTableDataChanged();
	}

	/**
	 * Create the frame.
	 */
	public SeeCost() {
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() / 2) - (598 / 2));
		int y = (int) ((dimension.getHeight() / 2) - (478 / 2));
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(x, y, 598, 478);
//		setBounds(100, 100, 598, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("CUSTOS");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 11, 556, 14);
		contentPane.add(lblTitle);
		
		UtilDateModel model = new UtilDateModel();
		Properties properties = new Properties();
		properties.put("text.month", "Month");
		properties.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("mudando o mes");
				
				//------
				String dataText = datePicker.getJFormattedTextField().getText();
				
				Date date = Date.valueOf(dataText);
				int year = date.getYear() + 1900;
				int month = date.getMonth() + 1;
				
				System.out.println(String.format("para %d-%d", year, month));
				
				String datain = String.format("%d-%d-01", year, month);
				String dataout;
				if (monthNow == 12) {
					dataout = String.format("%d-01-01", year+1);
				} else {
					dataout = String.format("%d-%d-01", year, month+1);
				}
				
				Date dtIn = Date.valueOf(datain);
				Date dtOut = Date.valueOf(dataout);
				
				updateTable(dtIn, dtOut);
				
			}
		});
		datePicker.setBounds(427, 41, 139, 30);
		getContentPane().add(datePicker);
		
		table = new JTable(new Object[0][0], columnNames);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(20, 83, 556, 307);
		contentPane.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("SELECIONE O M\u00CAS:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(257, 48, 152, 16);
		contentPane.add(lblNewLabel);
		
		JButton btnExtract = new JButton("EXTRAIR ANEXO");
		btnExtract.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				extractPDF(table.getSelectedRow());
			}
		});
		btnExtract.setBounds(204, 405, 176, 26);
		contentPane.add(btnExtract);
		
		setup();
	}
	
	private void extractPDF(int idCost) {
		
		byte[] pdf = SQLUtil.getAnexo(custos.get(idCost).id);
		
		if (pdf == null) {
			System.out.println("nao existe anexo para o id: " + custos.get(idCost).id);
		} else {
			System.out.println("salvando...");
			
			String nameFile = custos.get(idCost).descricao;
			nameFile = nameFile.replace(' ', '_');
			
			String dirFile = null;
			
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setCurrentDirectory(new java.io.File("."));
			fileChooser.setDialogTitle("Selecione a pasta de saida");
			
			if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				
				dirFile = fileChooser.getSelectedFile().toString();
				
				String completo = dirFile + '/' + nameFile + ".pdf";
				
				System.out.println("-> " + completo);
				
				try (FileOutputStream fos = new FileOutputStream(completo)) {
					
					fos.write(pdf);
					fos.flush();
					fos.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
