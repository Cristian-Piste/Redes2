package practicas.chat.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import javax.swing.*;

public class Usuario extends javax.swing.JFrame {
    private static final long serialVersionUID = 1L;
    private InetAddress group;
    private static final String MCAST_ADDR = "230.1.1.1";
    private static final int MCAST_PORT = 12000;
    public static final int DGRAM_BUF_LEN = 512;
    private MulticastSocket socket;
    private static JEditorPane je;
    private static JComboBox<String> combo;
    private static String usuario;
    private Mensaje msj;

    public Usuario(String usuario) {
        super("Chat de " + usuario);
        Usuario.usuario = usuario;
        initComponents();
        try {
            je = jEditorPane1;
            // je.setContentType("text/html");
            combo = getjComboBox1();
            Thread tu = new ThreadUsuario(je, combo, usuario);
            tu.start();
            group = InetAddress.getByName(MCAST_ADDR);
            socket = new MulticastSocket(MCAST_PORT);
            socket.joinGroup(group);
            System.out.println("Unido exitosamente");
            msj = new Mensaje(0, usuario);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(msj);
            oos.flush();
            byte[] b = baos.toByteArray();
            DatagramPacket packet = new DatagramPacket(b, b.length, group, MCAST_PORT);
            socket.send(packet);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponents() {

        JPanel jPanel1 = new JPanel();
        JScrollPane jScrollPane1 = new JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        setjComboBox1(new javax.swing.JComboBox());
        // Variables declaration - do not modify//GEN-BEGIN:variables
        JButton jButton1 = new JButton();
        JScrollPane jScrollPane2 = new JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jEditorPane1.setEditable(false);
        jScrollPane1.setViewportView(jEditorPane1);

        getjComboBox1().setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos" }));

        jButton1.setText("Enviar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout
                .setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup().addGap(43, 43, 43).addComponent(
                                                jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 278,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                jPanel1Layout.createSequentialGroup().addGap(44, 44, 44).addComponent(
                                                        jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(getjComboBox1(), javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(24, 24, 24))
                                        .addGroup(jPanel1Layout.createSequentialGroup().addGap(40, 40, 40)
                                                .addComponent(jButton1).addContainerGap(
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup().addGap(30, 30, 30).addComponent(getjComboBox1(),
                                javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1,
                                javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 65,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1))
                        .addContainerGap(29, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        try {
            if (getjComboBox1().getSelectedItem().toString().equals("Todos")) {
                msj.setTipo(2);
            } else {
                msj.setTipo(3);
            }
            msj.setOrigen(usuario);
            msj.setDestino(getjComboBox1().getSelectedItem().toString());
            msj.setMensaje(jTextArea2.getText());
            if (je.getText().equals("")) {
                je.setText("Tu: " + jTextArea2.getText());
            } else {
                je.setText(je.getText() + "\nTu: " + jTextArea2.getText());
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(msj);
            oos.flush();
            byte[] b = baos.toByteArray();
            DatagramPacket packet = new DatagramPacket(b, b.length, group, MCAST_PORT);
            socket.send(packet);
            jTextArea2.setText("");
            // socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }// GEN-LAST:event_jButton1ActionPerformed

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Usuario(usuario).setVisible(true);
            }
        });
    }

    public javax.swing.JComboBox<String> getjComboBox1() {
		return getjComboBox1();
	}

	public void setjComboBox1(@SuppressWarnings("rawtypes") javax.swing.JComboBox jComboBox1) {
		this.jComboBox1 = jComboBox1;
	}

	@SuppressWarnings({ "rawtypes", "unused" })
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}