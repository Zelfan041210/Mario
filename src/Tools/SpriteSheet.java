package Tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SpriteSheet {
    private BufferedImage spritesheet;
    private String path;

    public SpriteSheet(String path) throws IOException {
        spritesheet = ImageIO.read(new File("src/img/" + path));
        this.path = path;
    }

    public BufferedImage imageAt(int x, int y, int scale, Color colorkey,boolean ignoreTileSize,int xTileSize,int yTileSize) throws IOException {
        BufferedImage sub;
        if(ignoreTileSize){
            sub = spritesheet.getSubimage(x,y,xTileSize,yTileSize);
        }else {
            sub = spritesheet.getSubimage(x,y,32,32);
        }
        BufferedImage copy = new BufferedImage(sub.getWidth(), sub.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = copy.createGraphics();
        g.drawImage(sub, 0, 0, null);
        g.dispose();
        sub = copy;

        //deal with transparency
        if(colorkey!=null){
            BufferedImage newImage = new BufferedImage(sub.getWidth(),sub.getHeight(),BufferedImage.TYPE_INT_ARGB);
            for(int i=0;i<sub.getWidth();i++){
                for(int j=0;j<sub.getHeight();j++){
                    int rgb = sub.getRGB(i,j);
                    if((rgb & 0x00FFFFFF) == (colorkey.getRGB() & 0x00FFFFFF)){
                        newImage.setRGB(i, j, 0x00000000); // 设置完全透明
                    } else {
                        newImage.setRGB(i, j, rgb);
                    }
                }
            }
            sub = newImage;
        }

        //scale
        if(scale != 1){
            int w = sub.getWidth()*scale;
            int h = sub.getHeight()*scale;
            Image tmp = sub.getScaledInstance(w,h,Image.SCALE_SMOOTH);
            BufferedImage scaled = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = scaled.createGraphics();
            g2d.drawImage(tmp,0,0,null);
            g2d.dispose();
            return scaled;
        }

        return sub;
    }
}
