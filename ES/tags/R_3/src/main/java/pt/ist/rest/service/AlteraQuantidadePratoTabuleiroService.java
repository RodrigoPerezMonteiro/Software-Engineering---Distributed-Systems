package pt.ist.rest.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.Cliente;
import pt.ist.rest.domain.Item;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.exceptions.restException;
import pt.ist.rest.service.dto.ClienteDto;
import pt.ist.rest.service.dto.ItemDto;
import pt.ist.rest.service.dto.PratoDto;

public class AlteraQuantidadePratoTabuleiroService extends PortalRestauranteService {

	private ClienteDto _clienteDTO;
	private PratoDto _pratoDTO;
	private int _qtd;

	public  AlteraQuantidadePratoTabuleiroService (ClienteDto clienteDTO, PratoDto pratoDTO, int qtd){
		this._clienteDTO = clienteDTO;
		this._pratoDTO = pratoDTO;
		this._qtd = qtd;
	}
			public final void dispatch (){
			PortalRestaurante _portal = FenixFramework.getRoot();
			Cliente cl = _portal.getClienteByUsername(_clienteDTO.getUsername());
			
			for(Item i : cl.getTabuleiro().getListaItens()){
				if(_pratoDTO.getNome().equals(i.get_Prato().getNome())){
					i.set_quantidade(_qtd);
				}
			}
	}
}
