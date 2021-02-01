/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import Control.Control;
import dao.Zona;
import java.io.File;
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
    public void traspasa(){
        File archivoBinario = new File("src./recursos/prueba.bin");
        Control c = new Control();
        ArrayList<String> zonas = c.getZonas();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(archivoBinario));
            
            for(String zona:zonas){
                String[] data = zona.split(":");
                Zona z = new Zona();
                z.setNombreZona(data[0]);
                z.setCoordenadaX(Integer.parseInt(data[1]));
                z.setCoordenadaY(Integer.parseInt(data[2]));
                oos.writeObject(z);
            }
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(traspasoFIchero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
