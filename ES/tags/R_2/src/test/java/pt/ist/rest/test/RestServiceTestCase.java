package pt.ist.rest.test;

import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.Compra;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.domain.Prato;
import pt.ist.rest.domain.Restaurante;
import pt.ist.rest.domain.Utilizador;
import junit.framework.TestCase;
import java.util.Set;
import jvstm.Atomic;

public abstract class RestServiceTestCase extends TestCase {
    static {
        if (FenixFramework.getConfig() == null) {
            FenixFramework.initialize(new Config() {
                {
                    dbAlias = "//localhost:3306/rest";
                    dbUsername = "root";
                    dbPassword = "rootroot";
                    domainModelPath = "src/main/dml/domain.dml";
                    rootClass = PortalRestaurante.class;
                }
            });
        }
    }

    protected RestServiceTestCase(String msg) {
        super(msg);
    }

    protected RestServiceTestCase() {
        super();
    }
    
    protected void setUp() {
        cleanRest();
    }

    protected void tearDown() {
        cleanRest();
    }

    /**
     * Clears the persistent state in the PhoneBook application in the sense that the
     * current root object no longer holds any Person instance.
     **/
    @Atomic
    protected void cleanRest() {
        PortalRestaurante portal = FenixFramework.getRoot();
        Set<Utilizador> allUsers = portal.getUtilizadorSet();
        Set<Restaurante> allRests = portal.getRestauranteSet();
        // Set<Restaurante> allCheques = portal.getChequeRefeicaoSet();
        Set<Compra> allCompras = portal.getCompraSet();
        allUsers.clear();
        allRests.clear();
        //  allCheques.clear();
        allCompras.clear();
    }

    @Atomic
    protected Utilizador getUtilizador(String utilizadorUsername) {
        PortalRestaurante portal = FenixFramework.getRoot();
        return portal.getUtilizadorByUsename(utilizadorUsername);
    }

    @Atomic
    protected boolean checkUtilizador(String utilizadorUsername) {
        PortalRestaurante portal = FenixFramework.getRoot();
        return portal.existeUtilizador(utilizadorUsername);
    }

    @Atomic
    protected void addClient(String nome,
                             String username,
                             String password,
                             String morada,
                             String email,
                             double saldo) {
        PortalRestaurante portal = FenixFramework.getRoot();
        portal.registaCliente(nome, username, password, morada, email, saldo);
    }

    @Atomic
    protected void addGestor(String nome, String username, String password) {
        PortalRestaurante portal = FenixFramework.getRoot();
        portal.adicionaGestor(nome, username, password);
    }

    @Atomic
    protected Restaurante getRestaurante(String nomeRestaurante) {
        PortalRestaurante portal = FenixFramework.getRoot();
        return portal.getRestauranteByName(nomeRestaurante);
    }

    @Atomic
    protected boolean checkRestaurante(String nomeRestaurante) {
        PortalRestaurante portal = FenixFramework.getRoot();
        return portal.existeRestaurante(nomeRestaurante);
    }

    @Atomic
    protected void addRestaurante(String nomeRestaurante, String morada) {
        PortalRestaurante portal = FenixFramework.getRoot();
        portal.adicionaRestaurante(nomeRestaurante, morada);
    }
    
    protected Prato getPrato(String nomePrato) {
        PortalRestaurante portal = FenixFramework.getRoot();
        return portal.getPratoByNome(nomePrato);
    }
    
    protected boolean checkPrato(String nomePrato) {
        PortalRestaurante portal = FenixFramework.getRoot();
        return portal.existePrato(nomePrato);
    }
}
