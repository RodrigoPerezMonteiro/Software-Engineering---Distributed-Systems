package pt.ist.rest.service;

import pt.ist.chequerefeicao.CheckAlreadyUsedException;
import pt.ist.chequerefeicao.ChequeRefeicaoLocal;
import pt.ist.chequerefeicao.InvalidCheckException;
import pt.ist.rest.exceptions.restException;
import pt.ist.rest.service.dto.ChequeDto;
import pt.ist.rest.service.dto.ClienteDto;

public class ServiceBridgeLocal implements IServiceBridge {
    ChequeDto _chequeDto;
    ClienteDto _clienteDto;
    int valor;
    private static ChequeRefeicaoLocal chequeRefeicaoLocal;
    // aqui estara tb a factura local
    
    public ServiceBridgeLocal() {
        chequeRefeicaoLocal = new ChequeRefeicaoLocal();
    }


    @Override
    public void CashChecks(ClienteDto clienteDTO, ChequeDto chequeDTO) throws restException {
        this._chequeDto = chequeDTO;
        this._clienteDto = clienteDTO;
        try {
            valor = chequeRefeicaoLocal.cashChecks(_clienteDto.get_username(),_chequeDto.getChequeList());
        } catch (InvalidCheckException invC) {
            throw new restException(invC.getMessage());
        } catch (CheckAlreadyUsedException cAused) {
            throw new restException(cAused.getMessage());
        }
    }
    
    public int getValor() {
        return valor;
    }
}
