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
package main.java.br.com.savio2503.Financas.tools;

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
	
	private static final String DEFAULT_DRIVER = "org.sqlite.JDBC";
	private static final String DIR = "jdbc:sqlite:" + System.getProperty("user.dir") + "/banco.db";
	
	private static Connection createConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DEFAULT_DRIVER);
		
		return DriverManager.getConnection(DIR);
	}
	
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
				+ "	\"ASSINATURA\"	INTEGER,\r\n"
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
		
		System.out.println("Criando o banco em: " + System.getProperty("user.dir"));
		
		try (Connection connection = createConnection()) {
			
			Statement statement = connection.createStatement();
			
			//criando as tabelas
			statement.execute(commandTableCost);
			statement.execute(commandTableCard);
			statement.execute(commandTableAccount);
			
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean addCost(String descricao, Double valor, int parcelas, Date data, byte[] anexo, int conta, int cartao, boolean assinatura) {
		
		String query = "INSERT INTO CUSTO \r\n"
				+ "('DESCRICAO', 'VALOR_TOTAL', 'PARCERLAS', 'DATA', 'ANEXO', 'CONTA', 'CARTAO', 'PAGO', 'ASSINATURA') \r\n"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		try (Connection connection = createConnection()) {
			
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
			preparedStmt.setBoolean(9, assinatura);
			
			preparedStmt.execute();
			
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static boolean addCard(String nome, Double limite, int vencimento, int fechamento) {
		
		String query = "INSERT INTO CARD \r\n"
				+ "('NOME', 'LIMITE', 'VENCIMENTO', 'DIA_FECHAMENTO') \r\n"
				+ "VALUES (?, ?, ?, ?);";
		
		try (Connection connection = createConnection()) {
			
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, nome);
			preparedStmt.setDouble(2, limite == null ? 0.0 : limite);
			preparedStmt.setInt   (3, vencimento == 0 ? 28 : vencimento);
			preparedStmt.setInt   (4, fechamento == 0 ? 10 : fechamento);
			
			preparedStmt.execute();
			
			connection.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static boolean addAccount(String nome, Double valorAtual) {
		
		String query = "INSERT INTO ACCOUNT \r\n"
				+ "('NOME', 'VALOR_ATUAL') \r\n"
				+ "VALUES (?, ?);";
		
		try (Connection connection = createConnection()) {
			
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, nome);
			preparedStmt.setDouble(2, valorAtual == null ? 0.0 : valorAtual);
			
			preparedStmt.execute();
			
			connection.close();
			
		} catch (Exception e) {
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
		
		try (Connection connection = createConnection()) {
			
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
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	public static List<Account> getAccounts() {
		
		List<Account> result = new ArrayList<Account>();
		
		try (Connection connection = createConnection()) {
			
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
			
		}  catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static boolean modificarAccount(int id, String nome, Double valor) {
		
		String query = "UPDATE ACCOUNT SET NOME = ?, VALOR_ATUAL = ? WHERE ID = ?;";
		
		try (Connection connection = createConnection()) {
		
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, nome);
			preparedStmt.setDouble(2, valor);
			preparedStmt.setInt   (3, id);
			
			preparedStmt.execute();
		
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static boolean modificarCard(int id, String nome, Double limite, int vencimento, int fechamento) {
		
		String query = "UPDATE CARD SET \r\n"
				+ "NOME = ?, LIMITE = ?, VENCIMENTO = ?, DIA_FECHAMENTO = ? \r\n" +
				" WHERE ID = ?;";
		
		try (Connection connection = createConnection()) {
		
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString(1, nome);
			preparedStmt.setDouble(2, limite);
			preparedStmt.setInt   (3, vencimento);
			preparedStmt.setInt   (4, fechamento);
			preparedStmt.setInt   (5, id);
			
			preparedStmt.execute();
		
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static Double getTotalValueAccounts() {
		
		Double result = 0.0;
		String query = "SELECT SUM(ACCOUNT.VALOR_ATUAL) from ACCOUNT;";
		
		try (Connection connection = createConnection()) {
			
			Statement stmt = connection.createStatement();
			ResultSet rs;
			
			rs = stmt.executeQuery(query);
			
			while (rs.next() ) {
				result = rs.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static boolean addReceipt(int id, Double valor) {
		
		String query = "UPDATE ACCOUNT \r\n " 
				+ " SET VALOR_ATUAL = VALOR_ATUAL + ? \r\n " 
				+ " WHERE ID = ?;";
		
		try (Connection connection = createConnection()) {
		
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setDouble(1, valor);
			preparedStmt.setInt   (2, id);
			
			preparedStmt.execute();
		
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static boolean transferMoney(int idFonte, int idDestino, Double valor) {
		
		String queryDestino = "UPDATE ACCOUNT \r\n " 
				+ " SET VALOR_ATUAL = VALOR_ATUAL + ? \r\n " 
				+ " WHERE ID = ?;";
		String queryFonte   = "UPDATE ACCOUNT \r\n " 
				+ " SET VALOR_ATUAL = VALOR_ATUAL - ? \r\n " 
				+ " WHERE ID = ?;";
		
		try (Connection connection = createConnection()) {
		
			PreparedStatement preparedStmt = connection.prepareStatement(queryDestino);
			preparedStmt.setDouble(1, valor);
			preparedStmt.setInt   (2, idDestino);
			
			preparedStmt.execute();
			
			preparedStmt = connection.prepareStatement(queryFonte);
			preparedStmt.setDouble(1, valor);
			preparedStmt.setInt   (2, idFonte);
			
			preparedStmt.execute();
		
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static Double getTotalValueCostMonth(Date start, Date finish) {
		
		Double result = 0.0;
		String query = "SELECT SUM(VALOR_TOTAL) FROM CUSTO WHERE DATA BETWEEN ? AND ?;";
		
		try (Connection connection = createConnection()) {
			
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setDate(1, start);
			preparedStmt.setDate(2, finish);
			
			preparedStmt.execute();
			
			ResultSet rs = preparedStmt.getResultSet();
			
			while (rs.next() ) {
				result = rs.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static List<Cost> getCostMonth(Date start, Date finish) {
		
		List<Cost> costs = new ArrayList<Cost>();
		List<Account> accounts = getAccounts();
		List<Card> cards = getCards();
		String query = "SELECT * FROM CUSTO WHERE DATA BETWEEN ? AND ?;";
		
		try (Connection connection = createConnection()) {
			
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setDate(1, start);
			preparedStmt.setDate(2, finish);
			
			preparedStmt.execute();
			
			ResultSet rs = preparedStmt.getResultSet();
			
			while (rs.next() ) {
				Cost aux = new Cost();
				
				aux.id = rs.getInt("ID");
				aux.descricao = rs.getString("DESCRICAO");
				aux.valor = rs.getDouble("VALOR_TOTAL");
				aux.parcelas = rs.getInt("PARCERLAS");
				aux.data = rs.getDate("DATA").toString();
				int conta = rs.getInt("CONTA");
				int cartao = rs.getInt("CARTAO");
				aux.isFinish = rs.getBoolean("PAGO");
				aux.assinatura = rs.getBoolean("ASSINATURA");
				
				if (conta != 0) {
					for (Account account : accounts) {
						if (conta == account.id) {
							aux.conta = account.nome;
							break;
						}
					}
				} else if (cartao != 0) {
					for (Card card : cards) {
						if (cartao == card.id) {
							aux.cartao = card.nome;
							break;
						}
					}
				}
				
				costs.add(aux);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return costs;
	}
	
	public static byte[] getAnexo(int idCost) {
		
		byte[] result = null;
		
		String query = "SELECT ANEXO FROM CUSTO WHERE ID = ?";
		
		try (Connection connection = createConnection()) {
			
			PreparedStatement prepareStmt = connection.prepareStatement(query);
			prepareStmt.setInt(1, idCost);
			
			prepareStmt.execute();
			
			ResultSet rs = prepareStmt.getResultSet();
			
			while (rs.next()) {
				
				result = rs.getBytes("ANEXO");
			}
			
		} catch (Exception e ) {
			e.printStackTrace();
		}
		
		
		return result;
	}
}
