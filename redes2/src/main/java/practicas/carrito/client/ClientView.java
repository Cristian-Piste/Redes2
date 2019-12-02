package practicas.carrito.client;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;

import practicas.carrito.utils.Item;

import static practicas.carrito.utils.Codes.REQUEST_CLOSE;
import static practicas.carrito.utils.Codes.SERVER_REQUEST_PORT;

public class ClientView extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	private ArrayList<Item> productos;
	private ArrayList<Item> carritoAux;
	private Item mainProduct;
	private DefaultListModel<String> listaAux;
	private Client client;
	private Socket cl;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ClientView("127.0.0.1").setVisible(true);
			}
		});
	}

	public ClientView(String ip) {

		initComponents();

		this.productos = new ArrayList<>();
		this.carritoAux = new ArrayList<>();
		this.listaAux = new DefaultListModel<String>();
		try {
			this.cl = new Socket(ip, SERVER_REQUEST_PORT);
			this.in = new ObjectInputStream(this.cl.getInputStream());
			this.out = new ObjectOutputStream(this.cl.getOutputStream());

			this.client = new Client(out, in);

			this.productos = this.client.getStock();
			this.client.getFiles();

		} catch (Exception e) {
			e.printStackTrace();
		}

		int i = -1;
		do {
			i++;
			this.mainProduct = this.productos.get(i);
		} while (this.mainProduct.getStock() <= 0);
		this.updateSliderData();
	}

	private void updateSliderData() {
		this.mainProductLabel.setText(this.mainProduct.getName());
		this.mainProductPriceLabel.setText("$" + this.mainProduct.getPrice());
		this.mainProductStockLabel.setText("Disponibles: " + this.mainProduct.getStock());

		java.io.File f = new java.io.File(
				".\\Cliente\\" + this.mainProduct.getPic().substring(this.mainProduct.getPic().lastIndexOf("\\") + 1));
		try {
			mainImgProduct.setIcon(new javax.swing.ImageIcon(new javax.swing.ImageIcon(javax.imageio.ImageIO.read(f))
					.getImage().getScaledInstance(500, 300, java.awt.Image.SCALE_SMOOTH)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		productsPanel = new javax.swing.JPanel();
		izqButton = new javax.swing.JButton();
		derButton = new javax.swing.JButton();
		mainImgProduct = new javax.swing.JLabel();
		mainProductLabel = new javax.swing.JLabel();
		mainProductPriceLabel = new javax.swing.JLabel();
		quantityLabel = new javax.swing.JTextField();
		addToCartButton = new javax.swing.JButton();
		mainProductStockLabel = new javax.swing.JLabel();
		cartPanel = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		cartList = new javax.swing.JList<String>();
		deleteProduct = new javax.swing.JButton();
		confrimBuy = new javax.swing.JButton();
		finishBuy = new javax.swing.JButton();
		finalPanel = new javax.swing.JPanel();
		finalName = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		jScrollPane3 = new javax.swing.JScrollPane();
		finalList = new javax.swing.JList<String>();
		exit = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Voten por VMC");
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				formWindowClosing(evt);
			}
		});

		productsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(
				new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 255), 2, true), "Productos",
				javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
		productsPanel.setPreferredSize(new java.awt.Dimension(356, 356));

		izqButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/izq.png"))); // NOI18N
		izqButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				izqButtonActionPerformed(evt);
			}
		});

		derButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/der.png"))); // NOI18N
		derButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				derButtonActionPerformed(evt);
			}
		});

		mainImgProduct.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		mainImgProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/noimage.png"))); // NOI18N
		mainImgProduct.setToolTipText("");
		mainImgProduct.setAutoscrolls(true);

		mainProductLabel.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
		mainProductLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		mainProductLabel.setText("Producto 1");

		mainProductPriceLabel.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
		mainProductPriceLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		mainProductPriceLabel.setText("$100.00");
		mainProductPriceLabel.setToolTipText("");

		quantityLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
		quantityLabel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		quantityLabel.setText("1");

		addToCartButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cart.png"))); // NOI18N
		addToCartButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addToCartButtonActionPerformed(evt);
			}
		});

		mainProductStockLabel.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
		mainProductStockLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		mainProductStockLabel.setText("5");

		javax.swing.GroupLayout productsPanelLayout = new javax.swing.GroupLayout(productsPanel);
		productsPanel.setLayout(productsPanelLayout);
		productsPanelLayout.setHorizontalGroup(productsPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(productsPanelLayout.createSequentialGroup()
						.addComponent(izqButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(productsPanelLayout.createSequentialGroup().addGroup(productsPanelLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(mainImgProduct, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(mainProductLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGroup(javax.swing.GroupLayout.Alignment.LEADING, productsPanelLayout
												.createSequentialGroup()
												.addComponent(mainProductPriceLabel,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(mainProductStockLabel,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
										.addGap(18, 18, 18).addComponent(derButton,
												javax.swing.GroupLayout.PREFERRED_SIZE, 80,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(productsPanelLayout.createSequentialGroup().addGap(110, 110, 110)
										.addComponent(quantityLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 122,
												Short.MAX_VALUE)
										.addGap(107, 107, 107).addComponent(addToCartButton,
												javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
										.addGap(203, 203, 203)))));
		productsPanelLayout.setVerticalGroup(productsPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(productsPanelLayout.createSequentialGroup().addGroup(productsPanelLayout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(productsPanelLayout.createSequentialGroup().addGap(159, 159, 159)
								.addComponent(izqButton))
						.addGroup(productsPanelLayout.createSequentialGroup()
								.addComponent(mainProductLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(productsPanelLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(productsPanelLayout.createSequentialGroup().addGap(18, 18, 18)
												.addComponent(mainImgProduct, javax.swing.GroupLayout.PREFERRED_SIZE,
														359, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(productsPanelLayout.createSequentialGroup().addGap(132, 132, 132)
												.addComponent(derButton)))))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(mainProductPriceLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(mainProductStockLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGap(18, 18, 18)
						.addGroup(productsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(quantityLabel).addComponent(addToCartButton,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
						.addContainerGap()));

		cartPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(
				new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 255), 2, true), "Carrito",
				javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

		cartList.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
		jScrollPane1.setViewportView(cartList);

		deleteProduct.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		deleteProduct.setText("<<");
		deleteProduct.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deleteProductActionPerformed(evt);
			}
		});

		confrimBuy.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		confrimBuy.setText("Confirmar");
		confrimBuy.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				confrimBuyActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout cartPanelLayout = new javax.swing.GroupLayout(cartPanel);
		cartPanel.setLayout(cartPanelLayout);
		cartPanelLayout.setHorizontalGroup(cartPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(cartPanelLayout.createSequentialGroup().addContainerGap().addGroup(cartPanelLayout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1)
						.addGroup(cartPanelLayout.createSequentialGroup()
								.addComponent(deleteProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
								.addGap(45, 45, 45)
								.addComponent(confrimBuy, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)))
						.addContainerGap()));
		cartPanelLayout.setVerticalGroup(cartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(cartPanelLayout.createSequentialGroup().addComponent(jScrollPane1).addGap(14, 14, 14)
						.addGroup(cartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(deleteProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 33,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(confrimBuy, javax.swing.GroupLayout.PREFERRED_SIZE, 33,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));

		finishBuy.setText("Finalizar Compra");
		finishBuy.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					finishBuyActionPerformed(evt);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		finalPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(
				new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 255), 2, true), "Compra",
				javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

		jLabel1.setText("Nombre");

		jScrollPane3.setViewportView(finalList);

		javax.swing.GroupLayout finalPanelLayout = new javax.swing.GroupLayout(finalPanel);
		finalPanel.setLayout(finalPanelLayout);
		finalPanelLayout.setHorizontalGroup(finalPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(finalPanelLayout.createSequentialGroup().addContainerGap()
						.addGroup(finalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(finalPanelLayout.createSequentialGroup()
										.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18).addComponent(finalName))
								.addComponent(jScrollPane3))
						.addContainerGap()));
		finalPanelLayout.setVerticalGroup(finalPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(finalPanelLayout.createSequentialGroup()
						.addGroup(finalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(finalName, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel1))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
						.addContainerGap()));

		exit.setText("Salir");
		exit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exitActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(finalPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(productsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(cartPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(finishBuy, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE))
				.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(productsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
								.addComponent(cartPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(8, 8, 8)
										.addComponent(finishBuy, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(exit, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addComponent(finalPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void exitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_exitActionPerformed
		this.setVisible(false);
		this.dispose();
	}// GEN-LAST:event_exitActionPerformed

	private void izqButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_izqButtonActionPerformed
		int i = this.productos.indexOf(this.mainProduct);

		do {
			if (i == 0) {
				i = (this.productos.size() - 1);
			} else {
				i--;
			}
			this.mainProduct = this.productos.get(i);
		} while (this.mainProduct.getStock() <= 0);

		java.io.File f = new java.io.File(
				".\\Cliente\\" + this.mainProduct.getPic().substring(this.mainProduct.getPic().lastIndexOf("\\") + 1));
		try {
			mainImgProduct.setIcon(new javax.swing.ImageIcon(new javax.swing.ImageIcon(javax.imageio.ImageIO.read(f))
					.getImage().getScaledInstance(500, 300, java.awt.Image.SCALE_SMOOTH)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Main - " + this.mainProduct.getName());
		this.updateSliderData();
	}// GEN-LAST:event_izqButtonActionPerformed

	private void derButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_derButtonActionPerformed
		int i = this.productos.indexOf(this.mainProduct);

		do {
			if (i == (this.productos.size() - 1)) {
				i = 0;
			} else {
				i++;
			}
			this.mainProduct = this.productos.get(i);
		} while (this.mainProduct.getStock() <= 0);

		java.io.File f = new java.io.File(
				".\\Cliente\\" + this.mainProduct.getPic().substring(this.mainProduct.getPic().lastIndexOf("\\") + 1));
		try {
			mainImgProduct.setIcon(new javax.swing.ImageIcon(new javax.swing.ImageIcon(javax.imageio.ImageIO.read(f))
					.getImage().getScaledInstance(500, 300, java.awt.Image.SCALE_SMOOTH)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Main - " + this.mainProduct.getName());
		this.updateSliderData();
	}// GEN-LAST:event_derButtonActionPerformed

	private void addToCartButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_addToCartButtonActionPerformed
		int quantity = Integer.parseInt(this.quantityLabel.getText());
		int contin = 0;
		int stock = 0;
		for (Item item : this.carritoAux) {
			if (item.getId() == this.mainProduct.getId()) {// Si el producto ya se había agregado, se incrementa el
															// contador stock
				stock = item.getStock();
				break;
			}
		}
		this.mainProduct.getStock();
		// System.out.println(quantity + " " + stock + " " +
		// this.mainProduct.getStock());
		if (quantity + stock > this.mainProduct.getStock()) {
			contin = 1;
			contin = JOptionPane.showConfirmDialog(this,
					"La cantidad de elementos que desea comprar supera nuestro inventario. \nSu pedido podria tardar. \n¿Desea continuar?");
		}
		if (contin == 0) {
			for (int i = 0; i < quantity; i++) {
				this.carritoAux = this.client.addToCart(this.mainProduct);
			}
			this.updateList();
			this.cartList.setModel(this.listaAux);
		}
	}// GEN-LAST:event_addToCartButtonActionPerformed

	private void updateList() {
		this.listaAux.clear();
		for (Item aux : this.carritoAux) {
			String r = aux.getName() + " x " + aux.getStock();
			this.listaAux.addElement(r);
		}
	}

	private void deleteProductActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_deleteProductActionPerformed
		System.out.println(
				this.cartList.getSelectedIndex() + " - " + Arrays.toString(this.cartList.getSelectedIndices()));
		int[] idxs = this.cartList.getSelectedIndices();
		if (this.listaAux.getSize() > 0 && (this.cartList.getSelectedIndices().length > 0)) {
			for (int i = 0; i < idxs.length; i++) {
				int ndeleted = idxs[i];
				String search = this.listaAux.get(ndeleted);
				String[] parts = search.split(" x ");
				// System.out.println(parts[0]);
				for (Item producto : this.carritoAux) {
					if (producto.getName().equals(parts[0])) {
						int quant = producto.getStock();
						for (int j = 0; j < quant; j++) {
							// System.out.println("\t"+producto.getStock());
							this.carritoAux = this.client.removeFromCart(producto);
						}
						break;
					}
				}
			}
		}
		this.updateList();
	}// GEN-LAST:event_deleteProductActionPerformed

	private void confrimBuyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_confrimBuyActionPerformed
		this.finalList.setModel(this.listaAux);
	}// GEN-LAST:event_confrimBuyActionPerformed

	private void finishBuyActionPerformed(java.awt.event.ActionEvent evt) throws IOException {// GEN-FIRST:event_finishBuyActionPerformed
		javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
		int retrival = chooser.showSaveDialog(null);
		if (retrival == javax.swing.JFileChooser.APPROVE_OPTION) {
			try {
				String dir = chooser.getSelectedFile() + "";
				if (!dir.endsWith(".pdf")) {
					dir += ".pdf";
				}
				System.out.println(dir);
				this.client.generarPDF(dir);
				JOptionPane.showMessageDialog(null, "Ticket Guardado en :\n" + dir);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		System.out.println(this.carritoAux.toString());
		@SuppressWarnings({ "resource", "unused" })
		PDDocument tick = new PDDocument();
		@SuppressWarnings("unused")
		File ticket = client.buy(this.carritoAux);
		this.dispose();

		try {
			this.out.writeInt(REQUEST_CLOSE);// Cierra la conexión con el servidor
			this.out.flush();
			this.out.close();
			this.in.close();
			this.cl.close();
		} catch (IOException ex) {
			Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
		}
	}// GEN-LAST:event_finishBuyActionPerformed

	private void formWindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowClosing
		try {
			this.out.writeInt(REQUEST_CLOSE);// Cierra la conexión con el servidor
			this.out.flush();
			this.out.close();
			this.in.close();
			this.cl.close();
		} catch (IOException ex) {
			Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
		}
	}// GEN-LAST:event_formWindowClosing

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton addToCartButton;
	private javax.swing.JList<String> cartList;
	private javax.swing.JPanel cartPanel;
	private javax.swing.JButton confrimBuy;
	private javax.swing.JButton deleteProduct;
	private javax.swing.JButton derButton;
	private javax.swing.JButton exit;
	private javax.swing.JList<String> finalList;
	private javax.swing.JTextField finalName;
	private javax.swing.JPanel finalPanel;
	private javax.swing.JButton finishBuy;
	private javax.swing.JButton izqButton;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JLabel mainImgProduct;
	private javax.swing.JLabel mainProductLabel;
	private javax.swing.JLabel mainProductPriceLabel;
	private javax.swing.JLabel mainProductStockLabel;
	private javax.swing.JPanel productsPanel;
	private javax.swing.JTextField quantityLabel;
	// End of variables declaration//GEN-END:variables

}
