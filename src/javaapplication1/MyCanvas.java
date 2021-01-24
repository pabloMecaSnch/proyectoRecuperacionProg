/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Usuario
 */
public class MyCanvas extends Canvas{
    
    public MyCanvas(){
        super();
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        ImageIcon image = new ImageIcon("/javaapplication1/España.png");
        g.drawImage(image.getImage(), 100, 100, this);
    }
}
