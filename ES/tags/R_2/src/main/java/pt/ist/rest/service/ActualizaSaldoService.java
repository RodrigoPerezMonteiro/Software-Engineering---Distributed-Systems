package pt.ist.rest.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.Cliente;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.service.dto.ClienteDto;

public class ActualizaSaldoService extends PortalRestauranteService {
		 
		private ClienteDto _clientDTO;
		
		public ActualizaSaldoService (ClienteDto clienteDTO) {
	        this._clientDTO = clienteDTO;
	    }
		
		public final void dispatch (){
			PortalRestaurante portal = FenixFramework.getRoot();
			Cliente cl = portal.getClienteByUsername(_clientDTO.getUsername());
			cl.setSaldo(_clientDTO.getSaldo());
		}
		

}
