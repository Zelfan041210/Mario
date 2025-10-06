package main.java.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.io.*;
import java.net.URL;

public class SpriteSheetLoader {
    private BufferedImage spriteSheet;
    private int tileWidth, tileHeight;
    private String jsonPath;

    // 修改构造方法，支持通过类路径加载资源
    public SpriteSheetLoader(String jsonPath) throws IOException {
        this.jsonPath = jsonPath;
        try {
            // 使用类路径加载 JSON 文件
            URL jsonURL = getClass().getResource("/sprites/" + jsonPath);
            if (jsonURL == null) {
                throw new FileNotFoundException("JSON file not found: " + jsonPath);
            }

            // 通过 InputStream 读取 JSON 文件
            InputStream inputStream = jsonURL.openStream();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(inputStream);

            // 加载 spriteSheet 图片

            String spriteSheetURL = root.get("spriteSheetURL").asText();


            InputStream inputImage = getClass().getClassLoader().getResourceAsStream(spriteSheetURL);
            spriteSheet = ImageIO.read(inputImage);
            if (spriteSheet == null) {
                throw new IOException("Failed to load sprite sheet: " + spriteSheetURL);
            } else {
                System.out.println("Sprite sheet loaded successfully!");
            }

            // 默认大小
            tileWidth = 16;
            tileHeight = 16;

            // 获取小图的大小
            JsonNode sizeNode = root.get("size");
            if (sizeNode != null && sizeNode.isArray() && sizeNode.size() >= 2) {
                tileWidth = sizeNode.get(0).asInt(tileWidth);
                tileHeight = sizeNode.get(1).asInt(tileHeight);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 修改 loadSprites 方法，使用类路径加载 JSON 文件
    public Map<String, BufferedImage> loadSprites() {
        Map<String, BufferedImage> sprites = new HashMap<>();

        try {
            // 使用类路径加载 JSON 文件
            URL jsonURL = getClass().getResource("/sprites/" + jsonPath);
            if (jsonURL == null) {
                throw new FileNotFoundException("JSON file not found: " + jsonPath);
            }

            System.out.println("Now loading " + jsonPath);

            InputStream inputStream = jsonURL.openStream();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(inputStream);

            JsonNode spriteNode = root.get("sprites");


            String type = root.has("type") ? root.get("type").asText() : "character";

            for (JsonNode sprite : spriteNode) {
                String name = sprite.get("name").asText();
                int x = sprite.get("x").asInt();
                int y = sprite.get("y").asInt();
                int scale =  sprite.get("scalefactor").asInt();

                if ("background".equals(type)) {
                    x = x * tileWidth;
                    y = y * tileHeight;
                }



                BufferedImage frame = spriteSheet.getSubimage(x, y, tileWidth, tileHeight);



                BufferedImage newImage = new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_ARGB);



                Graphics2D g2d = newImage.createGraphics();
                g2d.drawImage(frame, 0, 0, null);  // 将原图绘制到新图上
                g2d.dispose();
                frame = newImage;



                // 如果需要放大
                if (scale > 1) {
                    BufferedImage scaled = new BufferedImage(tileWidth * scale, tileHeight * scale, frame.getType());
                    scaled.getGraphics().drawImage(frame, 0, 0, tileWidth * scale, tileHeight * scale, null);
                    frame = scaled;
                }

                sprites.put(name, frame);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sprites;
    }
}
