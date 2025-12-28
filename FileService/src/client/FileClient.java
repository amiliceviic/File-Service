/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package client;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aleksandar Milicevic
 */
public class FileClient {
    
    static String adress = "localhost";
    static int port = 2222;
    
    public static void downloadFileOrListDirectory(String filePath) throws IOException {
        
        Socket s = new Socket(adress, port);
        
        PrintStream tokKaServeruTekst = new PrintStream(s.getOutputStream());
        InputStream tokOdServeraBajtovi = s.getInputStream();
        BufferedReader tokOdServeraTekst = new BufferedReader(new InputStreamReader(s.getInputStream()));
        
        tokKaServeruTekst.println(filePath);
        
        int type = tokOdServeraBajtovi.read();
        
        if(type == 2) {
            String fileName = tokOdServeraTekst.readLine();
            
            while(fileName != null) {
                long fileSize = Long.parseLong(tokOdServeraTekst.readLine());
                
                if(fileSize == -1) {
                    System.out.println(fileName + " <DIR");
                }
                else {
                    System.out.println(fileName + " (" + fileSize + "b)");
                }
                fileName = tokOdServeraTekst.readLine();
            }
        } else if(type == 1) {
            RandomAccessFile randomAccessFile = new RandomAccessFile(filePath.substring(filePath.indexOf('\\') + 1), "rw");
            int n;
            byte[] buffer = new byte[1024];
            
            while(true) {
                n = tokOdServeraBajtovi.read(buffer, 0, 1024);
                
                if(n == -1) {
                    break;
                }
                
                randomAccessFile.close();
                System.out.println("Fajl " + filePath + " je sacuvan na hard disk.");
            }
        } else if(type == 0) {
            System.out.println(filePath + " ne postoji na serveru");
        }
        s.close();
    }
    
    public static void main(String[] args) {
        
        if(args.length > 0) {
            adress = args[0];
        }
        if(args.length > 1) {
            port = Integer.parseInt(args[1]);
        }
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            downloadFileOrListDirectory("");
            
            while(true) {
                downloadFileOrListDirectory(br.readLine());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
