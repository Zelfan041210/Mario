package classes;

import Tools.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class Font {
    private String chars = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
    // 保存原始8x8字体
    private HashMap<Character, BufferedImage> baseCharSprites;
    // 二级缓存: [字符 -> [大小 -> 缩放后的字形]]
    private HashMap<Character, HashMap<Integer, BufferedImage>> scaledCache;
    private SpriteSheet charSheet;

    public Font() throws IOException {
        this.charSheet = new SpriteSheet("font.png");
        this.baseCharSprites = loadBaseFont();
        this.scaledCache = new HashMap<>();
    }

    /** 加载基础8x8字形 */
    private HashMap<Character, BufferedImage> loadBaseFont() throws IOException {
        HashMap<Character, BufferedImage> font = new HashMap<>();
        int row = 0;
        int charAt = 0;

        for (char c : chars.toCharArray()) {
            if (charAt == 16) {
                charAt = 0;
                row++;
            }
            int pixelX = charAt * 8;
            int pixelY = row * 8;
            // 原始不缩放，保持8x8
            BufferedImage charImage = charSheet.imageAt(pixelX, pixelY, 2, new Color(0, 0, 0), true, 8, 8);
            font.put(c, charImage);
            charAt++;
        }
        return font;
    }

    /** 获取指定大小的字形 */
    private BufferedImage getCharImage(char c, int size) {
        if (!baseCharSprites.containsKey(c)) return null;

        // 检查缓存
        scaledCache.putIfAbsent(c, new HashMap<>());
        HashMap<Integer, BufferedImage> sizeMap = scaledCache.get(c);

        if (!sizeMap.containsKey(size)) {
            // 动态缩放
            BufferedImage base = baseCharSprites.get(c);
            Image scaled = base.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            BufferedImage scaledBuffered = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = scaledBuffered.createGraphics();
            g2d.drawImage(scaled, 0, 0, null);
            g2d.dispose();
            sizeMap.put(size, scaledBuffered);
        }

        return sizeMap.get(size);
    }

    /** 绘制文本，使用调用时的 size */
    public void drawText(Graphics g, String text, int x, int y, int size) {
        for (char c : text.toCharArray()) {
            BufferedImage charSprite = getCharImage(c, size);
            if (charSprite != null) {
                g.drawImage(charSprite, x, y, null);
            }
            if (c == ' ') {
                x += size / 2;
            } else {
                x += size;
            }
        }
    }
}
