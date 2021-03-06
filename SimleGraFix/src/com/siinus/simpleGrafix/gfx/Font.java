package com.siinus.simpleGrafix.gfx;

import java.util.ArrayList;

/**
 * Represents a font that can be rendered with this api
 *
 * @author Simon
 * @since 1.0
 */
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

    private final com.siinus.simpleGrafix.gfx.Image fontImage;
    private final int[] offsets;
    private final int[] widths;

    static {
        fonts.add("arial16");
    }

    /**
     * Creates a new font from the given font image.<br>
     * The font image has to be in the right format. Please use the font image generator to create a new one.<br>
     * The values for chars and start char are set to match a font generated by the font generator.<br>
     * If you made a font on your own, use {@link Font#Font(String, int, int, int, char)}.
     *
     * @param path The path to the font image
     * @param size The size of the font
     * @param imageSize The size of the font image
     */
    public Font(String path, int size, int imageSize) {
        this(path, 0x100, size, imageSize, '\0');
    }

    /**
     * Creates a new font from the given font image.<br>
     * The font image has to be in the right format. Please use the font image generator to create a new one.<br>
     * If you used the font generator, you can use the constructor {@link Font#Font(String, int, int)} for default parameters.<br>
     *
     * @param path The path to the font image
     * @param chars The number of chars
     * @param size The size of the font
     * @param imageSize The size of the font image
     * @param startChar The first character on this font image
     */
    public Font(String path, int chars, int size, int imageSize, char startChar) {
        this.chars = chars;
        this.size = size;
        this.imageSize = imageSize;
        this.startChar = startChar;

        fontImage = new com.siinus.simpleGrafix.gfx.Image(path);

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

    /**
     * Returns the standard font (Arial 16 if not changed)
     *
     * @return The standard font
     */
    public static Font getStandard() {
        return STANDARD;
    }

    /**
     * Sets the new standard font that will be used if no special font is provided.
     *
     * @param standard The new standard font
     */
    public static void setStandard(Font standard) {
        STANDARD = standard;
    }

    public int getPixelsOfText(String text) {
        int l = 0;
        for (char c : text.toCharArray()) {
            if (size>c) {
                continue;
            }
            l += widths[c-startChar];
            l++;
        }
        return l;
    }
}
