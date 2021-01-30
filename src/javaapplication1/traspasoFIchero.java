/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import Control.Control;
import entidades.Coordenadas;
import entidades.Zona;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class traspasoFIchero {
    public static void main(String[] arg){
        File archivoBinario = new File("src./recursos/prueba.bin");
        Control c = new Control();
        ArrayList<String> zonas = c.leeFZonas();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(archivoBinario));
            
            for(String zona:zonas){
                String[] data = zona.split(":");
                Zona z = new Zona(data[0], new Coordenadas(Integer.parseInt(data[1]), Integer.parseInt(data[2])) );
                oos.writeObject(z);
            }
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(traspasoFIchero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
