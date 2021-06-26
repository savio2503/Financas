package tools;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLUtil {
	
	public static void setup() {
		String commandTableCost = "CREATE TABLE IF NOT EXISTS \"CUSTO\" (\r\n"
				+ "	\"ID\"	INTEGER NOT NULL,\r\n"
				+ "	\"DESCRICAO\"	TEXT NOT NULL,\r\n"
				+ "	\"VALOR_TOTAL\"	REAL NOT NULL,\r\n"
				+ "	\"PARCERLAS\"	INTEGER DEFAULT 1,\r\n"
				+ "	\"DATA\"	DATE NOT NULL,\r\n"
				+ "	\"VALOR_PAGO\"	REAL DEFAULT 0,\r\n"
				+ "	\"ANEXO\"	BLOB,\r\n"
				+ "	\"CONTA\"	INTEGER,\r\n"
				+ "	\"CARTAO\"	INTEGER,\r\n"
				+ "	\"PAGO\"	INTEGER,\r\n"
				+ "	PRIMARY KEY(\"ID\" AUTOINCREMENT)\r\n"
				+ ");";
		
		String commandTableCard = "CREATE TABLE IF NOT EXISTS \"CARD\" (\r\n"
				+ "	\"ID\"	INTEGER NOT NULL,\r\n"
				+ "	\"NOME\"	TEXT NOT NULL,\r\n"
				+ "	\"LIMITE\"	REAL NOT NULL,\r\n"
				+ "	\"VENCIMENTO\"	INTEGER NOT NULL,\r\n"
				+ "	\"DIA_FECHAMENTO\"	INTEGER NOT NULL,\r\n"
				+ "	PRIMARY KEY(\"ID\" AUTOINCREMENT)\r\n"
				+ ");";
		
		String commandTableAccount = "CREATE TABLE IF NOT EXISTS \"ACCOUNT\" (\r\n"
				+ "	\"ID\"	INTEGER NOT NULL,\r\n"
				+ "	\"NOME\"	TEXT NOT NULL,\r\n"
				+ "	\"VALOR_ATUAL\"	REAL NOT NULL,\r\n"
				+ "	PRIMARY KEY(\"ID\" AUTOINCREMENT)\r\n"
				+ ");";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/banco.db")) {
			
			Statement statement = connection.createStatement();
			
			//criando as tabelas
			statement.execute(commandTableCost);
			statement.execute(commandTableCard);
			statement.execute(commandTableAccount);
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean addCost(String descricao, Double valor, int parcelas, Date data, byte[] anexo, int conta, int cartao) {
		
		String query = "INSERT INTO CUSTO \r\n"
				+ "('DESCRICAO', 'VALOR_TOTAL', 'PARCERLAS', 'DATA', 'ANEXO', 'CONTA', 'CARTAO', 'PAGO') \r\n"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/banco.db")) {
			
			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString (1, descricao);
			preparedStmt.setDouble (2, valor);
			preparedStmt.setInt    (3, parcelas);
			preparedStmt.setDate   (4, data);
			preparedStmt.setBytes  (5, anexo);
			preparedStmt.setInt    (6, conta);
			preparedStmt.setInt    (7, cartao);
			preparedStmt.setInt    (8, parcelas == 1 ? 1 : 0);
			
			preparedStmt.execute();
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static boolean addCard(String nome, Double limite, int vencimento, int fechamento) {
		
		String query = "INSERT INTO CARD \r\n"
				+ "('NOME', 'LIMITE', 'VENCIMENTO', 'DIA_FECHAMENTO') \r\n"
				+ "VALUES (?, ?, ?, ?);";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/banco.db")) {
			
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, nome);
			preparedStmt.setDouble(2, limite == null ? 0.0 : limite);
			preparedStmt.setInt   (3, vencimento == 0 ? 28 : vencimento);
			preparedStmt.setInt   (4, fechamento == 0 ? 10 : fechamento);
			
			preparedStmt.execute();
			
			connection.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static boolean addAccount(String nome, Double valorAtual) {
		
		String query = "INSERT INTO ACCOUNT \r\n"
				+ "('NOME', 'VALOR_ATUAL') \r\n"
				+ "VALUES (?, ?);";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/banco.db")) {
			
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, nome);
			preparedStmt.setDouble(2, valorAtual == null ? 0.0 : valorAtual);
			
			preparedStmt.execute();
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}

	public static List<Cost> getCosts(Boolean pago, Integer mes, Integer card, Integer account) {
		
		List<Cost> result = new ArrayList<Cost>();
		
		
		return result;
	}
	
	public static List<Card> getCards() {
		
		List<Card> result = new ArrayList<Card>();
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/banco.db")) {
			
			Statement stmt = connection.createStatement();
			ResultSet rs;
			
			rs = stmt.executeQuery("SELECT * FROM CARD");
			
			while(rs.next()) {
				Card aux = new Card();
				
				aux.id = rs.getInt("ID");
				aux.nome = rs.getString("NOME");
				aux.limite = rs.getDouble("LIMITE");
				aux.vencimento = rs.getInt("VENCIMENTO");
				aux.fechamento = rs.getInt("DIA_FECHAMENTO");
				
				result.add(aux);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	public static List<Account> getAccounts() {
		
		List<Account> result = new ArrayList<Account>();
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/banco.db")) {
			
			Statement stmt = connection.createStatement();
			ResultSet rs;
			
			rs = stmt.executeQuery("SELECT * FROM ACCOUNT");
			
			while (rs.next() ) {
				Account aux = new Account();
				aux.id = rs.getInt("ID");
				aux.nome = rs.getString("NOME");
				aux.valor = rs.getDouble("VALOR_ATUAL");
				
				result.add(aux);
			}
			
			connection.close();
			
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static boolean modificarAccount(int id, String nome, Double valor) {
		
		String query = "UPDATE ACCOUNT SET NOME = ?, VALOR_ATUAL = ? WHERE ID = ?;";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/banco.db")) {
		
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, nome);
			preparedStmt.setDouble(2, valor);
			preparedStmt.setInt   (3, id);
			
			preparedStmt.execute();
		
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static boolean modificarCard(int id, String nome, Double limite, int vencimento, int fechamento) {
		
		String query = "UPDATE CARD SET \r\n"
				+ "NOME = ?, LIMITE = ?, VENCIMENTO = ?, DIA_FECHAMENTO = ? \r\n" +
				" WHERE ID = ?;";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/banco.db")) {
		
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, nome);
			preparedStmt.setDouble(2, limite);
			preparedStmt.setInt   (3, vencimento);
			preparedStmt.setInt   (4, fechamento);
			preparedStmt.setInt   (5, id);
			
			preparedStmt.execute();
		
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static Double getTotalValueAccounts() {
		
		Double result = 0.0;
		String query = "SELECT SUM(ACCOUNT.VALOR_ATUAL) from ACCOUNT;";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/banco.db")) {
			
			Statement stmt = connection.createStatement();
			ResultSet rs;
			
			rs = stmt.executeQuery(query);
			
			while (rs.next() ) {
				result = rs.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static boolean addReceipt(int id, Double valor) {
		
		String query = "UPDATE ACCOUNT \r\n " 
				+ " SET VALOR_ATUAL = VALOR_ATUAL + ? \r\n " 
				+ " WHERE ID = ?;";
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/banco.db")) {
		
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setDouble(1, valor);
			preparedStmt.setInt   (2, id);
			
			preparedStmt.execute();
		
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
