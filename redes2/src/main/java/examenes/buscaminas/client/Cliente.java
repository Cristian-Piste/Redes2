package examenes.buscaminas.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import examenes.buscaminas.juego.Tablero;
import examenes.buscaminas.juego.Jugada;

public class Cliente {
	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
		if (args.length != 2) {
			System.err.println("java [opciones] <clase principal> [PUERTO][IP]");
			System.exit(0);
		}
		int port = Integer.parseInt(args[0]);
		String ipServer = args[1];
		Scanner sc = new Scanner(System.in);
		int n, m, p;

		System.out.println("BUSCAMINAS");
		System.out.println("\t1.Fácil (9x9)");
		System.out.println("\t2.Intermedio (16x16)");
		System.out.println("\t3.Avanzado (16x30)");
		System.out.print("Seleccione la dificultad: ");
		n = sc.nextInt();
		Tablero tab = Tiro(-1, -1, n, port, ipServer);

		System.out.println(tab);
		while (tab.getEstadoJuego() == 0) {
			System.out.println("\t1.Tirar");
			System.out.println("\t2.Marcar");
			System.out.print("Seleccione una jugada: ");
			n = sc.nextInt() - 1;
			System.out.println("Coordenadas");
			System.out.print("\nX: ");
			m = sc.nextInt() - 1;
			System.out.print("Y: ");
			p = sc.nextInt() - 1;
			tab = Tiro(m, p, n, port, ipServer);
			System.out.println(tab);
		}
		if (tab.getEstadoJuego() == 1) {
			System.out.println("GANAS!!!");
			sc.close();
		} else {
			System.out.println("HAS PERDIDO");
			sc.close();
		}

	}

	private static Tablero Tiro(int x, int y, int jugada, int port, String ip) throws UnknownHostException, IOException, ClassNotFoundException {
		Tablero t = null;
		// Apertura del socket y los flujos IO
		Socket s = new Socket(ip, port);
		ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
		ObjectInputStream is = new ObjectInputStream(s.getInputStream());

		os.flush();

		Jugada j = new Jugada();

		j.setTipo(jugada);
		j.setX(x);
		j.setY(y);
		os.writeObject(j);
		os.flush();

		t = (Tablero) is.readObject();

		// Cerramos los flujos y el socket
		is.close();
		os.close();
		s.close();
		return t;
	}
}