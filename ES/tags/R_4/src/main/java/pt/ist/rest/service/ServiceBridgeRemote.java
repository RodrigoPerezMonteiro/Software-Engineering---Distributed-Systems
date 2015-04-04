package pt.ist.rest.service;

import pt.ist.chequerefeicao.CheckAlreadyUsedException;
import pt.ist.chequerefeicao.ChequeRefeicaoLocal;
import pt.ist.chequerefeicao.InvalidCheckException;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.registofatura.RegistoFaturaRemote;
import pt.ist.rest.domain.Compra;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.exceptions.restException;
import pt.ist.rest.service.dto.ChequeDto;
import pt.ist.rest.service.dto.ClienteDto;

public class ServiceBridgeRemote extends PortalRestauranteService implements IServiceBridge {

    ChequeDto _chequeDto;
    ClienteDto _clienteDto;
    ClienteDto _clienteDto2;
    int valor;
    private int _numSerie = 1;
    private static ChequeRefeicaoLocal chequeRefeicaoLocal;
    private static RegistoFaturaRemote registoFaturaRemoto;
    
    public ServiceBridgeRemote() {
        chequeRefeicaoLocal = new ChequeRefeicaoLocal();
        registoFaturaRemoto = new RegistoFaturaRemote();
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

    @Override
    public void passaFactura(ClienteDto clienteDTO) {
        if(clienteDTO==null)System.out.println("clientedto2 e nulllllllllllll");
        this._clienteDto2 = clienteDTO;
    }

    @Override
    public final void dispatch() {
        PortalRestaurante _portal = FenixFramework.getRoot();
        Integer nifEmissor = _portal.get_nif();
        Integer nrSerieS, nrSequenciaS;
        Integer nifCliente = _portal.getNifOfClientByUsername(_clienteDto2.getUsername());
        String nomecliente = _clienteDto2.getNome();
        String nomeEmissor = _portal.get_nome();
        Compra compraAfacturar = _portal.getCompraById(_portal.get_idLastTabuleiroFechado());
        Double total = compraAfacturar.getTotal();
        
        if (_portal.needSerie()) {
            nrSerieS = registoFaturaRemoto.pedirSerie(_portal.get_nif()).getNumSerie();
            _numSerie++;

        } else {
            nrSerieS = _numSerie;
        }
        nrSequenciaS = _portal.get_currentFacturasNumber();
        
        registoFaturaRemoto.comunicarFatura(nomeEmissor, nifEmissor, nomecliente, nifCliente, total.intValue(), nrSerieS, nrSequenciaS, compraAfacturar.getListaItens().size(), compraAfacturar);
        
    }
    
}
