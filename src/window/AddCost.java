package window;

import java.util.List;
import java.util.Properties;

import javax.swing.JFrame;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import tools.Account;
import tools.Card;
import tools.SQLUtil;

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

import javax.swing.SwingConstants;

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
		int x = (int) ((dimension.getWidth() / 2) - (450 / 2));
		int y = (int) ((dimension.getHeight() / 2) - (446 / 2));
		
		setupNames();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(x, y, 450, 446);
		getContentPane().setLayout(null);
		
		JLabel lbTitle = new JLabel("ADICIONAR CUSTO");
		lbTitle.setBounds(153, 11, 115, 14);
		getContentPane().add(lbTitle);
		
		JButton btnCancel = new JButton("CANCELAR");
		btnCancel.setBounds(234, 350, 103, 23);
		getContentPane().add(btnCancel);
		
		JLabel lblDescricao = new JLabel("DESCRI\u00C7\u00C3O:");
		lblDescricao.setBounds(10, 48, 71, 14);
		getContentPane().add(lblDescricao);
		
		textFieldDescricao = new JTextField();
		textFieldDescricao.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldDescricao.setBounds(85, 45, 320, 20);
		getContentPane().add(textFieldDescricao);
		textFieldDescricao.setColumns(10);
		
		JLabel lblValor = new JLabel("VALOR:  R$");
		lblValor.setBounds(10, 99, 71, 14);
		getContentPane().add(lblValor);
		
		JLabel lblParcelas = new JLabel("QUANT. PARCELAS: ");
		lblParcelas.setBounds(234, 99, 115, 14);
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
		lblComprovante.setBounds(10, 180, 125, 14);
		getContentPane().add(lblComprovante);
		
		JLabel lblConta = new JLabel("ASSOCIAR A UMA CONTA:");
		lblConta.setBounds(10, 243, 154, 14);
		getContentPane().add(lblConta);
		
		JComboBox comboBoxParcelas = new JComboBox();
		comboBoxParcelas.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		comboBoxParcelas.setSelectedIndex(0);
		comboBoxParcelas.setBounds(355, 95, 50, 22);
		getContentPane().add(comboBoxParcelas);
		
		JMoneyFieldValor formattedValue = new JMoneyFieldValor();
		formattedValue.setHorizontalAlignment(SwingConstants.RIGHT);
		formattedValue.setBounds(85,96,130,20);
		getContentPane().add(formattedValue);

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
					
					if (SQLUtil.addCost(descricao, valor, parcelas, data, arquivo, conta, cartao))
						System.out.println("adicionada com sucesso");
				}
				else {
					System.out.println("alguem esta faltando");
				}
				
				System.out.println("des " + descricao + ", value " + valor + ", parcelas " + parcelas + ", data: " + dataText);
				
			}
		});
		btnAdd.setBounds(60, 350, 103, 23);
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
		lblCard.setBounds(10, 290, 154, 14);
		getContentPane().add(lblCard);
		
		
		comboBoxCard.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				if (cards.size() > 0 && comboBoxCard.getSelectedIndex() > 0) {

					comboBoxAccount.setSelectedIndex(0);
				}
				
			}
		});
		comboBoxCard.setModel(new DefaultComboBoxModel(nameCards));
		comboBoxCard.setBounds(169, 290, 236, 22);
		getContentPane().add(comboBoxCard);
		
		
		comboBoxAccount.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				if (accounts.size() > 0 && comboBoxAccount.getSelectedIndex() > 0) {
					
					comboBoxCard.setSelectedIndex(0);
				}
				
			}
		});
		comboBoxAccount.setModel(new DefaultComboBoxModel(nameAccounts));
		comboBoxAccount.setBounds(171, 239, 234, 22);
		getContentPane().add(comboBoxAccount);
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
