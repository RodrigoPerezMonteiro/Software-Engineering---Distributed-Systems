package pt.chequerefeicao.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.naming.*;
import javax.xml.registry.*;
import javax.xml.ws.BindingProvider;

import static javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY;
import pt.chequerefeicao.uddi.UDDINaming;
import pt.chequerefeicao.ws.ChequeInexistente_Exception;
import pt.chequerefeicao.ws.ChequeJaUsado_Exception;
import pt.chequerefeicao.ws.ChequeNaoEndossavel_Exception;
import pt.chequerefeicao.ws.ChequeRefeicaoPortType;
import pt.chequerefeicao.ws.ChequeRefeicaoService;
import pt.chequerefeicao.ws.UtilizadorInexistente_Exception;
import pt.chequerefeicao.ws.ValorInvalido_Exception;

public class ChequeRefeicaoCliente {

	public static void main(String[] args) throws JAXRException, UtilizadorInexistente_Exception, ValorInvalido_Exception, ChequeJaUsado_Exception, ChequeInexistente_Exception, ChequeNaoEndossavel_Exception {

		if (args.length < 2) {
			System.err.println("Argument(s) missing!");
			System.err.printf("Usage: java %s uddiURL name%n",
					ChequeRefeicaoCliente.class.getName());
			return;
		}

		String uddiURL = args[0];
		String name = args[1];

		System.out.printf("Contacting UDDI at %s%n", uddiURL);
		UDDINaming uddiNaming = new UDDINaming(uddiURL);

		System.out.printf("Looking for '%s'%n", name);
		String endpointAddress = uddiNaming.lookup(name);

		if (endpointAddress == null) {
			System.out.println("Not found!");
			return;
		} else {
			System.out.printf("Found %s%n", endpointAddress);
		}

		ChequeRefeicaoService service = new ChequeRefeicaoService();
		ChequeRefeicaoPortType port = service.getChequeRefeicaoPort();

		
		BindingProvider bindingProvider = (BindingProvider) port;
		Map<String, Object> requestContext = bindingProvider.getRequestContext();
		requestContext.put(ENDPOINT_ADDRESS_PROPERTY, endpointAddress);

		String funcao;
		Scanner scanIn = new Scanner(System.in);

		do {

			System.out.println("Introduza uma das opcoes \n\temitir \n\tsacar \n\tendossar \n\tlistar \n\tsair");
			funcao = scanIn.nextLine();

			try {
				switch (funcao) {

				case "emitir":
					System.out.println("Titular: ");
					String titular = scanIn.nextLine();
					System.out.println("Valor: ");
					String valorS = scanIn.nextLine();
					System.out.println("Endossavel - True or False: ");
					String endossavelS="";
					do{endossavelS = scanIn.nextLine();} 
					while( (!endossavelS.equals("True")) && (!endossavelS.equals("False")) &&
							(!endossavelS.equals("TRUE")) && (!endossavelS.equals("FALSE")) &&
							(!endossavelS.equals("1")) && (!endossavelS.equals("0")) &&
							(!endossavelS.equals("yes")) && (!endossavelS.equals("no")) &&
							(!endossavelS.equals("true")) && (!endossavelS.equals("false")));

					String resultEmitir = port.emitir(titular,
							Integer.parseInt(valorS),
							Boolean.parseBoolean(endossavelS));
					System.out.println(resultEmitir);

					break;

				case "sacar":
					String id;
					List<String> ids = new ArrayList<String>();
					System.out.println("Beneficiario: ");
					String beneficiario = scanIn.nextLine();
					System.out.println("Introduza ids e escreva 'end' para terminar): ");
					do {
						id = scanIn.nextLine();<<
						if (id.equals("end")) break;
						ids.add(id);
					} while (true);
					Integer resultSacar = port.sacar(beneficiario, ids);
					System.out.println(resultSacar);
					break;

				case "endossar":
					System.out.println("Titular: ");
					titular = scanIn.nextLine();
					System.out.println("Beneficiario: ");
					String benef = scanIn.nextLine();
					System.out.println("ID do cheque: ");
					String idCheque = scanIn.nextLine();

					String resultEndossar = port.endossar(titular,
							benef, idCheque);
					System.out.println(resultEndossar);
					break;

				case "listar":
					System.out.println("Titular: ");
					titular = scanIn.nextLine();
					System.out.println("Introduza True p/ cheques usados ou False p/ cheques nao-usados: ");
					String usado="";
					do{usado = scanIn.nextLine();} 
					while( (!usado.equals("True")) && (!usado.equals("False")) &&
							(!usado.equals("TRUE")) && (!usado.equals("FALSE")) &&
							(!usado.equals("1")) && (!usado.equals("0")) &&
							(!usado.equals("yes")) && (!usado.equals("no")) &&
							(!usado.equals("true")) && (!usado.equals("false")));

					System.out.printf("Remote call to %s ...%n",
							endpointAddress);
					List<String> resultListar = port.listar(titular,
							Boolean.parseBoolean(usado));
					System.out.println(resultListar);
					break;
				default:
					break;
				}
			} catch (UtilizadorInexistente_Exception e) {
				System.out.printf("%s%n", e);
			} catch (ValorInvalido_Exception e) {
				System.out.printf("%s%n", e);
			} catch (ChequeJaUsado_Exception e) {
				System.out.printf("%s%n", e);
			} catch (ChequeInexistente_Exception e) {
				System.out.printf("%s%n", e);
			} catch (ChequeNaoEndossavel_Exception e) {
				System.out.printf("%s%n", e);
			}
		} while (funcao != "sair");
		scanIn.close();

	}
}
