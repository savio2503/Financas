package window;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import tools.Cost;
import tools.SQLUtil;

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
import java.sql.Date;
import java.time.LocalDateTime;
import java.beans.PropertyChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SeeCost extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3509162996247030486L;
	private JPanel contentPane;
	
	private int monthNow, yearNow;
	private List<Cost> custos = new ArrayList<Cost>();
	private JTable table;
	JScrollPane scrollPane;
	private String columnNames[] = {"NUMERO",
						            "DESCRICAO",
						            "PARCELAS",
						            "DATA",
						            "CONTA",
						            "CARTAO",
						            "PAGO"};
	
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
			
		if (custos.size() > 0) {
			
			for (int i = 0; i < custos.size(); i++) {
				
				Object[] data = new Object[8];
				
				data[0] = i;
				data[1] = custos.get(i).descricao;
				data[2] = custos.get(i).parcelas;
				data[3] = custos.get(i).data;
				data[4] = custos.get(i).conta  != null ? custos.get(i).conta : "-------";
				data[5] = custos.get(i).cartao != null ? custos.get(i).cartao : "-------";
				data[6] = custos.get(i).isFinish;
				
				tableModel.addRow(data);
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
		int x = (int) ((dimension.getWidth() / 2) - (712 / 2));
		int y = (int) ((dimension.getHeight() / 2) - (450 / 2));
		
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(x, y, 712, 450);
		//setBounds(100, 100, 712, 450);
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
		scrollPane.setBounds(10, 83, 556, 307);
		contentPane.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("SELECIONE O M\u00CAS:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(288, 55, 121, 16);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("VER ANEXO");
		btnNewButton.setBounds(578, 214, 100, 26);
		contentPane.add(btnNewButton);
		
		setup();
	}
}
