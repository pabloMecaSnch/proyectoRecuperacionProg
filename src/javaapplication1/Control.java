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
    
    private BufferedReader br;
    private File fileAnimales;
    private File fileZonas;
    private FileReader fr;
    public HashMap<String, String> mapaAnimales;
    public HashMap<String, Coordenadas> mapaZonas;
    
    public Control(){
        
        mapaAnimales = new HashMap<>();
        mapaZonas = new HashMap<>();
        fr = null;
        fileAnimales = new File(".\\animales.txt");
        fileZonas = new File(".\\datos.dat");
        leeFAnimales();
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
        try {
            ArrayList<String> zonas = new ArrayList<String>();
            fr = new FileReader(fileZonas);
            br = new BufferedReader(fr);
            while(br.ready()){
                String linea = br.readLine();
                String[] datos = linea.split(":");
                mapaZonas.put(
                        datos[0],
                        new Coordenadas(Integer.parseInt(datos[1]), Integer.parseInt(datos[2])
                        ) );
                System.out.println(linea);
                zonas.add(linea);
            }
             return zonas;
        } catch (IOException ex) {
            System.out.println("Archivo no encontrado");
        }
       return null;
    }
    public String buscaAnimal(String nombre){
        String animal = mapaAnimales.get(nombre);
        return animal;
    }
}
