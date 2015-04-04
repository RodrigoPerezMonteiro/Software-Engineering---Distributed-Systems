package pt.ist.rest.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.domain.Prato;


public class AdicionarPratoService extends PortalRestauranteService{
	
	private String nomePrato;
	private String nomeRestaurante;
	private String usernameGestor;
	
	
	public AdicionarPratoService(String nomePrato, String nomeRestaurante, String usernameGestor){
		this.nomePrato = nomePrato;
		this.nomeRestaurante = nomeRestaurante;
		this.usernameGestor = usernameGestor;
	}
	
	public final void dispatch(){
		PortalRestaurante _portal = FenixFramework.getRoot();
		Prato p = _portal.getPratoByNome(nomePrato);
		_portal.addPratoToRestaurante(nomePrato, p.getPreco(), p.getCalorias(), nomeRestaurante, usernameGestor);
	}


}
