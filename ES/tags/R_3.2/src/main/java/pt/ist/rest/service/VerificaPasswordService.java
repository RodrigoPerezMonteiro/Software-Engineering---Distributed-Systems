package pt.ist.rest.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.Cliente;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.exceptions.ClienteNaoExisteException;
import pt.ist.rest.exceptions.WrongPasswordException;

public class VerificaPasswordService extends PortalRestauranteService{

	private String username;
	private String password;
	private boolean passwordCerta = false;
	
	public VerificaPasswordService (String username, String password) {
        this.username = username;
        this.password = password;
    }
	
	public final void dispatch () throws WrongPasswordException{
		PortalRestaurante portal = FenixFramework.getRoot();
		Cliente cl = portal.getClienteByUsername(username);

		if(cl.getPassword().equals(password)){
			passwordCerta = true;
		}
		else{
		    throw new WrongPasswordException("Password Incorrecta",username,password);
		}
	}
	
	public boolean getPasswordCerta(){
		return passwordCerta;
	}
	
}
