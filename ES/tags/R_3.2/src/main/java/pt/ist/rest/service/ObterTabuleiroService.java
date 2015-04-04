
package pt.ist.rest.service;

import java.util.ArrayList;
import java.util.List;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.Cliente;
import pt.ist.rest.domain.Compra;
import pt.ist.rest.domain.Item;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.service.dto.ItemDto;
import pt.ist.rest.service.dto.PratoDto;

public class ObterTabuleiroService extends PortalRestauranteService{
	
	List<ItemDto> itemList = new ArrayList<ItemDto>();
	
	private String username;
	
	public ObterTabuleiroService(String username){
		this.username = username;
	}
	
	public final void dispatch (){
		PortalRestaurante portal = FenixFramework.getRoot();
		Cliente cl = portal.getClienteByUsername(username);
		Compra c = cl.getTabuleiro();
		ItemDto itemDTO;
		PratoDto pratoDTO;
		
		for (Item i : c.getListaItens()){
			pratoDTO = new PratoDto(i.get_Prato().getNome(),i.getPrato().getPreco(), i.get_Prato().getCalorias(), i.get_Prato().getClassificacao(), i.get_Prato().getId(), i.getPrato().getRestaurante().getNome());
			itemDTO = new ItemDto(pratoDTO, i.getQuantidade());
			itemList.add(itemDTO);
		}
		
	}
	
	public List<ItemDto> getTabuleiro(){
		return this.itemList;
	}


}