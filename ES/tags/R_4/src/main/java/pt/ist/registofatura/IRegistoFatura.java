package pt.ist.registofatura;

import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import pt.ist.registofatura.ws.ClienteInexistente_Exception;
import pt.ist.registofatura.ws.EmissorInexistente_Exception;
import pt.ist.registofatura.ws.Fatura;
import pt.ist.registofatura.ws.FaturaInvalida_Exception;
import pt.ist.registofatura.ws.Serie;
import pt.ist.registofatura.ws.TotaisIncoerentes_Exception;


    public interface IRegistoFatura {

        public Serie pedirSerie(int nifEmissor) throws EmissorInexistente_Exception;
        public void comunicarFatura(Fatura fatura) throws ClienteInexistente_Exception, EmissorInexistente_Exception,FaturaInvalida_Exception, TotaisIncoerentes_Exception;
        public List<Fatura> listarFacturas(Integer nifEmissor, Integer nifCliente) throws ClienteInexistente_Exception, EmissorInexistente_Exception;
        public int consultarIVADevido(int nifEmissor, XMLGregorianCalendar ano) throws EmissorInexistente_Exception;
    }
