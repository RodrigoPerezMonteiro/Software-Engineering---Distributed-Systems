package pt.ist.registofatura;


import java.util.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.registry.JAXRException;
import javax.xml.ws.*;

import pt.ist.rest.domain.Compra;
import pt.ist.rest.domain.Item;
import pt.registofatura.cli.RegistoFaturaCliente;
import pt.registofatura.ws.*;
import pt.registofatura.ws.uddi.UDDINaming;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY;

public class RegistoFaturaRemote {
    static RegistoFaturaPortType port;
    static RegistoFaturaService service;
    static BindingProvider bindingProvider;
    static Map<String, Object> rc;
    static String endpointAddress;
    static pt.registofatura.ws.uddi.UDDINaming uddiNaming;
    static String uddiURL;
    static String name;


    public static void refreshPort(boolean showPrint) throws JAXRException {
        if (showPrint) {
            System.out.printf("Looking for '%s'%n", name);
        }
        endpointAddress = uddiNaming.lookup(name);

        if (endpointAddress == null) {
            System.out.println("Not found!");
            return;
        } else {
            if (showPrint) {
                System.out.printf("Found %s%n", endpointAddress);
            }
        }

        service = new RegistoFaturaService();
        port = service.getRegistoFaturaPort();


        bindingProvider = (BindingProvider) port;
        rc = bindingProvider.getRequestContext();
        rc.put(ENDPOINT_ADDRESS_PROPERTY, endpointAddress);

    }

    public static void main(String[] args) throws JAXRException,EmissorInexistente_Exception,FaturaInvalida_Exception,TotaisIncoerentes_Exception {

        if (args.length < 2) {
            System.err.println("Argument(s) missing!");
            System.err.printf("Usage: java %s uddiURL name%n", RegistoFaturaCliente.class.getName());
            return;
        }

        uddiURL = args[0];
        name = args[1];

        System.out.printf("Contacting UDDI at %s%n", uddiURL);

        uddiNaming = new UDDINaming(uddiURL);
    }




    public void test() {

        try {
            refreshPort(false);

            //COMUNICAR FATURA EMITIDA PELO EMISSOR XPTO(5001) AO CLIENTE ALICE(1001), VALOR 10, 1 ITEM = PATO À PEQUIM (VALOR 10, QUANTIDADE 1)


            Fatura fatura = new Fatura();

            ItemFatura item = new ItemFatura();

            item.setDescricao("Pato à Pequim");
            item.setQuantidade(1);
            item.setPreco(10);
            fatura.getItens().add(item);

            fatura.setIva(23);
            fatura.setNifCliente(1001); //alice
            fatura.setNifEmissor(5001);
            fatura.setNomeEmissor("xpto");
            fatura.setTotal(10);
            fatura.setNumSerie(1);
            fatura.setNumSeqFatura(1);

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());

            GregorianCalendar c = new GregorianCalendar(cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

            XMLGregorianCalendar xmlGCalendar = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(c);

            fatura.setData(xmlGCalendar);

            System.out.println("");
            System.out
                    .println("TESTE 1: COMUNICAR FATURA EMITIDA PELO EMISSOR XPTO(5001) AO CLIENTE ALICE(1001), VALOR 10, 1 ITEM = PATO À PEQUIM (VALOR 10, QUANTIDADE 1");
            System.out.println("");
            System.out.printf("Remote call to %s ...%n", endpointAddress);
            port.comunicarFatura(fatura);
            System.out.println("Fatura comunicada");


            //LISTAR FATURAS NIF EMISSOR = 5001; NIF CLIENTE = 1001

            System.out.println("");
            System.out.println("TESTE 2: LISTAR FATURAS NIF EMISSOR = 5001; NIF CLIENTE = 1001");
            System.out.println("");
            System.out.printf("Remote call to %s ...%n", endpointAddress);
            List<Fatura> resultListarFacturas = port.listarFacturas(5001, 1001);

            if (resultListarFacturas.size() != 0)
                System.out.println("Result:");

            for (Fatura f : resultListarFacturas) {
                System.out.println("Data: " + f.getData().toString() + "; Total: " + f.getTotal()
                        + "; Iva: " + f.getIva());
            }


            //PEDIR SERIE XPTO 5001


            System.out.println("");
            System.out.println("TESTE 3: PEDIR SERIE XPTO 5001");
            System.out.println("");
            System.out.printf("Remote call to %s ...%n", endpointAddress);
            Serie resultPedirSerie = port.pedirSerie(5001);
            System.out.println("Result: numSerie: " + resultPedirSerie.getNumSerie()
                    + " validoAte: " + resultPedirSerie.getValidoAte().toString());


            //CONSULTAR IVA DEVIDO NIF = 5001


            int resultConsultarIVADevido = 0;
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(new Date());

            GregorianCalendar c1 = new GregorianCalendar(cal1.get(Calendar.YEAR),
                    cal1.get(Calendar.MONTH), cal1.get(Calendar.DAY_OF_MONTH));

            XMLGregorianCalendar xmlGCalendar1 = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(c1);

            System.out.println("");
            System.out.println("TESTE 4: CONSULTAR IVA DEVIDO NIF = 5001");
            System.out.println("");
            System.out.printf("Remote call to %s ...%n", endpointAddress);

            resultConsultarIVADevido = port.consultarIVADevido(5001, xmlGCalendar1);

            System.out.println("Result: " + resultConsultarIVADevido);


        } catch (JAXRException e1) {
            e1.printStackTrace();
        } catch (ClienteInexistente_Exception e1) {
            e1.printStackTrace();
        } catch (EmissorInexistente_Exception e1) {
            e1.printStackTrace();
        } catch (FaturaInvalida_Exception e1) {
            e1.printStackTrace();
        } catch (TotaisIncoerentes_Exception e1) {
            e1.printStackTrace();
        } catch (DatatypeConfigurationException e) {
            System.out.printf("%s%n", e);
        }
    }

