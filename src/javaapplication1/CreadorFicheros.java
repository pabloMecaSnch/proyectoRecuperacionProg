/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class CreadorFicheros {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // NewJFrame nf = new NewJFrame();
        // TODO code application logic here
        
        Scanner sc = new Scanner(System.in);
        FileOutputStream fos = null;
        
        DataOutputStream salida = null;
        FileWriter fw = new FileWriter("src.\\recursos\\prueba.bin",true);
        fw.close();
        File file = new File("src.\\recursos\\prueba.bin");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String linea;
        int num = 0;
        Map<Integer ,String> zonasEspaña = new HashMap<Integer, String>();
        
        try{
            fos = new FileOutputStream(file,true);
            salida = new DataOutputStream(fos);
            
            System.out.println("Introduce salir para acabar: ");
            linea = sc.nextLine();
            while(!linea.equals("salir")){
                zonasEspaña.put(num, linea);
                System.out.println("nuevo dato: "+ zonasEspaña.get(num));
                num++;
                String linea_espaciada = linea.concat("\n");
                //fos.write(linea_espaciada.getBytes());
                salida.write(linea_espaciada.getBytes());
                linea = sc.nextLine();
                
            }
            try{
             if (fos != null) {
                    fos.close();
                }
                if (salida != null) {
                    salida.close();
                }
                if(fw != null){
                    fw.close();
                }
                } catch (IOException e) {
                System.out.println(e.getMessage());                                                               
            }
           while(br.ready()){
                System.out.println(br.readLine());
                //buffer = new byte[40];
            }
            /*for (Map.Entry<Integer, String> entry : zonasEspaña.entrySet()) {
                Integer key = entry.getKey();
                String value = entry.getValue();
                System.out.println(key+" "+value);
            }*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreadorFicheros.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{
              
             if (fos != null) {
                    fos.close();
                }
                if (salida != null) {
                    salida.close();
                }
                if(br != null){
                    br.close();
                }
                if(fr != null){
                    fr.close();
                }
                } catch (IOException e) {
                System.out.println(e.getMessage());                                                               
            }
        }
    }
    
}
