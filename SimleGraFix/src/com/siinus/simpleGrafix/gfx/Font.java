package com.siinus.simpleGrafix.gfx;

import java.util.ArrayList;

public class Font {
    private static Font STANDARD = new Font("/arial16.png",16, 19);
    public static final Font ARIAL16 = new Font("/arial16.png",16, 19);
    //public static final Font ARIAL32 = new Font("/arial32.png",32, 37);
    //public static final Font ALGERIAN32 = new Font("/algerian32.png",32, 37);

    private static ArrayList<String> fonts = new ArrayList<>();

    private final int chars;
    private final int size;
    private final int imageSize;
    private final char startChar;

    private final Image fontImage;
    private final int[] offsets;
    private final int[] widths;

    static {
        fonts.add("arial16");
    }

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
