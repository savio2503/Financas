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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
		int x = (int) ((dimension.getWidth() / 2) - (455 / 2));
		int y = (int) ((dimension.getHeight() / 2) - (374 / 2));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(x, y, 455, 374);
		//setBounds(100, 100, 455, 374);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		lblValor.setHorizontalAlignment(SwingConstants.CENTER);
		lblValor.setBounds(30, 13, 182, 14);
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
		btnCusto.setBounds(240, 55, 182, 23);
		contentPane.add(btnCusto);
		
		JButton btnCard = new JButton("ADICIONAR CART\u00C3O");
		btnCard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddCard frame = new AddCard();
				frame.setVisible(true);
			}
		});
		btnCard.setBounds(30, 55, 182, 23);
		contentPane.add(btnCard);
		
		JButton btnAccount = new JButton("ADICIONAR CONTA");
		btnAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddAccount frame = new AddAccount();
				frame.setVisible(true);
			}
		});
		btnAccount.setBounds(30, 89, 182, 23);
		contentPane.add(btnAccount);
		
		JButton btnRecebimento = new JButton("ADICIONAR RECEBIMENTO");
		btnRecebimento.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddReceive frame = new AddReceive();
				frame.setVisible(true);
			}
		});
		btnRecebimento.setBounds(30, 123, 182, 23);
		contentPane.add(btnRecebimento);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(30, 188, 182, 2);
		contentPane.add(separator);
		
		JButton btnModConta = new JButton("CONTA");
		btnModConta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ModConta frame = new ModConta();
				frame.setVisible(true);
			}
		});
		btnModConta.setBounds(29, 223, 182, 23);
		contentPane.add(btnModConta);
		
		JButton btnModCard = new JButton("CART\u00C3O");
		btnModCard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ModCard frame = new ModCard();
				frame.setVisible(true);
			}
		});
		btnModCard.setBounds(30, 257, 182, 23);
		contentPane.add(btnModCard);
		
		JButton btnModCusto = new JButton("CUSTO");
		btnModCusto.setBounds(29, 291, 182, 23);
		contentPane.add(btnModCusto);
		
		JLabel lblNewLabel = new JLabel("MODIFICAR");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(29, 198, 182, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnTransferencia = new JButton("TRANSFERIR");
		btnTransferencia.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Transfer frame = new Transfer();
				frame.setVisible(true);
			}
		});
		btnTransferencia.setBounds(30, 157, 182, 23);
		contentPane.add(btnTransferencia);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(229, 55, 2, 260);
		contentPane.add(separator_1);
		
		JButton btnCustoMes = new JButton("VER CUSTOS");
		btnCustoMes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnCustoMes.setBounds(240, 89, 182, 26);
		contentPane.add(btnCustoMes);
		
		JLabel lblCustoMes = new JLabel("Custo no m\u00EAs: R$ ");
		lblCustoMes.setHorizontalAlignment(SwingConstants.CENTER);
		lblCustoMes.setBounds(240, 12, 182, 16);
		contentPane.add(lblCustoMes);
		
		getCustoMes(lblCustoMes);
	}
	
	private void getCustoMes(JLabel label) {
		//2021-06-26
		LocalDateTime now = LocalDateTime.now();
		
		int year = now.getYear();
		int month = now.getMonthValue();
		
		Double total = SQLUtil.getTotalValueAccounts();
		label.setText(label.getText() + total.toString());
	}
	
	private void getTotal(JLabel label) {
		
		Double total = SQLUtil.getTotalValueAccounts();
		
		String result = label.getText() + total.toString();
		
		label.setText(result);
	}
}
