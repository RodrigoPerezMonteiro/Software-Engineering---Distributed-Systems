package pt.ist.rest.service;

import java.util.List;
import java.util.ArrayList;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.domain.Prato;
import pt.ist.rest.domain.Restaurante;
import pt.ist.rest.service.dto.PratoDto;

public class ObterMenuService extends PortalRestauranteService{

    private String _nomeRestaurante;
    List<PratoDto> pratoList = new ArrayList<PratoDto>();

    public ObterMenuService(String nomeRestaurante) {
        this._nomeRestaurante = nomeRestaurante;
    }

    public final void dispatch() {
        PortalRestaurante _portal = FenixFramework.getRoot();
        Restaurante restaurante = _portal.getRestauranteByName(_nomeRestaurante);
        for (Prato p : restaurante.getPratoSet()) {
            PratoDto pDto = new PratoDto(p.getNome(), p.getPreco(), p.getCalorias(),p.getClassificacao());
            pratoList.add(pDto);
        }
    }
 
    public List<PratoDto> getPratos() {
        return pratoList;
    }

}
