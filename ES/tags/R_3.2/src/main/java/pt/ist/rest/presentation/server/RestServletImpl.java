package pt.ist.rest.presentation.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import pt.ist.rest.DatabaseBootstrap;
import pt.ist.rest.exceptions.ClienteNaoExisteException;
import pt.ist.rest.exceptions.WrongPasswordException;
import pt.ist.rest.exceptions.restException;
import pt.ist.rest.presentation.client.RestServlet;
import pt.ist.rest.service.ActualizaSaldoService;
import pt.ist.rest.service.AdicionarItemAoTabuleiroDeComprasService;
import pt.ist.rest.service.AlteraQuantidadePratoTabuleiroService;
import pt.ist.rest.service.LoginService;
import pt.ist.rest.service.ObterMenuService;
import pt.ist.rest.service.ObterTabuleiroService;
import pt.ist.rest.service.RegistaPagamentoTabuleiroComprasService;
import pt.ist.rest.service.ServiceBridgeLocal;
import pt.ist.rest.service.VerificaPasswordService;
import pt.ist.rest.service.dto.ChequeDto;
import pt.ist.rest.service.dto.ClienteDto;
import pt.ist.rest.service.dto.ItemDto;
import pt.ist.rest.service.dto.PratoDto;
import pt.ist.rest.service.dto.RestauranteDto;
import pt.ist.rest.service.dto.UtilizadorDto;
import pt.ist.rest.service.ObterRestauranteService;

public class RestServletImpl extends RemoteServiceServlet implements RestServlet{

	  /**
     * 
     */
    private static final long serialVersionUID = 1L;

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
	  public void initServer(String serverType) {
		  DatabaseBootstrap.init();
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
	      ServiceBridgeLocal serviceBridgeLocal= new ServiceBridgeLocal();
	      serviceBridgeLocal.CashChecks(clientDTO, chequeDTO);
          return serviceBridgeLocal.getValor();
      }
	  
	  @Override
	  public void ActualizaSaldoCliente(ClienteDto clienteDTO){
	      ActualizaSaldoService actSaldoService = new ActualizaSaldoService(clienteDTO);
	      actSaldoService.execute();
	  }
}
