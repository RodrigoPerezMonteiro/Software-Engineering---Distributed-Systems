package pt.registofatura.ws.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.xml.ws.Endpoint;

import pt.registofatura.ws.uddi.UDDINaming;

public class RegistoFaturaMain {
    private static String[] ServersNames = { "MainServer", "SecondaryServer" };
    private static ServerSocket serverSocket;
    private static Socket client, server;
    private static String response;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static SClient sclient;
    
    public static void main(String[] args) {
        if (args.length < 4) {
            System.err.println("Argument(s) missing!");
            System.err.printf("Usage: java %s uddiURL wsName wsURL serverName%n",
                    RegistoFaturaMain.class.getName());
            return;
        }

        String uddiURL = args[0];
        String name = args[1];
        String url = args[2];
        String serverName = args[3];
        String mainDB = args[4];
        String currentDB = args[5];

        Endpoint endpoint = null;
        UDDINaming uddiNaming = null;

        try {

            if (serverName.equals(ServersNames[0])) {

                sclient = new SClient(server, "localhost", 19999, serverName,currentDB,mainDB,name, url,uddiNaming,uddiURL,endpoint);

            } else {
                try {
                    serverSocket = new ServerSocket(19999);
                    serverSocket.setSoTimeout(5000);
                    System.out.println("Waiting for client on port " + serverSocket.getLocalPort()
                            + "...");
                    RegistoFaturaImpl rgImpl = new RegistoFaturaImpl();
                    rgImpl.setServerName(serverName);
                    rgImpl.setCurrentDb(currentDB);
                    rgImpl.setMainDb(mainDB);
                    endpoint = Endpoint.create(rgImpl);
                    
                    // publish endpoint
                    System.out.printf("Starting %s%n", url);
                    endpoint.publish(url);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                while (true) {
                    try {
                        client = serverSocket.accept();
                        System.out.println("Connected to " + client.getRemoteSocketAddress());
                        in = new ObjectInputStream(client.getInputStream());
                        out = new ObjectOutputStream(client.getOutputStream());
                        response = (String) in.readObject();
                        System.out.println("Received the message: " + response.toString());
                        out.writeObject("Ok");
                        client.close();
                    } catch (SocketTimeoutException s) {
                        client.close();
                        System.out.println("Socket timed out!");


                        System.out.printf("Publishing '%s' to UDDI at %s%n", name, uddiURL);
                        uddiNaming = new UDDINaming(uddiURL);
                        uddiNaming.rebind(name, url);

                        // wait
                        System.out.println("Awaiting connections");
                        System.out.println("Press CTRL + C to shutdown");
                        System.in.read();
                    } catch (IOException e) {
                        e.printStackTrace();
                        try {
                            client.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            System.out.printf("Caught exception: %s%n", e);
            e.printStackTrace();
        } finally {
            try {
                if (endpoint != null) {
                    // stop endpoint
                    endpoint.stop();
                    System.out.printf("Stopped %s%n", url);
                }
            } catch (Exception e) {
                System.out.printf("Caught exception when stopping: %s%n", e);
            }
            try {
                if (uddiNaming != null) {
                    // delete from UDDI
                    uddiNaming.unbind(name);
                    System.out.printf("Deleted '%s' from UDDI%n", name);
                }
            } catch (Exception e) {
                System.out.printf("Caught exception when deleting: %s%n", e);
            }
        }
    }
}
