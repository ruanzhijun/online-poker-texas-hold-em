/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokercliente;

import entidades.Carta;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Clase encargada de gestionar y coordinar la conexion del Programa con el Servidor.
 * @author Mario Codes Sánchez
 * @since 19/02/2017
 */
public class Conexion {
    private static final int PUERTO = 8143;
    private static final String SERVER_IP = "127.0.0.1";
    
    private static Socket socket = null;
    
    private static InputStream in = null;
    private static OutputStream out = null;
    private static ObjectOutputStream oos = null;
    private static ObjectInputStream ois = null;
    
    /**
     * Get de un int del Server.
     * @return Int a recibir.
     */
    private static int getInt() throws IOException {
        return ois.readInt();
    }
    
    /**
     * Apertura de las cabeceras necesarias para la conexion.
     */
    private static void aperturasCabeceraConexion() {
        try {
            socket = new Socket(SERVER_IP, PUERTO);

            in = socket.getInputStream();
            out = socket.getOutputStream();
            oos = new ObjectOutputStream(out);
            ois = new ObjectInputStream(in);
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Cerrado de las aperturas realizadas.
     */
    static void cerradoCabecerasConexion() {
        try {
            if(ois != null) ois.close();
            if(oos != null) oos.close();
            if(out != null) out.close();
            if(in != null) in.close();
            if(socket != null) socket.close();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
//    public static int getFichas(String id) {
//        try {
////            aperturasCabeceraConexion();
//            
//            String idServer = ois.readObject().toString();
//            if(id.matches(idServer)) {
//                return ois.readInt();
//            }
//        }catch(IOException|ClassNotFoundException ex) {
//            ex.printStackTrace();
//        }finally {
////            cerradoCabecerasConexion();
//        }
//        
//        return 0;
//    }
    
    /**
     * Envio al server de la accion que deseamos realizar para que la lea en el Switch y actue.
     * @param accion Accion a realizar.
     * @param id ID propio del Jugador.
     * @throws IOException 
     * @return Booleano indicando si la accion a realizar es posible.
     */
    private static boolean accionMenu(int accion) throws IOException {
        oos.writeInt(accion);
        
        oos.flush();
        
        return ois.readBoolean();
    }
    
    public static void sendJugada(String id, String jugada, int valor) {
        try {
            aperturasCabeceraConexion();
            
            oos.writeInt(6);
            oos.flush();
            
            boolean b = ois.readBoolean();
            if(b) {
                System.out.println("Jugador con ID: " +id);
                oos.writeUTF(id);
            
                oos.writeUTF(jugada);

                oos.writeInt(valor);
                oos.flush();
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }finally {
            cerradoCabecerasConexion();
        }
    }
    
    static void sendBooleano(boolean b) {
        try {
            oos.writeBoolean(b);
            oos.flush();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static int getWinner(String id) {
        int ganancias = -1;
        try {
            aperturasCabeceraConexion();
            
            oos.writeInt(7);
            oos.writeUTF(id);
            oos.flush();
            
            boolean todasJugadas = ois.readBoolean();
            if(todasJugadas) {
                String idWinner = ois.readUTF();
                if(id.matches(idWinner)) {
                    ganancias = ois.readInt();
                } else {
                    ganancias = 0;
                }
                
                oos.writeUTF(id);
                oos.writeBoolean(true);
                oos.flush();
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }finally {
//            cerradoCabecerasConexion();
        }
        
        return ganancias;
    }
    
//    public static void sendValor(String id, int valor) {
//        try {
//            String idServer = (String) ois.readObject();
//            if(id.matches(idServer)) {
//                oos.writeInt(valor);
//                oos.flush();
//            }
//        }catch(IOException|ClassNotFoundException ex) {
//            ex.printStackTrace();
//        }finally{
//            cerradoCabecerasConexion();
//        }
//    }
    
    /**
     * Get de la carta enviada por Socket desde el Server.
     * @return Carta enviada desde el Server para este cliente.
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    private static Carta getCarta() throws IOException, ClassNotFoundException {
        return (Carta) ois.readObject();
    }
    
    /**
     * Obtencion de Cartas desde el Servidor. Esta automatizado independientemente del numero de cartas.
     *  Lo uso tanto para obtener las cartas propias del jugador como las comunes de la mesa.
     * @param accion Accion a realizar en el Server (2 recibo cartas propias, 3 recibo cartas comunes).
     * @return ArrayList de Cartas con las Cartas segun requiera la ocasion.
     */
    public static ArrayList<Carta> getCartas(int accion, String id) {
        ArrayList<Carta> cartas = new ArrayList<>();
        try {
            aperturasCabeceraConexion();
            
            oos.writeInt(accion);
            oos.writeUTF(id);
            oos.flush();
            
            boolean b = ois.readBoolean();
            
            if(b) {
                int cartasARecibir = ois.readInt(); //Numero de cartas a Recibir.

                for (int i = 0; i < cartasARecibir; i++) {
                    cartas.add(getCarta());
                }

                return cartas;
            }
        }catch(ClassNotFoundException | ClassCastException | IOException ex) {
            ex.printStackTrace();
        }finally {
            cerradoCabecerasConexion();
        }
        
        return null;
    }
    
    /**
     * Accion de apostar pasada al Servidor..
     * @param fichas Fichas que queremos apostar.
     * @return Fichas totales que hay en la pool comun. -1 si error.
     */
    public static int apostar(int fichas, String id) {
        try {
            aperturasCabeceraConexion();
            
            oos.writeInt(4);
            oos.flush();
            
            boolean apostar = ois.readBoolean();
            if(apostar) {
                oos.writeUTF(id);
                oos.flush();
                
                if(ois.readBoolean()) { //Este jugador no ha apostado.
                    oos.writeInt(fichas);
                    oos.flush();
                    
                    return ois.readInt(); //Fichas pool.
                }
            } else return -1;
        }catch(IOException ex) {
            ex.printStackTrace();
        }finally {
//            cerradoCabecerasConexion(); //todo: testear, si cierro aqui peta en el server cuando termina la fase river y espera inputs de cartas.
        }
        
        return -1;
    }
    
    static String joinNormal() {
        try {
            String id = ois.readUTF();
            return id;
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    static boolean retirarse(String id) {
        aperturasCabeceraConexion();
        try {
            oos.writeInt(8);
            oos.writeUTF(id);
            oos.flush();
            
            return ois.readBoolean();
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    static String joinFirst(int playersNumber) {
        try {
            oos.writeInt(playersNumber);
            oos.flush();

            String id = ois.readUTF();
            return id;
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    static boolean checkIsFirst() {
        try {
            aperturasCabeceraConexion();
            
            oos.writeInt(1);
            oos.flush();
            
            boolean isFirst = ois.readBoolean();
            return isFirst;
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Aviso al Server para que aniada a este Jugador.
     * @param selectorMenu Accion del Menu a ejecutar.
     * @return ID del Jugador.
     */
    private static String addJugadorServer(int selectorMenu) {
        try {
            aperturasCabeceraConexion();
            accionMenu(selectorMenu);
            
            String IDJugador = ois.readUTF();
            return IDJugador;
        }catch(ConnectException ex) {
            System.out.println("Problema en la conexion. " +ex.getLocalizedMessage());
        }catch(IllegalArgumentException ex) {
            System.out.println("Numero de argumentos erroneo. Comprobar que el puerto este dentro de rango.\t" +ex.getLocalizedMessage());
        }catch(IOException ex) {
            System.out.println("Problema de IO." +ex.getLocalizedMessage());
        }catch(ClassCastException ex) {
            System.out.println("Problema de Casteo. " +ex.getLocalizedMessage());
        }finally{
            cerradoCabecerasConexion();
        }
        
        return null;
    }
    
    /**
     * Aniadido de un Jugador mas.
     * @return ID del Jugador.
     */
    public static String addJugador() {
        return addJugadorServer(0);
    }
    
    /**
     * Aniadido del ultimo Jugador.
     * @return ID del Jugador.
     */
    public static String addUltimoJugador() {
        return addJugadorServer(1);
    }
}
