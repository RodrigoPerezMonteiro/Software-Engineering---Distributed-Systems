package pt.ist.rest.service;


import java.util.ArrayList;
import java.util.List;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.Cliente;
import pt.ist.rest.domain.Item;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.service.dto.ItemDto;
import pt.ist.rest.service.dto.PratoDto;




public class ListarComprasService extends PortalRestauranteService {
	
	private String usernameCliente;
	ItemDto itemDTO;
	List<ItemDto> listaItens = new ArrayList<ItemDto>();
	
	public ListarComprasService(String usernameCliente){
		this.usernameCliente = usernameCliente;
	}
	
	public final void dispatch (){
		PortalRestaurante _portal = FenixFramework.getRoot();
		Cliente cl = _portal.getClienteByUsername(usernameCliente);
		
		for(Item i : cl.getTabuleiro().getListaItens()){
			itemDTO = new ItemDto (new PratoDto(i.get_Prato().getNome(), i.get_Prato().getCalorias(), i.get_Prato().getPreco(), i.get_Prato().getClassificacao(), _portal.getRestauranteDoPrato(i.get_Prato().getNome())), i.getQuantidade());
			listaItens.add(itemDTO);
		}
			
	}
	
	public List<ItemDto> getItemsDto(){
		return listaItens;
	}


}