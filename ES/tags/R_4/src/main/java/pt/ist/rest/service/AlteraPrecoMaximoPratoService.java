package pt.ist.rest.service;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.PortalRestaurante;

public class AlteraPrecoMaximoPratoService extends PortalRestauranteService {
    private double _precoMaximo;
    
    public AlteraPrecoMaximoPratoService (double precoMaximo) {
        this._precoMaximo = precoMaximo;
    }
    
    public final void dispatch (){
        PortalRestaurante portal = FenixFramework.getRoot();
        portal.set_maxPrecoPrato(_precoMaximo);
    }
}
