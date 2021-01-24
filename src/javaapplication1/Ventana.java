/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author Pablo
 */
public class Ventana extends JFrame{
    public Ventana(Pizarra l) {
	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pizarra");
        setSize(500,536); 
        setResizable(false);
        setAlwaysOnTop(true);

        // Creo el lienzo
		add(l);
        setVisible(true);
    }
}
