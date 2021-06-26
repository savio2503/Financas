package window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import tools.Account;
import tools.Card;
import tools.SQLUtil;

public class ModCard extends JFrame {

	private JPanel contentPane;
	
	private String[] nameCards = null;
	
	private List<Card> cards = null;
	
	private void setupNames() {
		
		cards = SQLUtil.getCards();
		
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
		
	}

	/**
	 * Create the frame.
	 */
	public ModCard() {
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() / 2) - (313 / 2));
		int y = (int) ((dimension.getHeight() / 2) - (352 / 2));
		
		setupNames();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(x, y, 313, 352);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("MODIFICAR CART\u00C3O");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 11, 260, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNome = new JLabel("NOME:");
		lblNome.setBounds(12, 86, 46, 14);
		contentPane.add(lblNome);
		
		JLabel lblLimite = new JLabel("LIMITE:");
		lblLimite.setBounds(12, 126, 46, 14);
		contentPane.add(lblLimite);
		
		JLabel lblVencimento = new JLabel("VENCIMENTO:");
		lblVencimento.setBounds(12, 168, 80, 14);
		contentPane.add(lblVencimento);
		
		JLabel lblFechamento = new JLabel("FECHAMENTO:");
		lblFechamento.setBounds(10, 212, 90, 14);
		contentPane.add(lblFechamento);
		
		JTextField textName = new JTextField();
		textName.setHorizontalAlignment(SwingConstants.CENTER);
		textName.setBounds(68, 83, 204, 20);
		contentPane.add(textName);
		textName.setColumns(10);
		
		JMoneyFieldValor formattedValue = new JMoneyFieldValor();
		formattedValue.setHorizontalAlignment(SwingConstants.RIGHT);
		formattedValue.setBounds(68, 123, 204, 20);
		getContentPane().add(formattedValue);
		
		JComboBox comboBoxVencimento = new JComboBox();
		comboBoxVencimento.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"}));
		comboBoxVencimento.setBounds(110, 164, 52, 22);
		contentPane.add(comboBoxVencimento);
		
		JComboBox comboBoxFechamento = new JComboBox();
		comboBoxFechamento.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"}));
		comboBoxFechamento.setBounds(110, 208, 52, 22);
		contentPane.add(comboBoxFechamento);
		
		JButton btnAdicionar = new JButton("MODIFICAR");
		btnAdicionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String nome = textName.getText();
				int vencimento = Integer.parseInt((String) comboBoxVencimento.getSelectedItem());
				int fechamento = Integer.parseInt((String) comboBoxFechamento.getSelectedItem());
				
				Double limite = 0.0;
				
				if (!formattedValue.getText().isBlank()) {
					String aux = formattedValue.getText();
					
					aux = aux.replace(".", "");
					aux = aux.replace(",", ".");
					
					limite = Double.parseDouble(aux);
				}
				
				if (!nome.isBlank() && SQLUtil.addCard(nome, limite, vencimento, fechamento)) {
					System.out.println("inserido com sucesso");
				}
			}
		});
		btnAdicionar.setBounds(12, 273, 260, 23);
		contentPane.add(btnAdicionar);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(nameCards));
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				if (cards.size() > 0 && comboBox.getSelectedIndex() > 0) {
					
					Card cardAux = cards.get(comboBox.getSelectedIndex() - 1);
					
					textName.setText(cardAux.nome);
					formattedValue.setText(String.format("%.2f", cardAux.limite));
					comboBoxVencimento.setSelectedIndex(cardAux.vencimento - 1);
					comboBoxFechamento.setSelectedIndex(cardAux.fechamento - 1);
				}
				
			}
		});
		comboBox.setBounds(12, 40, 260, 22);
		contentPane.add(comboBox);
	}

}
