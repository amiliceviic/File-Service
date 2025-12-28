/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package server;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aleksandar Milicevic
 */
public class FileServer extends Thread {
    
    static String sharedDir = "Files";
    
    public static void main(String[] args) {
        
        int port = 2222;
        
        if(args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            
            while(true) {
                
            }
        } catch (BindException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

}
