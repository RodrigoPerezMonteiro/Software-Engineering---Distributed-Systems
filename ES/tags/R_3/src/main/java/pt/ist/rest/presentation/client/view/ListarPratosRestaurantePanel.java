package pt.ist.rest.presentation.client.view;

import java.util.List;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;


import pt.ist.rest.presentation.client.RestGWT;
import pt.ist.rest.presentation.client.RestServletAsync;
import pt.ist.rest.service.dto.ClienteDto;
import pt.ist.rest.service.dto.PratoDto;

public class ListarPratosRestaurantePanel extends FlexTable{
    private ClienteDto clienteDTO;
    private Button btnAddPrato;
    private TextBox tbQuantidade;
 
    public ListarPratosRestaurantePanel( final RestServletAsync rpcService, final RestGWT rootPage) {
 
        clienteDTO = rootPage.getLoggedClient();
        
        Label lblNome = new Label("Nome");
        lblNome.setStyleName("label");
        setWidget(0, 0, lblNome);
        
        Label lblCalorias = new Label("Calorias");
        lblCalorias.setStyleName("label");
        setWidget(0, 1, lblCalorias);
        
        Label lblPreco = new Label("Preco");
        lblPreco.setStyleName("label");
        setWidget(0, 2, lblPreco);
        
        Label lblClassificacao = new Label("Classificacao");
        lblClassificacao.setStyleName("label");
        setWidget(0, 3, lblClassificacao);
        
        Label lblQuantidade = new Label("Quantidade");
        lblQuantidade.setStyleName("label");
        setWidget(0, 4, lblQuantidade);
        
        Label lblAdicionar = new Label("Adicionar");
        lblAdicionar.setStyleName("label");
        setWidget(0, 5, lblAdicionar);
        
        btnAddPrato = new Button("Add");
        btnAddPrato.addClickHandler(new ClickHandler() {
          public void onClick(ClickEvent event) {
              //adiciona restaurante activo à rootpage
              //adiciona a quantia x ao prato y
          }
      });
        
        tbQuantidade = new TextBox();
 
    } 

    public void listarPratos (String nomeRest, final RestServletAsync rpcService) {
        rpcService.listMenuPratos(nomeRest, new AsyncCallback<List<PratoDto>>() {
            
            public void onSuccess(List<PratoDto> response) {
                GWT.log("SUCESSO NO SERVICO LISTAR RESTAURANTES");
                addPratoTable(response);
            }
 
            public void onFailure(Throwable caught) {
                GWT.log("Erro");
                GWT.log("presentationserver.client.RestGWT::onModuleLoad()::rpcService.listarrestaurantes");
                GWT.log("-- Throwable: '" + caught.getClass().getName() + " Message '" + caught.getMessage());
            }
        });
    }

 
    private void addPratoTable(List<PratoDto> listPratos) {
        GWT.log(String.valueOf(listPratos.size()));
        for (int i = 0; i < listPratos.size(); i++) {
            setHTML(i+1, 0, listPratos.get(i).getNome());
            setHTML(i+1, 1, String.valueOf(listPratos.get(i).getCalorias()));
            setHTML(i+1, 2, String.valueOf(listPratos.get(i).getPreco()));
            setHTML(i+1, 3, String.valueOf(listPratos.get(i).getClassificacao()));
            setWidget(i+1,4,tbQuantidade);
            setWidget(i+1,4,btnAddPrato);
        }
 
    }
}
