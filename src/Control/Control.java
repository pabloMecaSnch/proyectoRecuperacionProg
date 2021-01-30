/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

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
import entidades.Coordenadas;
import entidades.Zona;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;

/**
 *
 * @author usuario
 */
public class Control {
    
    private BufferedReader br;
    private File fileAnimales;
    private File fileZonas;
    private FileReader fr;
    public HashMap<String, String> mapaAnimales;
    public HashMap<String, Zona> mapaZonas;
    private FileWriter fw;
    public Control(){
        
        
        try {
            //Inicializo este file writer con los archivos porque hay veces que el ordenador
            //no encuentra la ruta con la clase File, pero usando FileWriter siempre la encuentra
            //y después el File va bien.
            fw = new FileWriter("src./recursos/animales.txt", true);
            fw.close();
            fw = new FileWriter("src./recursos/prueba.bin",true);
            fw.close();
            
            mapaAnimales = new HashMap<>();
            mapaZonas = new HashMap<>();
            fr = null;
            
            fileAnimales = new File("src./recursos/animales.txt");
            fileZonas = new File("src./recursos/prueba.bin");
            leeFAnimales();
        } catch (IOException ex) {
            System.out.println("Archivo no encontrado");
        }finally {
            try {
                if(fw !=null)
                    fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }   
    private void leeFAnimales(){
        //En este caso, todos los animales del fichero 
        //Pertenecen a Galicia para simplificar el código
        try {
            //ArrayList<String> zonas = new ArrayList<String>();
            fr = new FileReader(fileAnimales);
            br = new BufferedReader(fr);
            while(br.ready()){
                String linea = br.readLine();
                System.out.println(linea);
                //zonas.add(linea);
                mapaAnimales.put(linea, "Galicia");
            }
            //return zonas;
        } catch (IOException ex) {
            System.out.println("Archivo no encontrado");
        }
       //return null;
    }
    public ArrayList<String> leeFZonas(){
         ArrayList<String> zonas = new ArrayList<String>();
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(fileZonas));
            Object aux = ois.readObject();
            while(aux!=null){
                if (aux instanceof Zona){
                    mapaZonas.put(((Zona) aux).nombre, ((Zona)aux) );
                    zonas.add(((Zona) aux).nombre);
                    System.out.println(aux);
                    aux = ois.readObject();
                    
                }
            }
             return zonas;
        } catch (IOException ex) {
            System.out.println("Archivo no encontrado");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
       return zonas;
    }
    public String buscaAnimal(String nombre){
        String animal = mapaAnimales.get(nombre);
        return animal;
    }
}
