package pt.ist.rest.presentation.client;

import java.util.List;

import pt.ist.rest.service.dto.ChequeDto;
import pt.ist.rest.service.dto.ClienteDto;
import pt.ist.rest.service.dto.ItemDto;
import pt.ist.rest.service.dto.PratoDto;
import pt.ist.rest.service.dto.RestauranteDto;
import pt.ist.rest.service.dto.UtilizadorDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RestServletAsync {
    
      void initServer(String serverType, AsyncCallback<Void> callback);
      void login(ClienteDto clienteDTO, AsyncCallback<Void> callback);
      void listRestaurantes(AsyncCallback<List<RestauranteDto>> callback);
      void listMenuPratos(String nomeRest, AsyncCallback<List<PratoDto>> callback);
	  void adicionaPratoTabuleiro(PratoDto pratoDTO, UtilizadorDto utilizadorDTO, Integer quantidade,Boolean alterar, AsyncCallback<Void> callback);
	  void mostraTabuleiro(String username, AsyncCallback<List<ItemDto>> callback);
	  void alteraQtdPratoTabuleiro(ClienteDto clienteDTO, PratoDto pratoDTO, int quantidade, AsyncCallback<Void> callback);
	  void pagarTabuleiro(String username, AsyncCallback<Void> callback);
	  void UsarChequeRefeicao(ClienteDto clientDTO, ChequeDto chequeDTO,AsyncCallback<Integer> callback);
	  void ActualizaSaldoCliente(ClienteDto clienteDTO,AsyncCallback<Void> callback);
}