    public void test2() {
        try {
            refreshPort(false);
            //COMUNICAR FATURA EMITIDA PELO EMISSOR XPTO(5001) AO CLIENTE COM NIF = 1, VALOR 10, 1 ITEM = PATO À PEQUIM (VALOR 10, QUANTIDADE 1)


            Fatura fatura = new Fatura();

            ItemFatura item = new ItemFatura();

            item.setDescricao("Pato com Laranja");
            item.setQuantidade(1);
            item.setPreco(10);
            fatura.getItens().add(item);

            fatura.setIva(23);
            fatura.setNifCliente(1); //alice
            fatura.setNifEmissor(5001);
            fatura.setNomeEmissor("xpto");
            fatura.setTotal(10);
            fatura.setNumSerie(1);
            fatura.setNumSeqFatura(1);


            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());

            GregorianCalendar c = new GregorianCalendar(cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

            XMLGregorianCalendar xmlGCalendar = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(c);

            fatura.setData(xmlGCalendar);

            System.out.println("");
            System.out
                    .println("TESTE: COMUNICAR FATURA EMITIDA PELO EMISSOR XPTO(5001) AO CLIENTE COM NIF = 1, VALOR 10, 1 ITEM = PATO COM LARANJA (VALOR 10, QUANTIDADE 1");
            System.out.println("");
            System.out.printf("Remote call to %s ...%n", endpointAddress);
            port.comunicarFatura(fatura);

            System.out.println("Fatura comunicada");

        } catch (JAXRException e1) {
            e1.printStackTrace();
        } catch (DatatypeConfigurationException e) {
            System.out.printf("%s%n", e);
        } catch (ClienteInexistente_Exception e) {
            e.printStackTrace();
        } catch (EmissorInexistente_Exception e) {
            e.printStackTrace();
        } catch (FaturaInvalida_Exception e) {
            e.printStackTrace();
        } catch (TotaisIncoerentes_Exception e) {
            e.printStackTrace();
        }
    }

