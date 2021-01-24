/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Usuario
 */
public class MyCanvas extends Canvas{
    public int pos;
    public MyCanvas(){
        this.pos = 0;
        setBackground(Color.RED);
    }
    public MyCanvas(int posicion){
        this.pos =posicion;
        setBackground(Color.GREEN);
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.GREEN);
       
        ImageIcon img = new ImageIcon(getClass().getResource("/javaapplication1/España.png"));
        g.drawImage(img.getImage(), this.pos, this.pos, this);
        System.out.print(this.pos);
    }
    public void pinta(Graphics g){
          setBackground(Color.red);
        
    }
        
    }

