package com.siinus.simpleGrafix.gfx;

import com.siinus.simpleGrafix.Renderer;

/**
 * Represents an image that can be rendered partially by this api using {@link Renderer#drawImageTile(ImageTile, int, int, int, int)}.<br>
 * This can be used to make animations or objects that use different image at different states.<br>
 * Also, this extends the normal {@link com.siinus.simpleGrafix.gfx.Image}, so it can also be drawn using {@link Renderer#drawImage(com.siinus.simpleGrafix.gfx.Image, int, int)}.
 *
 * @author Simon
 * @since 1.0
 */
public class ImageTile extends Image {
    private final int tileWidth, tileHeight;

    /**
     * Creates a new tile image.
     *
     * @param path The path to the texture
     * @param tileWidth The width of a tile (image part)
     * @param tileHeight The height of a tile (image part)
     */
    public ImageTile(String path, int tileWidth, int tileHeight) {
        super(path);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    /**
     * Returns the width of the tile images.
     *
     * @return The tile width
     */
    public int getTileWidth() {
        return tileWidth;
    }

    /**
     * Returns the height of the tile images.
     *
     * @return The tile height
     */
    public int getTileHeight() {
        return tileHeight;
    }
}
