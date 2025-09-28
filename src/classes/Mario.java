package classes;

import Tools.SpriteSheetLoader;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;


public class Mario {
    private int x,y;//location
    private int speed = 5; //speed
    private Map<String, BufferedImage> spriteMap;
    private Animation runAnimation;
    private Animation idleAnimation;
    private Animation currentAnimation;

    private boolean facingLeft = false;
    private boolean moving = false;


    public Mario(int startX, int startY) throws IOException {
        this.x = startX;
        this.y = startY;

        SpriteSheetLoader loader = new SpriteSheetLoader("src/sprites/Mario.json");
        spriteMap = loader.loadSprites("src/sprites/Mario.json");

        //run animation
        this.runAnimation = new Animation(
                Arrays.asList(
                        spriteMap.get("mario_run1"),
                        spriteMap.get("mario_run2"),
                        spriteMap.get("mario_run3")
                ),150
        );
        this.idleAnimation = new Animation(
                Arrays.asList(spriteMap.get("mario_idle")),
                300  // 延迟多少随便定，因为就一帧
        );

        this.currentAnimation = idleAnimation;


    }
    public void moveLeft(){
        x -= speed;
        moving = true;
        currentAnimation = runAnimation;
    }

    public void moveRight(){
        x += speed;
        moving  = true;
        currentAnimation = runAnimation;
    }

    public void stop(){
        moving = false;
        currentAnimation = idleAnimation;
        currentAnimation.reset();
    }

    public void update(){
        if(currentAnimation != null)
            currentAnimation.update();
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        BufferedImage img = currentAnimation.getCurrentFrame();

        AffineTransform at = new AffineTransform();

        if(facingLeft){
            at.translate(x+img.getWidth(),y);
            at.scale(-1,1);
        } else{
            at.translate(x,y);
        }

        g2d.drawImage(img,at,null);
    }

    public void setFacingleft(Boolean facingLeft){
        this.facingLeft = facingLeft;
    }


}
