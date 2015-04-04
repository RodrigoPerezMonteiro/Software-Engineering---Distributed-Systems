package pt.ist.rest.presentation.client;


import java.util.List;

import pt.ist.rest.exceptions.PratoNaoExisteEmRestauranteException;
import pt.ist.rest.presentation.client.view.*;
import pt.ist.rest.service.dto.RestauranteDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ManagementPage extends Composite {
    private RestServletAsync rpcService;

    private RestGWT rootPage;

    private final Button refreshButton = new Button("Refresh");
    private final Button logOutButton = new Button("Logout");
    private final Button btnMostrarTabuleiro = new Button("Ver Tabuleiro");
    private final Button btnMostrarRestaurantes = new Button("Ver Restaurantes");
    private final Button btnProcurarPratoPorNome = new Button("Procurar Prato por Nome");
    private final Button btnProcurarPratoPorTipo = new Button("Procurar Prato por tipo");
    private final Button btnAlterarPrecoMaximo = new Button("Alterar Preco Maximo");
    private final TextBox subStringPrato = new TextBox();
    private final TextBox tbPrecoMaximo = new TextBox();
    private final ListBox tipoPrato = new ListBox();
    private FlexTable leftMenu = new FlexTable();
    private final VerticalPanel verticalPanel;
    private final HorizontalPanel hpAlteraPrecoMaximo;
    private final ListarRestaurantesPanel listarRestaurantesPanel;
    private final ListarPratosRestaurantePanel listarPratosRestaurantePanel;
    private final ListarTabuleiroComprasPanel listarTabuleiroComprasPanel;
    private final ListarPratosPorNomePanel listarPratosPorNomePanel;
    private final ListarPratosPorTipoPanel listarPratosPorTipoPanel;
    private boolean[] activePanel = new boolean[4];
   
    
    public ManagementPage(final RestGWT rootpage, final RestServletAsync rpcService) {
        for (boolean b : activePanel) {
            b= false;
        }
        
        this.rpcService = rpcService;
        this.rootPage = rootpage;
        this.leftMenu = new FlexTable();
        this.verticalPanel = new VerticalPanel();
        this.hpAlteraPrecoMaximo = new HorizontalPanel();
        
        btnMostrarRestaurantes.setSize("200px", "30px");
        btnMostrarTabuleiro.setSize("200px", "30px");
        btnProcurarPratoPorNome.setSize("200px", "30px");
        subStringPrato.setSize("190px", "20px");
        btnProcurarPratoPorTipo.setSize("200px", "30px");
        tipoPrato.setSize("200px", "20px");
        btnAlterarPrecoMaximo.setSize("150px", "30px");
        tbPrecoMaximo.setSize("40px", "17px");
        logOutButton.setSize("200px", "30px");
        
        hpAlteraPrecoMaximo.add(btnAlterarPrecoMaximo);
        hpAlteraPrecoMaximo.add(tbPrecoMaximo);
        hpAlteraPrecoMaximo.setSize("200px", "30px");
        
        tipoPrato.addItem("Carne");
        tipoPrato.addItem("Peixe");
        tipoPrato.addItem("Vegetariano");
        
        verticalPanel.add(btnMostrarRestaurantes);
        verticalPanel.add(btnMostrarTabuleiro);
        verticalPanel.add(btnProcurarPratoPorNome);
        verticalPanel.add(subStringPrato);
        verticalPanel.add(btnProcurarPratoPorTipo);
        verticalPanel.add(tipoPrato);
        verticalPanel.add(hpAlteraPrecoMaximo);
        verticalPanel.add(logOutButton);
        
        btnMostrarTabuleiro.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                rootPage.setPageTitle("Tabuleiro do cliente " + rootpage.getLoggedClient().get_username());
                rootPage.showTabuleiroCliente();
            }
        });
    
        btnProcurarPratoPorNome.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
            	listarPratosRestaurantePanel.HidePage();
                listarTabuleiroComprasPanel.HidePage();
                rootPage.setPageTitle("Lista de Pratos com a sub-string: " + subStringPrato.getText());
                rootPage.showPratosPorNome(subStringPrato.getText());
            }
        });
        
        btnProcurarPratoPorTipo.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
            	listarPratosRestaurantePanel.HidePage();
                listarTabuleiroComprasPanel.HidePage();
                rootPage.setPageTitle("Lista de Pratos do tipo: " + tipoPrato.getItemText(tipoPrato.getSelectedIndex()));
                rootPage.showPratosPorTipo(tipoPrato.getItemText(tipoPrato.getSelectedIndex()));
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
        listarPratosPorNomePanel = new ListarPratosPorNomePanel(rpcService, rootPage);
        listarPratosPorTipoPanel = new ListarPratosPorTipoPanel(rpcService, rootPage);
        
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
                            /*case 3:
                                listarPratosPorNomePanel.listarPratos(rootpage.getRestauranteActivo(), rpcService);
                                break;*/
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
                listarPratosPorNomePanel.HidePage();
                listarPratosPorTipoPanel.HidePage();
                rootPage.showLoginPage();
            }
        });
        
    
    
        btnAlterarPrecoMaximo.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (!tbPrecoMaximo.getText().equals("")) {
                    Double precoMaximo = Double.parseDouble(tbPrecoMaximo.getText());
                    rpcService.AlteraPrecoMaximo(precoMaximo, new AsyncCallback<Void>() {

                        public void onSuccess(Void response) {
                            GWT.log("SUCESSO NO SERVICO ALTERAR PRECO MAXIMO");
                        }

                        public void onFailure(Throwable caught) {
                            GWT.log("Erro");
                            GWT.log("presentationserver.client.ManagementPage::onModuleLoad()::rpcService.alteraPrecoMaximo");
                            GWT.log("-- Throwable: '" + caught.getClass().getName() + " Message '"
                                    + caught.getMessage());
                        }
                    });
                }
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
    
    public ListarPratosPorNomePanel getListarPratosPorNomePanel(String nomePrato) {
    	doListarPratosPorNome(nomePrato);
        return listarPratosPorNomePanel;
    }
    
    public ListarPratosPorTipoPanel getListarPratosPorTipoPanel(String tipo) {
    	doListarPratosPorTipo(tipo);
        return listarPratosPorTipoPanel;
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
    public void doListarPratosPorNome(String nomePrato){
        listarPratosPorNomePanel.listarPratos(nomePrato,rpcService);
    }
    public void doListarPratosPorTipo(String tipo){
        listarPratosPorTipoPanel.listarPratos(tipo,rpcService);
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
