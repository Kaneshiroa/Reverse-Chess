package StartGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
    private Map<String, ImageIcon> pieceIcons;

    public ImageLoader(String path, int targetSize) {
        pieceIcons = new HashMap<>();
        loadSprites(path, targetSize);
    }

    private void loadSprites(String path, int size) {
        try {
            // Load the 2000x667 image
            BufferedImage spriteSheet = ImageIO.read(new File(path));

            // Math based on your specific PNG dimensions
            int width = spriteSheet.getWidth() / 6;  // ~333 pixels
            int height = spriteSheet.getHeight() / 2; // ~333 pixels

            // Order must match the image: K, Q, B, N, R, P
            String[] types = {"king", "queen", "bishop", "knight", "rook", "pawn"};
            String[] colors = {"white", "black"};

            for (int row = 0; row < 2; row++) {
                for (int col = 0; col < 6; col++) {
                    // Snip the piece out
                    BufferedImage sub = spriteSheet.getSubimage(col * width, row * height, width, height);

                    // Scale it to fit your JButton (70-80 is usually good for 800x800 frames)
                    Image scaled = sub.getScaledInstance(size, size, Image.SCALE_SMOOTH);

                    // Store with key (e.g., "white_king")
                    String key = colors[row] + "_" + types[col];
                    pieceIcons.put(key, new ImageIcon(scaled));
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Could not find sprite sheet at " + path);
            e.printStackTrace();
        }
    }

    /**
     * Call this from your GameWindow to get the icon for a piece
     */
    public ImageIcon getIcon(String color, String type) {
        String key = color.toLowerCase() + "_" + type.toLowerCase();
        return pieceIcons.get(key);
    }
}