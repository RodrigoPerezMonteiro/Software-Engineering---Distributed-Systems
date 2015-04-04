package pt.ist.rest.presentation.client;

import pt.ist.rest.exceptions.ClienteNaoExisteException;
import pt.ist.rest.exceptions.WrongPasswordException;
import pt.ist.rest.service.dto.ClienteDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class LoginPage extends Composite {
    private Button btnLogin;
    private TextBox tbUsername;
    private PasswordTextBox tbPassword;

    public LoginPage(final RestGWT rootPage,final RestServletAsync rpcService) {
        
        FlexTable flexTable = new FlexTable();
        
        Label lblClient = new Label("Username:");
        lblClient.setStyleName("label");
        flexTable.setWidget(0, 0, lblClient);
        lblClient.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        
        Label lblPassword = new Label("Password:");
        lblPassword.setStyleName("label");
        flexTable.setWidget(1, 0, lblPassword);
        lblPassword.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);

        tbUsername = new TextBox();
        flexTable.setWidget(0, 1, tbUsername);
        
        tbPassword = new PasswordTextBox();
        flexTable.setWidget(1, 1, tbPassword);

        tbUsername.addKeyUpHandler(new KeyUpHandler() {
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    doLogin(rootPage, rpcService);
                }
            }
        });

        btnLogin = new Button("login");
        flexTable.setWidget(1, 2, btnLogin);

        btnLogin.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                doLogin(rootPage, rpcService);
            }
        });

        initWidget(flexTable);
    }

    private final void doLogin(final RestGWT rootPage,final RestServletAsync rpcService) {
        // if any of the fields is empty, show warning:
        if (this.tbUsername.getText().equals("")) {
            rootPage.showErrorMessage("Please fill your Client Username");
            return;
        }

        if (this.tbPassword.getText().equals("")) {
            rootPage.showErrorMessage("Please fill your Password");
            return;
        }

        final ClienteDto clienteDTO = new ClienteDto(this.tbUsername.getText(),this.tbPassword.getText());

        rpcService.login(clienteDTO, new AsyncCallback<Void>(){

            public void onSuccess(Void response) {
                rootPage.showManagementPage();
                GWT.log("logincom sucesso");
            }

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("presentationserver.client.RestGWT::onModuleLoad()::rpcService.login");
                GWT.log("-- Throwable: '" + caught.getClass().getName() + " Message '" + caught.getMessage());
                if (caught instanceof ClienteNaoExisteException) {
                    ClienteNaoExisteException ex = (ClienteNaoExisteException) caught;
                  rootPage.showErrorMessage("The client '" + ex.getNomeCliente() + "' does not exist.");
                }
                if (caught instanceof WrongPasswordException) {
                    WrongPasswordException wp = (WrongPasswordException) caught;
                  rootPage.showErrorMessage("The password '" + wp.getPasswordCliente() + "' for client '"+wp.getUsernameCliente()+"' is incorrect.");
                }
              }
        });
    }
    
    public void hidePage() {
        RootPanel.get("InformationContainer").clear();
        tbPassword.setText("");
        tbUsername.setText("");
    }

}
