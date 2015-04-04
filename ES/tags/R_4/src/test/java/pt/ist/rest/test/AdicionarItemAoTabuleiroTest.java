package pt.ist.rest.test;


import junit.framework.Assert;
import org.junit.Test;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.pstm.Transaction;
import pt.ist.rest.domain.Cliente;
import pt.ist.rest.domain.GestorRestaurante;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.domain.Prato;
import pt.ist.rest.domain.Restaurante;
import pt.ist.rest.exceptions.GestorAdicionaItemException;
import pt.ist.rest.exceptions.NaoExisteTabuleiroException;
import pt.ist.rest.exceptions.PratoNaoExisteException;
import pt.ist.rest.exceptions.RestauranteJaContemPratoException;
import pt.ist.rest.exceptions.RestauranteJaExisteException;
import pt.ist.rest.exceptions.RestauranteMaxPratosException;
import pt.ist.rest.exceptions.TabuleiroVazioException;
import pt.ist.rest.exceptions.restException;
import pt.ist.rest.service.AdicionarItemAoTabuleiroDeComprasService;
import pt.ist.rest.service.dto.ClienteDto;
import pt.ist.rest.service.dto.GestorRestauranteDto;
import pt.ist.rest.service.dto.PratoDto;


public class AdicionarItemAoTabuleiroTest extends RestServiceTestCase {

    private String nomePratoParcial1;
    private String nomePratoParcial2;
    private String nomePratoParcial3;
    private Prato p1;
    private Prato p2;
    private Prato p3;
    private Prato p4;
    private Prato p5;
    private Restaurante rest1;
    private Restaurante rest2;
    private Cliente cl1;
    private Cliente cl2;
    private GestorRestaurante gest1;
    private AdicionarItemAoTabuleiroDeComprasService addItem;
    private int quantidade_minustwo = -2;
    private int quantidade_minusone = -1;
    private int quantidade_zero = 0;
    private int quantidade_one = 1;
    private int quantidade_two = 2;
    private PortalRestaurante _portal;

    public AdicionarItemAoTabuleiroTest(String testName) {
        super(testName);
    }

    public PortalRestaurante getPortal() {
        boolean committed = false;
        try {
            Transaction.begin();
            _portal = FenixFramework.getRoot();
            return _portal;
        } finally {
            if(!committed) {
                Transaction.abort();
            }
        }
    }
    
@Override
    protected void setUp() {
        super.setUp();
        nomePratoParcial1 = "acalhau";
        nomePratoParcial2 = "Carn";
        nomePratoParcial3 = "Azeite";
        boolean committed = false;
        try {
            Transaction.begin();
            p1 = new Prato("Bacalhau com Natas","Peixe", 2, 2,getPortal().generatePratoId());
            p2 = new Prato("Bacalhau a Bras","Peixe", 3, 3,getPortal().generatePratoId());
            p3 = new Prato("Carninha","Carne", 70, 1,getPortal().generatePratoId());
            p4 = new Prato("Carne de Vaca","Carne", 7, 3,getPortal().generatePratoId());
            p5 = new Prato("Peixe embacalhau","Peixe", 8, 60,getPortal().generatePratoId());
            cl1 = new Cliente("Francisco Peixeiro", "Gancho", "hothot", "Rua da Pilhas","frpe@gmail.com", 1);
            cl2 = new Cliente("Francisca", "princesa", "Botemel", "Rua do Vizinho","frpinxexa@gmail.com", 30);
            gest1 = new GestorRestaurante("Pedro Francisco", "Hipster", "toroymoi");
            rest1 = new Restaurante("Afonso dos Leitoes", "Rua do Cruzeiro");
            rest2 = new Restaurante("Ze do Pipo", "Rua das Conchas");
            rest1.adicionaPrato(p1.getNome(),p1.getTipo(), p1.getPreco(), p1.getCalorias(), p1.getId());
            rest2.adicionaPrato(p2.getNome(),p2.getTipo(), p2.getPreco(), p2.getCalorias(),p2.getId());
            rest1.adicionaPrato(p3.getNome(),p3.getTipo(), p3.getPreco(), p3.getCalorias(),p3.getId());
            rest2.adicionaPrato(p4.getNome(),p4.getTipo(), p4.getPreco(), p4.getCalorias(),p4.getId());
            rest2.adicionaPrato(p5.getNome(),p5.getTipo(), p5.getPreco(), p5.getCalorias(),p5.getId());
            Transaction.commit();
            committed = true;
        }catch (RestauranteMaxPratosException e) {
            System.out.print("\n" + e);
        }catch (RestauranteJaContemPratoException e) {
            System.out.print("\n" + e);
        } 
        finally {
            if(!committed) {
                Transaction.abort();
            }
        }
    }

@Override
    protected void tearDown() {
        super.tearDown();
        p1 = null;
        p2 = null;
        p3 = null;
        p4 = null;
        p5 = null;
        rest1 = null;
        rest2 = null;
        cl1 = null;
        cl2 = null;
        addItem = null;
    }

    public void testClienteAdicionaItemZeroQuantidade() throws NaoExisteTabuleiroException, PratoNaoExisteException {
        PratoDto pratoDTO = new PratoDto(p1.getNome(), p1.getCalorias(), p1.getPreco(),p1.getClassificacao(), getPortal().getPratoById(p1.getId()).getRestaurante().getNome());
        ClienteDto clienteDTO = new ClienteDto(cl1.get_nome(), cl1.get_username(),cl1.get_password(), cl1.get_morada(), cl1.get_email(), cl1.get_saldo());
        addItem = new AdicionarItemAoTabuleiroDeComprasService(pratoDTO, clienteDTO,quantidade_zero, false);
     
        try {
            addItem.execute();
        } catch (NaoExisteTabuleiroException e) {
            System.out.println("\n" +e);
        }catch (PratoNaoExisteException e1) {
            System.out.println("\n" +e1);
        }
        assertFalse("O prato nao foi adicionado ao tabuleiro do cliente: "+ cl1.getNome()+" ",cl1.hasTabuleiro());
    }

