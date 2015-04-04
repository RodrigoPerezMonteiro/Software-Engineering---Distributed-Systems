package pt.ist.rest.presentation.client;

import pt.ist.rest.service.dto.ClienteDto;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class RestGWT implements EntryPoint {

        private static final String remoteServerType    = "ES+SD";
        private static final String localServerType     = "ES-only";
        private static final String pageTitle           = "Titulo";
        private ClienteDto clienteDTO;
        private String restauranteActivo;

        private final RestServletAsync rpcService       = GWT.create(RestServlet.class);
        
        private final Label pageTitleLabel              = new Label("Page Title");
        private final Label serverTypeLabel             = new Label("Portal Restaurantes");
        private final Label errorMessage                = new Label("");

        private LoginPage loginPage;
        private ManagementPage managementPage;
        
        public void onModuleLoad() {
            GWT.log("presentation.client.Rest::onModuleLoad() - begin");
            
                // create label with mode type
            serverTypeLabel.setStyleName("h1");
            String serverType; // depends on type of running
            if (RootPanel.get(remoteServerType) != null) {
                GWT.log("presentation.client.Rest::onModuleLoad() running on remote mode");
                serverTypeLabel.setText("Rest - Remote mode");
                serverType = remoteServerType;
            } else { // default: local - even if it is misspelled
                GWT.log("presentation.client.Rest::onModuleLoad() running on local mode");
                serverTypeLabel.setText("Rest - Local mode");
                serverType = localServerType;
            }

            RootPanel typeRootPanel = RootPanel.get(serverType);
            typeRootPanel.add(serverTypeLabel);
            serverTypeLabel.setWidth("100%");
            
            // set the mode of the GWT server, local or remote.
            
            this.rpcService.initServer(serverType, new AsyncCallback<Void>() {
                @Override
                public void onSuccess(Void result) {
                }
                @Override
                public void onFailure(Throwable caught) {
                    GWT.log("presentation.client.Rest::onModuleLoad()::rpcService.initBridge");
                    GWT.log("-- Throwable: '" + caught.getClass().getName() + "'");
                    showErrorMessage("Not able to init aplication server bridge: " + caught.getMessage());
                }
            });
            
            loginPage = new LoginPage(this, rpcService);
            managementPage = new ManagementPage(this, rpcService);
            
            GWT.log("presentation.client.Rest::onModuleLoad() - done!");
            
            RootPanel updateRootPanel = RootPanel.get("labelError");
            updateRootPanel.add(errorMessage);
            errorMessage.setStyleName("labelError");
            errorMessage.setWidth("100%");
            
            showLoginPage();
        }
        
        // show the contact page of the logged person
        void showLoginPage() {
            managementPage.hidePage();
            GWT.log("ENTREI NO SHOW LOGINPAGE");
            
            pageTitleLabel.setStyleName("pageTitle");
            pageTitleLabel.setText("Pagina de Login");
            RootPanel titleRootPanel = RootPanel.get(pageTitle);
            titleRootPanel.add(pageTitleLabel);
            
            RootPanel.get("InformationContainer").add(loginPage);
            errorMessage.setText("");
            
        } 
        
        void showManagementPage() {
            loginPage.hidePage();
            GWT.log("ENTREI NO SHOW ManagementPage");
            
            pageTitleLabel.setStyleName("pageTitle");
            pageTitleLabel.setText("Lista de Restaurantes");
            RootPanel titleRootPanel = RootPanel.get(pageTitle);
            titleRootPanel.add(pageTitleLabel);
            
            RootPanel.get("InformationContainer").add(managementPage.getListarRestaurantesPanel());
            errorMessage.setText("");
            
        }
        public void showPratosRestaurante(String restaurante){
            managementPage.hidePage();
            GWT.log("ENTREI NO showPratosRestaurante");
            RootPanel.get("InformationContainer").add(managementPage.getListarPratosRestaurantePanel(restaurante));
            errorMessage.setText("");
        }
        
        public void setPageTitle(String title){
            pageTitleLabel.setText(title);
        }
        
        public void setLoggedClient(ClienteDto clienteDTO){
        	this.clienteDTO = clienteDTO;
        }
        
        public ClienteDto getLoggedClient(){
        	return clienteDTO;
        }
        
        public String getRestauranteActivo() {
            return restauranteActivo;
        }

        public void setRestauranteActivo(String restauranteActivo) {
            this.restauranteActivo = restauranteActivo;
        }
        
        public void showErrorMessage(String message) {
            errorMessage.setText(message);
        }
    }
