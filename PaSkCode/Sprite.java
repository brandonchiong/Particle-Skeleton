package PaSkCode;

import java.awt.Graphics2D;
import java.awt.Image;

public class Sprite extends Particle {
    
    private Image image;
    
    public Sprite() {
    }
    
    public Sprite(int r, int nx, int ny, int vx, int vy, Image img) {
        initSprite(r, nx, ny, vx, vy, img);
    }
    
    public void initSprite(int r, int nx, int ny, int vx, int vy, Image img) {
        initParticle(r, nx, ny, vx, vy);
        image = img;
    }
    
    public void draw(Graphics2D g) {
        g.drawImage(image, x-radius, y-radius, radius*2, radius*2, null);
    }
    
}
