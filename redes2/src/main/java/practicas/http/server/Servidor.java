package practicas.http.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private static ServerSocket ss;

    public static void main(String [] args) {
    	if(args.length != 3) {
    		System.err.println("java [opciones] <clase principal> [PUERTO][PATH][/archivo.html]");
    		System.exit(0);
    	}
    	int LISTENING_PORT;
        String PATH;
        String PAGE;
    	
    	LISTENING_PORT = Integer.parseInt(args[0]);
    	PATH = args[1];
    	PAGE = args[2];
    	
        try {
            ss = new ServerSocket(LISTENING_PORT);
            for (;;) {
                Socket s = ss.accept();
                new Peticion(s, PATH, PAGE).start();
            }
        } catch (IOException ignored) {
        }
    }
}
