package pt.ist.rest.presentation.client.view;

import java.awt.Button;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.presentation.client.RestGWT;
import pt.ist.rest.presentation.client.RestServletAsync;
import pt.ist.rest.service.dto.ClienteDto;
import pt.ist.rest.service.dto.ItemDto;
import pt.ist.rest.service.dto.PratoDto;
import pt.ist.rest.service.dto.RestauranteDto;

public class ListarTabuleiroComprasPanel extends FlexTable {
//
//	private Button voltar, alteraQtd, pagarTabuleiro;
//	private TextBox textBox;
//	private ClienteDto clienteDTO;
//	private PratoDto pratoDTO;
//	private List<ItemDto> listItens;
//	private FlexTable flexTable = new FlexTable();
//
	public ListarTabuleiroComprasPanel( final RestServletAsync rpcService, final RestGWT rootPage) {}
//
//		Label lblNome = new Label("Nome Prato:");
//		lblNome.setStyleName("label");
//		flexTable.setWidget(0, 0, lblNome);
//		Label lblCalorias = new Label("Calorias:");
//		lblCalorias.setStyleName("label");
//		flexTable.setWidget(0, 1, lblCalorias);
//		Label lblPreco = new Label("Preco:");
//		lblPreco.setStyleName("label");
//		flexTable.setWidget(0, 2, lblPreco);
//		Label lblClassificacao = new Label("Classificacao:");
//		lblClassificacao.setStyleName("label");
//		flexTable.setWidget(0, 3, lblClassificacao);
//		Label lblQuantidade = new Label("Quantidade:");
//		lblQuantidade.setStyleName("label");
//		flexTable.setWidget(0, 4, lblQuantidade);
//
//		textBox = new TextBox();
//		flexTable.setWidget(2, 5, textBox);
//
//		rpcService.mostraTabuleiro(clienteDTO.getUsername(),
//				new AsyncCallback<List<ItemDto>>() {
//
//					public void onSuccess(List<ItemDto> response) {
//						addItemTable(response);
//					}
//
//					public void onFailure(Throwable caught) {
//						GWT.log("Erro");
//						/*
//						 * GWT.log("-- Throwable: '" +
//						 * caught.getClass().getName() + "'");
//						 * Window.alert("ERROR: Cannot list contacts: " +
//						 * caught.getMessage());
//						 */
//					}
//				});
//
//		voltar = new Button("Voltar");
//		flexTable.setWidget(1, 5, voltar);
//
//		alteraQtd = new Button("Voltar");
//		flexTable.setWidget(2, 6, alteraQtd);
//
//		pagarTabuleiro = new Button("Pagar Tabuleiro");
//		flexTable.setWidget(7, 5, pagarTabuleiro);
//		initWidget(flexTable);
//
//		voltar.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				rootPage.ListarRestaurantesPage(clienteDTO);
//			}
//		});
//
//		new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				int cellIndex = flexTable.getCellForEvent(event).getCellIndex();
//				int rowIndex = flexTable.getCellForEvent(event).getRowIndex();
//
//				pratoDTO = listItens.get(rowIndex - 1);
//			}
//		};
//
//		alteraQtd.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				rpcService.alteraQtdPratoTabuleiro(clienteDTO, pratoDTO,
//						Integer.parseInt(this.textBox.getText()),
//						new AsyncCallback<Void>() {
//
//							public void onSuccess(Void response) {
//								rootPage.ListarTabuleiroComprasPage(clienteDTO);
//							}
//
//							public void onFailure(Throwable caught) {
//								GWT.log("Erro");
//								/*
//								 * GWT.log("-- Throwable: '" +
//								 * caught.getClass().getName() + "'");
//								 * Window.alert("ERROR: Cannot list contacts: "
//								 * + caught.getMessage());
//								 */
//							}
//						});
//
//			}
//		});
//
//		pagarTabuleiro.addClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				// Ir para a pagina Pagar
//			}
//		});
//
//		textBox.addKeyUpHandler(new KeyUpHandler() {
//			public void onKeyUp(KeyUpEvent event) {
//				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
//					rpcService.alteraQtdPratoTabuleiro(clienteDTO, pratoDTO,
//							Integer.parseInt(this.textBox.getText()),
//							new AsyncCallback<Void>() {
//
//								public void onSuccess(Void response) {
//									rootPage.ListarTabuleiroComprasPage(clienteDTO);
//								}
//
//								public void onFailure(Throwable caught) {
//									GWT.log("Erro");
//									/*
//									 * GWT.log("-- Throwable: '" +
//									 * caught.getClass().getName() + "'");
//									 * Window
//									 * .alert("ERROR: Cannot list contacts: " +
//									 * caught.getMessage());
//									 */
//								}
//							});
//				}
//			}
//		});
//
//	}
//
//	public ListarTabuleiroComprasPage(RestGWT restGWT, RestServletAsync rpcService) {
//        // TODO Auto-generated constructor stub
//    }
//
//    private void addItemTable(List<ItemDto> listItens) {
//		int row = 1;
//		for (ItemDto i : listItens) {
//			flexTable.insertCell(row, 0);
//			flexTable.insertCell(row, 1);
//			flexTable.insertCell(row, 2);
//			flexTable.insertCell(row, 3);
//			flexTable.insertCell(row, 4);
//			flexTable.setWidget(row, 0, i.getPratoDto().getNome());
//			flexTable.setWidget(row, 1, i.getPratoDto().getCalorias());
//			flexTable.setWidget(row, 2, i.getPratoDto().getPreco());
//			flexTable.setWidget(row, 3, i.getPratoDto().getClassificacao());
//			flexTable.setWidget(row, 4, i.getQuantidade());
//			row++;
//		}
//
//	}
//
//    public void hidePage() {
//        // TODO Auto-generated method stub
//        
//    }
//

    public void refreshContactList() {
        // TODO Auto-generated method stub
        
    }
}
