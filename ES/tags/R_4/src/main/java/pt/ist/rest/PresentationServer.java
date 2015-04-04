package pt.ist.rest;

import java.util.ArrayList;
import java.util.List;

import pt.ist.chequerefeicao.CheckAlreadyUsedException;
import pt.ist.chequerefeicao.InvalidCheckException;
import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.pstm.Transaction;
import pt.ist.rest.domain.Item;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.domain.Prato;
import pt.ist.rest.exceptions.ClienteJaGostaPratoException;
import pt.ist.rest.exceptions.ClienteMaxVotosException;
import pt.ist.rest.exceptions.ClienteNaoExisteException;
import pt.ist.rest.exceptions.GestorAddRemovePratosException;
import pt.ist.rest.exceptions.GestorAdicionaItemException;
import pt.ist.rest.exceptions.GestorJaGereRestauranteException;
import pt.ist.rest.exceptions.NaoExisteTabuleiroException;
import pt.ist.rest.exceptions.RestauranteJaExisteException;
import pt.ist.rest.exceptions.RestauranteNaoExisteException;
import pt.ist.rest.exceptions.RestauranteSemRequisitosClassificacaoException;
import pt.ist.rest.exceptions.UtilizadorJaExisteException;
import pt.ist.rest.exceptions.UtilizadorNaoExisteException;
import pt.ist.rest.exceptions.restException;
import pt.ist.rest.service.ActualizaSaldoService;
import pt.ist.rest.service.AdicionarItemAoTabuleiroDeComprasService;
import pt.ist.rest.service.AdicionarPratoService;
import pt.ist.rest.service.AssociaGestorService;
import pt.ist.rest.service.LikePratoService;
import pt.ist.rest.service.ListarUtilizadoresService;
import pt.ist.rest.service.ObterMenuService;
import pt.ist.rest.service.ObterRestauranteComClassificacaoService;
import pt.ist.rest.service.ObterRestauranteService;
import pt.ist.rest.service.ObterTabuleiroService;
import pt.ist.rest.service.RegistaPagamentoTabuleiroComprasService;
import pt.ist.rest.service.RegistarRestauranteService;
import pt.ist.rest.service.ServiceBridgeLocal;
import pt.ist.rest.service.dto.ChequeDto;
import pt.ist.rest.service.dto.ClienteDto;
import pt.ist.rest.service.dto.GestorRestauranteDto;
import pt.ist.rest.service.dto.ItemDto;
import pt.ist.rest.service.dto.PratoDto;
import pt.ist.rest.service.dto.RestauranteDto;
import pt.ist.rest.service.dto.UtilizadorDto;


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
        List<String> cheques = new ArrayList<String>();
        DatabaseBootstrap.init();  
        System.out.println("Welcome to the Rest application!");

        boolean committed = false;
        PortalRestaurante _portal;
        try {
            Transaction.begin();
            _portal = FenixFramework.getRoot();
            Transaction.commit();
            committed = true;
        } finally {
            if (!committed)
                Transaction.abort();
        }
        
        committed = false;
        try {
            Transaction.begin();
            try {
                RegistarRestauranteService registo = new RegistarRestauranteService(
                        "Barriga Cheia", "Porto, Portugal");
               registo.dispatch();
            } catch (RestauranteJaExisteException e) {
                System.out.print("\n" + e);
            }
            Transaction.commit();
            committed = true;
        } finally {
            if (!committed)
                Transaction.abort();
        }

        committed = false;
        try {
            Transaction.begin();
            try {
                AssociaGestorService associaGestor = new AssociaGestorService("Barriga Cheia", "pp");
                associaGestor.dispatch();
            } catch (GestorJaGereRestauranteException e) {
                System.out.print("\n" + e);
            }
            Transaction.commit();
            committed = true;
        } finally {
            if (!committed)
                Transaction.abort();
        }
        
        committed = false;
        try {
                Transaction.begin();
                try {
            	ListarUtilizadoresService listaUsers = new ListarUtilizadoresService ();
            	listaUsers.dispatch();
            	for (ClienteDto clienteDto : listaUsers.getClientes()) {
                    System.out.println("\nCLIENTE - Nome: " + clienteDto.getNome() + "\tusername: " + clienteDto.getUsername() + "\tpassword: " + clienteDto.getPassword() + "\t morada = " + clienteDto.getMorada() + "\temail = " + clienteDto.getEmail() + "\tsaldo = " + clienteDto.getSaldo());
                }
            	for (GestorRestauranteDto gestorDto : listaUsers.getGestores()) {
                    System.out.println("\nGESTOR - Nome: " + gestorDto.getNome() + "\tusername: " + gestorDto.getUsername() + "\tpassword: " + gestorDto.getPassword());
                }
            	} catch (Exception e) {
                    System.out.print("\n" + e);
                }
                Transaction.commit();
                committed = true;
        } finally {
            if (!committed)
                Transaction.abort();
        }
        
        committed = false;
        try {
            Transaction.begin();
            try {
             ObterRestauranteService obterRestaurantesService = new ObterRestauranteService();
             obterRestaurantesService.dispatch();
             for (RestauranteDto restDto : obterRestaurantesService.getRestaurantes()) {
                System.out.println("Restaurante - " + "Nome: " + restDto.getNome() + " Morada: "
                        + restDto.getMorada());
                ObterMenuService menu = new ObterMenuService(restDto.getNome());
                menu.dispatch();
                for(PratoDto p: menu.getPratos()){
                    System.out.println("\nPrato - " + "Nome: " + p.getNome() + " Preco: " + p.getPreco()+" Calorias: "+p.getCalorias());
                }
            }
            } catch (GestorAddRemovePratosException e) {
                System.out.print("\n" + e);
            } catch (RestauranteSemRequisitosClassificacaoException e1) {
                System.out.print("\n" + e1);
            }catch (RestauranteNaoExisteException e2) {
                System.out.print("\n" + e2);
            }
            Transaction.commit();
            committed = true;
        } finally {
            if (!committed)
                Transaction.abort();
        }

        committed = false;
        try {
        Transaction.begin();
        try {
            AdicionarPratoService addPratoServ = new AdicionarPratoService("Bitoque", "Barriga Cheia", "pp");
            addPratoServ.dispatch();
        } catch (GestorAddRemovePratosException e) {
            System.out.print("\n" + e);
        }
        Transaction.commit();
        committed = true;
        } finally {
            if (!committed)
                Transaction.abort();
        }
        
        committed = false;
        try {
            Transaction.begin();
            try {
                ObterRestauranteComClassificacaoService obterRestaurantesService = new ObterRestauranteComClassificacaoService();
                    obterRestaurantesService.dispatch();
                    for (RestauranteDto restDto : obterRestaurantesService.getRestaurantes()) {
                            System.out.println("\nRestaurante - " + "Nome: " + restDto.getNome() + "\tClassificao: "+ restDto.getClassificacao());
                    } 
            }catch (GestorAddRemovePratosException e) {
                System.out.print("\n" + e);
            }
            Transaction.commit();
            committed = true;
            } finally {
            if (!committed)
                Transaction.abort();
        }
        
        committed = false;
        try{
                Transaction.begin();
                try {
                    ClienteDto clDto = new ClienteDto(_portal.getClienteByUsername("zeze").getNome(), "zeze", _portal.getClienteByUsername("zeze").getPassword(), _portal.getClienteByUsername("zeze").getMorada(), _portal.getClienteByUsername("zeze").getEmail(), _portal.getClienteByUsername("zeze").getSaldo());
                    PratoDto pDto = new PratoDto("Bitoque", _portal.getPratoByNomeFromRestaurante("Bitoque", _portal.getRestauranteByName("Barriga Feliz")).getPreco(), _portal.getPratoByNomeFromRestaurante("Bitoque", _portal.getRestauranteByName("Barriga Feliz")).getCalorias(), _portal.getPratoByNomeFromRestaurante("Bitoque", _portal.getRestauranteByName("Barriga Feliz")).getClassificacao(), "Barriga Feliz");
                    LikePratoService likePrato = new LikePratoService(clDto, pDto);
                    likePrato.dispatch();
                } catch (ClienteMaxVotosException e) {
                    System.out.println("\n" +e);
                } catch (ClienteJaGostaPratoException e1) {
                    System.out.println("\n" +e1);
                }
                Transaction.commit();
                committed = true;
                } finally {
            if (!committed)
                Transaction.abort();
        }
        
        committed = false;
        try {
            Transaction.begin();
            try {
        	LikePratoService likePrato = new LikePratoService(new ClienteDto(_portal.getClienteByUsername("zeze").getNome(), "zeze", _portal.getClienteByUsername("zeze").getPassword(), _portal.getClienteByUsername("zeze").getMorada(), _portal.getClienteByUsername("zeze").getEmail(), _portal.getClienteByUsername("zeze").getSaldo()), 
            		new PratoDto("Canja de Galinha", _portal.getPratoByNomeFromRestaurante("Canja de Galinha", _portal.getRestauranteByName("Barriga Feliz")).getPreco(), _portal.getPratoByNomeFromRestaurante("Canja de Galinha", _portal.getRestauranteByName("Barriga Feliz")).getCalorias(), _portal.getPratoByNomeFromRestaurante("Canja de Galinha", _portal.getRestauranteByName("Barriga Feliz")).getClassificacao(), "Barriga Feliz"));
            likePrato.dispatch();
            } catch (ClienteMaxVotosException e) {
                System.out.println("\n" +e);
            } catch (ClienteJaGostaPratoException e1) {
                System.out.println("\n" +e1);
            }
            Transaction.commit();
            committed = true;
        } finally {
            if (!committed)
                Transaction.abort();
        }
        
        committed = false;
        try {
            Transaction.begin();
            try {
            LikePratoService likePrato = new LikePratoService(new ClienteDto(_portal.getClienteByUsername("zeze").getNome(), "zeze", _portal.getClienteByUsername("zeze").getPassword(), _portal.getClienteByUsername("zeze").getMorada(), _portal.getClienteByUsername("zeze").getEmail(), _portal.getClienteByUsername("zeze").getSaldo()), 
                new PratoDto("Bacalhau com batatas", _portal.getPratoByNomeFromRestaurante("Bacalhau com batatas", _portal.getRestauranteByName("Barriga Feliz")).getPreco(), _portal.getPratoByNomeFromRestaurante("Bacalhau com batatas", _portal.getRestauranteByName("Barriga Feliz")).getCalorias(), _portal.getPratoByNomeFromRestaurante("Bacalhau com batatas", _portal.getRestauranteByName("Barriga Feliz")).getClassificacao(), "Barriga Feliz"));
            likePrato.dispatch();
            } catch (ClienteMaxVotosException e) {
                System.out.println("\n" +e);
            } catch (ClienteJaGostaPratoException e1) {
                System.out.println("\n" +e1);
            }
            Transaction.commit();
            committed = true;
        } finally {
            if (!committed)
                Transaction.abort();
        }
        
        committed = false;
        try{
            Transaction.begin();
            try {
            LikePratoService likePrato = new LikePratoService(new ClienteDto(_portal.getClienteByUsername("mariazinha").getNome(), "mariazinha", _portal.getClienteByUsername("mariazinha").getPassword(), _portal.getClienteByUsername("mariazinha").getMorada(), _portal.getClienteByUsername("mariazinha").getEmail(), _portal.getClienteByUsername("mariazinha").getSaldo()), 
                    new PratoDto("Canja de Galinha", _portal.getPratoByNomeFromRestaurante("Canja de Galinha", _portal.getRestauranteByName("Barriga Feliz")).getPreco(), _portal.getPratoByNomeFromRestaurante("Canja de Galinha", _portal.getRestauranteByName("Barriga Feliz")).getCalorias(), _portal.getPratoByNomeFromRestaurante("Canja de Galinha", _portal.getRestauranteByName("Barriga Feliz")).getClassificacao(), "Barriga Feliz"));
            likePrato.dispatch();
            } catch (ClienteMaxVotosException e) {
                System.out.println("\n" +e);
            } catch (ClienteJaGostaPratoException e1) {
                System.out.println("\n" +e1);
            }
            Transaction.commit();
            committed = true;
        } finally {
        if (!committed)
            Transaction.abort();
        }
        
        committed = false;
        try {
            Transaction.begin();
            try {
                    ObterRestauranteComClassificacaoService obterRestaurantesService = new ObterRestauranteComClassificacaoService();
                    obterRestaurantesService.dispatch();
                    for (RestauranteDto restDto : obterRestaurantesService.getRestaurantes()) {
                        try {
                            System.out.println("\nRestaurante - " + "Nome: " + restDto.getNome() + "\tClassificao: "+ restDto.getClassificacao());
                        } catch (RestauranteSemRequisitosClassificacaoException e1) {
                            System.out.print("\n" + e1);
                        }
                    }
            }catch (GestorAddRemovePratosException e) {
                System.out.print("\n" + e);
            }
            Transaction.commit();
            committed = true;
            } finally {
            if (!committed)
                Transaction.abort();
        }
        
        
        committed = false;
        try {
            Transaction.begin();
            try {
             PratoDto pratoDto = new PratoDto("Canja de Galinha", _portal.getPratoByNomeFromRestaurante("Canja de Galinha", _portal.getRestauranteByName("Barriga Feliz")).getPreco(), _portal.getPratoByNomeFromRestaurante("Canja de Galinha", _portal.getRestauranteByName("Barriga Feliz")).getCalorias(), _portal.getPratoByNomeFromRestaurante("Canja de Galinha", _portal.getRestauranteByName("Barriga Feliz")).getClassificacao(), "Barriga Feliz");
             ClienteDto clienteDto = new ClienteDto(_portal.getClienteByUsername("zeze").getNome(), "zeze", _portal.getClienteByUsername("zeze").getPassword(), _portal.getClienteByUsername("zeze").getMorada(), _portal.getClienteByUsername("zeze").getEmail(), _portal.getClienteByUsername("zeze").getSaldo());
             AdicionarItemAoTabuleiroDeComprasService addItemService = new AdicionarItemAoTabuleiroDeComprasService(pratoDto, clienteDto, 3, false);
             addItemService.dispatch();
            } catch (GestorAdicionaItemException e) {
                System.out.println("\n" +e);
            } catch (UtilizadorNaoExisteException e1) {
                System.out.println("\n" +e1);
            } Transaction.commit();
            committed = true;
        } finally {
            if (!committed)
                Transaction.abort();
        }
        
        committed = false;
        try {
            Transaction.begin();
            try {
             PratoDto pratoDto = new PratoDto("Bacalhau com batatas", _portal.getPratoByNomeFromRestaurante("Bacalhau com batatas", _portal.getRestauranteByName("Barriga Feliz")).getPreco(), _portal.getPratoByNomeFromRestaurante("Bacalhau com batatas", _portal.getRestauranteByName("Barriga Feliz")).getCalorias(), _portal.getPratoByNomeFromRestaurante("Bacalhau com batatas", _portal.getRestauranteByName("Barriga Feliz")).getClassificacao(), "Barriga Feliz");
             ClienteDto clienteDto = new ClienteDto(_portal.getClienteByUsername("zeze").getNome(), "zeze", _portal.getClienteByUsername("zeze").getPassword(), _portal.getClienteByUsername("zeze").getMorada(), _portal.getClienteByUsername("zeze").getEmail(), _portal.getClienteByUsername("zeze").getSaldo());
             AdicionarItemAoTabuleiroDeComprasService addItemService = new AdicionarItemAoTabuleiroDeComprasService(pratoDto, clienteDto, 2, false);
             addItemService.dispatch();
            } catch (GestorAdicionaItemException e) {
                System.out.println("\n" +e);
            } catch (UtilizadorNaoExisteException e1) {
                System.out.println("\n" +e1);
            } Transaction.commit();
            committed = true;
        } finally {
            if (!committed)
                Transaction.abort();
        }
   
        
        committed = false;
        try {
            Transaction.begin();
            try {
             ObterTabuleiroService obterTabuleiroService = new ObterTabuleiroService("zeze");
             obterTabuleiroService.dispatch();
             System.out.println("\nLista de Compras do zeze:\n");
             for (ItemDto iDto : obterTabuleiroService.getTabuleiro()) {
                System.out.println("\nPrato: " + iDto.getPratoDto().getNome()+ "\tQuantidade: " + iDto.getQuantidade());
            }
            } catch (ClienteNaoExisteException e) {
                System.out.println("\n" + e);
            }catch (NaoExisteTabuleiroException e1) {
                System.out.println("\n" + e1);
            }   
            Transaction.commit();
            committed = true;
        }finally {
            if (!committed)
                Transaction.abort();
        }
        
        committed = false;
        try {
            Transaction.begin();
            try {
             ObterTabuleiroService obterTabuleiroService = new ObterTabuleiroService("mariazinha");
             obterTabuleiroService.dispatch();
             System.out.println("\nLista de Compras da mariazinha:\n");
             for (ItemDto iDto : obterTabuleiroService.getTabuleiro()) {
                System.out.println("\nPrato: " + iDto.getPratoDto().getNome()+ "\tQuantidade: " + iDto.getQuantidade());
            }
            } catch (ClienteNaoExisteException e) {
                System.out.println("\n" + e);
            }catch (NaoExisteTabuleiroException e1) {
                System.out.println("\n" + e1);
            }
            Transaction.commit();
            committed = true;
        }finally{
            if (!committed)
                Transaction.abort();
        }
        
       
        
        committed = false;
        try {
            Transaction.begin();
            try {
             ObterTabuleiroService obterTabuleiroService = new ObterTabuleiroService("zeze");
             obterTabuleiroService.dispatch();
             System.out.println("\nTabuleiro do zeze:\n");
             int precoTotal=0;
             for (ItemDto iDto : obterTabuleiroService.getTabuleiro()) {
            	precoTotal+=iDto.getPratoDto().getPreco();
                System.out.println("\nPrato: " + iDto.getPratoDto().getNome()+ "\tPreco: " + iDto.getPratoDto().getPreco() + "\tId: " + iDto.getPratoDto().getId() + "\tQuantidade: " + iDto.getQuantidade());
             }
             System.out.println("\nPreco Total: " + precoTotal + "\tSaldo Cliente: " + _portal.getClienteByUsername("zeze").getSaldo());
            } catch (ClienteNaoExisteException e) {
                System.out.println("\n" + e);
            }catch (NaoExisteTabuleiroException e1) {
                System.out.println("\n" + e1);
            }
            Transaction.commit();
            committed = true;
        }finally{
            if (!committed)
                Transaction.abort();
        }
        
        cheques.add("19");
        pagamentoCompra("zeze", cheques);
        
        committed = false;
        try {
            Transaction.begin();
            try {
             ObterTabuleiroService obterTabuleiroService = new ObterTabuleiroService("zeze");
             obterTabuleiroService.dispatch();
             System.out.println("\nTabuleiro do zeze:\n");
             int precoTotal=0;
             for (ItemDto iDto : obterTabuleiroService.getTabuleiro()) {
                precoTotal+=iDto.getPratoDto().getPreco();
                System.out.println("\nPrato: " + iDto.getPratoDto().getNome()+ "\tPreco: " + iDto.getPratoDto().getPreco() + "\tId: " + iDto.getPratoDto().getId() + "\tQuantidade: " + iDto.getQuantidade());
             }
             System.out.println("\nPreco Total: " + precoTotal + "\tSaldo Cliente: " + _portal.getClienteByUsername("zeze").getSaldo());
            } catch (ClienteNaoExisteException e) {
                System.out.println("\n" + e);
            }catch (NaoExisteTabuleiroException e1) {
                System.out.println("\n" + e1);
            }
            Transaction.commit();
            committed = true;
        }finally{
            if (!committed)
                Transaction.abort();
        }
        
    }

    public static void pagamentoCompra(String username, List<String> checks) throws restException{
        //PortalRestaurante _portal = FenixFramework.getRoot();
        int valor;
       // ClienteDto cDto = new ClienteDto(_portal.getClienteByUsername(username).getNome(), username, _portal.getClienteByUsername(username).getPassword(), _portal.getClienteByUsername(username).getMorada(), _portal.getClienteByUsername(username).getEmail(), _portal.getClienteByUsername(username).getSaldo());
        ClienteDto cDto = new ClienteDto(username);
        ChequeDto chequeDto = new ChequeDto(checks,username);
        ServiceBridgeLocal serviceBridgeLocal= new ServiceBridgeLocal();
        serviceBridgeLocal.CashChecks(cDto, chequeDto);
        
        valor = serviceBridgeLocal.getValor();
        cDto.setSaldo(valor);
        ActualizaSaldoService actSaldoService = new ActualizaSaldoService(cDto);
        actSaldoService.execute();

        RegistaPagamentoTabuleiroComprasService regPagamentoTabuleiroService = new RegistaPagamentoTabuleiroComprasService(cDto.getUsername());
        regPagamentoTabuleiroService.execute();


    }
}
