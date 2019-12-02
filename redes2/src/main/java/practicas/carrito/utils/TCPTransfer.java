package practicas.carrito.utils;

import java.io.*;

public class TCPTransfer {
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public TCPTransfer(ObjectOutputStream oos, ObjectInputStream ois) {
		this.oos = oos;
		this.ois = ois;
	}

	public int sendFile(File f) {
		try {
			oos.writeUTF(f.getName());
			oos.writeLong(f.length());
			oos.flush();
			DataInputStream dis = new DataInputStream(new FileInputStream(f));
			byte b[] = new byte[1500];
			int enviado = 0, porcentaje = 0, n;
			while (enviado < f.length()) {
				n = dis.read(b);
				oos.write(b, 0, n);
				oos.flush();
				enviado = enviado + n;
				porcentaje = (int) ((enviado * 100) / f.length());
				System.out.println("Se ha transmitido el " + porcentaje + "%");

			}
			dis.close();
			return Codes.SUCCESFULL;

		} catch (IOException e) {
			return Codes.ERROR;
		}
	}

	public int getFile(String path) {
		try {

			File folder = new File(path);
			folder.mkdir();
			if (folder.exists())
				if (folder.isDirectory())
					System.out.println("Directorio valido");
				else
					throw new IllegalArgumentException("Invalid Path Name");

			String name = ois.readUTF();
			long tam = ois.readLong();
			if (tam == 0) {
				return Codes.ERROR;
			}
			// file = file.substring(file.indexOf('\\')+1,file.length());
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + name));
			byte receivedData[] = new byte[1500];
			long r = 0;
			int n, p;
			while (r < tam) {
				n = ois.read(receivedData);
				bos.write(receivedData, 0, n);
				bos.flush();
				r = r + n;
				p = (int) ((r * 100) / tam);
				System.out.println("\rRecibido el " + p + "%");
			}
			bos.close();

			return Codes.SUCCESFULL;
		} catch (IOException e) {
			return Codes.ERROR;
		}
	}

	public ObjectOutputStream getOutput() {
		return oos;
	}

	public ObjectInputStream getInput() {
		return ois;
	}
}
