package com.makarov.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {

    private Color[][] colors;

    public Image(String filePath) {
        this.colors = Images.getColors(readImage(filePath));
    }

    private BufferedImage readImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setRGB(int x, int y, int rgb) {
        int red = (rgb >> 16) & 0x000000FF;
        int green = (rgb >> 8) & 0x000000FF;
        int blue = (rgb) & 0x000000FF;
        setRed(x, y, red);
        setGreen(x, y, green);
        setBlue(x, y, blue);
    }

    public int getRGB(int x, int y) {
        return (colors[x][y].getRed() << 16) + (colors[x][y].getGreen() << 8) + colors[x][y].getBlue();
    }

    public void setRed(int x, int y, int red) {
        colors[x][y].setRed(red);
    }

    public void setGreen(int x, int y, int green) {
        colors[x][y].setGreen(green);
    }

    public void setBlue(int x, int y, int blue) {
        colors[x][y].setBlue(blue);
    }

    public int getRed(int x, int y) {
        return colors[x][y].getRed();
    }

    public int getGreen(int x, int y) {
        return colors[x][y].getGreen();
    }

    public int getBlue(int x, int y) {
        return colors[x][y].getBlue();
    }

    public int getWidth() {
        return colors[0].length;
    }

    public int getHeight() {
        return colors.length;
    }

    public void save(String imageName, String format) {
        Images.createNewImage(colors, imageName, format);
    }

    public void save(String imageName) {
        Images.createNewImage(colors, imageName);
    }

    public Color[][] getColors() {
        return colors;
    }
}
