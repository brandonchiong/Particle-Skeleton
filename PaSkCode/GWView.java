package PaSkCode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GWView extends PSysView {
    
    void draw(GWModel gw, Graphics g) {
        
        Graphics2D g2d = (Graphics2D) g;
        gw.player.draw(g2d);
	
        for (int i=0; i<gw.botList.size(); i++) {
            Sprite s = gw.botList.get(i);
	    s.draw(g2d); 
	}
        
        for (int i=0; i<gw.projectileList.size(); i++) {
            Sprite s = gw.projectileList.get(i);
	    s.draw(g2d); 
	}
        
        for (int i=0; i<gw.graveList.size(); i++) {
            Sprite s = gw.graveList.get(i);
	    s.draw(g2d); 
	}
    }
    
}
