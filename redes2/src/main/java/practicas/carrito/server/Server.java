package practicas.carrito.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import static practicas.carrito.utils.Strings.*;
import static practicas.carrito.utils.Codes.*;
/*import utils.Codes;
import static practicas.carrito.utils.*;
import static utils.Strings.CLIENT_PATH;
import static utils.Strings.SERVER_PATH;
import utils.TCPTransfer;*/

import practicas.carrito.utils.Item;
import practicas.carrito.utils.ItemList;
import practicas.carrito.utils.TCPTransfer;

public class Server {
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		String directorio = getDir();
		try {
			ss = new ServerSocket(SERVER_REQUEST_PORT);
			ArrayList<Item> stock = getStock();
			for (;;) {
				System.out.println("Preparado para nueva conexión");
				Socket s = ss.accept();
				ArrayList<File> images = getPics(stock);
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				TCPTransfer transfer = new TCPTransfer(oos, ois);
				int requestCode = 0;
				do {
					try {
						requestCode = ois.readInt();
					} catch (IOException e) {
						requestCode = 0;
					}

					switch (requestCode) {
					case REQUEST_DOWNLOAD:
						sendPics(images, transfer);
						break;
					case REQUEST_GET_STOCK:
						sendStock(stock, oos, ois);
						break;
					case REQUEST_BUY:
						buy(stock, oos, ois, transfer);
						break;
					}
				} while (requestCode != REQUEST_CLOSE);

			}
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static String getDir() {
		String directorio = System.getProperty("user.dir");
		directorio += SERVER_PATH;
		System.out.println(directorio);
		File folder = new File(directorio);
		folder.mkdir();
		if (folder.exists())
			if (folder.isDirectory())
				System.out.println("Carpeta creada");
		return directorio;
	}

	private static ArrayList<Item> getStock() {
		int cont = 1;
		ArrayList<Item> list = new ArrayList<Item>();
		try {
			File f = new File(".\\src\\img\\productos\\listaProductos");
			FileInputStream fis = new FileInputStream(f);
			br = new BufferedReader(new InputStreamReader(fis));
			String linea = br.readLine();
			while (linea != null && !"".equals(linea)) {
				System.out.println(linea);
				String params[];
				params = linea.split(",");
				Item i = new Item(cont++, params[0], params[1], Integer.parseInt(params[2]),
						Integer.parseInt(params[3]));
				list.add(i);
				linea = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(list.size());
		// Item i=new Item(1,"p1","p1",10,100);
		// Item i2=new Item(2,"p2","p2",10,200);
		// Item i3=new Item(3,"p3","p3",10,300);
		// list.add(i);
		// list.add(i2);
		// list.add(i3);
		return list;
	}

	private static ArrayList<File> getPics(ArrayList<Item> imags) {
		ArrayList<File> files = new ArrayList<File>();
		for (int i = 0; i < imags.size(); i++) {
			File dir = new File(imags.get(i).getPic());
			files.add(dir);
		}
		return files;
	}

	static final String[] EXTENSIONS = new String[] { "gif", "png", "bmp", "jpg", "jpeg" // and other formats you need
	};
	// filter to identify images based on their extensions
	static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

		@Override
		public boolean accept(final File dir, final String name) {
			for (final String ext : EXTENSIONS) {
				if (name.endsWith("." + ext)) {
					return (true);
				}
			}
			return (false);
		}
	};
	private static ServerSocket ss;
	private static BufferedReader br;

	private static void sendPics(ArrayList<File> pics, TCPTransfer transfer) {
		System.out.println("Preparado para enviar imagenes");
		for (File pic : pics) {
			try {
				transfer.getOutput().writeInt(REQUEST_UPLOAD);
				transfer.getOutput().flush();
				transfer.sendFile(pic);
				int result = transfer.getInput().readInt();
				if (result == ERROR)
					System.out
							.println("Error al enviar el archivo: " + pic.getName() + " verifique e intente de nuevo");
			} catch (IOException ex) {
				Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		try {
			transfer.getOutput().writeInt(TASK_COMPLETE);
			transfer.getOutput().flush();
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("Se enviaron todas las imagenes");

	}

	private static void sendStock(ArrayList<Item> stock, ObjectOutputStream oos, ObjectInputStream ois) {
		try {
			oos.writeObject(new ItemList(stock));
			oos.flush();
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private static void buy(ArrayList<Item> stock, ObjectOutputStream oos, ObjectInputStream ois,
			TCPTransfer transfer) {
		try {
			ItemList list = (ItemList) ois.readObject();
			ArrayList<Item> cart = list.getList();
			for (Item i : cart) {// Descuenta los artíclulos del stock de la tienda
				// boolean flag=false;
				for (Item a : stock) {
					if (i.getId() == a.getId()) {
						a.setStock(a.getStock() - i.getStock());
						break;// Si encuentra el artíclulo detiene la iteración
					}
				}
			}
			File ticket = generateTicket(stock);
			oos.writeInt(TASK_COMPLETE);
			// Se envía el ticket
			oos.writeUTF(ticket.getName());
			oos.flush();
			transfer.sendFile(ticket);
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@SuppressWarnings("deprecation")
	private static File generateTicket(ArrayList<Item> cart) {
		try {
			PDDocument tick = new PDDocument();
			PDPage pag = new PDPage();
			tick.addPage(pag);
			PDPageContentStream content = new PDPageContentStream(tick, pag);
			content.setFont(PDType1Font.COURIER, 50);
			content.beginText();
			content.moveTextPositionByAmount(230, 700);
			content.showText("ESCOM");
			content.endText();
			content.setFont(PDType1Font.COURIER, 20);
			content.beginText();
			content.moveTextPositionByAmount(75, 650);
			content.showText("Aplicaciones para comunicaciones de red");
			content.endText();
			content.beginText();
			content.moveTextPositionByAmount(200, 600);
			content.showText("Ticket de compra");
			content.endText();

			content.beginText();
			content.moveTextPositionByAmount(350, 550);
			content.showText("Fecha: " + LocalDate.now());
			content.endText();

			int cont = 0;
			int total = 0;
			content.beginText();
			content.moveTextPositionByAmount(50, 500);
			content.showText("Artículo");
			content.endText();
			content.beginText();
			content.moveTextPositionByAmount(180, 500);
			content.showText("Cantidad");
			content.endText();
			content.beginText();
			content.moveTextPositionByAmount(300, 500);
			content.showText("Precio Unitario");
			content.endText();
			content.beginText();
			content.moveTextPositionByAmount(500, 500);
			content.showText("Subtotal");
			content.endText();
			for (Item i : cart) {
				content.beginText();
				content.moveTextPositionByAmount(60, 450 - (cont * 30));
				content.showText(i.getName());
				content.endText();
				content.beginText();
				content.moveTextPositionByAmount(220, 450 - (cont * 30));
				content.showText(i.getStock() + "");
				content.endText();
				content.beginText();
				content.moveTextPositionByAmount(370, 450 - (cont * 30));
				content.showText(i.getPrice() + "");
				content.endText();
				content.beginText();
				content.moveTextPositionByAmount(530, 450 - (cont * 30));
				content.showText(i.getStock() * i.getPrice() + "");
				total += (i.getStock() * i.getPrice());
				content.endText();
				cont++;
			}
			content.beginText();
			content.moveTextPositionByAmount(80, 450 - ((cont + 1) * 30));
			content.showText("Total");
			content.endText();
			content.beginText();
			content.moveTextPositionByAmount(530, 450 - ((cont + 1) * 30));
			content.showText(total + "");
			content.endText();

			PDImageXObject image = PDImageXObject.createFromFile(".\\src\\img\\logoIPN.png", tick);
			content.drawImage(image, 0, 670, 120, 120);

			content.drawLine(40, 530, 40, 440 - ((cont + 1) * 30));
			content.drawLine(600, 530, 600, 440 - ((cont + 1) * 30));
			content.drawLine(490, 530, 490, 440 - ((cont + 1) * 30));
			content.drawLine(290, 530, 290, 440 - (cont * 30));
			content.drawLine(170, 530, 170, 440 - (cont * 30));

			content.drawLine(40, 530, 600, 530);
			content.drawLine(40, 490, 600, 490);
			content.drawLine(40, 440 - (cont * 30), 600, 440 - (cont * 30));
			content.drawLine(40, 440 - ((cont + 1) * 30), 600, 440 - ((cont + 1) * 30));

			image = PDImageXObject.createFromFile(".\\src\\img\\logoESCOM.png", tick);
			content.drawImage(image, 480, 680, 100, 80);

			content.close();
			final File file = File.createTempFile("blabla", ".pdf");
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
