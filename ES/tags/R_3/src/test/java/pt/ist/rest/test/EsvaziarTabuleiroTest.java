package pt.ist.rest.test;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.pstm.Transaction;
import pt.ist.rest.domain.Cliente;
import pt.ist.rest.domain.Compra;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.domain.Prato;
import pt.ist.rest.exceptions.NaoExisteTabuleiroException;
import pt.ist.rest.exceptions.TabuleiroVazioException;
import pt.ist.rest.service.EsvaziarTabuleiroDeComprasService;
import pt.ist.rest.service.dto.ClienteDto;


public class EsvaziarTabuleiroTest extends
        RestServiceTestCase {

    private Prato p1;
    private Prato p2;
    private Prato p3;
    private Prato p4;
    private Prato p5;
    private Cliente cl1;
    private Cliente cl2;
    private PortalRestaurante port1;
    private Compra c1;
    private EsvaziarTabuleiroDeComprasService esvazia;

    public EsvaziarTabuleiroTest(String testName) {
        super(testName);
    }


    @Before
    protected void setUp() {
        super.setUp();
        boolean committed = false;
        try {
            Transaction.begin();
            PortalRestaurante portal = FenixFramework.getRoot();
            p1 = new Prato("tchuca a la funes mori", 3, 4, portal.generatePratoId());
            p2 = new Prato("Lasanha", 3, 3, portal.generatePratoId());
            p3 = new Prato("Tchica", 70, 1, portal.generatePratoId());
            p4 = new Prato("Prota", 7, 3, portal.generatePratoId());
            p5 = new Prato("Peixe embacalhau", 8, 60, portal.generatePratoId());
            cl1 = new Cliente("Francisco Peixeiro", "Gancho", "hothot", "Rua da Pilhas","frpe@gmail.com", 1);
            port1 = FenixFramework.getRoot();
            c1 = new Compra();
            Transaction.commit();
            committed = true;

        } finally {
            if (!committed) {
                Transaction.abort();
            }
        }
    }


    @After
    protected void terminou() {
        p1 = null;
        p2 = null;
        p3 = null;
        p4 = null;
        p5 = null;
        cl1 = null;
        cl2 = null;
        port1 = null;
        esvazia = null;
    }


    @Test
    public void testEsvaziouTabuleiro() throws NaoExisteTabuleiroException, TabuleiroVazioException {

        c1 = new Compra();

        port1.adicionaPratoCliente(p1, "Gancho", 100);
        port1.adicionaPratoCliente(p2, "Gancho", 120);
        port1.adicionaPratoCliente(p3, "Gancho", 90);
        port1.adicionaPratoCliente(p4, "Gancho", 50);
        port1.adicionaPratoCliente(p5, "Gancho", 70);

        ClienteDto clienteDTO = new ClienteDto(cl1.get_nome(), cl1.get_username(),
                cl1.get_password(), cl1.get_morada(), cl1.get_email(), cl1.get_saldo());
        esvazia = new EsvaziarTabuleiroDeComprasService(clienteDTO);

    	try{ esvazia.execute(); }
    	catch(NaoExisteTabuleiroException e){
    		fail("\n" + e);
    	}
    	catch(TabuleiroVazioException e){
    		fail("\n" + e);
    	}
        assertEquals(c1.getListaItens(), cl1.getTabuleiro().getListaItens());
    }
}
