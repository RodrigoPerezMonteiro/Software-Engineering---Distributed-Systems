package pt.ist.rest.service;

import java.util.ArrayList;
import java.util.List;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.domain.Prato;
import pt.ist.rest.service.dto.PratoDto;

public class ProcurarPratosPorTipoService extends PortalRestauranteService{

	List<PratoDto> pratosList = new ArrayList<PratoDto>();
	
	private String tipo;
	
	public ProcurarPratosPorTipoService (String tipo) {
        this.tipo = tipo;
    }
	
	public final void dispatch (){
		PortalRestaurante portal = FenixFramework.getRoot();
		ArrayList<Prato> fullPratosList = portal.getListaDePratos();
		PratoDto pratoDTO;
			
			for(Prato prato : fullPratosList){
				if(prato.getTipo().equals(tipo)){
					pratoDTO = new PratoDto(prato.getNome(), prato.getPreco(), prato.getCalorias(), prato.getClassificacao(), prato.getRestaurante().getNome());
					pratosList.add(pratoDTO);
				}
			}
	}
	
	public List<PratoDto> getPratosTipo(){
		return pratosList;
	}
}
