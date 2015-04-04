package pt.ist.rest.presentation.client.view;


import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;



import pt.ist.rest.exceptions.PratoNaoExisteEmRestauranteException;
import pt.ist.rest.exceptions.TabuleiroVazioException;
import pt.ist.rest.presentation.client.RestGWT;
import pt.ist.rest.presentation.client.RestServletAsync;
import pt.ist.rest.service.dto.ClienteDto;
import pt.ist.rest.service.dto.PratoDto;

public class ListarPratosPorNomePanel extends FlexTable{
    private ClienteDto clienteDTO;
    private TextBox tbQuantidade;
    private RestGWT rootPage;
    private RestServletAsync rpcService;
    private ArrayList<TextBox> textBoxs = new ArrayList<TextBox>();
    private Label lblNome,lblCalorias, lblPreco,lblClassificacao,lblQuantidade,lblAdicionar, lblRestaurante;
    
    private class BotaoAddPrato {
        public Button getNewButton(){
            
            Button btnAddPrato = new Button("Add");
            btnAddPrato.setWidth("75px");
            btnAddPrato.addClickHandler(new ClickHandler() {
              public void onClick(ClickEvent event) {
                  final int selectedRow = getCellForEvent(event).getRowIndex();
                  final String pratoName = getText(selectedRow, 0);
                  final double pratoCalorias = Double.parseDouble(getText(selectedRow, 1));
                  final double pratoPreco = Double.parseDouble(getText(selectedRow, 2));
                  final int pratoClass = Integer.parseInt(getText(selectedRow, 3));
                  TextBox tbQuantidadetemp = (TextBox) getWidget(selectedRow, 4);
                  final int quantidade = Integer.parseInt(tbQuantidadetemp.getValue());
                  final PratoDto pratoDTO = new PratoDto(pratoName, pratoPreco, pratoCalorias, pratoClass, rootPage.getRestauranteActivo());
                  
                  rpcService.adicionaPratoTabuleiro(pratoDTO,rootPage.getLoggedClient(),quantidade,false, new AsyncCallback<Void>() {
                     
                      @Override
                      public void onSuccess(Void result) {
                          GWT.log("SUCESSO NO SERVICO ADICIONAR PRATO AO TABULEIRO");
                      }
                      @Override
                      public void onFailure(Throwable caught) {
                          GWT.log("presentationserver.client.RestGWT::onModuleLoad()::rpcService.adicionaPratoTabuleiro");
                          GWT.log("-- Throwable: '" + caught.getClass().getName() + " Message '" + caught.getMessage());
                         
                          if (caught instanceof PratoNaoExisteEmRestauranteException) {
                              PratoNaoExisteEmRestauranteException prNexiste = (PratoNaoExisteEmRestauranteException) caught;
                              rootPage.showErrorMessage("O prato '" + prNexiste.getIdPrato()+ " nao existe no restaurante " + prNexiste.getNomeRestaurante());
                          }
                      }
                  });
              }
          });
            return btnAddPrato;
        }
        public TextBox getNewTextBox(){
            TextBox _add = new TextBox();
            _add.setWidth("75px");
            textBoxs.add(_add);
            return _add;
        }
    }
 
    public ListarPratosPorNomePanel( final RestServletAsync rpcService, final RestGWT rootPage) {
        this.rootPage = rootPage;
        this.rpcService = rpcService;
        clienteDTO = rootPage.getLoggedClient();
        
        lblNome = new Label("Nome");
        lblNome.setStyleName("label");
        
        
        lblCalorias = new Label("Calorias");
        lblCalorias.setStyleName("label");
        
        
        lblPreco = new Label("Preco");
        lblPreco.setStyleName("label");
        
        
        lblClassificacao = new Label("Classificacao");
        lblClassificacao.setStyleName("label");
        
        
        lblQuantidade = new Label("Quantidade");
        lblQuantidade.setStyleName("label");
        
        
        lblAdicionar = new Label("Adicionar");
        lblAdicionar.setStyleName("label");
        
        lblRestaurante = new Label("Restaurante");
        lblRestaurante.setStyleName("label");
        
    } 

    public void listarPratos (String nomePrato, final RestServletAsync rpcService) {
        rpcService.listPratosPorNome(nomePrato, new AsyncCallback<List<PratoDto>>() {
            
            public void onSuccess(List<PratoDto> response) {
                GWT.log("SUCESSO NO SERVICO LISTAR PRATOS");
                addPratoTable(response);
            }
 
            public void onFailure(Throwable caught) {
                GWT.log("Erro");
                GWT.log("presentationserver.client.RestGWT::onModuleLoad()::rpcService.listMenuPratos");
                GWT.log("-- Throwable: '" + caught.getClass().getName() + " Message '" + caught.getMessage());
            }
        });
    }

 
    private void addPratoTable(List<PratoDto> listPratos) {
        this.clear(true);
        clearBoxsContent();
        setWidget(0, 0, lblNome);
        setWidget(0, 1, lblCalorias);
        setWidget(0, 2, lblPreco);
        setWidget(0, 3, lblClassificacao);
        setWidget(0, 4, lblRestaurante);
        setWidget(0, 5, lblQuantidade);
        setWidget(0, 6, lblAdicionar);
        GWT.log(String.valueOf(listPratos.size()));
        for (int i = 0; i < listPratos.size(); i++) {
            setHTML(i+1, 0, listPratos.get(i).getNome());
            setHTML(i+1, 1, String.valueOf(listPratos.get(i).getCalorias()));
            setHTML(i+1, 2, String.valueOf(listPratos.get(i).getPreco()));
            setHTML(i+1, 3, String.valueOf(listPratos.get(i).getClassificacao()));
            setHTML(i+1, 4, listPratos.get(i).getRestaurante());
            GWT.log("CLASSIFICACAO "+String.valueOf(i)+": "+String.valueOf(listPratos.get(i).getClassificacao()));
            
            BotaoAddPrato btnAddPrato = new BotaoAddPrato();
            setWidget(i+1,5,btnAddPrato.getNewTextBox());
            setWidget(i+1,6,btnAddPrato.getNewButton());
        }
        for (int i = 0; i < 6; i++) {
            getColumnFormatter().setWidth(i, "75px");
        }
 
    }
    
    public void clearBoxsContent() {
        for (TextBox tb : textBoxs) {
            tb.setText("");
        }
    }
    
    public void HidePage() {
        clearBoxsContent();
        this.clear(true);
        RootPanel.get("InformationContainer").clear();
    }
}
