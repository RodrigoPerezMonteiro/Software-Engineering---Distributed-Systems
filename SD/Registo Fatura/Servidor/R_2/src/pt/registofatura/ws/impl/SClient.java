package pt.registofatura.ws.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.registry.JAXRException;
import javax.xml.ws.Endpoint;

import pt.registofatura.ws.uddi.UDDINaming;


public class SClient extends
        Thread {

    ObjectOutputStream out;
    ObjectInputStream in;
    RegistoFaturaImpl rgImpl;
    Timer timer;
    Socket server;
    String host;
    int port;
    String secondary_endpoint = "http://localhost:8082/registofatura-ws/endpoint";
    boolean secondaryEndpointActivated = false;

    public SClient(Socket server,
                   String hostname,
                   int port,
                   String serverName,
                   String currentDB,
                   String mainDB,
                   String name,
                   String url,
                   UDDINaming uddiNaming,
                   String uddiURL,
                   Endpoint endpoint) throws IOException {
        this.server = server;
        this.host = hostname;
        this.port = port;
        timer = new Timer();
        timer.schedule(new sendAlive(), 0, 4 * 1000);
        try {
            rgImpl = new RegistoFaturaImpl();
            rgImpl.setServerName(serverName);
            rgImpl.setCurrentDb(currentDB);
            rgImpl.setMainDb(mainDB);
            endpoint = Endpoint.create(rgImpl);

            // publish endpoint
            System.out.printf("Starting %s%n", url);
            endpoint.publish(url);


            System.out.printf("Publishing '%s' to UDDI at %s%n", name, uddiURL);

            uddiNaming = new UDDINaming(uddiURL);
            uddiNaming.rebind(name, url);

            // wait
            System.out.println("Awaiting connections");
            System.out.println("Press CTRL + C to shutdown");
            System.in.read();
        } catch (JAXRException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    class sendAlive extends
            TimerTask {
        String alive = new String("I'm alive!");

        public void run() {
            System.out.println("Sending Message: " + alive);
            try {
                server = new Socket(host, port);
                if (!secondaryEndpointActivated) {
                    secondaryEndpointActivated = true;
                    rgImpl.activateSecondaryPort(secondary_endpoint);
                }
                out = new ObjectOutputStream(server.getOutputStream());
                out.writeObject(alive);
                in = new ObjectInputStream(server.getInputStream());
                String response = (String) in.readObject();
                System.out.println("Receive: " + response.toString());
                server.close();
            } catch (IOException e) {
                System.out.println("Secondary Server not Initiated");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
