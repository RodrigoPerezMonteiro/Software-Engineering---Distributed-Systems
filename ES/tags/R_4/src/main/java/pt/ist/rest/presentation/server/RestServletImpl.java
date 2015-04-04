package pt.ist.rest.presentation.server;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import pt.ist.rest.DatabaseBootstrap;
import pt.ist.rest.exceptions.ClienteNaoExisteException;
import pt.ist.rest.exceptions.WrongPasswordException;
import pt.ist.rest.exceptions.restException;
import pt.ist.rest.presentation.client.RestServlet;
import pt.ist.rest.service.ActualizaSaldoService;
import pt.ist.rest.service.AdicionarItemAoTabuleiroDeComprasService;
import pt.ist.rest.service.AlteraPrecoMaximoPratoService;
import pt.ist.rest.service.AlteraQuantidadePratoTabuleiroService;
import pt.ist.rest.service.IServiceBridge;
import pt.ist.rest.service.LoginService;
import pt.ist.rest.service.ObterMenuService;
import pt.ist.rest.service.ObterTabuleiroService;
import pt.ist.rest.service.ProcurarPratosPorNomeService;
import pt.ist.rest.service.ProcurarPratosPorTipoService;
import pt.ist.rest.service.RegistaPagamentoTabuleiroComprasService;
import pt.ist.rest.service.ServiceBridgeLocal;
import pt.ist.rest.service.ServiceBridgeRemote;
import pt.ist.rest.service.VerificaPasswordService;
import pt.ist.rest.service.dto.ChequeDto;
import pt.ist.rest.service.dto.ClienteDto;
import pt.ist.rest.service.dto.ItemDto;
import pt.ist.rest.service.dto.PratoDto;
import pt.ist.rest.service.dto.RestauranteDto;
import pt.ist.rest.service.dto.UtilizadorDto;
import pt.ist.rest.service.ObterRestauranteService;

public class RestServletImpl extends RemoteServiceServlet implements RestServlet{

    private static final long serialVersionUID = 1L;
    public IServiceBridge serviceBridge;

    @Override
	  public void adicionaPratoTabuleiro(PratoDto pratoDTO, UtilizadorDto utilizadorDTO, Integer quantidade, Boolean alterar){
        if(!alterar){
		AdicionarItemAoTabuleiroDeComprasService service = new AdicionarItemAoTabuleiroDeComprasService(pratoDTO, utilizadorDTO, quantidade, alterar);
	    service.execute();
        }else{
            AlteraQuantidadePratoTabuleiroService service = new AlteraQuantidadePratoTabuleiroService(utilizadorDTO,pratoDTO, quantidade);
            service.execute();
        }
	  }

	  @Override
	  public void alteraQtdPratoTabuleiro(ClienteDto clienteDTO, PratoDto pratoDTO, int quantidade){
	    AlteraQuantidadePratoTabuleiroService service = new AlteraQuantidadePratoTabuleiroService(clienteDTO, pratoDTO, quantidade);
	    service.execute();
	  }
	  
	  @Override
	  public void pagarTabuleiro(String username){
		RegistaPagamentoTabuleiroComprasService service = new RegistaPagamentoTabuleiroComprasService(username);
	    service.execute();
	  }

	  @Override
	  public List<RestauranteDto> listRestaurantes() {
		  ObterRestauranteService service = new ObterRestauranteService();
		  service.execute();
		  return service.getRestaurantes();
	  }
	  
	  @Override
	  public List<ItemDto> mostraTabuleiro(String username) {
		  ObterTabuleiroService service = new ObterTabuleiroService(username);
		  service.execute();
		  return service.getTabuleiro();
	  }

	  
	  @Override
	  public List<PratoDto> listMenuPratos(String nomeRest) {
		  ObterMenuService service = new ObterMenuService(nomeRest);
		  service.execute();
		  return service.getPratos();
	  }
	  
	  @Override
	  public List<PratoDto> listPratosPorNome(String nomePrato){
		  ProcurarPratosPorNomeService service = new ProcurarPratosPorNomeService(nomePrato);
		  service.execute();
		  return service.getPratos();
	  }
	  
	  @Override
	  public List<PratoDto> listPratosPorTipo(String tipo){
		  ProcurarPratosPorTipoService service = new ProcurarPratosPorTipoService(tipo);
		  service.execute();
		  return service.getPratosTipo();
	  }
	  
	  @Override
	  public void initServer(String serverType) {
		  DatabaseBootstrap.init();
		  if(serverType.equals("ES+SD")){
              serviceBridge =  new ServiceBridgeRemote();
          }
          else{
              serviceBridge =  new ServiceBridgeLocal();
          }
	  }

	  @Override
	  public void login(ClienteDto clienteDTO) throws ClienteNaoExisteException, WrongPasswordException {
            LoginService service = new LoginService(clienteDTO);
            VerificaPasswordService vPassword = new VerificaPasswordService(clienteDTO.get_username(), clienteDTO.getPassword());
            service.execute();
            vPassword.execute();
	  }
	  
	  @Override
      public int UsarChequeRefeicao(ClienteDto clientDTO, ChequeDto chequeDTO) {
	      serviceBridge.CashChecks(clientDTO, chequeDTO);
          return serviceBridge.getValor();
      }
	  
	  @Override
	  public void ActualizaSaldoCliente(ClienteDto clienteDTO){
	      System.out.println("USERNAME DO CLIENTE DTO: \t" + clienteDTO.getUsername());
	      System.out.println("SALDO DO CLIENTE DTO: \t" + clienteDTO.getSaldo());
	      ActualizaSaldoService actSaldoService = new ActualizaSaldoService(clienteDTO);
	      actSaldoService.execute();
	  }

    @Override
    public void AlteraPrecoMaximo(double precoMaximo) {
        AlteraPrecoMaximoPratoService altPrecoMaximoService = new AlteraPrecoMaximoPratoService(precoMaximo);
        altPrecoMaximoService.execute();
    }

    @Override
    public void PassaFatura(ClienteDto clienteDTO) {
      if(clienteDTO==null)System.out.println("CLIENT DTO NULL");
      serviceBridge.passaFactura(clienteDTO);
      serviceBridge.execute();
        
    }
}
