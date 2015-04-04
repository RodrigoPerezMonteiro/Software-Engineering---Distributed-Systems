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
		ArrayList<String> fullPratosList = portal.getListaDePratos();
		Prato p;
		PratoDto pratoDTO;
		boolean tipoEncontrado;
		
		if(tipo.equals("Carne")){
			
			for(String nomePrato : fullPratosList){
				p = portal.getPratoByNome(nomePrato);
				tipoEncontrado = false;
				
				for(Alimento al : p.getAlimentoSet()){
					if(al instanceof Carne){
						tipoEncontrado = true;
					}
				}
				
				if(tipoEncontrado){
					pratoDTO = new PratoDto(p.getNome(), p.getPreco(), p.getCalorias(), p.getClassificacao(), portal.getRestauranteDoPrato(nomePrato));
					pratosList.add(pratoDTO);
				}
			}
		}
		
		if(tipo.equals("Peixe")){
			
			for(String nomePrato : fullPratosList){
				p = portal.getPratoByNome(nomePrato);
				tipoEncontrado = false;
				
				for(Alimento al : p.getAlimentoSet()){
					if(al instanceof Peixe){
						tipoEncontrado = true;
					}
				}
				
				if(tipoEncontrado){
					pratoDTO = new PratoDto(p.getNome(), p.getPreco(), p.getCalorias(), p.getClassificacao(), portal.getRestauranteDoPrato(nomePrato));
					pratosList.add(pratoDTO);
				}
			}
		}
		
		if(tipo.equals("Vegetal")){
			int counterVeg;
			int nAlimentos;
			for(String nomePrato : fullPratosList){
				p = portal.getPratoByNome(nomePrato);
				counterVeg=0;
				nAlimentos = p.getAlimentoSet().size();
				
				for(Alimento al : p.getAlimentoSet()){
					if(al instanceof Vegetal){
						counterVeg++;
					}
				}
				
				if(nAlimentos == counterVeg){
					pratoDTO = new PratoDto(p.getNome(), p.getPreco(), p.getCalorias(), p.getClassificacao(), portal.getRestauranteDoPrato(nomePrato));
					pratosList.add(pratoDTO);
				}
			}
		}
		

	}
	
	public List<PratoDto> getPratosTipo(){
		return pratosList;
	}
}
