package practicas.carrito.client;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import practicas.carrito.utils.Item;
import practicas.carrito.utils.ItemList;
import practicas.carrito.utils.TCPTransfer;

//import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import static practicas.carrito.utils.Strings.*;
import static practicas.carrito.utils.Codes.*;

public class Client {

	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private TCPTransfer transfer;
	private String directorio;
	private ArrayList<Item> cart;

	public Client(ObjectOutputStream oos, ObjectInputStream ois) {
		this.oos = oos;
		this.ois = ois;
		transfer = new TCPTransfer(oos, ois);
		directorio = getFilesDir();
		cart = new ArrayList<Item>();
	}

	public void getFiles() throws IOException {
		int requestCode = 0;
		oos.writeInt(REQUEST_DOWNLOAD);
		oos.flush();
		do {
			try {
				requestCode = ois.readInt();

			} catch (IOException e) {
				requestCode = 0;
			}
			if (requestCode == REQUEST_UPLOAD) {
				try {
					int result = transfer.getFile(directorio);
					System.out.println("Enviando resultado: " + result);
					oos.writeInt(result);
					oos.flush();
				} catch (IOException ex) {
					Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		} while (requestCode != TASK_COMPLETE);
	}

	public ArrayList<Item> getStock() throws IOException {
		System.out.println("Obteniendo Stock");
		ArrayList<Item> stock = new ArrayList<Item>();
		oos.writeInt(REQUEST_GET_STOCK);
		oos.flush();
		try {
			ItemList list = (ItemList) ois.readObject();
			stock = list.getList();
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
		return stock;
	}

	public File buy(ArrayList<Item> carrito) {
		try {
			oos.writeInt(REQUEST_BUY);
			oos.flush();
			System.out.println("Realizando compra");
			oos.writeObject(new ItemList(carrito));
			oos.flush();
			int requestCode = 0;
			do {// Espera mientras el servidor procesa la compra
				try {
					requestCode = ois.readInt();
				} catch (IOException e) {
					requestCode = 0;
				}

			} while (requestCode != TASK_COMPLETE);
			// Se recibe el ticket
			String filename = ois.readUTF();
			int result = transfer.getFile(directorio);
			if (result == SUCCESFULL) {
				return new File(directorio + filename);
			}
		} catch (IOException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public ArrayList<Item> addToCart(Item i) {// Agrega un artículo al carrito
		if (cart.isEmpty()) {
			cart.add(new Item(i));
		} else {
			boolean flag = false;
			for (Item item : cart) {
				if (item.getId() == i.getId()) {// Si el producto ya se había agregado, se incrementa el contador stock
					item.setStock(item.getStock() + 1);
					flag = true;
					break;
				}
			}
			if (!flag) {// Si el producto no estaba en el carrito
				cart.add(new Item(i));
			}
		}
		return cart;
	}

	public ArrayList<Item> removeFromCart(Item i) {
		boolean flag = false;
		for (Item item : cart) {
			if (item.getId() == i.getId()) {
				flag = true;
				if (item.getStock() == 1) {// Si sólo había 1 se remueve de la lista
					cart.remove(item);
				} else {
					item.setStock(item.getStock() - 1);// Si había más de uno se resta una unidad al pedido
				}
				break;
			}
		}
		if (!flag) {
			System.out.println("El producto seleccionado no estaba en el carrito");
		}
		return cart;
	}

	private String getFilesDir() {
		String directorio = System.getProperty("user.dir");
		directorio += CLIENT_PATH;
		System.out.println(directorio);
		File folder = new File(directorio);
		folder.mkdir();
		if (folder.exists())
			if (folder.isDirectory())
				System.out.println("Carpeta creada");
		return directorio;
	}

	@SuppressWarnings("deprecation")
	public void generarPDF(String dir) {
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

			tick.save(dir);
			tick.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}