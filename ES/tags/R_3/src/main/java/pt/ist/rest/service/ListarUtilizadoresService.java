package pt.ist.rest.service;

import java.util.ArrayList;
import java.util.List;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.Cliente;
import pt.ist.rest.domain.GestorRestaurante;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.domain.Utilizador;
import pt.ist.rest.service.dto.ClienteDto;
import pt.ist.rest.service.dto.GestorRestauranteDto;
import pt.ist.rest.service.dto.PratoDto;
import pt.ist.rest.service.dto.UtilizadorDto;


public class ListarUtilizadoresService extends PortalRestauranteService {
	
	List<ClienteDto> clienteList = new ArrayList<ClienteDto>();
	List<GestorRestauranteDto> gestorList = new ArrayList<GestorRestauranteDto>();
	
	public final void dispatch (){
		PortalRestaurante portal = FenixFramework.getRoot();
		for (Cliente cl : portal.getClientes()){
			ClienteDto clienteDto = new ClienteDto(cl.getNome(), cl.getUsername(), cl.getPassword(), cl.getMorada(), cl.getEmail(), cl.getSaldo());
			clienteList.add(clienteDto);
		}
		
		for (GestorRestaurante g : portal.getGestores()){
			GestorRestauranteDto gestorDto = new GestorRestauranteDto(g.getNome(), g.getUsername(), g.getPassword());
			gestorList.add(gestorDto);
		}
		
	}
	
	public List<ClienteDto> getClientes(){
		return clienteList;
	}
	
	public List<GestorRestauranteDto> getGestores(){
		return gestorList;
	}


}
