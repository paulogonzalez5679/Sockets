package momservidor;

/*
 * @author alejo
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ConectorServidor extends Thread {

    //Instancias
    private Socket socket;
    private ServerSocket servidor;
    private InputStreamReader entrada;
    private DataOutputStream salida;
    private BufferedReader mensaje;
    final int puerto = 8181;

    public ConectorServidor() {
    }

    public void run() {
        String texto;
        try {
            servidor = new ServerSocket(puerto);
            socket = servidor.accept();
            entrada = new InputStreamReader(socket.getInputStream());
            mensaje = new BufferedReader(entrada);
            salida = new DataOutputStream(socket.getOutputStream());

            while (true) {
                texto = this.mensaje.readLine();
                VentanaServidorMOM.textAreaSer.setText(VentanaServidorMOM.textAreaSer.getText() + '\n' + texto);
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void enviarMensaje(String mensaje) {
        try {
            salida.writeUTF(mensaje + "\n");
        } catch (Exception e) {
        }
    }
    
    public void Imagen(String imagen)
    {
        try {
            salida.writeUTF("Archivo: "+imagen+" Entregada "+"\n");
            ObjectInputStream out = new ObjectInputStream(socket.getInputStream());
            FileOutputStream file = new FileOutputStream(imagen);
            byte[] buf = new byte[4096];
            
            while(true)
            {
                int len = out.read(buf);
                if(len==-1) break;
                file.write(buf, 0, len);
            }
        } catch (Exception e) {}
    }

    public void desconectar() throws IOException {
        try {
           
            socket.close();
            servidor.close();
        } catch (Exception e) {
             if (socket != null) {
             socket.close();
        }
        }
        
       
    }
}

