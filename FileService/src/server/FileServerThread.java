/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package server;

import java.io.*;
import java.net.*;

/**
 *
 * @author Aleksandar Milicevic
 */
public class FileServerThread extends Thread {
    
    byte[] buffer = new byte[1024];
    Socket socket;
    
    public FileServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            PrintWriter tokKaKlijentuTekst = new PrintWriter(socket.getOutputStream());
            OutputStream tokKaKlijentuBajtovi = socket.getOutputStream();
            BufferedReader tokOdKlijentaTekst = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            String nazivOdKlijenta = tokOdKlijentaTekst.readLine();
            
            File fileOrDir = new File(FileServer.sharedDir + "\\" + nazivOdKlijenta);
            
            if(fileOrDir.exists()) {
                if(!fileOrDir.isDirectory()) {
                    tokKaKlijentuBajtovi.write(1);
                    
                    RandomAccessFile randomAccessFile = new RandomAccessFile(FileServer.sharedDir + "\\" + nazivOdKlijenta, "r");
                    
                    int n;
                    while(true) {
                        n = randomAccessFile.read(buffer);
                        
                        if(n == -1) {
                            break;
                        }
                        
                        tokKaKlijentuBajtovi.write(buffer, 0, n);
                    }
                    randomAccessFile.close();
                }
                else {
                    tokKaKlijentuBajtovi.write(2);
                    try {
                        String[] list = fileOrDir.list();
                        
                        for(int i = 0; i < list.length; i++) {
                            long size;
                            
                            fileOrDir = new File(FileServer.sharedDir + "\\" + list[i]);
                            
                            if(fileOrDir.isDirectory()) {
                                size = -1;
                            }
                            else {
                                size = fileOrDir.length();
                            }
                            tokKaKlijentuTekst.println(list[i]);
                            tokKaKlijentuTekst.println(size);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                tokKaKlijentuBajtovi.write(0);
            }
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
