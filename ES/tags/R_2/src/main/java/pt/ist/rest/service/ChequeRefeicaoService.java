package pt.ist.rest.service;

import pt.ist.chequerefeicao.CheckAlreadyUsedException;
import pt.ist.chequerefeicao.ChequeRefeicaoLocal;
import pt.ist.chequerefeicao.InvalidCheckException;
import pt.ist.rest.exceptions.restException;
import pt.ist.rest.service.dto.ChequeDto;
import pt.ist.rest.service.dto.ClienteDto;

public class ChequeRefeicaoService extends
        PortalRestauranteService {
    ChequeDto _chequeDto;
    ClienteDto _clienteDto;
    int valor;

    public ChequeRefeicaoService(ClienteDto clienteDTO, ChequeDto chequeDTO) {
        this._chequeDto = chequeDTO;
        this._clienteDto = clienteDTO;
    }


    @Override
    public void dispatch() throws restException {
        ChequeRefeicaoLocal chequeRefeicaoLocal = new ChequeRefeicaoLocal();
        try {
            valor = chequeRefeicaoLocal.cashChecks(_clienteDto.get_username(),_chequeDto.getChequeList());
        } catch (InvalidCheckException e) {
           System.out.println("\n"+e);
        } catch (CheckAlreadyUsedException e1) {
            System.out.println("\n"+e1);
        }
    }
    
    public int getValor() {
        return valor;
    }
}
