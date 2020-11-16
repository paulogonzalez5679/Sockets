/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package momcliente;
/*
 * @author alejo
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConectorCliente extends Thread{
    //Instancias
    private Socket socket;
    private InputStreamReader entrada;
    private DataOutputStream salida;
    private BufferedReader mensaje;
    final int puerto = 8181;
    String ip;
    
    public ConectorCliente(String ip){
        ip = this.ip;
    }
    
    public void run(){
        String texto;
        try {
            socket = new Socket(ip, this.puerto);
            entrada = new InputStreamReader(socket.getInputStream());
            mensaje = new BufferedReader(entrada);
            salida = new DataOutputStream(socket.getOutputStream());
            salida.writeUTF("Conectado... \n");
            

            
            while(true){
                texto = this.mensaje.readLine();
                VentanaClienteMOM.txtMensaje.setText(VentanaClienteMOM.txtMensaje.getText()+'\n'+texto);
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
    
    public void enviarMensaje(String mensaje){
        try {
            salida.writeUTF(mensaje+"\n");
        } catch (Exception e) {}
    }
    
    public void Imagen(String imagen)
    {
        try {
            salida.writeUTF("Archivo: "+imagen+" Aceptada "+"\n");
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            FileInputStream file = new FileInputStream(imagen);
            byte[] buf = new byte[4096];
            
            while(true)
            {
                int len = file.read(buf);
                if(len==-1) break;
                out.write(buf, 0, len);
            }
        } catch (Exception e) {}
    }
    
    public void desconectar(){
        try {
            socket.close();
        } catch (Exception e) {}
    }
}
