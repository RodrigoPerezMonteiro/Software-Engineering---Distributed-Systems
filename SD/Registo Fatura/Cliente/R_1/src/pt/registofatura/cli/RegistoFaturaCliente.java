package pt.registofatura.cli;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.registry.JAXRException;
import javax.xml.ws.*;

import pt.registofatura.uddi.UDDINaming;
import pt.registofatura.ws.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import static javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY;

public class RegistoFaturaCliente {

	/**
	 * Convert string date to xml gregorian calendar.
	 * 
	 * @param dateStr
	 *            the date str
	 * @param dateFormat
	 *            the date format
	 * @return the XML gregorian calendar
	 */
	public static void main(String[] args) throws JAXRException, EmissorInexistente_Exception, FaturaInvalida_Exception, TotaisIncoerentes_Exception{
		
		if (args.length < 2) {
			System.err.println("Argument(s) missing!");
			System.err.printf("Usage: java %s uddiURL name%n", RegistoFaturaCliente.class.getName());
			return;
		}

		String uddiURL = args[0];
		String name = args[1];
	
		System.out.printf("Contacting UDDI at %s%n", uddiURL);
		String endpointAddress;
	
		UDDINaming uddiNaming = new UDDINaming(uddiURL);

		System.out.printf("Looking for '%s'%n", name);
		endpointAddress = uddiNaming.lookup(name);

		if (endpointAddress == null) {
			System.out.println("Not found!");
			return;
		} else {
			System.out.printf("Found %s%n", endpointAddress);
		}
		
		RegistoFaturaService service = new RegistoFaturaService();
		RegistoFaturaPortType port = service.getRegistoFaturaPort();


		BindingProvider bindingProvider = (BindingProvider) port;
		Map<String, Object> rc = bindingProvider.getRequestContext();
		rc.put(ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
	
		String option;
		Scanner scanIn = new Scanner(System.in);
		int numSerie = 0;
		int numSequencia;
		TreeMap<Integer, Integer> _serie = new TreeMap<Integer, Integer>();	
		
		do{
			System.out.println("");
			System.out.println("Seleccione uma das seguintes opcoes:");
			System.out.println("");
			System.out.println("ps - pedirSerie");
			System.out.println("cf - comunicarFatura");
			System.out.println("lf - listarFaturas");
			System.out.println("cid - consultarIVADevido");
			System.out.println("test - correr teste automatico 1");
			System.out.println("test2 - cliente inexistente ");
			System.out.println("");	
			
			option = scanIn.nextLine();
	
			try{
				
				if(option.equals("test")){
					
					
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
					
					try{
						
						Calendar cal = Calendar.getInstance();
				        cal.setTime(new Date());
				        
				        GregorianCalendar c = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
				        
				        XMLGregorianCalendar xmlGCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
				        
				        fatura.setData(xmlGCalendar);
					
					} catch(DatatypeConfigurationException e) {
						System.out.printf("%s%n", e);
					}
				    
					System.out.println("");
				    System.out.println("TESTE 1: COMUNICAR FATURA EMITIDA PELO EMISSOR XPTO(5001) AO CLIENTE ALICE(1001), VALOR 10, 1 ITEM = PATO À PEQUIM (VALOR 10, QUANTIDADE 1");
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
					
					if(resultListarFacturas.size() != 0) System.out.println("Result:");	
					
					for(Fatura f :  resultListarFacturas){
						System.out.println("Data: " + f.getData().toString() + "; Total: " + f.getTotal() + "; Iva: " + f.getIva());
					}
					
					
					//PEDIR SERIE XPTO 5001
					
					
					System.out.println("");
				    System.out.println("TESTE 3: PEDIR SERIE XPTO 5001");
				    System.out.println("");
					System.out.printf("Remote call to %s ...%n", endpointAddress);
					Serie resultPedirSerie = port.pedirSerie(5001);
					System.out.println("Result: numSerie: " + resultPedirSerie.getNumSerie() + " validoAte: " + resultPedirSerie.getValidoAte().toString());
					
					
					//CONSULTAR IVA DEVIDO NIF = 5001
					
					
					int resultConsultarIVADevido = 0;
					try{
					
						Calendar cal = Calendar.getInstance();
				        cal.setTime(new Date());
				        
				        GregorianCalendar c = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
				        
				        XMLGregorianCalendar xmlGCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

				        System.out.println("");
					    System.out.println("TESTE 4: CONSULTAR IVA DEVIDO NIF = 5001");
					    System.out.println("");
				        System.out.printf("Remote call to %s ...%n", endpointAddress);
						
					    resultConsultarIVADevido = port.consultarIVADevido(5001, xmlGCalendar);
						
						System.out.println("Result: " + resultConsultarIVADevido);
						
					} catch(DatatypeConfigurationException e) {
						System.out.printf("%s%n", e);
					}
				
				}else if(option.equals("test2")){
					
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
					
					try{
						
						Calendar cal = Calendar.getInstance();
				        cal.setTime(new Date());
				        
				        GregorianCalendar c = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
				        
				        XMLGregorianCalendar xmlGCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
				        
				        fatura.setData(xmlGCalendar);
					
					} catch(DatatypeConfigurationException e) {
						System.out.printf("%s%n", e);
					}
				    
					System.out.println("");
				    System.out.println("TESTE: COMUNICAR FATURA EMITIDA PELO EMISSOR XPTO(5001) AO CLIENTE COM NIF = 1, VALOR 10, 1 ITEM = PATO COM LARANJA (VALOR 10, QUANTIDADE 1");
				    System.out.println("");
					System.out.printf("Remote call to %s ...%n", endpointAddress);
					port.comunicarFatura(fatura);
					System.out.println("Fatura comunicada");
					
				}else if(option.equals("pedirSerie") || option.equals("ps")) {
				
					System.out.println("Introduza nif do Emissor: ");
					String nifS = scanIn.nextLine();
	
					System.out.printf("Remote call to %s ...%n", endpointAddress);
					Serie resultPedirSerie = port.pedirSerie(Integer.parseInt(nifS));
					if(resultPedirSerie != null) System.out.println("Result: numSerie: " + resultPedirSerie.getNumSerie() + " validoAte: " + resultPedirSerie.getValidoAte().toString());
					
					
				}else if(option.equals("comunicarFatura") || option.equals("cf")) {
					System.out.println("Introduza o nome do Emissor: ");
					String nomeEmissor = scanIn.nextLine();
					System.out.println("Introduza o nif do Emissor: ");
					String nifRestauranteS = scanIn.nextLine();
					System.out.println("Introduza o nome do Cliente: ");
					String nomeCliente = scanIn.nextLine();
					System.out.println("Introduza o nif do Cliente: ");
					String nifClienteS = scanIn.nextLine();
					System.out.println("Introduza o valor da fatura: ");
					String valorS = scanIn.nextLine();
					System.out.println("Introduza o numero de serie: ");
					String nrSerieS = scanIn.nextLine();
					System.out.println("Introduza o numero de itens: ");
					String nrItensS = scanIn.nextLine();
					
					Fatura fatura = new Fatura(); 
					
					for(int i=1; i<=Integer.parseInt(nrItensS); i++){
						System.out.printf("Introduza a descricao do item %d:\n", i);
						String descricao = scanIn.nextLine();
						System.out.printf("Introduza o preco do item %d:\n", i);
						String preco = scanIn.nextLine();
						int precoInt = Integer.parseInt(preco); 
						System.out.printf("Introduza a quantidade do item %d:\n", i);
						String quantidade = scanIn.nextLine();
						int quantidadeInt = Integer.parseInt(quantidade);
						ItemFatura item = new ItemFatura();
						item.setDescricao(descricao);
						item.setQuantidade(quantidadeInt);
						item.setPreco(precoInt);
						fatura.getItens().add(item);
					}
					
					int nifClienteInt = Integer.parseInt(nifClienteS);
					int nifRestauranteInt = Integer.parseInt(nifRestauranteS);
					int valorInt = Integer.parseInt(valorS);
					int nrSerieInt = Integer.parseInt(nrSerieS);
			        
					try{
					
						Calendar cal = Calendar.getInstance();
				        cal.setTime(new Date());
				        
				        GregorianCalendar c = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
				        
				        XMLGregorianCalendar xmlGCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
				        
				        fatura.setData(xmlGCalendar);
					
					} catch(DatatypeConfigurationException e) {
						System.out.printf("%s%n", e);
					}
			        
				    fatura.setIva(23);
				    fatura.setNifCliente(nifClienteInt);
				    fatura.setNifEmissor(nifRestauranteInt);
				    fatura.setNomeEmissor(nomeEmissor);
				    fatura.setTotal(valorInt);
				    fatura.setNumSerie(nrSerieInt);
				    
				    if(_serie.containsKey(nrSerieInt)){
				    	int sequencia = _serie.get(nrSerieInt);
				    	numSequencia = sequencia;
				    	_serie.put(nrSerieInt, numSequencia + 1);
				    	fatura.setNumSeqFatura(numSequencia + 1);
				    }else{
				    	numSequencia = 1;
				    	_serie.put(nrSerieInt, numSequencia);
				    	fatura.setNumSeqFatura(numSequencia);
				    }
					
					System.out.printf("Remote call to %s ...%n", endpointAddress);
					port.comunicarFatura(fatura);
					System.out.println("Fatura comunicada");
					
					
				}else if(option.equals("listarFaturas") || option.equals("lf")) {
					System.out.println("Introduza nif do Emissor: ");
					String nifEmissorS = scanIn.nextLine();
					System.out.println("Introduza nif do Cliente: ");
					String nifClienteS2 = scanIn.nextLine();
	
					System.out.printf("Remote call to %s ...%n", endpointAddress);
					List<Fatura> resultListarFacturas = port.listarFacturas(Integer.parseInt(nifEmissorS), Integer.parseInt(nifClienteS2));
					
					if(resultListarFacturas.size() != 0) System.out.println("Result:");
					
					for(Fatura f :  resultListarFacturas){
						System.out.println("Data: " + f.getData().toString() + "; Total: " + f.getTotal() + "; Iva: " + f.getIva());
						int a = 1;
						for(ItemFatura i : f.getItens()){
							System.out.println("Item " + a + ":" );
							System.out.println("Descricao: " + i.getDescricao() + "; Preco: " + i.getPreco() + "; Quantidade: " + i.getQuantidade());
							a++;
						}
					}
					
				}else if (option.equals("consultarIVADevido") || option.equals("cid")) {
					System.out.println("Introduza o nif do Emissor: ");
					String nifEmissorS2 = scanIn.nextLine();
					int resultConsultarIVADevido = 0;
					try{
					
						Calendar cal = Calendar.getInstance();
				        cal.setTime(new Date());
				        
				        GregorianCalendar c = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
				        
				        XMLGregorianCalendar xmlGCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

					    System.out.printf("Remote call to %s ...%n", endpointAddress);
						resultConsultarIVADevido = port.consultarIVADevido(Integer.parseInt(nifEmissorS2), xmlGCalendar);
						System.out.println("Result: " + resultConsultarIVADevido);
						
					} catch(DatatypeConfigurationException e) {
						System.out.printf("%s%n", e);
					}
				}
				
			}catch(ClienteInexistente_Exception e){
				System.out.printf("%s%n", e);
			}catch(EmissorInexistente_Exception e){
				System.out.printf("%s%n", e);
			}catch(FaturaInvalida_Exception e){
				System.out.printf("%s%n", e);
			}catch(TotaisIncoerentes_Exception e){
				System.out.printf("%s%n", e);
			}
		} while(!option.equals("sair"));
		scanIn.close();
	}
}