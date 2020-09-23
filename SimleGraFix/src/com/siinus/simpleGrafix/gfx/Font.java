package com.siinus.simpleGrafix.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Font {
    private static Font STANDARD = new Font("/arial16.png",16, 19);
    public static final Font ARIAL16 = new Font("/arial16.png",16, 19);
    public static final Font ARIAL32 = new Font("/arial32.png",32, 37);
    public static final Font ALGERIAN32 = new Font("/algerian32.png",32, 37);

    private int chars;
    private int size;
    private int imageSize;
    private char startChar;

    private Image fontImage;
    private int[] offsets;
    private int[] widths;

    public Font(String path, int size, int imageSize) {
        this(path, 0x100, size, imageSize, '\0');
    }

    public Font(String path, int chars, int size, int imageSize, char startChar) {
        this.chars = chars;
        this.size = size;
        this.imageSize = imageSize;
        this.startChar = startChar;

        fontImage = new Image(path);

        offsets = new int[chars];
        widths = new int[chars];

        int unicode = 0;

        for (int i=0; i<fontImage.getWidth(); i++) {
            if (fontImage.getPixels()[i]==0xff0000ff) {
                offsets[unicode] = i;
            }
            if (fontImage.getPixels()[i]==0xffffff00) {
                widths[unicode] = i-offsets[unicode];
                unicode++;
            }
        }
    }

    private BufferedImage resize(BufferedImage srcimage, int new_width, int new_height) {
        BufferedImage resizedImage = new BufferedImage(new_width, new_height, srcimage.getType());
        Graphics2D g2 = resizedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcimage, 0, 0, new_width, new_height, null);
        g2.dispose();

        return resizedImage;
    }

    public int getChars() {
        return chars;
    }

    public int getSize() {
        return size;
    }

    public int getImageSize() {
        return imageSize;
    }

    public char getStartChar() {
        return startChar;
    }

    public Image getFontImage() {
        return fontImage;
    }

    public int[] getWidths() {
        return widths;
    }

    public int[] getOffsets() {
        return offsets;
    }

    public static Font getStandard() {
        return STANDARD;
    }

    public static void setStandard(Font standard) {
        STANDARD = standard;
    }
}
