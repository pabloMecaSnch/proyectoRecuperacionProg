/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author Pablo
 */

public class Animal   {

  
    private int idAnimal;
    private String nombre;
    private int zonaidZona;

    public Animal() {
    }

    public Animal(Integer idAnimal) {
        this.idAnimal = idAnimal;
    }

    public Integer getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(Integer idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getZonaidZona() {
        return zonaidZona;
    }

    public void setZonaidZona(int zonaidZona) {
        this.zonaidZona = zonaidZona;
    }
    
}
