package pt.ist.rest.presentation.client;


import pt.ist.rest.presentation.client.view.*;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;

public class ManagementPage extends Composite {
    private RestServletAsync rpcService;

    private RestGWT rootPage;

    private final Button refreshButton = new Button("Refresh");
    private final Button logOutButton = new Button("Logout");
    private FlexTable leftMenu = new FlexTable();

    private final ListarRestaurantesPanel listarRestaurantesPanel;
    private final ListarPratosRestaurantePanel listarPratosRestaurantePanel;
    private final ListarTabuleiroComprasPanel listarTabuleiroComprasPanel;
    private boolean[] activePanel = new boolean[4];
   
    
    public ManagementPage(final RestGWT rootpage, final RestServletAsync rpcService) {
        for (boolean b : activePanel) {
            b= false;
        }
        
        this.rpcService = rpcService;
        this.rootPage = rootpage;
        this.leftMenu = new FlexTable();
        
        listarRestaurantesPanel = new ListarRestaurantesPanel(rpcService, rootPage);
        listarPratosRestaurantePanel = new ListarPratosRestaurantePanel(rpcService, rootPage);
        listarTabuleiroComprasPanel = new ListarTabuleiroComprasPanel(rpcService, rootPage);
        
        refreshButton.setStyleName("refreshListButton");
        refreshButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                for (int i = 0; i < activePanel.length; i++) {
                    if(activePanel[i]){
                        switch (i) {
                            case 0:
                                listarRestaurantesPanel.listarRestaurantes(rpcService);
                                break;
                            case 1:
                                listarPratosRestaurantePanel.listarPratos(rootpage.getRestauranteActivo(), rpcService);
                                break;
                            case 2:
                                listarTabuleiroComprasPanel.refreshContactList();
                                break;
                            default:
                                break;
                        }
                    }
                }
                
            }
        });
        
        logOutButton.setStyleName("refreshListButton");
        logOutButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                rootPage.showLoginPage();
            }
        });
        
    }

  


    public ListarRestaurantesPanel getListarRestaurantesPanel() {
        doListarRestaurantes();
        return listarRestaurantesPanel;
    }

    public ListarPratosRestaurantePanel getListarPratosRestaurantePanel(String restaurante) {
        doListarPratos(restaurante);
        return listarPratosRestaurantePanel;
    }

    public ListarTabuleiroComprasPanel getListarTabuleiroComprasPanel() {
        return listarTabuleiroComprasPanel;
    }
    
    
    public void doListarRestaurantes(){
        listarRestaurantesPanel.listarRestaurantes(rpcService);
    }
    public void doListarPratos(String nomeRest){
        listarPratosRestaurantePanel.listarPratos(nomeRest,rpcService);
    }

    public void hidePage() {
        RootPanel.get("leftMenuContainer").clear();
        RootPanel.get("InformationContainer").clear();
    }
}
