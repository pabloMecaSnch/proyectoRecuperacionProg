/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class Control {
    
    public BufferedReader br;
    File file;
    FileReader fr;
    public Control(){
        try {
            fr = null;
            file = new File(".\\animales.txt");
            fr = new FileReader(file);
            br = new BufferedReader(fr);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex); 
        }
    }   
    public ArrayList<String> lee(){
        try {
            ArrayList<String> zonas = new ArrayList<String>();
            while(br.ready()){
                String linea = br.readLine();
                System.out.println(linea);
                zonas.add(linea);
            }
             return zonas;
        } catch (IOException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
       return null;
    }
}
