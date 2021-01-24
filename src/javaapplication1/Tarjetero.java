
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;

public class Tarjetero {
	private MediaTracker media;
	private Graphics g;
	private ImageObserver imgObs;
	
	public Tarjetero(MediaTracker media, Graphics g, ImageObserver imgObs) {
		this.media=media;
		this.g = g;
		this.imgObs=imgObs;
	}
	
	public Image insertaTarjeta(String ruta) {
		File f1 = new File(ruta);
		Image img = Toolkit.getDefaultToolkit().getImage(ruta);
		media.addImage(img, 0);
        try {
            media.waitForID(0); 
        }
        catch (Exception e) {}
        return img;
	}

	public void pegaTarjeta(Image img, int x, int y) {
		g.drawImage(img, x, y, imgObs);
	}
	
}
