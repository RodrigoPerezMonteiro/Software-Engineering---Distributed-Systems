package pt.chequerefeicao.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
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
	
	private static Map<String, PrivateKey> privateKeys;
	private static PublicKey chequeRefeicaoPubKey;
	private static File folder = new File("keys");
	private static File[] folderFiles = folder.listFiles();
	private static String publicKey = null;
	private static String result;
	private static File file;
	private static byte[] pubEncoded;

	public static Map<String, PrivateKey> getPrivateKeys(){
		return privateKeys;
	}
	
	public static PublicKey getCRPubKey(){
		return chequeRefeicaoPubKey;
	}
	
	private static byte[] readFile(String path)
			throws FileNotFoundException, IOException {
		FileInputStream fis = new FileInputStream(path);
        byte[] content = new byte[fis.available()];
        fis.read(content);
        fis.close();
		return content;
	}
	
	public static void main(String[] args) throws JAXRException, UtilizadorInexistente_Exception, ValorInvalido_Exception, ChequeJaUsado_Exception, ChequeInexistente_Exception, ChequeNaoEndossavel_Exception, IOException, NoSuchAlgorithmException, InvalidKeySpecException {

		if (args.length < 2) {
			System.err.println("Argument(s) missing!");
			System.err.printf("Usage: java %s uddiURL name%n",
					ChequeRefeicaoCliente.class.getName());
			return;
		}
		privateKeys = new HashMap<String, PrivateKey>();
		
		for(int i=0; i<folderFiles.length; i++){
			result = (folderFiles[i].getName()).substring(0, (folderFiles[i].getName()).indexOf(".")).trim();

			file = new File(folderFiles[i].getName());

			pubEncoded = readFile("keys/" + folderFiles[i].getName());


			if(!result.equals("chequerefeicao")){
				PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(pubEncoded);
				KeyFactory keyFacPriv = KeyFactory.getInstance("RSA");
				PrivateKey priv = keyFacPriv.generatePrivate(privSpec);
				privateKeys.put(result, priv);
			} else{
				X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(pubEncoded);
				KeyFactory keyFacPub = KeyFactory.getInstance("RSA");
				PublicKey pub = keyFacPub.generatePublic(pubSpec);
				chequeRefeicaoPubKey = pub;
			}


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

			System.out.println("Introduza uma das opcoes \n\temitir \n\tsacar \n\tendossar \n\tlistar \n\tsair"
					+ "\n\temitir1teste \n\temitir2teste \n\temitir3teste \n\temitir4teste \n\temitir5teste"
					+ "\n\tsacar1teste \n\tsacar2teste \n\tsacar3teste \n\tlistar1teste \n\tlistar2teste \n\tlistar3teste"
					+ "\n\tendossar1teste \n\tendossar2teste \n\tendossar3teste \n\tendossar4teste \n\tendossar5teste");
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
						id = scanIn.nextLine();
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

				case "emitir1teste":
					System.out.println("Titular: alice");
					titular = "alice";
					System.out.println("Valor: 60");
					valorS = "60";
					System.out.println("Endossavel - True or False: False");
					endossavelS="False";

					resultEmitir = port.emitir(titular,
							Integer.parseInt(valorS),
							Boolean.parseBoolean(endossavelS));
					System.out.println(resultEmitir);
					break;



				case "emitir2teste":
					System.out.println("Titular: peixeiro");
					titular = "peixeiro";
					System.out.println("Valor: 50");
					valorS = "50";
					System.out.println("Endossavel - True or False: True");
					endossavelS="True";

					resultEmitir = port.emitir(titular,
							Integer.parseInt(valorS),
							Boolean.parseBoolean(endossavelS));
					System.out.println(resultEmitir);
					break;


				case "emitir3teste":
					System.out.println("Titular: alice");
					titular = "alice";
					System.out.println("Valor: 101");
					valorS = "101";
					System.out.println("Endossavel - True or False: False");
					endossavelS="True";

					resultEmitir = port.emitir(titular,
							Integer.parseInt(valorS),
							Boolean.parseBoolean(endossavelS));
					System.out.println(resultEmitir);
					break;

				case "emitir4teste":
					System.out.println("Titular: alice");
					titular = "alice";
					System.out.println("Valor: 30");
					valorS = "30";
					System.out.println("Endossavel - True or False: True");
					endossavelS="True";

					resultEmitir = port.emitir(titular,
							Integer.parseInt(valorS),
							Boolean.parseBoolean(endossavelS));
					System.out.println(resultEmitir);
					break;

				case "emitir5teste":
					System.out.println("Titular: alice");
					titular = "alice";
					System.out.println("Valor: 10");
					valorS = "10";
					System.out.println("Endossavel - True or False: True");
					endossavelS="True";

					resultEmitir = port.emitir(titular,
							Integer.parseInt(valorS),
							Boolean.parseBoolean(endossavelS));
					System.out.println(resultEmitir);
					break;

				case "sacar1teste":
					String id1 = "1";
					String id2 = "2";
					ids = new ArrayList<String>();
					System.out.println("Beneficiario: bruno ");
					beneficiario = "bruno";
					System.out.println("Introduza ids e escreva 'end' para terminar): 1\n2\nend ");
					ids.add(id1);
					ids.add(id2);

					resultSacar = port.sacar(beneficiario, ids);
					System.out.println(resultSacar);
					break;

				case "sacar2teste":
					id1 = "1";
					id2 = "2";
					ids = new ArrayList<String>();
					System.out.println("Beneficiario: sampaio ");
					beneficiario = "sampaio";
					System.out.println("Introduza ids e escreva 'end' para terminar): 1\n2\nend ");
					ids.add(id1);
					ids.add(id2);

					resultSacar = port.sacar(beneficiario, ids);
					System.out.println(resultSacar);
					break;

				case "sacar3teste":
					id1 = "3";
					id2 = "4";
					ids = new ArrayList<String>();
					System.out.println("Beneficiario: bruno ");
					beneficiario = "bruno";
					System.out.println("Introduza ids e escreva 'end' para terminar): 3\n4\nend ");
					ids.add(id1);
					ids.add(id2);

					resultSacar = port.sacar(beneficiario, ids);
					System.out.println(resultSacar);
					break;

				case "listar1teste":
					System.out.println("Titular: alice");
					titular = "alice";
					System.out.println("Introduza True p/ cheques usados ou False p/ cheques nao-usados: True");
					usado= "true";


					System.out.printf("Remote call to %s ...%n",
							endpointAddress);
					resultListar = port.listar(titular,
							Boolean.parseBoolean(usado));
					System.out.println(resultListar);
					break;

				case "listar2teste":
					System.out.println("Titular: alice");
					titular = "alice";
					System.out.println("Introduza True p/ cheques usados ou False p/ cheques nao-usados: False");
					usado= "false";


					System.out.printf("Remote call to %s ...%n",
							endpointAddress);
					resultListar = port.listar(titular,
							Boolean.parseBoolean(usado));
					System.out.println(resultListar);
					break;

				case "listar3teste":
					System.out.println("Titular: peixeiro");
					titular = "peixeiro";
					System.out.println("Introduza True p/ cheques usados ou False p/ cheques nao-usados: True");
					usado= "true";


					System.out.printf("Remote call to %s ...%n",
							endpointAddress);
					resultListar = port.listar(titular,
							Boolean.parseBoolean(usado));
					System.out.println(resultListar);
					break;

				case "endossar1teste":
					System.out.println("Titular: alice ");
					titular = "alice";
					System.out.println("Beneficiario: bruno ");
					benef = "bruno";
					System.out.println("ID do cheque: 2");
					idCheque = "2";

					resultEndossar = port.endossar(titular,
							benef, idCheque);
					System.out.println(resultEndossar);
					break;

				case "endossar2teste":
					System.out.println("Titular: alice ");
					titular = "alice";
					System.out.println("Beneficiario: benteke ");
					benef = "benteke";
					System.out.println("ID do cheque: 3");
					idCheque = "3";

					resultEndossar = port.endossar(titular,
							benef, idCheque);
					System.out.println(resultEndossar);
					break;

				case "endossar3teste":
					System.out.println("Titular: alice ");
					titular = "alice";
					System.out.println("Beneficiario: bruno");
					benef = "bruno";
					System.out.println("ID do cheque: 4");
					idCheque = "4";

					resultEndossar = port.endossar(titular,
							benef, idCheque);
					System.out.println(resultEndossar);
					break;

				case "endossar4teste":
					System.out.println("Titular: benteke ");
					titular = "benteke";
					System.out.println("Beneficiario: bruno ");
					benef = "bruno";
					System.out.println("ID do cheque: 1");
					idCheque = "1";

					resultEndossar = port.endossar(titular,
							benef, idCheque);
					System.out.println(resultEndossar);
					break;

				case "endossar5teste":
					System.out.println("Titular: alice ");
					titular = "alice";
					System.out.println("Beneficiario: bruno ");
					benef = "bruno";
					System.out.println("ID do cheque: 1");
					idCheque = "1";

					resultEndossar = port.endossar(titular,
							benef, idCheque);
					System.out.println(resultEndossar);
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
