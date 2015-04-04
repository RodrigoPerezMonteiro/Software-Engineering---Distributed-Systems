package pt.ist.rest.presentation.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import pt.ist.rest.exceptions.ClientESaldoInsuficienteException;
import pt.ist.rest.exceptions.ClienteNaoExisteException;
import pt.ist.rest.exceptions.NaoExisteTabuleiroException;
import pt.ist.rest.exceptions.PratoNaoExisteEmRestauranteException;
import pt.ist.rest.exceptions.TabuleiroVazioException;
import pt.ist.rest.exceptions.restException;
import pt.ist.rest.presentation.client.RestGWT;
import pt.ist.rest.presentation.client.RestServletAsync;
import pt.ist.rest.service.dto.ChequeDto;
import pt.ist.rest.service.dto.ClienteDto;
import pt.ist.rest.service.dto.ItemDto;
import pt.ist.rest.service.dto.PratoDto;

public class ListarTabuleiroComprasPanel extends FlexTable {
    private RestGWT rootPage;
    private RestServletAsync rpcService;
    private final HorizontalPanel horizontalPanel;
    private TextBox tbIdCheques;
    private Button btPagar;
    private ArrayList<TextBox> textBoxs = new ArrayList<TextBox>();
    private Label lblNome, lblCalorias,lblPreco,lblClassificacao, lblQuantidade,lblAlteraQuantidade,lblAlterar;
    
