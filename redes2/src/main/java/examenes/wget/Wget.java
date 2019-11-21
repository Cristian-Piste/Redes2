package examenes.wget;


import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.*;
import javax.swing.*;

public class Wget extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JTextField textfield1, textfield2;
    private JButton boton1;
    private Wget() {
        setLayout(null);
        textfield1=new JTextField();
        textfield1.setBounds(10,10,500,30);
        add(textfield1);
        textfield2=new JTextField();
        textfield2.setBounds(10,50,500,30);
        add(textfield2);
        boton1=new JButton("Enviar");
        boton1.setBounds(200,90,100,30);
        add(boton1);
        boton1.addActionListener(this);
    }

    private void getWebSite(String pagina, String path)  {

        try {
            int byteRead;
            URL url = new URL(pagina);
            URLConnection urlc = url.openConnection();
            
            BufferedReader buffer = new BufferedReader (new InputStreamReader( urlc.getInputStream(), StandardCharsets.UTF_8));
            
            StringBuilder builder = new StringBuilder();
            
            while ((byteRead = buffer.read()) != -1)
                builder.append((char) byteRead);
            
            buffer.close();

            File archivo = new File(path);
            BufferedWriter bw;
            
            if(archivo.exists()) {
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write(builder.toString());
                bw.flush();
            } else {
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write(builder.toString());
                bw.flush();
            }
            bw.close();
            
            System.out.println(builder.toString());
            System.out.println("Tamaño del archivo: " + builder.length() + " bytes.");
            JOptionPane.showMessageDialog(null, "Se genero con exito el archivo " + path);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex, "ERROR", JOptionPane.WARNING_MESSAGE);
            Logger.getLogger(Wget.class.getName()).log(Level.SEVERE, null, ex);
        }

        }
    public static void main(String[] args) throws IOException {
        Wget rutas=new Wget();
        rutas.setBounds(0,0,540,170);
        rutas.setVisible(true);
        //new wget().getWebSite();
    }

    public void actionPerformed(ActionEvent e)  {
        if (e.getSource()==boton1) {
            String cad1=textfield1.getText();
            String cad2=textfield2.getText();
            new Wget().getWebSite(cad1, cad2);
        }
    }
    
}