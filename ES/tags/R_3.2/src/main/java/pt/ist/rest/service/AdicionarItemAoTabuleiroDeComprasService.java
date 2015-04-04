package pt.ist.rest.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.domain.Prato;
import pt.ist.rest.service.dto.ClienteDto;
import pt.ist.rest.service.dto.PratoDto;
import pt.ist.rest.service.dto.UtilizadorDto;

public class AdicionarItemAoTabuleiroDeComprasService extends PortalRestauranteService {
    PratoDto pratoDTO;
    UtilizadorDto utilizadorDto;
    Integer quantidade;
    Boolean _alterar = false;
    
    public AdicionarItemAoTabuleiroDeComprasService(PratoDto pratoDTO, UtilizadorDto utilizadorDTO, Integer quantidade,boolean alterar) {
        this.pratoDTO = pratoDTO;
        this.utilizadorDto = utilizadorDTO;
        this.quantidade = quantidade;
        this._alterar = alterar;
    }
    
    public final void dispatch (){
        PortalRestaurante portal = FenixFramework.getRoot();
        Prato prato = portal.getPratoByIdFromRestaurante(portal.getPratoByNomeFromRestaurante(pratoDTO.getNome(), portal.getRestauranteByName(pratoDTO.getRestaurante())).getId(), portal.getRestauranteByName(pratoDTO.getRestaurante()));
        portal.adicionaPratoCliente(prato, utilizadorDto.get_username(), quantidade, this._alterar);
    }
}
