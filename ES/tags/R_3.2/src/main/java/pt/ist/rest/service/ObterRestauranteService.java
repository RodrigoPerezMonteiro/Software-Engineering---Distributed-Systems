package pt.ist.rest.service;

import java.util.ArrayList;
import java.util.List;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.domain.Restaurante;
import pt.ist.rest.service.dto.PratoDto;
import pt.ist.rest.service.dto.RestauranteDto;

public class ObterRestauranteService extends PortalRestauranteService{
	
	List<RestauranteDto> restList = new ArrayList<RestauranteDto>();
	
	public final void dispatch (){
		PortalRestaurante portal = FenixFramework.getRoot();
		for (Restaurante rest : portal.getRestaurantes()){
			RestauranteDto restDto = new RestauranteDto(rest.getNome(), rest.getMorada());
			restList.add(restDto);
		}
		
	}
	
	public List<RestauranteDto> getRestaurantes(){
		return restList;
	}


}
