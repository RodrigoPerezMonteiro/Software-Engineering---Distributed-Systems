package pt.ist.rest.service;


import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.PortalRestaurante;



public class RegistarRestauranteService extends PortalRestauranteService {
	
	private String nome;
	private String morada;
	
	public RegistarRestauranteService (String nome, String morada) {
        this.nome = nome;
        this.morada = morada;
    }
	
	
	public final void dispatch (){
		PortalRestaurante portal = FenixFramework.getRoot();
		portal.adicionaRestaurante(nome, morada);
		
	}


}
