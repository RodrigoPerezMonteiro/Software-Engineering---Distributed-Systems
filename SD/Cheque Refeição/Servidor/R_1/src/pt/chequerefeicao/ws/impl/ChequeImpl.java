package pt.chequerefeicao.ws.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.HandlerChain;
import javax.jws.WebService;

import pt.chequerefeicao.crypto.GenerateSecret;
import pt.chequerefeicao.ws.handler.Handler;
import pt.chequerefeicao.ws.ChequeInexistente;
import pt.chequerefeicao.ws.ChequeInexistente_Exception;
import pt.chequerefeicao.ws.ChequeJaUsado;
import pt.chequerefeicao.ws.ChequeJaUsado_Exception;
import pt.chequerefeicao.ws.ChequeNaoEndossavel_Exception;
import pt.chequerefeicao.ws.ChequeRefeicaoPortType;
import pt.chequerefeicao.ws.UtilizadorInexistente;
import pt.chequerefeicao.ws.UtilizadorInexistente_Exception;
import pt.chequerefeicao.ws.ValorInvalido_Exception;

@WebService(endpointInterface = "pt.chequerefeicao.ws.ChequeRefeicaoPortType", wsdlLocation = "ChequeRefeicao.1_0.wsdl", name = "ChequeRefeicao", portName = "ChequeRefeicaoPort", targetNamespace = "urn:pt:chequerefeicao:ws", serviceName = "ChequeRefeicaoService")
@HandlerChain(file = "/handler-chain.xml")
public class ChequeImpl implements ChequeRefeicaoPortType {

	private String dbDriverName = "com.mysql.jdbc.Driver";
	private String dbUrl = "jdbc:mysql://localhost:3306/testdb";
	private String dbUsername = "root";
	private String dbPassword = "rootroot";

	@Override
	public String emitir(String titular, int valor, boolean endossavel)
			throws UtilizadorInexistente_Exception, ValorInvalido_Exception {
		Connection connection;
		int id = 0;
		String segredo = "";
		try {
			Class.forName(dbDriverName);

			connection = DriverManager.getConnection(dbUrl, dbUsername,
					dbPassword);
			String sql = "SELECT COUNT(*) FROM UTILIZADORES WHERE NAME = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, titular);
			int nUsers = 0;
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				nUsers = rs.getInt(1);
			}
			
			if (nUsers == 0) {
				throw new UtilizadorInexistente_Exception(
						"Utilizador Inexistente", new UtilizadorInexistente());
			}
			connection.close();
		} catch (ClassNotFoundException e) {
			System.out.printf("Could not find the database driver %s %n", e);
		} catch (SQLException e) {
			System.out.printf("SQL exception %s %n", e);
		}

		if (valor > 100 || valor <= 0) {
			throw new ValorInvalido_Exception(
					"Valor invalido. O valor deve ser entre 0 e 100", null);
		}
		
