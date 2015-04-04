package pt.ist.rest.service;
import pt.ist.rest.exceptions.restException;
import pt.ist.rest.service.dto.ChequeDto;
import pt.ist.rest.service.dto.ClienteDto;


public interface IServiceBridge {

    public void CashChecks(ClienteDto clienteDTO, ChequeDto chequeDTO) throws restException;
}
