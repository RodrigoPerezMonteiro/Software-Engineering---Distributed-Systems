package pt.ist.rest.presentationserver;

import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.pstm.Transaction;
import pt.ist.rest.domain.PortalRestaurante;


// TODO: Auto-generated Javadoc
/**
 * The main class for the rest application.
 */
public final class PresentationServer {

    /**
     * Instantiates a new presentation server.
     */
    private PresentationServer() {
        // Never used
    }
    
    /**
     * The main method.
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

        System.out.println("Welcome to the Rest application!");

        boolean committed = false;

        try {
            Transaction.begin();
            
            PortalRestaurante _portal = FenixFramework.getRoot();
            
            _portal.adicionaRestaurante("Barriga Cheia", "Porto, Portugal");
            
            _portal.associaGestor("pp", "Barriga Cheia");
            
            _portal.printUtilizadores();
            _portal.printRestaurantes();
            
            _portal.addPratoToRestaurante("Bitoque",null,null,null, "Barriga Cheia", "pp");
            
            System.out.println("Classificação do Barriga Cheia: "+_portal.getRestauranteByName("Barriga Cheia").getMediaClassificacao());
            System.out.println("Classificação do Barriga Cheia: "+_portal.getRestauranteByName("Barriga Feliz").getMediaClassificacao());
            
            _portal.likePrato("zeze", "Bitoque", "Barriga Feliz");
            _portal.likePrato("zeze", "Canja de Galinha", "Barriga Feliz");
            _portal.likePrato("zeze", "Bacalhau com batatas", "Barriga Feliz");
            
            _portal.likePrato("mariazinha", "Canja de Galinha", "Barriga Feliz");
            
            System.out.println("Classificação do Barriga Cheia: "+_portal.getRestauranteByName("Barriga Cheia").getMediaClassificacao());
            System.out.println("Classificação do Barriga Cheia: "+_portal.getRestauranteByName("Barriga Feliz").getMediaClassificacao());
            
            Transaction.commit();
            committed = true;
        } finally {
            if (!committed)
                Transaction.abort();
        }
    }
}