    @Test(expected=GestorAdicionaItemException.class)
    public void testGestorAdicionaItem() {
        PratoDto pratoDTO = new PratoDto(p1.getNome(), p1.getCalorias(), p1.getPreco(),p1.getClassificacao(), getPortal().getPratoById(p1.getId()).getRestaurante().getNome());
        GestorRestauranteDto gestorDTO = new GestorRestauranteDto(gest1.get_nome(),gest1.get_username(), gest1.get_password());
        addItem = new AdicionarItemAoTabuleiroDeComprasService(pratoDTO, gestorDTO, quantidade_zero, false);
        try {
            addItem.execute();
        } catch (GestorAdicionaItemException e1) {
            System.out.println("\n" + e1);

        }
    }

    @Test(expected=PratoNaoExisteException.class)
    public void testAdicionarPratoInexistente() {
        PratoDto pratoDTO = new PratoDto("cozido", 5, 20,0,"Afonso dos Leitoes" );
        ClienteDto clienteDTO = new ClienteDto(cl1.get_nome(), cl1.get_username(),cl1.get_password(), cl1.get_morada(), cl1.get_email(), cl1.get_saldo());
        addItem = new AdicionarItemAoTabuleiroDeComprasService(pratoDTO, clienteDTO,quantidade_zero, false);
    }
    
    public void testAdicionarQuantidadePositiva() {
        PratoDto pratoDTO = new PratoDto(p1.getNome(), p1.getCalorias(), p1.getPreco(),p1.getClassificacao(), getPortal().getPratoById(p1.getId()).getRestaurante().getNome());
        ClienteDto clienteDTO = new ClienteDto(cl1.get_nome(), cl1.get_username(),cl1.get_password(), cl1.get_morada(), cl1.get_email(), cl1.get_saldo());
        addItem = new AdicionarItemAoTabuleiroDeComprasService(pratoDTO, clienteDTO,quantidade_two, false);

        try {
            addItem.execute();
        } catch (NaoExisteTabuleiroException e) {
            System.out.println("\n" +e);
        }catch (PratoNaoExisteException e2) {
            System.out.println("\n" +e2);
        }
        assertTrue("Foi adicionado um tabuleiro do cliente: "+ cl1.getNome()+" ",cl1.hasTabuleiro());
        assertTrue("O prato foi adicionado ao tabuleiro do cliente: "+ cl1.getNome()+" ",cl1.getTabuleiro().hasPrato(p1));
        assertEquals("Foram adicionados dois pratos "+p1.getNome()+" ao tabuleiro do cliente "+cl1.getUsername()+" ",quantidade_two, cl1.getTabuleiro().getItemOfPrato(p1.getNome()).getQuantidade());
    }
    

    public void testAdicionarQuantidadeNegativaMantendoQuantidadePositiva() {
        PratoDto pratoDTO = new PratoDto(p1.getNome(), p1.getCalorias(), p1.getPreco(),p1.getClassificacao(), getPortal().getPratoById(p1.getId()).getRestaurante().getNome());
        ClienteDto clienteDTO = new ClienteDto(cl1.get_nome(), cl1.get_username(),cl1.get_password(), cl1.get_morada(), cl1.get_email(), cl1.get_saldo());
        addItem = new AdicionarItemAoTabuleiroDeComprasService(pratoDTO, clienteDTO,quantidade_minusone, false);
        
        try {
            addItem.execute();
        } catch (NaoExisteTabuleiroException e) {
            System.out.println("\n" +e);
        }catch (PratoNaoExisteException e1) {
            System.out.println("\n" +e1);
        }
        assertEquals("Foi retirado um prato "+p1.getNome()+" ao tabuleiro do cliente "+cl1.getUsername()+" ",quantidade_one, cl1.getTabuleiro().getItemOfPrato(p1.getNome()).getQuantidade());
    }
    
    @Test(expected=TabuleiroVazioException.class)
    public void testAdicionarQuantidadeNegativaFicandoVazio() {
        PratoDto pratoDTO = new PratoDto(p1.getNome(), p1.getCalorias(), p1.getPreco(),p1.getClassificacao(), getPortal().getPratoById(p1.getId()).getRestaurante().getNome());
        ClienteDto clienteDTO = new ClienteDto(cl1.get_nome(), cl1.get_username(),cl1.get_password(), cl1.get_morada(), cl1.get_email(), cl1.get_saldo());
        addItem = new AdicionarItemAoTabuleiroDeComprasService(pratoDTO, clienteDTO,quantidade_minustwo, false);
        
        try {
            addItem.execute();
        } catch (NaoExisteTabuleiroException e) {
            System.out.println("\n" +e);
        }catch (PratoNaoExisteException e2) {
            System.out.println("\n" +e2);
        }
        assertTrue("Existe Tabuleiro do cliente "+ cl1.getNome()+" ",cl1.hasTabuleiro());
        assertEquals("Foram retirados dois pratos "+p1.getNome()+" do tabuleiro do cliente "+cl1.getUsername()+" ",quantidade_zero, cl1.getTabuleiro().getItemOfPrato(p1.getNome()).getQuantidade());
    }
}