    private class BotaoAlterarTabuleiro {
        public Button getNewAlterarTabuleiroButton() {
            Button btnAlterarTabuleiro = new Button("Alterar");
            btnAlterarTabuleiro.setWidth("75px");
            btnAlterarTabuleiro.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    
                    final int selectedRow = getCellForEvent(event).getRowIndex();
                    final String pratoName = getText(selectedRow, 0);
                    final double pratoCalorias = Double.parseDouble(getText(selectedRow, 1));
                    final double pratoPreco = Double.parseDouble(getText(selectedRow, 2));
                    final int pratoClass = Integer.parseInt(getText(selectedRow, 3));
                    TextBox tbNovaQuantidade = (TextBox) getWidget(selectedRow, 5);
                    final int novaQuantidade = Integer.parseInt(tbNovaQuantidade.getValue());
                    GWT.log("RESTAURANTE: "+ rootPage.getRestauranteActivo());
                    final PratoDto pratoDTO = new PratoDto(pratoName, pratoPreco, pratoCalorias, pratoClass);
                    
                    rpcService.adicionaPratoTabuleiro(pratoDTO,rootPage.getLoggedClient(),novaQuantidade,true, new AsyncCallback<Void>() {
                       
                        @Override
                        public void onSuccess(Void result) {
                            GWT.log("SUCESSO NO SERVICO ALTERAR QUANTIDADE DE UM PRATO NO TABULEIRO");
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
            return btnAlterarTabuleiro;
        }

        public TextBox getNewTbNovaQuantia() {
            TextBox tbNovaQuantia = new TextBox();
            tbNovaQuantia.setWidth("75px");
            textBoxs.add(tbNovaQuantia);
            return tbNovaQuantia;
        }
    }


    public ListarTabuleiroComprasPanel(final RestServletAsync rpcService, final RestGWT rootPage) {
        this.rootPage = rootPage;
        this.rpcService = rpcService;
        
        lblNome = new Label("Nome Prato");
        lblNome.setStyleName("label");
        
        lblCalorias = new Label("Calorias");
        lblCalorias.setStyleName("label");
        
        lblPreco = new Label("Preco");
        lblPreco.setStyleName("label");
        
        lblClassificacao = new Label("Classificacao");
        lblClassificacao.setStyleName("label");
        
        lblQuantidade = new Label("Quantidade");
        lblQuantidade.setStyleName("label");
        
        lblAlteraQuantidade = new Label("Nova Quantidade");
        lblAlteraQuantidade.setStyleName("label");
        
        lblAlterar = new Label("Alterar");
        lblAlterar.setStyleName("label");
        
        horizontalPanel = new HorizontalPanel();
        horizontalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        tbIdCheques = new TextBox();
        tbIdCheques.setWidth("100px");
        btPagar = new Button("Pagar");
        btPagar.setWidth("75px");
       
        horizontalPanel.add(tbIdCheques);
        horizontalPanel.add(btPagar);
        
        btPagar.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final ClienteDto cDto = new ClienteDto(rootPage.getLoggedClient().getUsername());
                if(!tbIdCheques.getValue().equals("")){
                    List<String> cheques = new ArrayList<String>();
                    cheques = new ArrayList<String>(Arrays.asList(tbIdCheques.getValue().split(",")));
                    final ChequeDto chequeDto = new ChequeDto(cheques,cDto.getUsername());
                    
                    rpcService.UsarChequeRefeicao(cDto,chequeDto, new AsyncCallback<Integer>() {
                        @Override
                        public void onSuccess(Integer result) {
                            GWT.log("SUCESSO NO SERVICO USAR CHUEQUE REFEICAO");
                            cDto.setSaldo(result);
                        }
                        @Override
                        public void onFailure(Throwable caught) {
                            GWT.log("presentationserver.client.RestGWT::onModuleLoad()::rpcService.UsarChequeRefeicao");
                            GWT.log("-- Throwable: '" + caught.getClass().getName() + " Message '" + caught.getMessage());
                           
                            if (caught instanceof restException) {
                                restException rExc = (restException) caught;
                                rootPage.showErrorMessage("Erro ao pagar o tabuleiro: "+rExc.getMessage());
                            }
                        }
                    });
                    
                    rpcService.ActualizaSaldoCliente(cDto,new AsyncCallback<Void>() {
                        @Override
                        public void onSuccess(Void result) {
                            GWT.log("SUCESSO NO SERVICO ACTUALIZAR SALDO");
                        }
                        @Override
                        public void onFailure(Throwable caught) {
                            GWT.log("presentationserver.client.RestGWT::onModuleLoad()::rpcService.ActualizaSaldoCliente");
                            GWT.log("-- Throwable: '" + caught.getClass().getName() + " Message '" + caught.getMessage());
                           
                            if (caught instanceof restException) {
                                ClienteNaoExisteException clNex = (ClienteNaoExisteException) caught;
                                rootPage.showErrorMessage("Erro ao actualizar saldo, o cliente: "+clNex.getNomeCliente()+" nao existe.");
                            }
                        }
                    });
                }
                
                rpcService.pagarTabuleiro(cDto.getUsername(),new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        GWT.log("SUCESSO NO SERVICO PAGAR TABULEIRO");
                    }
                    @Override
                    public void onFailure(Throwable caught) {
                        GWT.log("presentationserver.client.RestGWT::onModuleLoad()::rpcService.ActualizaSaldoCliente");
                        GWT.log("-- Throwable: '" + caught.getClass().getName() + " Message '" + caught.getMessage());
                       
                        if (caught instanceof restException) {
                            ClienteNaoExisteException clNex = (ClienteNaoExisteException) caught;
                            rootPage.showErrorMessage("Erro ao pagar tabuleiro, o cliente: "+clNex.getNomeCliente()+" nao existe.");
                        }
                        if (caught instanceof restException) {
                            ClientESaldoInsuficienteException slInsuf = (ClientESaldoInsuficienteException) caught;
                            rootPage.showErrorMessage("Erro ao pagar tabuleiro, o cliente: "+slInsuf.get_nomeCliente()+" nao tem saldo suficiente, saldo do cliente: "+String.valueOf(slInsuf.get_saldo()));
                        }
                        if (caught instanceof restException) {
                            NaoExisteTabuleiroException nExTab = (NaoExisteTabuleiroException) caught;
                            rootPage.showErrorMessage("Erro ao pagar tabuleiro, o cliente: "+nExTab.getNomeCliente()+" nao tem tabuleiro.");
                        }
                    }
                });
                

            }
        });

            

    }

    public void listarTabuleiro(final RestServletAsync rpcService) {
        GWT.log(rootPage.getLoggedClient().getUsername());
        String clientUsername = rootPage.getLoggedClient().getUsername();
        rpcService.mostraTabuleiro(clientUsername, new AsyncCallback<List<ItemDto>>() {

            @Override
            public void onSuccess(List<ItemDto> response) {
                GWT.log("SUCESSO NO SERVICO LISTAR TABULEIRO");
                addItemTable(response);
            }

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("presentationserver.client.RestGWT::onModuleLoad()::rpcService.mostraTabuleiro");
                GWT.log("-- Throwable: '" + caught.getClass().getName() + " Message '"
                        + caught.getMessage());
                if (caught instanceof TabuleiroVazioException) {
                    TabuleiroVazioException tne = (TabuleiroVazioException) caught;
                    rootPage.showErrorMessage("The client '" + tne.getNomeCliente()
                            + "'does not have shopping on the board.");
                }

            }
        });
    }

    private void addItemTable(List<ItemDto> listItens) {
        this.clear(true);
        clearBoxsContent();
        setWidget(0, 0, lblNome);
        setWidget(0, 1, lblCalorias);
        setWidget(0, 2, lblPreco);
        setWidget(0, 3, lblClassificacao);
        setWidget(0, 4, lblQuantidade);
        setWidget(0, 5, lblAlteraQuantidade);
        setWidget(0, 6, lblAlterar);
        GWT.log(String.valueOf(listItens.size()));
        for (int i = 0; i < listItens.size(); i++) {
            setHTML(i + 1, 0, listItens.get(i).getPratoDto().getNome());
            setHTML(i + 1, 1, String.valueOf(listItens.get(i).getPratoDto().getCalorias()));
            setHTML(i + 1, 2, String.valueOf(listItens.get(i).getPratoDto().getPreco()));
            setHTML(i + 1, 3, String.valueOf(listItens.get(i).getPratoDto().getClassificacao()));
            GWT.log("CLASSIFICACAO "+String.valueOf(i)+": "+String.valueOf(listItens.get(i).getPratoDto().getClassificacao()));
            setHTML(i + 1, 4, String.valueOf(listItens.get(i).getQuantidade()));
            BotaoAlterarTabuleiro btnPagarTabuleiro = new BotaoAlterarTabuleiro();
            setWidget(i + 1, 5, btnPagarTabuleiro.getNewTbNovaQuantia());
            setWidget(i + 1, 6, btnPagarTabuleiro.getNewAlterarTabuleiroButton());
        }
        setWidget(listItens.size() + 1, 0, horizontalPanel);
        for (int i = 0; i < 7; i++) {
            this.getColumnFormatter().setWidth(i, "75px");
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

    public void refreshContactList() {
        // TODO Auto-generated method stub
        
    }
}
