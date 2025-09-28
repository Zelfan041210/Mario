package classes;

import java.awt.image.BufferedImage;
import java.util.List;


public class Animation {
    private List<BufferedImage> frames;
    private int currentIndex = 0;
    private long lastTime;
    private int delay;

    public Animation(List<BufferedImage> frames, int delay) {
        this.frames = frames;
        this.delay = delay;
        this.lastTime = System.currentTimeMillis();
    }

    public void update(){
        long now = System.currentTimeMillis();
        if(now - lastTime > delay){
            currentIndex = (currentIndex + 1) % frames.size();
            lastTime = now;
        }
    }

    public BufferedImage getCurrentFrame(){
        return frames.get(currentIndex);
    }

    public void reset(){
        currentIndex = 0;
    }
}
