package pt.ist.rest.service;

import java.util.ArrayList;
import java.util.List;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.domain.Prato;
import pt.ist.rest.service.dto.PratoDto;

public class ProcurarPratosPorNomeService extends PortalRestauranteService{
	
	private String _nomePrato;
	List<PratoDto> pratoList = new ArrayList<PratoDto>();
	
	public ProcurarPratosPorNomeService(String nomePrato){
		this._nomePrato = nomePrato;
	}
	
	public final void dispatch(){
		PortalRestaurante _portal = FenixFramework.getRoot();
		Prato p;
		for (Prato prato : _portal.getListaDePratos()){
			if(prato.getNome().contains(_nomePrato)){
				p = _portal.getPratoById(prato.getId());
				PratoDto pDto = new PratoDto(p.getNome(), p.getCalorias(), p.getPreco(), p.getClassificacao(), p.getRestaurante().getNome());
				pratoList.add(pDto);
			}
		}
	}
	
	public List<PratoDto> getPratos(){
		return pratoList;	
	}

}
