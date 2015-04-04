package pt.ist.rest.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.Cliente;
import pt.ist.rest.domain.GestorRestaurante;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.domain.Restaurante;
import pt.ist.rest.exceptions.restException;
import jvstm.Atomic;


// TODO: Auto-generated Javadoc
/**
 * The Class PortalRestauranteService.
 */
public abstract class PortalRestauranteService {

    
    /**
     * Execute.
     *
     * @throws restException the rest exception
     */
    @Atomic
    public void execute() throws restException {
        dispatch();
    }

  
    /**
     * Dispatch.
     *
     * @throws restException the rest exception
     */
    public abstract void dispatch() throws restException;

    
  
    /**
     * Gets the portal.
     *
     * @return the portal
     */
    public PortalRestaurante getPortal() {
        PortalRestaurante _portal = FenixFramework.getRoot();
        return _portal;
    }
    

    /**
     * Gets the restaurante.
     *
     * @param name the name
     * @return the restaurante
     */
    public Restaurante getRestaurante(String name) {
        Restaurante rest = getPortal().getRestauranteByName(name);
        return rest;
    }

   
    /**
     * Gets the cliente.
     *
     * @param usernameCliente the username cliente
     * @return the cliente
     */
    public Cliente getCliente(String usernameCliente) {
        Cliente cl = getPortal().getClienteByUsername(usernameCliente);
        return cl;
    }
    
    /**
     * Gets the gestor.
     *
     * @param usernameGestor the username gestor
     * @return the gestor
     */
    public GestorRestaurante getGestor(String usernameGestor) {
        GestorRestaurante gestor = getPortal().getGestorByUsername(usernameGestor);
        return gestor;
    }
}
