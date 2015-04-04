package pt.ist.rest.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.PortalRestaurante;

public class RegistaPagamentoTabuleiroComprasService extends PortalRestauranteService{

    String usernameCliente;

    public RegistaPagamentoTabuleiroComprasService(String usernameCliente) {
        this.usernameCliente = usernameCliente;
    }
    public final void dispatch (){
        PortalRestaurante portal = FenixFramework.getRoot();
        portal.clienteFinalizaCompra(usernameCliente);
    }
}
