package main.java.classes;

import main.java.tools.SpriteSheet;
import main.java.tools.SpriteSheetLoader;
import main.java.state.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Menu {
    private SpriteSheet menuSpriteSheet = new SpriteSheet("title_screen.png");
    private Map<String, BufferedImage> spriteMap1, spriteMap2, spriteMap3;
    private BufferedImage skyTile, groundTile, banner, Dot, Dot2, bush1, bush2, bush3, mario, goomba;
    private GameState gameState;
    private int state = 0;
    private Font font;
    private boolean inChoosingLevel = false;
    private int CurrenSelectedLevel = 1;


    public Menu() throws IOException {

        SpriteSheetLoader loader1 = new SpriteSheetLoader("BackgroundSprites.json");
        spriteMap1 = loader1.loadSprites();


        SpriteSheetLoader loader2 = new SpriteSheetLoader("Mario.json");
        spriteMap2 = loader2.loadSprites();

        SpriteSheetLoader loader3 = new SpriteSheetLoader("Goomba.json");
        spriteMap3 = loader3.loadSprites();


        banner = menuSpriteSheet.imageAt(0, 60, 2, new Color(255, 0, 220),
                true, 180, 88);
        Dot = menuSpriteSheet.imageAt(0, 150, 2, new Color(255, 0, 220),
                true, 16, 16);
        Dot2 = menuSpriteSheet.imageAt(20, 150, 2, new Color(255, 0, 220),
                true, 16, 16);

        System.out.println(Integer.toHexString(Dot.getRGB(0, 0) & 0x00FFFFFF));
        skyTile = spriteMap1.get("sky");
        groundTile = spriteMap1.get("ground");
        bush1 = spriteMap1.get("bush1");
        bush2 = spriteMap1.get("bush2");
        bush3 = spriteMap1.get("bush3");

        mario = spriteMap2.get("mario_idle");
        goomba = spriteMap3.get("goomba-1");

        initFont();


    }

    public void initFont() throws IOException {
        font = new Font();
    }

    public void drawDot(Graphics g) {
        if (state == 0) {
            g.drawImage(Dot, 145, 273, null);
            g.drawImage(Dot2, 145, 313, null);
            g.drawImage(Dot2, 145, 353, null);
        } else if (state == 1) {
            g.drawImage(Dot, 145, 313, null);
            g.drawImage(Dot2, 145, 273, null);
            g.drawImage(Dot2, 145, 353, null);
        } else if (state == 2) {
            g.drawImage(Dot, 145, 353, null);
            g.drawImage(Dot2, 145, 273, null);
            g.drawImage(Dot2, 145, 313, null);
        }
    }

    public void draw_menu(Graphics g) throws IOException {
        drawDot(g);

        font.drawText(g, "CHOOSE LEVEL", 180, 280,24);
        font.drawText(g, "SETTINGS", 180, 320,24);
        font.drawText(g, "EXIT", 180, 360,24);
    }

    public void draw_menubackground(Graphics g, boolean withBanner) {
        //draw sky
        for (int y = 0; y < 13; y++) {
            for (int x = 0; x < 20; x++) {
                g.drawImage(skyTile, x * 32, y * 32, 32, 32, null);
            }
        }
        //draw ground
        for (int y = 13; y < 15; y++) {
            for (int x = 0; x < 20; x++) {
                g.drawImage(groundTile, x * 32, y * 32, 32, 32, null);
            }
        }
        if (withBanner) {
            g.drawImage(banner, 150, 80, null);
        }
        g.drawImage(mario, 2 * 32, 12 * 32, null);

        g.drawImage(bush1, 14 * 32, 12 * 32, null);
        g.drawImage(bush2, 15 * 32, 12 * 32, null);
        g.drawImage(bush2, 16 * 32, 12 * 32, null);
        g.drawImage(bush2, 17 * 32, 12 * 32, null);
        g.drawImage(bush3, 18 * 32, 12 * 32, null);

        g.drawImage(goomba, 592, 12 * 32, null);

    }

    public void drawborder(Graphics g,int x,int y,int width,int height,Color color,int thickness) {
        g.setColor(color);
        g.fillRect(x, y, width, thickness);
        g.fillRect(x, y + height - thickness, width, thickness);
        g.fillRect(x, y, thickness, height);
        g.fillRect(x + width - thickness, y, thickness, height);
    }
    public List<String> loadLevelNames(){
        List<String> res = new ArrayList<>();
        File dir = new File("src/main/resources/levels");
        File[] files = dir.listFiles();
        if (files == null) {
            System.out.println("No levels found");
        }
        if(files!=null){
            Arrays.sort(files, (file1, file2) -> file1.getName().compareTo(file2.getName()));
            for (File file : files) {
                if(file.isFile()){
                    String name = file.getName();
                    int dotIndex = name.indexOf(".");
                    if(dotIndex > 0)
                        name = name.substring(0, dotIndex);
                    res.add(name);
                }

            }
        }
        return res;
    }

    public void drawLevelChooser(Graphics g) {
        int offset = 75;
        int j = 0;
        int textOffset = 90;
        List<String> levelNames = loadLevelNames();
        for (int i = 0; i < levelNames.size(); i++) {
            String levelName = levelNames.get(i);
            Color color = (i == CurrenSelectedLevel - 1) ? Color.WHITE : new Color(150, 150, 150);

            if (i < 3) {
                font.drawText(g, levelName, textOffset + i * 175, 100, 12);
                drawborder(g, offset + i * 175, 55, 125, 75, color, 5);
            } else {
                font.drawText(g, levelName, textOffset + (i-3) * 175, 250, 12);
                drawborder(g, offset + (i-3) * 175, 210, 125, 75, color, 5);
            }
        }

    }




    public boolean getInChoosingLevel() {
        return inChoosingLevel;
    }
    public void setInChoosingLevel(boolean inChoosingLevel) {
        this.inChoosingLevel = inChoosingLevel;
    }
    public int getstate(){
        return state;
    }
    public void setState(int state){
        this.state = state;
    }
    public int getCurrenSelectedLevel(){
        return CurrenSelectedLevel;
    }
    public void setCurrenSelectedLevel(int currenSelectedLevel){
        CurrenSelectedLevel = currenSelectedLevel;
    }
}



