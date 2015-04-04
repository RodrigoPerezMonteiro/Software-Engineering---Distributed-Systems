package pt.ist.rest;

import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import jvstm.Atomic;
import pt.ist.registofatura.RegistoFaturaLocal;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.exceptions.ClienteJaExisteException;
import pt.ist.rest.exceptions.GestorAddRemovePratosException;
import pt.ist.rest.exceptions.GestorAdicionaItemException;
import pt.ist.rest.exceptions.GestorJaGereRestauranteException;
import pt.ist.rest.exceptions.MaxPrecoPratoException;
import pt.ist.rest.exceptions.RestauranteJaContemPratoException;
import pt.ist.rest.exceptions.RestauranteJaExisteException;
import pt.ist.rest.exceptions.RestauranteMaxPratosException;
import pt.ist.rest.exceptions.RestauranteNaoExisteException;
import pt.ist.rest.exceptions.UtilizadorJaExisteException;
import pt.ist.rest.exceptions.UtilizadorNaoExisteException;

// TODO: Auto-generated Javadoc
/**
 * The Class SetupDomain.
 */
public class RestSetup {

    /**
     * The main method that connect to the database.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        DatabaseBootstrap.init();
        populateDomain();
    }

    /**
     * Populate domain.
     */
    @Atomic
    public static void populateDomain() {
        PortalRestaurante _portal = FenixFramework.getRoot();
       // if (_portal.get_idPrato() == null) {
            _portal.set_maxVotosPorCliente(15);
            _portal.set_minPratosParaClassificarRest(3);
            _portal.set_minClassificacaoPratos(0);
            _portal.set_maxPratosPorRestaurante(15);
            _portal.set_idPrato(0);
            _portal.set_nif(1234);
            _portal.set_nome("PortalRest");
            _portal.set_maxPrecoPrato(7.0);
            _portal.set_iva(23);
            _portal.set_currentFacturasNumber(0);
        System.out.println("Creating the Managers... ");
        
        try {
            _portal.adicionaGestor("Passos Lebre","mng", "nm8");
        } catch (UtilizadorJaExisteException e) {
            System.out.println("\n" +e);
        }
        
        try {
            _portal.adicionaGestor("Paulo Portão","pp", "pp");
        } catch (UtilizadorJaExisteException e) {
            System.out.println("\n" +e);
        }
       
        System.out.println("Creating the Clients... ");
        
        try {
            _portal.registaCliente("Zé Ninguém", "zeze", "z3z3", "Lisboa, Portugal", "ze.ninguem@ist.utl.pt",100.00);
        } catch (ClienteJaExisteException e) {
            System.out.println("\n" +e);
        }
       
        try {
        _portal.registaCliente("Maria Silva", "mariazinha", "****", "Porto, Portugal", "maria.silva@ist.utl.pt",0.00);
        } catch (ClienteJaExisteException e) {
            System.out.println("\n" +e);
        }
        
        System.out.println("Adding the Restaurants... ");
        
        try {
        _portal.adicionaRestaurante("Barriga Cheia", "Lisboa, Portugal");
        } catch (RestauranteJaExisteException e) {
            System.out.println("\n" +e);
        }
        
        try {
        _portal.adicionaRestaurante("Barriga Feliz", "Lisboa, Portugal");
        } catch (RestauranteJaExisteException e) {
            System.out.println("\n" +e);
        }
        
        System.out.println("Associating the Managers to Restaurants... ");
        
        try {
        _portal.associaGestor("mng", "Barriga Cheia");
        } catch (GestorJaGereRestauranteException e) {
            System.out.println("\n" +e);
        } catch (RestauranteNaoExisteException e1) {
        	System.out.println("\n" +e1);
        } catch (UtilizadorNaoExisteException e2) {
        	System.out.println("\n" +e2);
        }
        
        try {
        _portal.associaGestor("pp", "Barriga Feliz");
        } catch (GestorJaGereRestauranteException e) {
            System.out.println("\n" +e);
        } catch (RestauranteNaoExisteException e1) {
        	System.out.println("\n" +e1);
        } catch (UtilizadorNaoExisteException e2) {
        	System.out.println("\n" +e2);
        }
        
        System.out.println("Creating dishes and associating them to Restaurants... ");
        
        
        try {
        _portal.addPratoToRestaurante("Bitoque","Carne",8.0,0.0,"Barriga Cheia", "mng");
        } catch (GestorAddRemovePratosException e) {
            System.out.println("\n" +e);
        } catch (RestauranteMaxPratosException e1) {
            System.out.println("\n" +e1);
        } catch (RestauranteJaContemPratoException e2) {
            System.out.println("\n" +e2);
        }catch (MaxPrecoPratoException e3) {
            System.out.println("\n" +e3);
        }
        
        try {
        _portal.addPratoToRestaurante("Bacalhau com batatas","Peixe",6.0,0.0,"Barriga Feliz", "pp");
        } catch (GestorAddRemovePratosException e) {
            System.out.println("\n" +e);
        } catch (RestauranteMaxPratosException e1) {
            System.out.println("\n" +e1);
        } catch (RestauranteJaContemPratoException e2) {
            System.out.println("\n" +e2);
        }catch (MaxPrecoPratoException e3) {
            System.out.println("\n" +e3);
        }
        
        try {
        _portal.addPratoToRestaurante("Bitoque","Carne",7.0,0.0,"Barriga Feliz", "pp");
        } catch (GestorAddRemovePratosException e) {
            System.out.println("\n" +e);
        } catch (RestauranteMaxPratosException e1) {
            System.out.println("\n" +e1);
        } catch (RestauranteJaContemPratoException e2) {
            System.out.println("\n" +e2);
        }catch (MaxPrecoPratoException e3) {
            System.out.println("\n" +e3);
        }
        
        try {
        _portal.addPratoToRestaurante("Canja de Galinha","Carne",2.0,0.0,"Barriga Feliz", "pp");
        } catch (GestorAddRemovePratosException e) {
            System.out.println("\n" +e);
        } catch (RestauranteMaxPratosException e1) {
            System.out.println("\n" +e1);
        } catch (RestauranteJaContemPratoException e2) {
            System.out.println("\n" +e2);
        }catch (MaxPrecoPratoException e3) {
            System.out.println("\n" +e3);
        }
        
        //Excepcoes
        try {
            _portal.setClienteNIF("mariazinha", 222222222);
        } catch (Exception e) {
            System.out.println("\n" +e);
        }
        
        //Excepcoes
        try {
            _portal.setClienteNIF("zeze", 111111111);
        } catch (Exception e) {
            System.out.println("\n" +e);
        }
        
        //Excepcoes NIF
        try {
            _portal.registaCliente("Alice Alves", "alice", "aaa", "Aveiro, Portugal", "alice.alves@sonet.pt", 200.00);
            _portal.setClienteNIF("alice", 555555555);
        } catch (ClienteJaExisteException e) {
            System.out.println("\n" +e);
        }
        //Excepcoes NIF
        try {
            _portal.registaCliente("Bruno Bento", "bruno", "bbb", "Beja, Portugal", "bruno.bento@sonet.pt", 300.00);
            _portal.setClienteNIF("bruno", 333333333);
        } catch (ClienteJaExisteException e) {
            System.out.println("\n" +e);
        }
        //Excepcoes NIF
        try {
            _portal.registaCliente("Carlos Calado", "carlos", "ccc", "Coimbra, Portugal", "carlos.calado@sonet.pt", 200.00);
            _portal.setClienteNIF("carlos", 444444444);
        } catch (ClienteJaExisteException e) {
            System.out.println("\n" +e);
        }
        
    }
}
