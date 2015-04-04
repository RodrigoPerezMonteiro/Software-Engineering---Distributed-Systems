package pt.ist.rest.presentation.client;


import pt.ist.rest.presentation.client.view.*;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ManagementPage extends Composite {
    private RestServletAsync rpcService;

    private RestGWT rootPage;

    private final Button refreshButton = new Button("Refresh");
    private final Button logOutButton = new Button("Logout");
    private final Button btnMostrarTabuleiro = new Button("Ver Tabuleiro");
    private final Button btnMostrarRestaurantes = new Button("Ver Restaurantes");
    private FlexTable leftMenu = new FlexTable();
    private final VerticalPanel verticalPanel;
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
        this.verticalPanel = new VerticalPanel();
        
        btnMostrarRestaurantes.setSize("200px", "30px");
        btnMostrarTabuleiro.setSize("200px", "30px");
        logOutButton.setSize("200px", "30px");
        
        verticalPanel.add(btnMostrarRestaurantes);
        verticalPanel.add(btnMostrarTabuleiro);
        verticalPanel.add(logOutButton);
        
        btnMostrarTabuleiro.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                rootPage.setPageTitle("Tabuleiro do cliente " + rootpage.getLoggedClient().get_username());
                rootPage.showTabuleiroCliente();
            }
        });
        
        btnMostrarRestaurantes.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                listarPratosRestaurantePanel.HidePage();
                listarTabuleiroComprasPanel.HidePage();
                rootPage.showManagementPage();
            }
        });
        
        leftMenu.setWidget(0, 0,verticalPanel);
        RootPanel.get("NavigationMenu").add(leftMenu);
        
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
        
        
        logOutButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                listarRestaurantesPanel.clear();
                listarPratosRestaurantePanel.HidePage();
                listarTabuleiroComprasPanel.HidePage();
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
        doListarTabuleiroComprasPanel();
        return listarTabuleiroComprasPanel;
    }
    
    public void doListarTabuleiroComprasPanel(){
        listarTabuleiroComprasPanel.listarTabuleiro(rpcService);
    }
    
    public void doListarRestaurantes(){
        listarRestaurantesPanel.listarRestaurantes(rpcService);
    }
    public void doListarPratos(String nomeRest){
        listarPratosRestaurantePanel.listarPratos(nomeRest,rpcService);
    }

    public void hidePage() {
        RootPanel.get("InformationContainer").clear();
    }
    public void ShowNavigation(){
        RootPanel.get("NavigationMenu").setVisible(true);
    }
    
    public void HideNavigation(){
        RootPanel.get("NavigationMenu").setVisible(false);
    }
    
}
