package pt.ist.rest.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.Cliente;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.domain.Prato;
import pt.ist.rest.service.dto.ClienteDto;
import pt.ist.rest.service.dto.PratoDto;


public class LikePratoService extends PortalRestauranteService{
		 
		private ClienteDto _clienteDTO;
		private PratoDto _pratoDTO;
		
		public LikePratoService (ClienteDto clienteDTO, PratoDto pratoDTO) {
	        this._clienteDTO = clienteDTO;
	        this._pratoDTO = pratoDTO;
	    }
		
		public final void dispatch (){
			PortalRestaurante portal = FenixFramework.getRoot();
			Cliente cl = portal.getClienteByUsername(_clienteDTO.getUsername());
			Prato prato = portal.getPratoByNomeFromRestaurante(_pratoDTO.getNome(), portal.getRestauranteByName(_pratoDTO.getRestaurante()));
			cl.adicionaPratoQueGosta(prato);
		}
		

}
