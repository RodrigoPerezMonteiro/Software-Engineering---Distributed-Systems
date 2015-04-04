package pt.registofatura.ws.impl;

import static javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

import pt.registofatura.ws.ClienteInexistente;
import pt.registofatura.ws.EmissorInexistente;
import pt.registofatura.ws.Fatura;
import pt.registofatura.ws.EmissorInexistente_Exception;
import pt.registofatura.ws.ClienteInexistente_Exception;
import pt.registofatura.ws.FaturaInvalida;
import pt.registofatura.ws.FaturaInvalida_Exception;
import pt.registofatura.ws.ItemFatura;
import pt.registofatura.ws.RegistoFaturaService;
import pt.registofatura.ws.Serie;
import pt.registofatura.ws.TotaisIncoerentes;
import pt.registofatura.ws.TotaisIncoerentes_Exception;
import pt.registofatura.ws.RegistoFaturaPortType;


/**
 * The Class RegistoFaturaImpl.
 */
@WebService(
		endpointInterface = "pt.registofatura.ws.RegistoFaturaPortType",
		wsdlLocation = "RegistoFatura.1_0.wsdl",
		name = "RegistoFatura",
		portName = "RegistoFaturaPort",
		targetNamespace = "urn:pt:registofatura:ws",
		serviceName = "RegistoFaturaService"
)

public class RegistoFaturaImpl implements pt.registofatura.ws.RegistoFaturaPortType{
	
	private String dbDriverName = "com.mysql.jdbc.Driver";
	private static String dbMainUrl = "jdbc:mysql://localhost:3306/";
	private static String dbCurrentUrl = "jdbc:mysql://localhost:3306/";
	private String dbUsername = "root";
	private String dbPassword = "rootroot";
	private String mainDbName, dbName;
	private String serverName;
	private boolean activateSecondaryPort =false;
	private RegistoFaturaPortType secondaryPort;
	/**
	 * Instantiates a new registo fatura impl.
	 */
	
	public RegistoFaturaImpl(){
		super();
	}
	
	public void setServerName(String serverName) {
        this.serverName = serverName;
    }
	
	public void setCurrentDb(String currDB){
        this.dbName = currDB;
        this.dbCurrentUrl = dbCurrentUrl+currDB;
	}
	
	public String getCurrentDb(){
        return this.dbCurrentUrl;
    }
	
	public void setMainDb(String mainDB){
	    this.mainDbName = mainDB;
	    this.dbMainUrl = dbMainUrl+mainDB;
    }
	
	public void activateSecondaryPort(String url) {
	    activateSecondaryPort = true;
	    RegistoFaturaService service = new RegistoFaturaService();
        secondaryPort = service.getRegistoFaturaPort();
        BindingProvider bindingProvider = (BindingProvider) secondaryPort;
        bindingProvider.getRequestContext().put(ENDPOINT_ADDRESS_PROPERTY, url);
    }
	
	
	public ItemFatura copiaItem(ItemFatura i){
		ItemFatura newi = new ItemFatura();
		newi.setDescricao(i.getDescricao());
		newi.setPreco(i.getPreco());
		newi.setQuantidade(i.getQuantidade());
		return newi;	
	}
	
	public Fatura copiaFatura(Fatura f){
		Fatura newf = new Fatura();
		newf.setData(f.getData());
		newf.setIva(f.getIva());
		newf.setTotal(f.getTotal());
		newf.setNifCliente(f.getNifCliente());
		newf.setNifEmissor(f.getNifEmissor());
		newf.setNumSerie(f.getNumSerie());
		newf.setNumSeqFatura(f.getNumSeqFatura());
		newf.setNomeEmissor(f.getNomeEmissor());
		for(ItemFatura i : f.getItens()){
			newf.getItens().add(copiaItem(i));
		}
		
		return newf;	
	}
	
