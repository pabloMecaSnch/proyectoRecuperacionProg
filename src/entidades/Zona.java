/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;

/**
 *
 * @author Usuario
 */
public class Zona implements Serializable{
    public String nombre;
    public Coordenadas coordenadas;
    
    public Zona(String nombre,Coordenadas c){
        this.nombre=nombre;
        this.coordenadas=c;
    }
}
