package pt.ist.rest.service;

import pt.ist.rest.exceptions.ClienteNaoExisteException;
import pt.ist.rest.service.dto.ClienteDto;


public class LoginService extends PortalRestauranteService {
	
	private ClienteDto clienteDto;
	
	public LoginService (ClienteDto cliente){
		this.clienteDto=cliente;
	}
	
	public final void dispatch() throws ClienteNaoExisteException {
		String cl = clienteDto.getUsername();
		if (cl == null)
			throw new ClienteNaoExisteException();
	}

}