	public Serie pedirSerie(int nifEmissor) throws EmissorInexistente_Exception {
		if(dbMainUrl.equals(dbCurrentUrl) && activateSecondaryPort){
		    secondaryPort.pedirSerie(nifEmissor);
		}
		Serie serie = new Serie();
        
        try{
        
        Class.forName(dbDriverName);
        Connection conn = DriverManager.getConnection(getCurrentDb(), dbUsername, dbPassword);
        
        String sql = "SELECT * FROM EMISSORES";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        boolean naoexiste = true;
        
        while (rs.next()) {
            int nif = rs.getInt("NIF");
            if (nif == nifEmissor)
                naoexiste = false;
            
        }
        
        if(naoexiste){
            EmissorInexistente emissorNaoExistente = new EmissorInexistente();
            emissorNaoExistente.setMensagem("Não existe emissor");
            emissorNaoExistente.setNif(nifEmissor);
            throw new EmissorInexistente_Exception(emissorNaoExistente.getMensagem(), emissorNaoExistente);
        }
        
        String sql1 = "SELECT * FROM EMISSORES ORDER BY NRSERIE";
        PreparedStatement pstmt1 = conn.prepareStatement(sql1);
        ResultSet rs1 = pstmt1.executeQuery();
        
        int novaserie;
        if(rs1.last()) {
        	novaserie = rs1.getInt("NRSERIE") + 1;
        } else {
        	novaserie = 1;
        }
        String sql2 = "UPDATE EMISSORES SET NRSERIE=? WHERE NIF=?";
        PreparedStatement pstmt2 = conn.prepareStatement(sql2);
        
        pstmt2.setInt(1, novaserie);
        pstmt2.setInt(2, nifEmissor);
        pstmt2.executeUpdate();
        
        String sql3 = "INSERT INTO SERIE (NRSERIE, VALIDADE_DIA, VALIDADE_MES, VALIDADE_ANO) VALUES(?,?,?,?)";
        PreparedStatement pstmt3 = conn.prepareStatement(sql3);
        
        pstmt3.setInt(1, novaserie);
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, 10);
        
        GregorianCalendar c = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
        
        XMLGregorianCalendar xmlGCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        
        pstmt3.setInt(2, xmlGCalendar.getDay());
        pstmt3.setInt(3, xmlGCalendar.getMonth());
        pstmt3.setInt(4, xmlGCalendar.getYear());
        pstmt3.executeUpdate();
        
        serie.setNumSerie(novaserie);
        serie.setValidoAte(xmlGCalendar);
        
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }	        			
		return serie;
	}

    /**
     * Comunicar fatura.
     *
     * @param dia the dia
     * @param mes the mes
     * @param ano the ano
     * @param nomeRestaurante the nome restaurante
     * @param nomeCliente the nome cliente
     * @param nifCliente the nif cliente
     * @param nifEmissor the nif emissor
     * @param valor the valor
     * @param iva the iva
     * @throws ClienteInexistente_Exception the cliente inexistente_ exception
     * @throws EmissorInexistente_Exception the emissor inexistente_ exception
     * @throws FaturaInvalida_Exception the fatura invalida_ exception
     * @throws TotaisIncoerentes_Exception the totais incoerentes_ exception
     */
    public void comunicarFatura(Fatura parameters) throws ClienteInexistente_Exception, EmissorInexistente_Exception, FaturaInvalida_Exception, TotaisIncoerentes_Exception {
        if(dbMainUrl.equals(dbCurrentUrl) && activateSecondaryPort){
            secondaryPort.comunicarFatura(parameters);
        }
	 try{
		 
		Class.forName(dbDriverName);
        Connection conn = DriverManager.getConnection(getCurrentDb(), dbUsername, dbPassword);
            
        String sql = "SELECT * FROM UTILIZADORES";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        boolean naoexiste = true;
        
        while (rs.next()) {
            int nifcliente = rs.getInt("NIF");
            if (nifcliente == parameters.getNifCliente()){
                naoexiste = false;
                break;
            }
        }
        
        if(naoexiste){
            ClienteInexistente clientenaoexiste = new ClienteInexistente();
            clientenaoexiste.setMensagem("Nao existe Cliente");
            clientenaoexiste.setNif(parameters.getNifCliente());
            throw new ClienteInexistente_Exception(clientenaoexiste.getMensagem(), clientenaoexiste);
        }
        
        String sql1 = "SELECT * FROM EMISSORES";
        PreparedStatement pstmt1 = conn.prepareStatement(sql1);
        ResultSet rs1 = pstmt1.executeQuery();
        naoexiste = true;
        
        while (rs1.next()) {
            int nifemissor = rs1.getInt("NIF");
            if (nifemissor == parameters.getNifEmissor()){
                naoexiste = false;
                break;
            }
        }
        
        if(naoexiste){
            EmissorInexistente emissornaoexiste = new EmissorInexistente();
            emissornaoexiste.setMensagem("Nao existe o emissor especificado");
            emissornaoexiste.setNif(parameters.getNifEmissor());
            throw new EmissorInexistente_Exception(emissornaoexiste.getMensagem(), emissornaoexiste);
        }
        
        int totalPrecoItens = 0;
        for (ItemFatura i : parameters.getItens())
            totalPrecoItens += (i.getPreco() * i.getQuantidade());
        
        if(totalPrecoItens != parameters.getTotal()){
            TotaisIncoerentes incoerencia = new TotaisIncoerentes();
            incoerencia.setRazao("Incoerencia da soma com o total do preço dos items");
            throw new TotaisIncoerentes_Exception(incoerencia.getRazao(), incoerencia);
        }
        
        String sql2 = "SELECT * FROM SERIE";
        PreparedStatement pstmt2 = conn.prepareStatement(sql2);
        ResultSet rs2 = pstmt2.executeQuery();
        
        boolean naoexisteserie = true;
        
        while (rs2.next()) {
            int numserie = rs2.getInt("NRSERIE");
            if (numserie == parameters.getNumSerie()){
            	naoexisteserie = false;
                break;
            }
        }
        
        String sql3 = "SELECT * FROM FATURAS";
        PreparedStatement pstmt3 = conn.prepareStatement(sql3);
        ResultSet rs3 = pstmt3.executeQuery();
        
        boolean jaExisteFatura = false;
        
        while (rs3.next()) {
            int nrSerie = rs3.getInt("NRSERIE");
            int nrSequencia = rs3.getInt("NRSEQUENCIA");
            if (nrSerie == parameters.getNumSerie() && nrSequencia == parameters.getNumSeqFatura()){
                jaExisteFatura = true;
                break;
            }
        }
        
        if(jaExisteFatura){
        	FaturaInvalida f = new FaturaInvalida();
        	f.setNumSerie(parameters.getNumSerie());
        	f.setNumSeqFatura(parameters.getNumSeqFatura());
        	f.setMensagem("Ja existe uma fatura com este numero de serie e sequencia");
        	throw new FaturaInvalida_Exception(f.getMensagem(), f);
        }
        
        boolean limiteUltrapassado = false;
        boolean serieInvalida = false;
        
        XMLGregorianCalendar calendar = parameters.getData();
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -10); //verificar validade max 10 dias
        
        GregorianCalendar c = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
        
        XMLGregorianCalendar xmlGCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        
        if(xmlGCalendar.compare(calendar) >= 0) //se a data actual menos 10 dias for superior à data da factura esta é invalida
            limiteUltrapassado = true;
        
        
        if(limiteUltrapassado){
            FaturaInvalida f = new FaturaInvalida();
            f.setNumSerie(parameters.getNumSerie());
            f.setNumSeqFatura(parameters.getNumSeqFatura());
            f.setMensagem("Fatura nao e valida: validade expirou");
            throw new FaturaInvalida_Exception(f.getMensagem(), f);
        }   
        
        if(parameters.getNumSeqFatura() < 1 || parameters.getNumSeqFatura() > 4){
        	serieInvalida = true;
        }
        
        if(serieInvalida){
        	FaturaInvalida f = new FaturaInvalida();
            f.setNumSerie(parameters.getNumSerie());
            f.setNumSeqFatura(parameters.getNumSeqFatura());
            f.setMensagem("Fatura nao e valida: numSequencia out of bounds");
            throw new FaturaInvalida_Exception(f.getMensagem(), f);
        	
        }
        
        if(parameters.getIva() != 23){
            FaturaInvalida f = new FaturaInvalida();
            f.setNumSerie(parameters.getNumSerie());
            f.setNumSeqFatura(parameters.getNumSeqFatura());
            f.setMensagem("Fatura nao e valida: Iva != 23");
            throw new FaturaInvalida_Exception(f.getMensagem(), f);
        }   
            
        String sql4 = "INSERT INTO FATURAS (NRSERIE, NRSEQUENCIA, VALOR, IVA, NOMEEMISSOR, NIFEMISSOR, NIFUTILIZADOR, DIA, MES, ANO) VALUES(?,?,?,?,?,?,?,?,?,?)";
    
        PreparedStatement pstmt4 = conn.prepareStatement(sql4);
        
        pstmt4.setInt(1, parameters.getNumSerie());
        pstmt4.setInt(2, parameters.getNumSeqFatura());
        pstmt4.setInt(3, parameters.getTotal());
        pstmt4.setInt(4, parameters.getIva());
        pstmt4.setString(5, parameters.getNomeEmissor());
        pstmt4.setInt(6, parameters.getNifEmissor());
        pstmt4.setInt(7, parameters.getNifCliente());
        pstmt4.setInt(8, parameters.getData().getDay());
        pstmt4.setInt(9, parameters.getData().getMonth());
        pstmt4.setInt(10, parameters.getData().getYear());
        pstmt4.executeUpdate();
                
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }	
    }
    
    /**
     * Listar facturas.
     *
     * @param nifEmissor the nif emissor
     * @param nifCliente the nif cliente
     * @return the list
     * @throws ClienteInexistente_Exception the cliente inexistente_ exception
     * @throws EmissorInexistente_Exception the emissor inexistente_ exception
     */
    public List<Fatura> listarFacturas(Integer nifEmissor, Integer nifCliente) throws ClienteInexistente_Exception, EmissorInexistente_Exception {
    	
        if(dbMainUrl.equals(dbCurrentUrl) && activateSecondaryPort){
            secondaryPort.listarFacturas(nifEmissor, nifCliente);
        }
    	List<Fatura> _faturas = new ArrayList<Fatura>();
    	
    	try{
    		
    		Class.forName(dbDriverName);
            Connection conn = DriverManager.getConnection(getCurrentDb(), dbUsername, dbPassword);
                
            String sql = "SELECT * FROM UTILIZADORES";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            boolean naoexiste = true;
            
            while (rs.next()) {
                int nif_Cliente = rs.getInt("NIF");
                if (nif_Cliente == nifCliente){
                    naoexiste = false;
                    break;
                }
            }
            
            if(naoexiste){
                ClienteInexistente clientenaoexiste = new ClienteInexistente();
                clientenaoexiste.setMensagem("Nao existe Cliente");
                clientenaoexiste.setNif(nifCliente);
                throw new ClienteInexistente_Exception(clientenaoexiste.getMensagem(), clientenaoexiste);
            }
            
            String sql1 = "SELECT * FROM EMISSORES";
            PreparedStatement pstmt1 = conn.prepareStatement(sql1);
            ResultSet rs1 = pstmt1.executeQuery();
            naoexiste = true;
            
            while (rs1.next()) {
                int nifemissor = rs1.getInt("NIF");
                if (nifemissor == nifEmissor){
                    naoexiste = false;
                    break;
                }
            }
            
            if(naoexiste){
                EmissorInexistente emissornaoexiste = new EmissorInexistente();
                emissornaoexiste.setMensagem("Nao existe o emissor especificado");
                emissornaoexiste.setNif(nifEmissor);
                throw new EmissorInexistente_Exception(emissornaoexiste.getMensagem(), emissornaoexiste);
            }
            
            String sql2 = "SELECT * FROM FATURAS WHERE NIFUTILIZADOR=? AND NIFEMISSOR=?";
            PreparedStatement pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setInt(1,  nifCliente);
            pstmt2.setInt(2, nifEmissor);
            ResultSet rs2 = pstmt2.executeQuery();
            
            String sql3 = "SELECT * FROM ITEM WHERE NRSEQUENCIA=? AND NRSERIE=?";
            PreparedStatement pstmt3 = conn.prepareStatement(sql3);
            ResultSet rs3;
            
            Fatura f = new Fatura();
            ItemFatura i = new ItemFatura();
            GregorianCalendar c;
            XMLGregorianCalendar xmlGCalendar;
            while (rs2.next()){
                f.setIva(rs2.getInt("IVA"));
                f.setTotal(rs2.getInt("VALOR"));
                f.setNifCliente(rs2.getInt("NIFUTILIZADOR"));
                f.setNifEmissor(rs2.getInt("NIFEMISSOR"));
                f.setNomeEmissor(rs2.getString("NOMEEMISSOR"));
                f.setNumSerie(rs2.getInt("NRSERIE"));
                f.setNumSeqFatura(rs2.getInt("NRSEQUENCIA"));
                c = new GregorianCalendar(rs2.getInt("ANO"), rs2.getInt("MES"), rs2.getInt("DIA"));
                
                xmlGCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                
                f.setData(xmlGCalendar);
                
                pstmt3 = conn.prepareStatement(sql3);
                pstmt3.setInt(1, f.getNumSeqFatura());
                pstmt3.setInt(2, f.getNumSerie());
                rs3 = pstmt3.executeQuery();
                
                while(rs3.next()){
                    i.setDescricao(rs3.getString("DESCRICAO"));
                    i.setQuantidade(rs3.getInt("QUANTIDADE"));
                    i.setPreco(rs3.getInt("PRECO"));
                    
                    f.getItens().add(copiaItem(i));
                }
                
                _faturas.add(copiaFatura(f));
            }
            return _faturas;
            
        } catch (SQLException e) {
                e.printStackTrace();
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }	
    	
    	return _faturas;
    }

    /**
     * Consultar iva devido.
     *
     * @param nifEmissor the nif emissor
     * @param dia the dia
     * @param mes the mes
     * @param data the ano
     * @return the int
     * @throws EmissorInexistente_Exception the emissor inexistente_ exception
     */
    public int consultarIVADevido(int nifEmissor, XMLGregorianCalendar data) throws EmissorInexistente_Exception {
        
        if(dbMainUrl.equals(dbCurrentUrl) && activateSecondaryPort){
            secondaryPort.consultarIVADevido(nifEmissor, data);
        }
    	float IVA = 0;
    	
        try{
        	
        	Class.forName(dbDriverName);
            Connection conn = DriverManager.getConnection(getCurrentDb(), dbUsername, dbPassword);
            
            String sql = "SELECT * FROM EMISSORES";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            boolean naoexiste = true;
            
            while (rs.next()) {
                int nifemissor = rs.getInt("NIF");
                if (nifemissor == nifEmissor){
                    naoexiste = false;
                    break;
                }
            }
            
            if(naoexiste){
                EmissorInexistente naoexisteemissor = new EmissorInexistente();
                naoexisteemissor.setMensagem("Nao existe o emissor especificado");
                naoexisteemissor.setNif(nifEmissor);
                throw new EmissorInexistente_Exception(naoexisteemissor.getMensagem(), naoexisteemissor);
            }
            
            String sql1 = "SELECT * FROM FATURAS WHERE NIFEMISSOR=?";
            PreparedStatement pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setInt(1, nifEmissor);
            ResultSet rs1 = pstmt1.executeQuery();
            int calendarData;
            while(rs1.next()){
                calendarData = rs1.getInt("ANO");
                if (calendarData == data.getYear())
                    IVA += rs1.getInt("VALOR")*(0.23/1.23);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }	
    	return Math.round(IVA);
    }
}
