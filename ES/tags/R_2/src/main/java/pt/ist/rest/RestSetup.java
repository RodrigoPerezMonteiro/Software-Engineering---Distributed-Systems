package pt.ist.rest;

import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import jvstm.Atomic;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.exceptions.ClienteJaExisteException;
import pt.ist.rest.exceptions.GestorAddRemovePratosException;
import pt.ist.rest.exceptions.GestorAdicionaItemException;
import pt.ist.rest.exceptions.GestorJaGereRestauranteException;
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
        FenixFramework.initialize(new Config() {
            {
//                dbAlias = "//localhost:3306/restdb";
//                dbUsername = "rest";
//                dbPassword = "r3st";
                dbAlias = "//localhost:3306/rest";
                dbUsername = "root";
                dbPassword = "rootroot";
                domainModelPath = "src/main/dml/domain.dml";
                rootClass = PortalRestaurante.class;
            }
        });
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
        //}
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
        _portal.registaCliente("Maria Silva", "mariazinha", "****", "Porto, Portugal", "maria.silva@ist.utl.pt",200.00);
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
        _portal.addPratoToRestaurante("Bacalhau com batatas",2.0,0.0,"Barriga Cheia", "mng");
        } catch (GestorAddRemovePratosException e) {
            System.out.println("\n" +e);
        } catch (RestauranteMaxPratosException e1) {
            System.out.println("\n" +e1);
        } catch (RestauranteJaContemPratoException e2) {
            System.out.println("\n" +e2);
        }
        
        try {
        _portal.addPratoToRestaurante("Bacalhau com batatas",2.0,0.0,"Barriga Feliz", "pp");
        } catch (GestorAddRemovePratosException e) {
            System.out.println("\n" +e);
        } catch (RestauranteMaxPratosException e1) {
            System.out.println("\n" +e1);
        } catch (RestauranteJaContemPratoException e2) {
            System.out.println("\n" +e2);
        }
        
        try {
        _portal.addPratoToRestaurante("Bitoque",1.0,0.0,"Barriga Feliz", "pp");
        } catch (GestorAddRemovePratosException e) {
            System.out.println("\n" +e);
        } catch (RestauranteMaxPratosException e1) {
            System.out.println("\n" +e1);
        } catch (RestauranteJaContemPratoException e2) {
            System.out.println("\n" +e2);
        }
        
        try {
        _portal.addPratoToRestaurante("Canja de Galinha",1.0,0.0,"Barriga Feliz", "pp");
        } catch (GestorAddRemovePratosException e) {
            System.out.println("\n" +e);
        } catch (RestauranteMaxPratosException e1) {
            System.out.println("\n" +e1);
        } catch (RestauranteJaContemPratoException e2) {
            System.out.println("\n" +e2);
        }
        
    }
}
