package pt.ist.rest.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.Cliente;
import pt.ist.rest.domain.Item;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.exceptions.restException;
import pt.ist.rest.service.dto.ClienteDto;
import pt.ist.rest.service.dto.ItemDto;
import pt.ist.rest.service.dto.PratoDto;
import pt.ist.rest.service.dto.UtilizadorDto;

public class AlteraQuantidadePratoTabuleiroService extends
        PortalRestauranteService {

    private UtilizadorDto _utilizadorDTO;
    private PratoDto _pratoDTO;
    private int _qtd;
    private boolean alterar = true;

    public AlteraQuantidadePratoTabuleiroService(UtilizadorDto utilizadorDTO,PratoDto pratoDTO,int qtd) {
        this._utilizadorDTO = utilizadorDTO;
        this._pratoDTO = pratoDTO;
        this._qtd = qtd;
    }

    public final void dispatch() {
        PortalRestaurante _portal = FenixFramework.getRoot();
        Cliente cl = _portal.getClienteByUsername(_utilizadorDTO.get_username());

        for (Item i : cl.getTabuleiro().getListaItens()) {
            if (_pratoDTO.getNome().equals(i.get_Prato().getNome())) {
                _portal.adicionaPratoCliente(i.get_Prato(), cl.getUsername(), _qtd, alterar);
            }
        }
    }
}
