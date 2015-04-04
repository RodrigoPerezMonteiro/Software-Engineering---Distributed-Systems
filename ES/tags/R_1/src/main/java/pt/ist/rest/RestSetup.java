package pt.ist.rest;

import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import jvstm.Atomic;

import pt.ist.rest.domain.PortalRestaurante;

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
                dbAlias = "//localhost:3306/restdb";
                dbUsername = "rest";
                dbPassword = "r3st";
//                dbAlias = "//localhost:3306/rest";
//                dbUsername = "root";
//                dbPassword = "rootroot";
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
        
        _portal.adicionaGestor("Passos Lebre","mng", "nm8");
        _portal.adicionaGestor("Paulo Portão","pp", "pp");
        
        System.out.println("Creating the Clients... ");
        
        _portal.registaCliente("Zé Ninguém", "zeze", "z3z3", "Lisboa, Portugal", "ze.ninguem@ist.utl.pt");
        _portal.registaCliente("Maria Silva", "mariazinha", "****", "Porto, Portugal", "maria.silva@ist.utl.pt");
        
        System.out.println("Adding the Restaurants... ");
        
        _portal.adicionaRestaurante("Barriga Cheia", "Lisboa, Portugal");
        _portal.adicionaRestaurante("Barriga Feliz", "Lisboa, Portugal");
        
        System.out.println("Associating the Managers to Restaurants... ");
        
        _portal.associaGestor("mng", "Barriga Cheia");
        _portal.associaGestor("pp", "Barriga Feliz");
        
        System.out.println("Creating dishes and associating them to Restaurants... ");
        
        _portal.addPratoToRestaurante("Bacalhau com batatas",null,null,null, "Barriga Cheia", "mng");
        _portal.addPratoToRestaurante("Bacalhau com batatas",null,null,null, "Barriga Feliz", "pp");
        _portal.addPratoToRestaurante("Bitoque",null,null,null, "Barriga Feliz", "pp");
        _portal.addPratoToRestaurante("Canja de Galinha",null,null,null, "Barriga Feliz", "pp");
        
    }
}
