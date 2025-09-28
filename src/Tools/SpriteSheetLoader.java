package Tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SpriteSheetLoader {
    private BufferedImage spriteSheet;
    private int tileWidth, tileHeight;
    public SpriteSheetLoader(String jsonPath) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File(jsonPath));

            //load the big png
            String spriteSheetURL = root.get("spriteSheetURL").asText();

            spriteSheet = ImageIO.read(new File(spriteSheetURL));
            if (spriteSheet == null) {
                throw new IOException("Failed to load sprite sheet: " + spriteSheetURL);
            }

            //default size
            tileWidth = 16;
            tileHeight = 16;

            //Size of each small picture
            JsonNode sizeNode = root.get("size");
            if (sizeNode != null && sizeNode.isArray() && sizeNode.size() >= 2) {
                tileWidth = sizeNode.get(0).asInt(tileWidth); // 如果读取失败仍用默认值
                tileHeight = sizeNode.get(1).asInt(tileHeight);
            }


        }catch(Exception e){
            e.printStackTrace();
        }


    }
    public Map<String,BufferedImage> loadSprites(String jsonPath){
        Map<String,BufferedImage> sprites = new HashMap<>();
        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File(jsonPath));

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
                g2d.drawImage(frame, 0, 0, null);  // 把原图绘制到新图上
                g2d.dispose();
                frame = newImage;
                //If enlarge needed
                if (scale > 1){
                    BufferedImage scaled = new BufferedImage(tileWidth *scale, tileHeight*scale, frame.getType());
                    scaled.getGraphics().drawImage(frame, 0, 0, tileWidth * scale, tileHeight * scale, null);
                    frame = scaled;
                }
                sprites.put(name,frame);

            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return sprites;

    }
}
