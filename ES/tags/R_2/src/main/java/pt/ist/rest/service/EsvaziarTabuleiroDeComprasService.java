package pt.ist.rest.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.Cliente;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.service.dto.ClienteDto;

public class EsvaziarTabuleiroDeComprasService extends PortalRestauranteService{
    private ClienteDto _clienteDTO;

    public EsvaziarTabuleiroDeComprasService(ClienteDto clienteDTO) {
        this._clienteDTO = clienteDTO;
    }

    public final void dispatch() {
        PortalRestaurante portal = FenixFramework.getRoot();
        Cliente cl = portal.getClienteByUsername(_clienteDTO.get_nome());
        cl.getTabuleiro().limpaLista();
    }

}
