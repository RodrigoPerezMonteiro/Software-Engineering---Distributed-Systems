package pt.ist.rest.presentation.client.view;

import java.util.List;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;

import pt.ist.rest.presentation.client.RestGWT;
import pt.ist.rest.presentation.client.RestServletAsync;
import pt.ist.rest.service.dto.ClienteDto;
import pt.ist.rest.service.dto.RestauranteDto;

public class ListarRestaurantesPanel extends
        FlexTable {

    private ClienteDto clienteDTO;
    private RestGWT rootPage;
    private Label lblNome,lblMorada,lblClassificacao,lblmenu;
    
    private class botaoMenu {
        public Button getNewButton(){
            Button btnVerMenu = new Button("Menu");
            
            btnVerMenu.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    final int selectedRow = getCellForEvent(event).getRowIndex();
                    final String restauranteName = getText(selectedRow, 0);
                    rootPage.setRestauranteActivo(restauranteName);
                    rootPage.setPageTitle("Listar Pratos do restaurante " + restauranteName);
                    rootPage.showPratosRestaurante(restauranteName);
                }
            });
            return btnVerMenu;
        }
    }

    public ListarRestaurantesPanel(final RestServletAsync rpcService, final RestGWT rootPage) {
        this.rootPage = rootPage;
        clienteDTO = rootPage.getLoggedClient();

        lblNome = new Label("Nome");
        lblNome.setStyleName("label");
        

        lblMorada = new Label("Morada");
        lblMorada.setStyleName("label");
        

        lblClassificacao = new Label("Classificacao");
        lblClassificacao.setStyleName("label");
        

        lblmenu = new Label("Menu");
        lblmenu.setStyleName("label");
        
    }

    public void listarRestaurantes(final RestServletAsync rpcService) {
        rpcService.listRestaurantes(new AsyncCallback<List<RestauranteDto>>() {

            public void onSuccess(List<RestauranteDto> response) {
                GWT.log("SUCESSO NO SERVICO LISTAR RESTAURANTES");
                addRestauranteTable(response);
            }

            public void onFailure(Throwable caught) {
                GWT.log("Erro");
                GWT.log("presentationserver.client.RestGWT::onModuleLoad()::rpcService.listarrestaurantes");
                GWT.log("-- Throwable: '" + caught.getClass().getName() + " Message '"
                        + caught.getMessage());
            }
        });
    }


    private void addRestauranteTable(List<RestauranteDto> listRest) {
        this.clear(true);
        GWT.log(String.valueOf(listRest.size()));
        setWidget(0, 0, lblNome);
        setWidget(0, 1, lblMorada);
        setWidget(0, 2, lblClassificacao);
        setWidget(0, 3, lblmenu);
        for (int i = 0; i < listRest.size(); i++) {
            setHTML(i + 1, 0, listRest.get(i).getNome());
            setHTML(i + 1, 1, listRest.get(i).getMorada());
            setHTML(i + 1, 2, String.valueOf(listRest.get(i).getClassificacao()));
            setWidget(i + 1, 3, new botaoMenu().getNewButton());
        }
        for (int i = 0; i < 4; i++) {
            this.getColumnFormatter().setWidth(i, "75px");
        }
    }
}
