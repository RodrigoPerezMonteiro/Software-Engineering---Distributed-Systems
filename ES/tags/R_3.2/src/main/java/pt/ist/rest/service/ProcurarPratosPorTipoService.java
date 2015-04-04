package pt.ist.rest.service;

import java.util.ArrayList;
import java.util.List;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.Alimento;
import pt.ist.rest.domain.Carne;
import pt.ist.rest.domain.Peixe;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.domain.Prato;
import pt.ist.rest.domain.Vegetal;
import pt.ist.rest.service.dto.PratoDto;

public class ProcurarPratosPorTipoService {

	List<PratoDto> pratosList = new ArrayList<PratoDto>();
	
	private String tipo;
	
	public ProcurarPratosPorTipoService (String tipo) {
        this.tipo = tipo;
    }
	
	public final void dispatch (){
		PortalRestaurante portal = FenixFramework.getRoot();
		ArrayList<Prato> fullPratosList = portal.getListaDePratos();
		Prato p;
		PratoDto pratoDTO;
		boolean tipoEncontrado;
		
		if(tipo.equals("Carne")){
			
			for(Prato prato : fullPratosList){
				tipoEncontrado = false;
				
				for(Alimento al : prato.getAlimentoSet()){
					if(al instanceof Carne){
						tipoEncontrado = true;
					}
				}
				
				if(tipoEncontrado){
					pratoDTO = new PratoDto(prato.getNome(), prato.getPreco(), prato.getCalorias(), prato.getClassificacao(), prato.getRestaurante().getNome());
					pratosList.add(pratoDTO);
				}
			}
		}
		
		if(tipo.equals("Peixe")){
			
		    for(Prato prato : fullPratosList){
				tipoEncontrado = false;
				
				for(Alimento al : prato.getAlimentoSet()){
					if(al instanceof Peixe){
						tipoEncontrado = true;
					}
				}
				
				if(tipoEncontrado){
					pratoDTO = new PratoDto(prato.getNome(), prato.getPreco(), prato.getCalorias(), prato.getClassificacao(), prato.getRestaurante().getNome());
					pratosList.add(pratoDTO);
				}
			}
		}
		
		if(tipo.equals("Vegetal")){
			int counterVeg;
			int nAlimentos;
			 for(Prato prato : fullPratosList){
			     
				counterVeg=0;
				nAlimentos = prato.getAlimentoSet().size();
				
				for(Alimento al : prato.getAlimentoSet()){
					if(al instanceof Vegetal){
						counterVeg++;
					}
				}
				
				if(nAlimentos == counterVeg){
				    pratoDTO = new PratoDto(prato.getNome(), prato.getPreco(), prato.getCalorias(), prato.getClassificacao(), prato.getRestaurante().getNome());
					pratosList.add(pratoDTO);
				}
			}
		}
		

	}
	
	public List<PratoDto> getPratosTipo(){
		return pratosList;
	}
}
