package practicas.http.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;

public class Peticion extends Thread {

    private static String SERVER_ROUTE;
    private static String INDEX;
    private static final int BUFFER_SIZE = 1024;
    private byte[] buffer = new byte[BUFFER_SIZE];
    private Socket socket;
    private BufferedOutputStream bos;
    protected BufferedReader br;

    Peticion(Socket socket, String path, String archivo) {
        this.socket = socket;
        Peticion.SERVER_ROUTE = path;
        Peticion.INDEX = archivo;
    }

    @Override
    public void run() {
        try {
            bos = new BufferedOutputStream(socket.getOutputStream());
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(bos));

            Header header = new Header(socket.getInputStream());
            header.parse();
            // Archivo solicitado
            String file = header.getFile();
            file = file.equals("/") ? INDEX : file;


            System.out.println("Petición: " + header.getMethod() + " " + file);
            HashMap<String, String> parametros = header.getParametros();
            if (parametros.keySet().size() > 0) {
                System.out.println("Parámetros:");
                for (String s : parametros.keySet()) {
                    System.out.println(String.format("\t%s = %s", s, parametros.get(s)));
                }
            }

            sendFile(file);

            bos.close();
            pw.close();


            socket.shutdownInput();
            socket.close();
        } catch (IOException ignored) {
        }
    }

    private void sendFile(String file) throws FileNotFoundException, IOException {
        File seleccionado = new File(SERVER_ROUTE + file);
        long length = seleccionado.length();
        int leidos = 0;

        String sb = "";
        sb = sb + "HTTP/1.0 200 ok\n";
        sb = sb + "Server: Pichonazo/1.0 \n";
        sb = sb + "Date: " + new Date() + " \n";
        sb = sb + "Content-Type: text/html \n";
        sb = sb + "Content-Length: " + length + " \n";
        sb = sb + "\n";
        bos.write(sb.getBytes());
        bos.flush();

        try (FileInputStream fr = new FileInputStream(seleccionado)) {
            leidos = fr.read(buffer);
            while (leidos != -1) {
                bos.write(buffer, 0, leidos);
                //pw.flush();
                leidos = fr.read(buffer);
            }
            bos.flush();
        }
    }
}
