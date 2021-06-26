package window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tools.Account;
import tools.SQLUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ModConta extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -990763805711092965L;
	private JPanel contentPane;
	private JTextField textNome;
	
	private String[] nameAccounts = null;
	private List<Account> accounts = null;
	
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
	public ModConta() {
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() / 2) - (325 / 2));
		int y = (int) ((dimension.getHeight() / 2) - (250 / 2));
		
		setupNames();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(x, y, 325, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("MODIFICAR CONTA");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 280, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("NOME:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 81, 120, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("VALOR ATUAL: R$");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(10, 106, 120, 14);
		contentPane.add(lblNewLabel_2);
		
		textNome = new JTextField();
		textNome.setEditable(false);
		textNome.setHorizontalAlignment(SwingConstants.CENTER);
		textNome.setBounds(140, 78, 150, 20);
		contentPane.add(textNome);
		textNome.setColumns(10);
		
		JMoneyFieldValor formattedValue = new JMoneyFieldValor();
		formattedValue.setHorizontalAlignment(SwingConstants.RIGHT);
		formattedValue.setBounds(140, 106, 150,20);
		getContentPane().add(formattedValue);
		
		JComboBox comboBoxNome = new JComboBox();
		comboBoxNome.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				if (accounts.size() > 0 && comboBoxNome.getSelectedIndex() > 0) {
					
					Account accountAux = accounts.get(comboBoxNome.getSelectedIndex() - 1);
					
					textNome.setText(accountAux.nome);
					formattedValue.setText(String.format("%.2f", accountAux.valor));
				}
				
			}
		});
		comboBoxNome.setModel(new DefaultComboBoxModel(nameAccounts));
		comboBoxNome.setBounds(10, 36, 280, 22);
		contentPane.add(comboBoxNome);
		
		JButton btnModificar = new JButton("MODIFICAR");
		btnModificar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (comboBoxNome.getSelectedIndex() > 0) {
					
					Double valor = 0.0;
					
					if (!formattedValue.getText().isBlank()) {
						String aux = formattedValue.getText();
						
						aux = aux.replace(".", "");
						aux = aux.replace(",", ".");
						
						valor = Double.parseDouble(aux);
					}
					
					Account accountAux = accounts.get(comboBoxNome.getSelectedIndex() - 1);
					
					if (SQLUtil.modificarAccount(accountAux.id, textNome.getText(), valor)) {
						JOptionPane.showMessageDialog(new JFrame(), "MUDANCA APLICADA", "Dialog", JOptionPane.YES_OPTION);
					} else {
						JOptionPane.showMessageDialog(new JFrame(), "ERRO AO INSERIR", "Dialog", JOptionPane.ERROR_MESSAGE);
					}
					
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "CONTA NAO SELECIONADA", "Dialog", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnModificar.setBounds(10, 164, 280, 23);
		contentPane.add(btnModificar);
	}
}
