package window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tools.SQLUtil;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Main extends JFrame {

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
		int x = (int) ((dimension.getWidth() / 2) - (277 / 2));
		int y = (int) ((dimension.getHeight() / 2) - (417 / 2));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(x, y, 277, 417);
		setBounds(0, 0, 277, 421);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		lblValor.setHorizontalAlignment(SwingConstants.CENTER);
		lblValor.setBounds(29, 30, 200, 14);
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
		btnCusto.setBounds(29, 55, 200, 23);
		contentPane.add(btnCusto);
		
		JButton btnCard = new JButton("ADICIONAR CART\u00C3O");
		btnCard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddCard frame = new AddCard();
				frame.setVisible(true);
			}
		});
		btnCard.setBounds(29, 89, 200, 23);
		contentPane.add(btnCard);
		
		JButton btnAccount = new JButton("ADICIONAR CONTA");
		btnAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddAccount frame = new AddAccount();
				frame.setVisible(true);
			}
		});
		btnAccount.setBounds(29, 123, 200, 23);
		contentPane.add(btnAccount);
		
		JButton btnRecebimento = new JButton("ADICIONAR RECEBIMENTO");
		btnRecebimento.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddReceive frame = new AddReceive();
				frame.setVisible(true);
			}
		});
		btnRecebimento.setBounds(29, 157, 200, 23);
		contentPane.add(btnRecebimento);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(29, 222, 200, 2);
		contentPane.add(separator);
		
		JButton btnModConta = new JButton("CONTA");
		btnModConta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ModConta frame = new ModConta();
				frame.setVisible(true);
			}
		});
		btnModConta.setBounds(28, 257, 200, 23);
		contentPane.add(btnModConta);
		
		JButton btnModCard = new JButton("CART\u00C3O");
		btnModCard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ModCard frame = new ModCard();
				frame.setVisible(true);
			}
		});
		btnModCard.setBounds(29, 291, 199, 23);
		contentPane.add(btnModCard);
		
		JButton btnModCusto = new JButton("CUSTO");
		btnModCusto.setBounds(28, 325, 200, 23);
		contentPane.add(btnModCusto);
		
		JLabel lblNewLabel = new JLabel("MODIFICAR");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(28, 232, 200, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnTransferencia = new JButton("TRANSFERIR");
		btnTransferencia.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Transfer frame = new Transfer();
				frame.setVisible(true);
			}
		});
		btnTransferencia.setBounds(29, 191, 200, 23);
		contentPane.add(btnTransferencia);
	}
	
	private void getTotal(JLabel label) {
		
		Double total = SQLUtil.getTotalValueAccounts();
		
		String result = label.getText() + total.toString();
		
		label.setText(result);
	}
}