		try {
			Class.forName(dbDriverName);
			connection = DriverManager.getConnection(dbUrl, dbUsername,
					dbPassword);
			segredo = GenerateSecret.generateRandomNumber();
			String sql = "INSERT INTO CHEQUES (titular, valor, endossavel, segredo) VALUES (?,?,?,?)";
			PreparedStatement pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, titular);
			pstmt.setInt(2, valor);
			pstmt.setBoolean(3, endossavel);
			pstmt.setString(4, segredo);
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);

			connection.close();

		} catch (ClassNotFoundException e) {
			System.out.printf("Could not find the database driver %s %n", e);
		} catch (SQLException e) {
			System.out.printf("SQL exception %s %n", e);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return Integer.toString(id) + segredo;
	}

	@Override
	public Integer sacar(String beneficiario, List<String> ids)
			throws ChequeInexistente_Exception, ChequeJaUsado_Exception,
			UtilizadorInexistente_Exception {
		int valor = 0;

		Connection connection;
		try {
			Class.forName(dbDriverName);

			connection = DriverManager.getConnection(dbUrl, dbUsername,
					dbPassword);
			String sql = "SELECT COUNT(*) FROM UTILIZADORES WHERE NAME = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, beneficiario);
			int nUsers = 0;
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				nUsers = rs.getInt(1);
			}

			if (nUsers == 0)
				throw new UtilizadorInexistente_Exception(
						"Utilizador Inexistente", new UtilizadorInexistente());
			connection.close();
		} catch (ClassNotFoundException e) {
			System.out.printf("Could not find the database driver %s %n", e);
		} catch (SQLException e) {
			System.out.printf("SQL exception %s %n", e);
		}

		try {

			for (String cId : ids) {	
				Class.forName(dbDriverName);
				String idSeparado = GenerateSecret.separaId(cId);
				String segredoSeparado = GenerateSecret.separaSegredo(cId);
				connection = DriverManager.getConnection(dbUrl, dbUsername,
						dbPassword);
				String sql = "SELECT COUNT(*) FROM CHEQUES WHERE ID = ? AND SEGREDO = ?";
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(idSeparado));
				pstmt.setString(2, segredoSeparado);
				

				int nId = 0;
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					nId = rs.getInt(1);
				}

				if (nId == 0) {
					throw new ChequeInexistente_Exception("Cheque Inexistente",
							new ChequeInexistente());
				}

				sql = "SELECT * FROM CHEQUES WHERE ID = ?";
				pstmt = connection.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(idSeparado));
				
				boolean usado = false;
				rs = pstmt.executeQuery();
				while (rs.next()) {
					usado = rs.getBoolean("USADO");
					valor += rs.getInt("VALOR");
				}

				if (usado) {
					throw new ChequeJaUsado_Exception("Cheque Usado",
							new ChequeJaUsado());
				}
				
				sql = "UPDATE CHEQUES SET USADO=? WHERE ID = ? AND SEGREDO = ?";
				
				pstmt = connection.prepareStatement(sql);
				usado = true;
				pstmt.setBoolean(1, usado);
				pstmt.setInt(2, Integer.parseInt(idSeparado));
				pstmt.setString(3,segredoSeparado);
				pstmt.executeUpdate();

				rs = pstmt.getGeneratedKeys();
				rs.next();

				connection.close();

			}
		}

		catch (ClassNotFoundException e) {
			System.out.printf("Could not find the database driver %s %n", e);
		} catch (SQLException e) {
			System.out.printf("SQL exception %s %n", e);
		}
		
		
		return valor;
	}

	@Override
	public String endossar(String titular, String benef, String id)
			throws ChequeInexistente_Exception, ChequeJaUsado_Exception,
			ChequeNaoEndossavel_Exception, UtilizadorInexistente_Exception {

		int valor = 0;
		Connection connection;
		try {
			Class.forName(dbDriverName);

			connection = DriverManager.getConnection(dbUrl, dbUsername,
					dbPassword);
			String sql = "SELECT COUNT(*) FROM UTILIZADORES WHERE NAME = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, titular);

			int nUsers = 0;
			int nUsersBenef = 0;

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				nUsers = rs.getInt(1);
			}

			if (nUsers == 0)
				throw new ChequeNaoEndossavel_Exception(
						"Cheque nao endossavel", null);
			
			sql = "SELECT COUNT(*) FROM UTILIZADORES WHERE NAME = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, benef);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				nUsersBenef = rs.getInt(1);
			}
			
			if (nUsersBenef == 0) throw new UtilizadorInexistente_Exception("Beneficiario Inexistente", new UtilizadorInexistente());
				
			connection.close();
		} catch (ClassNotFoundException e) {
			System.out.printf("Could not find the database driver %s %n", e);
		} catch (SQLException e) {
			System.out.printf("SQL exception %s %n", e);
		}

		try {
			Class.forName(dbDriverName);

			connection = DriverManager.getConnection(dbUrl, dbUsername,
					dbPassword);
			String sql = "SELECT COUNT(*) FROM CHEQUES WHERE ID = ? AND TITULAR = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(id));
			pstmt.setString(2, titular);

			int nCheques = 0;

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				nCheques = rs.getInt(1);
			}

			if (nCheques == 0)
				throw new ChequeInexistente_Exception("Cheque inexistente",
						null);
			connection.close();
		} catch (ClassNotFoundException e) {
			System.out.printf("Could not find the database driver %s %n", e);
		} catch (SQLException e) {
			System.out.printf("SQL exception %s %n", e);
		}

		try {
			Class.forName(dbDriverName);

			connection = DriverManager.getConnection(dbUrl, dbUsername,
					dbPassword);
			String sql = "SELECT * FROM CHEQUES WHERE ID = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(id));
			boolean endossavel = false, usado = false;
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				usado = rs.getBoolean("USADO");
				endossavel = rs.getBoolean("ENDOSSAVEL");
				valor = rs.getInt("VALOR");
			}

			if (usado)
				throw new ChequeJaUsado_Exception("Cheque ja usado", null);
			if (!endossavel)
				throw new ChequeNaoEndossavel_Exception(
						"Cheque nao endossavel", null);
			connection.close();
		} catch (ClassNotFoundException e) {
			System.out.printf("Could not find the database driver %s %n", e);
		} catch (SQLException e) {
			System.out.printf("SQL exception %s %n", e);
		}

		int idEndossado = 0;

		try {
			Class.forName(dbDriverName);

			connection = DriverManager.getConnection(dbUrl, dbUsername,
					dbPassword);

			String sql = "UPDATE CHEQUES SET TITULAR=?, VALOR=?, USADO=?, ENDOSSAVEL=?, SEGREDO=? WHERE ID = ? AND TITULAR = ?";
		
			PreparedStatement pstmt = connection.prepareStatement(sql);
			boolean usado = false;
			boolean endossavel = false;
			String segredo = GenerateSecret.generateRandomNumber();
			pstmt.setString(1, benef);
			pstmt.setInt(2, valor);
			pstmt.setBoolean(3, usado);
			pstmt.setBoolean(4, endossavel);
			pstmt.setString(5, segredo);
			pstmt.setInt(6, Integer.parseInt(id));
			pstmt.setString(7, titular);
			pstmt.executeUpdate();

			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			idEndossado = Integer.parseInt(id);

			connection.close();

		} catch (ClassNotFoundException e) {
			System.out.printf("Could not find the database driver %s %n", e);
		} catch (SQLException e) {
			System.out.printf("SQL exception %s %n", e);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Integer.toString(idEndossado);

	}

	@Override
	public List<String> listar(String titular, boolean usado)
			throws UtilizadorInexistente_Exception {

		Connection connection;
		try {
			Class.forName(dbDriverName);

			connection = DriverManager.getConnection(dbUrl, dbUsername,
					dbPassword);
			String sql = "SELECT COUNT(*) FROM UTILIZADORES WHERE NAME = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, titular);
			int nUsers = 0;
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				nUsers = rs.getInt(1);
			}

			if (nUsers == 0) {
				throw new UtilizadorInexistente_Exception(
						"Utilizador Inexistente", new UtilizadorInexistente());
			}
			connection.close();
		} catch (ClassNotFoundException e) {
			System.out.printf("Could not find the database driver %s %n", e);
		} catch (SQLException e) {
			System.out.printf("SQL exception %s %n", e);
		}

		List<String> chequesId = new ArrayList<String>();

		try {
			Class.forName(dbDriverName);

			connection = DriverManager.getConnection(dbUrl, dbUsername,
					dbPassword);
			String sql = "SELECT ID FROM CHEQUES WHERE USADO = ? AND TITULAR = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setBoolean(1, usado);
			pstmt.setString(2, titular);
			ResultSet rs = pstmt.executeQuery();
			int id;
			while (rs.next()) {
				id = rs.getInt("ID");
				chequesId.add(Integer.toString(id));
			}

			connection.close();

		} catch (ClassNotFoundException e) {
			System.out.printf("Could not find the database driver %s %n", e);
		} catch (SQLException e) {
			System.out.printf("SQL exception %s %n", e);
		}

		return chequesId;
	}

}