    public Serie pedirSerie(int nifS) {
           
        System.out.printf("Remote call to %s ...%n", endpointAddress);
        Serie resultPedirSerie;
        try {
            refreshPort(false);
            resultPedirSerie = port.pedirSerie(nifS);

            if (resultPedirSerie != null){
                System.out.println("Result: numSerie: " + resultPedirSerie.getNumSerie()
                        + " validoAte: " + resultPedirSerie.getValidoAte().toString());
                return resultPedirSerie;
            }
            else{
                return null;
            }
        } catch (EmissorInexistente_Exception e) {
            e.printStackTrace();
        }catch (JAXRException e1) {
            e1.printStackTrace();
        }
        return null;

    }

    public void comunicarFatura(String nomeEmissor,
                                Integer nifRestauranteS,
                                String nomeCliente,
                                Integer nifClienteS,
                                Integer valorS,
                                Integer nrSerieS,
                                Integer nrSequenciaS,
                                Integer nrItensS,
                                Compra c) {


        Fatura fatura = new Fatura();

        for (Item i : c.getItemSet()) {

            ItemFatura item = new ItemFatura();
            item.setDescricao(i.get_Prato().get_nome());
            item.setQuantidade(i.getQuantidade());
            item.setPreco(i.get_Prato().getPreco().intValue());
            fatura.getItens().add(item);
        }

        try {
            refreshPort(false);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());

            GregorianCalendar c2 = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

            XMLGregorianCalendar xmlGCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c2);

            fatura.setData(xmlGCalendar);



            fatura.setIva(23);
            fatura.setNifCliente(nifClienteS);
            fatura.setNifEmissor(nifRestauranteS);
            fatura.setNomeEmissor(nomeEmissor);
            fatura.setTotal(valorS);
            fatura.setNumSerie(nrSerieS);
            fatura.setNumSeqFatura(nrSequenciaS);

            System.out.printf("Remote call to %s ...%n", endpointAddress);
            port.comunicarFatura(fatura);
            System.out.println("Fatura comunicada");
        } catch (DatatypeConfigurationException e) {
            System.out.printf("%s%n", e);
        } catch (ClienteInexistente_Exception e) {
            e.printStackTrace();
        } catch (EmissorInexistente_Exception e) {
            e.printStackTrace();
        } catch (FaturaInvalida_Exception e) {
            e.printStackTrace();
        } catch (TotaisIncoerentes_Exception e) {
            e.printStackTrace();
        } catch (JAXRException e) {
            e.printStackTrace();
        }

    }

    public void listarFaturas(Integer nifEmissorS, Integer nifClienteS2) {
        try {
            refreshPort(false);
            System.out.printf("Remote call to %s ...%n", endpointAddress);
            List<Fatura> resultListarFacturas;

            resultListarFacturas = port.listarFacturas(nifEmissorS, nifClienteS2);


            if (resultListarFacturas.size() != 0)
                System.out.println("Result:");

            for (Fatura f : resultListarFacturas) {
                System.out.println("Data: " + f.getData().toString() + "; Total: " + f.getTotal()
                        + "; Iva: " + f.getIva());
                int a = 1;
                for (ItemFatura i : f.getItens()) {
                    System.out.println("Item " + a + ":");
                    System.out.println("Descricao: " + i.getDescricao() + "; Preco: "+ i.getPreco() + "; Quantidade: " + i.getQuantidade());
                    a++;
                }
            }

        } catch (ClienteInexistente_Exception e) {
            e.printStackTrace();
        } catch (EmissorInexistente_Exception e) {
            e.printStackTrace();
        } catch (JAXRException e) {
            e.printStackTrace();
        }

    }

    public void consultarIVADevido(Integer nifEmissorS2) {
        int resultConsultarIVADevido = 0;
        try {
            refreshPort(false);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());

            GregorianCalendar c = new GregorianCalendar(cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

            XMLGregorianCalendar xmlGCalendar = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(c);

            System.out.printf("Remote call to %s ...%n", endpointAddress);
            resultConsultarIVADevido = port.consultarIVADevido(nifEmissorS2, xmlGCalendar);

            System.out.println("Result: " + resultConsultarIVADevido);

        } catch (DatatypeConfigurationException e) {
            System.out.printf("%s%n", e);
        } catch (EmissorInexistente_Exception e) {
            e.printStackTrace();
        } catch (JAXRException e) {
            e.printStackTrace();
        }
    }
}
