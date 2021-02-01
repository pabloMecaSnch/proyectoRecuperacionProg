/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import dao.Animal;
import dao.AnimalJpaController;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import dao.Zona;
import dao.ZonaJpaController;
import dao.exceptions.NonexistentEntityException;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author usuario
 */
public class Control {
    
    private BufferedReader br;
    private File fileAnimales;
    private File fileZonas;
    private FileReader fr;
    private HashMap<String, Zona> mapaAnimales;
    private HashMap<String, Zona> mapaZonas;
    private FileWriter fw;
    
    private EntityManagerFactory emf;
    private ZonaJpaController zonaController;
    private AnimalJpaController animalController;
    
    public Control(){
        this.emf = Persistence.createEntityManagerFactory("proyectoRecuperacionProgPU");
        this.zonaController = new ZonaJpaController(emf);
        this.animalController = new AnimalJpaController(emf);
        try {
            //Inicializo este file writer con los archivos porque hay veces que el ordenador
            //no encuentra la ruta con la clase File, pero usando FileWriter siempre la encuentra
            //y después el File va bien.
            fw = new FileWriter("src./recursos/animales.txt", true);
            fw.close();
            fw = new FileWriter("src./recursos/zonas.bin",true);
            fw.close();
            
            mapaAnimales = new HashMap<>();
            mapaZonas = new HashMap<>();
            fr = null;
            
            fileAnimales = new File("src./recursos/animales.txt");
            fileZonas = new File("src./recursos/zonas.bin");
            leeFZonas();
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
                String[] animal = linea.split(":");
                mapaAnimales.put(animal[0],mapaZonas.get(animal[1]) );
            }
            //return zonas;
        } catch (IOException ex) {
            System.out.println("Archivo no encontrado");
        }
       //return null;
    }
    public void leeFZonas(){
         
         //EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyectoRecuperacionProgPU");
         //ZonaJpaController controlZona = new ZonaJpaController(emf);
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(fileZonas));
            Object aux = ois.readObject();
            while(aux!=null){
                if (aux instanceof Zona){
                    mapaZonas.put(((Zona) aux).getNombreZona(), ((Zona)aux) );
                    System.out.println(((Zona) aux).getNombreZona());
                    //Las siguientes líneas comentadas las usé para introducir los datos en la base de datos
                    //a medidia que los leía del fichero
                   /* Zona z = new Zona();
                    z.setNombreZona(((Zona) aux).getNombreZona());
                    z.setCoordenadaX(((Zona) aux).getCoordenadaX());
                    z.setCoordenadaY(((Zona) aux).getCoordenadaY());
                    controlZona.create(z);*/
                    aux = ois.readObject();   
                }
            }
        } catch (IOException ex) {
            System.out.println("Archivo no encontrado");
       // } catch (ClassNotFoundException ex) {
        //    Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }catch(DatabaseException e){
            System.out.println(e);
        }
    }
    public Zona getoZonaFromAnimal(String nombre){
        Zona zona = new Zona();
        zona = mapaAnimales.get(nombre);
        if(zona == null){
            zona = buscarZonaEnBase(nombre);
        }
        return zona;
    }
    public Zona getDatosZona(String zona){
        return mapaZonas.get(zona);
    }
    
    public boolean anadirAnimal(Animal animal){
        
        if( mapaAnimales.get(animal.getNombre()) == null ){
            if(this.animalController.findAnimalByName(animal.getNombre()) == null){
                mapaAnimales.put(animal.getNombre(), animal.getZonaidZona());
                this.animalController.create(animal);
                return true;
            }else{
                return false;
            }
        }else{
            System.out.println("Animal ya registrado");
            return false;
        }
        
    }
    
    public ArrayList<String> getZonas(){
        ArrayList<String> zonas = new ArrayList<>();
        mapaZonas.forEach((clave,valor)-> zonas.add(valor.getNombreZona()) );
        return zonas;
    }
    public Zona buscaZona(String nombre){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyectoRecuperacionProgPU");
        ZonaJpaController zonaController = new ZonaJpaController(emf);
        Zona z = zonaController.findZonaByName(nombre);
        return z;
    }

    private Zona buscarZonaEnBase(String nombre) {
        Zona zona = new Zona();
        Animal animal = new Animal();
        animal = this.animalController.findAnimalByName(nombre);
        if(animal !=null){
            zona =  this.zonaController.findZona(animal.getZonaidZona().getIdZona());
        }else{
            zona = null;
        }
        return zona;
    }
    public boolean borraAnimal(String nombre){
        boolean borrado = false;
        boolean borradoBase = true;
        try {
            
        if(mapaAnimales.containsKey(nombre)){
            mapaAnimales.remove(nombre);
            borrado=true;
        }
        animalController.destroy( animalController.findAnimalByName(nombre).getIdAnimal() );
        } catch (NonexistentEntityException ex) {
            ex.printStackTrace();
            borradoBase = false;
        }catch(NullPointerException ex){
            borradoBase = false;
        }
        return (borrado || borradoBase);
    }
    public ArrayList<String> getAnimales(){
        ArrayList<String> animales = new ArrayList<>();
        List<Animal> animalesBD = this.animalController.findAnimalEntities();
        this.mapaAnimales.forEach((clave,valor)->animales.add(clave));
        animalesBD.forEach((animal)->animales.add(animal.getNombre()));
        return animales;
    }
}
