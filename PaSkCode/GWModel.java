package PaSkCode;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GWModel extends PSysModel {
    
    ArrayList <Sprite> botList = new ArrayList<>();
    ArrayList <Sprite> projectileList = new ArrayList<>();
    ArrayList <Sprite> graveList = new ArrayList<>();
            
    Sprite player;
    boolean playerdead = false;
    Image projectileImg;
    Image gravestoneImg;
    
    public void addBot(int r, int nx, int ny, int vx, int vy, Image img) {
        
        Sprite bot = new Sprite(r, nx, ny, vx, vy, img);
        botList.add(bot);
        pList.add(bot);
    }
    
    public void addPlayer(int r, int nx, int ny, int vx, int vy, Image img) {
        
        player = new Sprite(r, nx, ny, vx, vy, img);
        pList.add(player);
    }
    
    public void addGraveStone(Image gImg) {
        gravestoneImg = gImg;
    }
    
    public void addProjectileImg(Image pImg) {
        projectileImg = pImg;
    }
    
    public void addProjectile(Sprite p1) {
        int projvx = 0;
        int projvy = 0;
        
        if (p1.velX > 0) {
            projvx = 1;
        } else if (p1.velX == 0) {
            projvx = 0;
        } else if (p1.velX < 0) {
            projvx = -1;
        }
        
        if (p1.velY > 0) {
            projvy = 1;
        } else if (p1.velY == 0) {
            projvy = 0;
        } else if (p1.velY < 0) {
            projvy = -1;
        }
        
        if (projvx == 0 && projvy == 0) {  // if player/bot not moving, return
            return;
        }
        
        int xposition = p1.x + (projvx * (p1.radius + 10));
        int yposition = p1.y + (projvy * (p1.radius + 10));
        
        Sprite projectile = new Sprite(10, xposition, yposition, p1.velX*2, p1.velY*2, projectileImg);
        pList.add(projectile);
        projectileList.add(projectile);
        
    }
    
    public void orientBots() {
        for (int i = 0; i < botList.size(); i++) {
            Sprite bot = botList.get(i);
            
            boolean stop = false;
            if (bot.velX == 0 && bot.velY == 0) { //check if bot is moving
                stop = true;
            }
            
            if (bot.x > player.x) {
                bot.velX = -1;
            } else if (bot.x < player.x) {
                bot.velX = 1;
            }
            
            if (bot.y > player.y) {
                bot.velY = -1;
            } else if (bot.y < player.y) {
                bot.velY = 1;
            }
            //fire projectile
            if (playerdead == false && stop == false) {
                addProjectile(bot);
            }
        }
    }
    
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
	if (playerdead == false) {
            if (code == KeyEvent.VK_UP) {
              player.velY = -5;
            }
            else if (code == KeyEvent.VK_DOWN) {
               player.velY = +5;
            }
            else if (code == KeyEvent.VK_LEFT) {
               player.velX = -5;
            }
            else if (code == KeyEvent.VK_RIGHT) {
                player.velX = +5;
            }
        
            if (code == KeyEvent.VK_SPACE) {
                addProjectile(player);
            }
        }
    }
    
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
	if (code == KeyEvent.VK_UP) {
            player.velY = 0;
	}
	else if (code == KeyEvent.VK_DOWN) {
            player.velY = 0;
	}
	else if (code == KeyEvent.VK_LEFT) {
            player.velX = 0;
	}
	else if (code == KeyEvent.VK_RIGHT) {
            player.velX = 0;
	}
    }
    
    public void checkCollide() {
        //player-bot
        for (int i = 0; i < botList.size(); i++) {
            Particle bot = botList.get(i);
            boolean collide = isOverlap(player, bot);
            
            if (collide == true) {
                playerdead = true;
                player.initSprite(player.radius, player.x, player.y, 0, 0, gravestoneImg);
                
                Sprite sbot = botList.get(i);
                sbot.initSprite(sbot.radius, sbot.x, sbot.y, 0, 0, gravestoneImg);
                pList.remove(sbot);
            }
        }
        //player-projectile
        for (int i = 0; i < projectileList.size(); i++) {
            Particle proj = projectileList.get(i);
            boolean collide = isOverlap(player, proj);
            
            if (collide == true) {
                playerdead = true;
                player.initSprite(player.radius, player.x, player.y, 0, 0, gravestoneImg);
                
                projectileList.remove(i);
            }
        }
        //player-gravestone
        for (int i = 0; i < graveList.size(); i++) {
            Particle grave = graveList.get(i);
            boolean collide = isOverlap(player, grave);
            
            if (collide == true) {
                playerdead = true;
                player.initSprite(player.radius, player.x, player.y, 0, 0, gravestoneImg);
            }
        }
        //bot-bot
        for (int i = 0; i < botList.size(); i++) {
            Particle bot1 = botList.get(i);
            
            for (int j = i+1; j < botList.size(); j++) {
                Particle bot2 = botList.get(j);
                boolean collide = isOverlap(bot1, bot2);
            
                if (collide == true) {
                    
                    Sprite sbot1 = botList.get(i);
                    sbot1.initSprite(sbot1.radius, sbot1.x, sbot1.y, 0, 0, gravestoneImg);
                    pList.remove(sbot1);
                    
                    Sprite sbot2 = botList.get(j);
                    sbot2.initSprite(sbot2.radius, sbot2.x, sbot2.y, 0, 0, gravestoneImg);
                    pList.remove(sbot2);
                }
            }
        }
        //bot-projectile
        for (int i = 0; i < botList.size(); i++) {
            Particle bot = botList.get(i);
            
            for (int j = 0; j < projectileList.size(); j++) {
                Particle proj = projectileList.get(j);
                boolean collide = isOverlap(bot, proj);
            
                if (collide == true) {
                    
                    Sprite sbot = botList.get(i);

                    sbot.initSprite(sbot.radius, sbot.x, sbot.y, 0, 0, gravestoneImg);
                    graveList.add(sbot);
                    pList.remove(sbot); 
                    
                    projectileList.remove(j);
                }
            }
        }
        //bot-gravestone
        for (int i = 0; i < graveList.size(); i++) {
            Particle grave = graveList.get(i);
            
            for (int j = 0; j < botList.size(); j++) {
                Particle bot = botList.get(j);
                boolean collide = isOverlap(grave, bot);
            
                if (collide == true) {
                    Sprite sbot = botList.get(j);

                    sbot.initSprite(sbot.radius, sbot.x, sbot.y, 0, 0, gravestoneImg);
                    graveList.add(sbot);
                    botList.remove(sbot); 
                }
            }
        }
        
        //projectile-projectile
        for (int i = 0; i < projectileList.size(); i++) {
            Particle proj1 = projectileList.get(i);
            
            for (int j = i+1; j < projectileList.size(); j++) {
                Particle proj2 = projectileList.get(j);
                boolean collide = isOverlap(proj1, proj2);
            
                if (collide == true) {
                    
                    projectileList.remove(i);
                    projectileList.remove(j-1);
                }
            }
        }
        //grave-projectile
        for (int i = 0; i < graveList.size(); i++) {
            Particle grave = graveList.get(i);
            
            for (int j = 0; j < projectileList.size(); j++) {
                Particle proj = projectileList.get(j);
                boolean collide = isOverlap(grave, proj);
            
                if (collide == true) {

                    projectileList.remove(j);
                }
            }
        }
    }
    
    public boolean isOverlap(Particle p1, Particle p2) {
        int diffX = Math.abs(p1.x - p2.x);
        int diffY = Math.abs(p1.y - p2.y);
        if (diffX < p1.radius + p2.radius && diffY < p1.radius + p2.radius)
            return true;
        else
            return false;
    }
}
