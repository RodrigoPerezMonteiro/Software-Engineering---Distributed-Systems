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
		for (String nomePrato : _portal.getListaDePratos()){
			if(nomePrato.contains(_nomePrato)){
				p = _portal.getPratoByNome(nomePrato);
				PratoDto pDto = new PratoDto(p.getNome(), p.getCalorias(), p.getPreco(), p.getClassificacao(), _portal.getRestauranteDoPrato(nomePrato));
				pratoList.add(pDto);
			}
		}
	}
	
	public List<PratoDto> getPratos(){
		return pratoList;	
	}

}
