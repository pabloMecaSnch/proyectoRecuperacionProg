package javaapplication1;



import java.awt.*;

public abstract class Pizarra extends Canvas
{
	private boolean ejecucion=false;
	
	public Pizarra() {
		super();
	}
	
	public abstract void pintar (Graphics g, Tarjetero t);

	@Override
	public void paint (Graphics g) {
	    Tarjetero t = new Tarjetero( new MediaTracker(this),g,this);
		try {
		if (!ejecucion) {
			pintar(g,t);
			ejecucion=true;
			g.setColor(Color.black);
			g.drawString("<<Pulse Intro para terminar>>", 150, 15);
			System.out.println("<<Pulse Intro para terminar>>");
			char caracter = (char) System.in.read();
			System.exit(0);
		}
		else {
			System.exit(0);
		}
		} catch(Exception e) {}
    }

    private void pixel(int x, int y, Graphics g) {
    	g.drawLine(x,y,x,y);
    }
    
}
