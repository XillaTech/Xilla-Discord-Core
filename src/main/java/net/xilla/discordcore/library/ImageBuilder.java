package net.xilla.discordcore.library;

import lombok.Getter;
import net.dv8tion.jda.api.entities.TextChannel;
import net.xilla.discordcore.DiscordCore;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ImageBuilder {

    /**
     * Used to store the width of the image
     */
    @Getter
    private int width;

    /**
     * Used to store the height of the image
     */
    @Getter
    private int height;

    /**
     * 2D Graphics image used for editing
     */
    @Getter
    private Graphics2D g2d;

    /**
     * BufferedImage used for exporting and sending to discord
     */
    @Getter
    private BufferedImage bufferedImage;

    /**
     * Takes the width and height to create an image builder that
     * uses a Graphics2D and BufferedImage to send an image over
     * discord.
     *
     * @param width Image Width
     * @param height Image Height
     */
    public ImageBuilder(int width, int height) {

        // Constructs a BufferedImage of one of the predefined image types.
        this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.g2d = bufferedImage.createGraphics();

        // Setting quality to high by default
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }

    /**
     * Used to load a pre-made groph
     *
     * @param points List of points
     */
    public void loadGraph(List<Long> points) {
        int header = 50;

        // fill all the image with white
        g2d.setColor(Color.decode(DiscordCore.getInstance().getCoreSetting().getEmbedColor()));
        g2d.fillRect(0, 0, width, header - 5);
        g2d.setColor(Color.decode("#f0f0f0"));
        g2d.fillRect(0, header - 5, width, height - header + 5);
        g2d.setFont(new Font("Arial", Font.PLAIN, 25));

        g2d.setColor(Color.white);
        g2d.drawString("Test Graph (Points: " + points.size() + ") (Top: " + points.get(points.size() - 1) + ")", 10, 30);

        // Create a graphics which can be used to draw into the buffered image
        long yoffset = 0;

        long highest = -1;
        long lowest = 0;
        for(Long x : points) {
            if (x > highest)
                highest = x;
            if (x < lowest)
                lowest = x;
        }

        if(lowest < 0) {
            yoffset = (0 - lowest);
        }
        highest -= lowest;

        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.decode("#1a1a1a"));
        for(int i = 0; i < points.size() - 1; i++) {
            Long x1 = points.get(i);
            Long x2 = points.get(i + 1);
            g2d.drawLine(
                    (i * (width / (points.size() - 1) + 1)), // x1
                    (int)(height - ((x1 + yoffset) * (((height - header) / highest)))), // y1
                    ((i + 1) * (width / (points.size() - 1) + 1)), // x2
                    (int)(height - ((x2 + yoffset) * (((height - header) / highest))))); // y2
        }

    }

    /**
     * Used to send the graph to a text chanel and clear it from the memory.
     *
     * @param channel Discord Channel
     */
    public void build(TextChannel channel) {
        // Disposes of this graphics context and releases any system resources that it is using.
        g2d.dispose();

        // Save as PNG
        Path path = Paths.get("myimage.png");
        try {
            ImageIO.write(bufferedImage, "png", path.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        channel.sendFile(path.toFile()).queue();
    }

}
