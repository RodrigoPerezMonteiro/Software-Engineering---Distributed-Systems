package pt.chequerefeicao.ws.impl;

import javax.xml.ws.Endpoint;

import pt.chequerefeicao.ws.uddi.UDDINaming;

public class ChequeRefeicaoMain {
	
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Argument(s) missing!");
            System.err.printf("Usage: java %s uddiURL wsName wsURL%n", ChequeRefeicaoMain.class.getName());
            return;
        }

		String uddiURL = args[0];
		String name = args[1];
		String url = args[2];

		Endpoint endpoint = null;
		UDDINaming uddiNaming = null;


		try {
			endpoint = Endpoint.create(new ChequeImpl());

			// publish endpoint
			System.out.printf("Starting %s%n", url);
			endpoint.publish(url);


			System.out.printf("Publishing '%s' to UDDI at %s%n", name, uddiURL);
			uddiNaming = new UDDINaming(uddiURL);
			uddiNaming.rebind(name, url);

			// wait
			System.out.println("Awaiting connections");
			System.out.println("Press enter to shutdown");
			System.in.read();

		} catch(Exception e) {
			System.out.printf("Caught exception: %s%n", e);
			e.printStackTrace();

		} finally {
			try {
				if (endpoint != null) {
					// stop endpoint
					endpoint.stop();
					System.out.printf("Stopped %s%n", url);
				}
			} catch(Exception e) {
				System.out.printf("Caught exception when stopping: %s%n", e);
			}
			try {
				if (uddiNaming != null) {
					// delete from UDDI
					uddiNaming.unbind(name);
					System.out.printf("Deleted '%s' from UDDI%n", name);
				}
			} catch(Exception e) {
				System.out.printf("Caught exception when deleting: %s%n", e);
			}
		}
	}
}