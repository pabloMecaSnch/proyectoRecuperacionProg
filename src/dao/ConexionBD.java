package dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pablo
 */
public class ConexionBD {
    private String drivermysql="com.mysql.jdbc.Driver";
    private String url=null;
    private Connection conexion=null;
    private PreparedStatement sentencia=null;
    private ResultSet registros;
    private ResultSetMetaData metadatos;
    private int numeroColumnas;
    private String[] columnas;
    private String usuario;
    private String pass;
    public ConexionBD(String tipo,String nombreBD,String usuario,String pass){
        url="jdbc:mysql://localhost:3306/"+nombreBD+"?serverTimezone=GMT";
        this.usuario=usuario;
        this.pass = pass;
        if(tipo.equals("mysql")){
           abrirConexion();
        }
    }
    private void abrirConexion(){
         try{
                Class.forName(drivermysql);
                conexion=DriverManager.getConnection(url, usuario, pass);
                System.out.println("exito");

            }catch(Exception e){
                System.out.println("error");

            }
        
    }
    private void cerrarConexion(){
        try {
             registros.close();
             sentencia.close();
             conexion.close();
        } catch (SQLException ex) {

        }

    }

   private ResultSet ejecutarSQL(String sql) {
        try {
            //sentencia = conexion.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            //sentencia =conexion.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            Statement s = conexion.createStatement();
            this.registros=s.executeQuery(sql);
            metadatos=(ResultSetMetaData) registros.getMetaData();
            numeroColumnas=metadatos.getColumnCount();
            this.nombreColumnas();
            System.out.println(registros.next());
            return registros;

        } catch (SQLException ex) {
            return null;
        }
    }
   private boolean ejecutarUpdate(String sql){
       try{
         Statement s = conexion.createStatement();
         s.executeUpdate(sql);
         return true;
       } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
   }
    private void nombreColumnas() {
        columnas = new String[numeroColumnas];
       for(int i=0;i<columnas.length;i++){
            try {
               columnas[i] = metadatos.getColumnName(i+1);
            } catch (SQLException ex) {   }
       }
    }
    public Map<String,Zona> getZonas(){
        ejecutarSQL("SELECT * FROM zona");
        int num=this.numRegistros();
           HashMap<String,Zona> tabla=new HashMap<String, Zona>();
           try {
           for (int i=0;i<=num;i++) {
                registros.next();
                Zona z = new Zona();
                z.setIdZona(registros.getInt(1));
                z.setNombreZona(registros.getString(2));
                z.setCoordenadaX(registros.getInt(3));
                z.setCoordenadaY(registros.getInt(4));
                tabla.put(z.getNombreZona(), z);
            }
        } catch (SQLException ex) {
        ex.printStackTrace();
        }

          return tabla;
    }
    public Map<String,Animal> getAnimales(){
        ejecutarSQL("SELECT * FROM animal");
        int num=this.numRegistros();
           HashMap<String,Animal> tabla=new HashMap<String, Animal>();
           try {
           for (int i=0;i<=num;i++) {
                registros.next();
                Animal a = new Animal();
                a.setIdAnimal(registros.getInt(1));
                a.setNombre(registros.getString(2));
                a.setZonaidZona(registros.getInt(3));
                tabla.put(a.getNombre(), a);
            }
        } catch (SQLException ex) {
        ex.printStackTrace();
        }

          return tabla;
    }
    public Zona getZonaByAnimal(String animal){
        try {
            ejecutarSQL("SELECT z.idZona,z.nombreZona,z.coordenadaX,z.coordenadaY FROM Animal a, Zona z WHERE a.nombre='"+animal+"' AND a.Zona_idZona=z.idZona;");
            Zona z = new Zona();
            z.setIdZona(registros.getInt(1));
            z.setNombreZona(registros.getString(2));
            z.setCoordenadaX(registros.getInt(3));
            z.setCoordenadaY(registros.getInt(4));
            return z;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    public Animal getAnimalByNombre(String nombre){
        try{
            ejecutarSQL("SELECT * FROM animal WHERE nombre='"+nombre+"'");
            Animal a = new Animal();
            a.setIdAnimal(registros.getInt(1));
            a.setNombre(registros.getString(2));
            a.setZonaidZona(registros.getInt(3));
            return a;
        }catch (SQLException ex) {
            return null;
        }
    }
    public Zona getZonaById(int id){
        try{
            ejecutarSQL("SELECT * FROM zona WHERE idZona="+id);
            Zona z = new Zona();
            z.setIdZona(registros.getInt(1));
            z.setNombreZona(registros.getString(2));
            z.setCoordenadaX(registros.getInt(3));
            z.setCoordenadaY(registros.getInt(4));
            return z;
        }catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
     public Zona getZonaByNombre(String nombre){
        try{
            ejecutarSQL("SELECT * FROM zona WHERE nombreZona='"+nombre+"'");
            Zona z = new Zona();
            z.setIdZona(registros.getInt(1));
            z.setNombreZona(registros.getString(2));
            z.setCoordenadaX(registros.getInt(3));
            z.setCoordenadaY(registros.getInt(4));
            return z;
        }catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public void anadirAnimal(Animal animal){
        ejecutarUpdate("INSERT INTO animal (`nombre`, `Zona_idZona`) VALUES ('"+animal.getNombre()+"', "+animal.getZonaidZona()+")");
    }
    public int numRegistros(){
         int num=0;
         try {
            while (registros.next()) {
               num++;

            }
            registros.beforeFirst();
             System.out.println("todo ok");
        } catch (SQLException ex) {
             ex.printStackTrace();}

         return num;
     }
    public boolean borrarAnimal(String animal){
        if(ejecutarUpdate("DELETE FROM animal WHERE nombre='"+animal+"'"))
            return true;
        return false;
    }
}
