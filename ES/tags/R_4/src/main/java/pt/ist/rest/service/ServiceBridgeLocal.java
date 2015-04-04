package pt.ist.rest.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import pt.ist.chequerefeicao.CheckAlreadyUsedException;
import pt.ist.chequerefeicao.ChequeRefeicaoLocal;
import pt.ist.chequerefeicao.InvalidCheckException;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.registofatura.RegistoFaturaLocal;
import pt.ist.registofatura.ws.ClienteInexistente_Exception;
import pt.ist.registofatura.ws.EmissorInexistente_Exception;
import pt.ist.registofatura.ws.Fatura;
import pt.ist.registofatura.ws.FaturaInvalida_Exception;
import pt.ist.registofatura.ws.Serie;
import pt.ist.registofatura.ws.TotaisIncoerentes_Exception;
import pt.ist.rest.domain.Compra;
import pt.ist.rest.domain.Factura;
import pt.ist.rest.domain.Item;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.exceptions.restException;
import pt.ist.rest.service.dto.ChequeDto;
import pt.ist.rest.service.dto.ClienteDto;

public class ServiceBridgeLocal extends PortalRestauranteService implements IServiceBridge  {
    ChequeDto _chequeDto;
    ClienteDto _clienteDto;
    ClienteDto _clienteDto2;
    int valor;
    private static ChequeRefeicaoLocal chequeRefeicaoLocal;
    private static RegistoFaturaLocal registoFaturaLocal;
    
    public ServiceBridgeLocal() {
        chequeRefeicaoLocal = new ChequeRefeicaoLocal();
        registoFaturaLocal = new RegistoFaturaLocal();
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
    
    
    /**
     * Passa factura.
     */
    @Override
    public void passaFactura(ClienteDto clienteDTO) {
        if(clienteDTO==null)System.out.println("clientedto2 e nulllllllllllll");
        this._clienteDto2 = clienteDTO;
    }

    public final void dispatch() {
        PortalRestaurante _portal = FenixFramework.getRoot();
        System.out.println("IVA ->\t"+ _portal.get_iva());
        
        if(_portal == null) System.out.println("portal esta null");
        Integer iva = _portal.get_iva();
        Integer numSerie, numseqfact;
        Integer nifEmissor = _portal.get_nif();
        Integer nifCliente = _portal.getNifOfClientByUsername(_clienteDto2.getUsername());
        String nomeEmissor = _portal.get_nome();
        Compra compraAfacturar = _portal.getCompraById(_portal.get_idLastTabuleiroFechado());
        double total = compraAfacturar.getTotal();
        XMLGregorianCalendar xmlGCalendarActual, xmlGCalendarValidade;
        Fatura fatura = new Fatura();
        Factura factura = new Factura();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        Calendar calValidade = Calendar.getInstance();
        calValidade.setTime(new Date());

        calValidade.add(Calendar.DAY_OF_YEAR, 10);

        GregorianCalendar grActual = new GregorianCalendar(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        GregorianCalendar grValidade = new GregorianCalendar(calValidade.get(Calendar.YEAR),
                calValidade.get(Calendar.MONTH), calValidade.get(Calendar.DAY_OF_MONTH));

        try {
            xmlGCalendarActual = DatatypeFactory.newInstance().newXMLGregorianCalendar(grActual);
            xmlGCalendarValidade = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(grValidade);
            fatura.setData(xmlGCalendarActual);
            factura.set_ano(xmlGCalendarActual.getYear());
            factura.set_mes(xmlGCalendarActual.getMonth());
            factura.set_dia(xmlGCalendarActual.getDay());

            if (_portal.needSerie()) {
                System.out.println("preciso de serieeeeeeeeeeeeee");
                System.out.println("Nif portal:\t" + _portal.get_nif());
                Serie  serie = registoFaturaLocal.pedirSerie(_portal.get_nif());
                serie.setValidoAte(xmlGCalendarValidade);
                _portal.setSerie(serie);
                numSerie = _portal.getSerie().getNumSerie();

            } else {
                if(_portal.getSerie()==null){
                    Serie  serie = registoFaturaLocal.pedirSerie(_portal.get_nif());
                    serie.setValidoAte(xmlGCalendarValidade);
                    _portal.setSerie(serie);
                    numSerie = _portal.getSerie().getNumSerie();
                }
                else{
                numSerie = _portal.getSerie().getNumSerie();
                }
            }
            numseqfact = _portal.get_currentFacturasNumber();
            fatura.setIva(iva);
            fatura.setNifCliente(nifCliente);
            fatura.setNifEmissor(nifEmissor);
            fatura.setNomeEmissor(nomeEmissor);
            fatura.setNumSeqFatura(numseqfact);
            fatura.setNumSerie(numSerie);
            fatura.setTotal((int)total);
            
            registoFaturaLocal.comunicarFatura(fatura);
            
            factura.set_iva(iva);
            factura.set_nifCliente(nifCliente);
            factura.set_nifEmissor(nifEmissor);
            factura.set_nomeEmissor(nomeEmissor);
            factura.set_numSeqFactura(numseqfact);
            factura.set_numSerie(numSerie);
            factura.set_total(total);
            for (Item i : compraAfacturar.getItemSet()) {
                factura.addItem(i);
            }
            _portal.addFactura(factura);
            _portal.incCurrentFacturasNumber();
            
        } catch (DatatypeConfigurationException e1) {
            throw new restException(e1.getMessage());
        } catch (EmissorInexistente_Exception e2) {
            throw new restException(e2.getMessage());
        } catch (ClienteInexistente_Exception e3) {
            throw new restException(e3.getMessage());
        } catch (FaturaInvalida_Exception e4) {
            throw new restException(e4.getMessage());
        } catch (TotaisIncoerentes_Exception e5) {
            throw new restException(e5.getMessage());
        }
    }
    
}
