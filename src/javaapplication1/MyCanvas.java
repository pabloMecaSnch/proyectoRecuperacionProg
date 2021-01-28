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
    public Color color;
    private boolean pintaCirculo;
    private int x;
    private int y;
    private final int _TAM_CIRCULO=20;
    public MyCanvas(){
        this.pintaCirculo = false;
        this.x = 0;
        this.y = 0;
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        setBackground(this.color);
       
        ImageIcon img = new ImageIcon(getClass().getResource("/javaapplication1/España.png"));
        g.drawImage(img.getImage(), 0, 0, this);
        if(pintaCirculo){
            g.drawOval(x, y, _TAM_CIRCULO, _TAM_CIRCULO);
        }
    }
    public void cambiaFondo(){
        this.color=Color.green;
        
    }
    public void setCoordenadas(Coordenadas c){
        this.pintaCirculo = true;
        x = c.getX();
        y = c.getY();
    }
    public void pintaMapa(){
        this.pintaCirculo = false;
    }
        
    }

