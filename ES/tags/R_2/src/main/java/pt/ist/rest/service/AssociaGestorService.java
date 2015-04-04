package pt.ist.rest.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.PortalRestaurante;

public class AssociaGestorService extends PortalRestauranteService {
	private String nomeRestaurante;
	private String usernameGestor;
	
	public AssociaGestorService (String nomeRestaurante, String usernameGestor) {
        this.nomeRestaurante = nomeRestaurante;
        this.usernameGestor = usernameGestor;
    }
	
	
	public final void dispatch (){
		PortalRestaurante portal = FenixFramework.getRoot();
		portal.associaGestor(usernameGestor, nomeRestaurante);
	}


}
