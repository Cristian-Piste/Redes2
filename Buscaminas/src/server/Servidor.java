package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import juego.Tablero;
import juego.Jugada;

public class Servidor {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Tablero juego = null;
		if(args.length != 1){
			System.out.println("java [opciones] <clase-principal> [PUERTO]");
			System.exit(0);
		}
		int port = Integer.parseInt(args[0]);
		// Socket de servidor
		@SuppressWarnings("resource")
		ServerSocket ss = new ServerSocket(port);
		System.out.println("Servidor escuchando.");
		while (true) {
			// Aceptamos un cliente y abrimos los flujos de IO
			Socket s = ss.accept();
			ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream is = new ObjectInputStream(s.getInputStream());
			os.flush();

			Jugada j = (Jugada) is.readObject();

			if (j != null) {
				if (j.getX() < 0 && j.getY() < 0) {
					System.out.println("Se ha iniciado un juego nuevo con " + s.getInetAddress().getHostAddress());
					juego = new Tablero(j.getTipo());
				} else {
					juego.hazJugada(j.getX(), j.getY(), j.getTipo());
					if (juego.getEstadoJuego() == -1) {
						System.out.println("El cliente " + s.getInetAddress().getHostAddress() + " ha perdido");
					}else if (juego.getEstadoJuego() == 1) {
						System.out.println("El cliente " + s.getInetAddress().getHostAddress() + " ha ganado");
					}
				}
			}
			os.writeObject(juego);
			os.flush();
			is.close();
			os.close();
			s.close();
		}

	}
}