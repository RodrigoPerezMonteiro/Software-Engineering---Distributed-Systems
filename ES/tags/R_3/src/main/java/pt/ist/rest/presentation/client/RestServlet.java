package pt.ist.rest.presentation.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import pt.ist.rest.exceptions.ClienteNaoExisteException;
import pt.ist.rest.exceptions.WrongPasswordException;
import pt.ist.rest.service.dto.ClienteDto;
import pt.ist.rest.service.dto.ItemDto;
import pt.ist.rest.service.dto.PratoDto;
import pt.ist.rest.service.dto.RestauranteDto;
import pt.ist.rest.service.dto.UtilizadorDto;

@RemoteServiceRelativePath("service")
public interface RestServlet extends RemoteService  {

	  public void initServer(String serverType);
	  public void login(ClienteDto clienteDTO) throws ClienteNaoExisteException, WrongPasswordException ;
	  public List<RestauranteDto> listRestaurantes();
	  public List<PratoDto> listMenuPratos(String nomeRest);
	  public void adicionaPratoTabuleiro(PratoDto pratoDTO, UtilizadorDto utilizadorDTO, Integer quantidade);
	  public List<ItemDto> mostraTabuleiro(String username);
	  public void alteraQtdPratoTabuleiro(ClienteDto clienteDTO, PratoDto pratoDTO, int quantidade);
	  public void pagarTabuleiro(String username);	  
}
