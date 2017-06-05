package com.makarov.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

public class Images {

    public static Color[][] getColors(BufferedImage image) {
        int height = image.getHeight();
        int width = image.getWidth();

        Color[][] colors = new Color[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);
                int red = (rgb >> 16) & 0x000000FF;
                int green = (rgb >> 8) & 0x000000FF;
                int blue = (rgb) & 0x000000FF;
                colors[x][y] = new Color(red, green, blue);
            }
        }

        return colors;
    }

    public static int[][] getColors(Color[][] colors) {
        int height = colors[0].length;
        int width = colors.length;

        int[][] pixels = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = colors[i][j].toRGB();
            }
        }

        return pixels;
    }

    public static void createNewImage(Color[][] colors, String imageName, String format) {
        int height = colors[0].length;
        int width = colors.length;

        int[][] pixels = getColors(colors);

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                newImage.setRGB(x, y, pixels[x][y]);
            }
        }

        File outfile = new File(imageName + "." + format);
        try {
            ImageIO.write(newImage, format, outfile);
        } catch (IOException exception) {
            //do nothing
        }
    }

    public static void createNewImage(BufferedImage image, String imageName, String format) {
        createNewImage(getColors(image), imageName, format);
    }

    public static void createNewImage(BufferedImage image, String imageName) {
        createNewImage(getColors(image), imageName, "png");
    }

    public static void createNewImage(Color[][] colors, String imageName) {
        createNewImage(colors, imageName, "png");
    }

    public static void toMonochrome(Image image) {
        forAll(image, new ImageRedactor() {
            @Override
            public void redact(Image image, int x, int y) {
                int rgb = (image.getRed(x, y) + image.getGreen(x, y) + image.getBlue(x, y)) / 3;
                image.setRed(x, y, rgb);
                image.setGreen(x, y, rgb);
                image.setBlue(x, y, rgb);
            }
        });
    }

    public static void forAll(Image image, ImageRedactor redactor) {
        for (int x = 0; x < image.getHeight(); x++) {
            for (int y = 0; y < image.getWidth(); y++) {
                redactor.redact(image, x, y);
            }
        }
    }

    public static int getMiddleColor(Image image) {
        BigInteger integer = BigInteger.ZERO;
        for (int x = 0; x < image.getHeight(); x++) {
            for (int y = 0; y < image.getWidth(); y++) {
                integer = integer.add(BigInteger.valueOf(image.getRGB(x, y)));
            }
        }

        integer = integer.divide(BigInteger.valueOf(image.getWidth() * image.getHeight()));

        return integer.intValue();
    }

    public static void middlingImage(Image image) {
        final int middleColor = Images.getMiddleColor(image);
        Images.forAll(image, new ImageRedactor() {
            @Override
            public void redact(Image image, int x, int y) {
                int rgb = image.getRGB(x, y);
                if (rgb > middleColor) {
                    image.setRGB(x, y, 0xFFFFFF);
                } else {
                    image.setRGB(x, y, 0);
                }
            }
        });
    }
}
