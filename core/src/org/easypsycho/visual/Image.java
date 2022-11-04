/*******************************************************************************
 * Copyright (C) 2022 Gancheng Zhu
 * org.easypsycho.EasyPsycho is a Free Software project under the GNU Affero General
 * Public License v3, which means all its code is available for everyone 
 * to download, examine, use, modify, and distribute, subject to the usual 
 * restrictions attached to any GPL software. If you are not familiar with the AGPL, 
 * see the COPYING file for for more details on license terms and other legal issues.
 ******************************************************************************/

package org.easypsycho.visual;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import org.easypsycho.EasyPsycho;
import org.easypsycho.misc.Rect;
import org.easypsycho.visual.mask.AlphaMask;


public class Image extends BaseVisual<Image> implements Disposable {
    private String name;
    private String internalPath;
    private Sprite sprite;
    private Texture texture;
    private Texture maskTexture;
    private boolean flipX = false;
    private boolean flipY = false;


    /**
     * In this, construct method only can pass image path and image file handle, position, and size.
     * if you want to modify other attributes like flip, contrast, or opacity, you should use set method of the attributes.
     */
    public Image() {
    }

    public Image(String internalPath) {
        this(internalPath, 0, 0, 0);
    }

    public Image(String internalPath, float posX, float posY) {
        this(internalPath, posX, posY, 0);
    }

    public Image(String internalPath, float posX, float posY, float size) {
        this(internalPath, posX, posY, size, size);
    }

    public Image(String internalPath, float posX, float posY, float sizeX, float sizeY) {
        this(EasyPsycho.files.internal(internalPath), posX, posY, sizeX, sizeY);
        this.internalPath = internalPath;
    }

    public Image(FileHandle fileHandle) {
        this(fileHandle, 0, 0);
    }

    public Image(FileHandle fileHandle, float posX, float posY) {
        this(fileHandle, posX, posY, 0);
    }

    public Image(FileHandle fileHandle, float posX, float posY, float size) {
        this(fileHandle, posX, posY, size, size);
    }

    // size = 0 represents the original size of image.
    public Image(FileHandle fileHandle, float posX, float posY, float sizeX, float sizeY) {
        this.texture = new Texture(fileHandle);
        this.sprite = new Sprite(texture);
        setPosition(posX, posY);
        if (sizeX == 0f && sizeY == 0f) {
            setSize(texture.getWidth(), texture.getHeight());
        } else {
            setSize(sizeX, sizeY);
        }
    }

    @Override
    public void dispose() {
        if (texture != null) texture.dispose();
        if (maskTexture != null) maskTexture.dispose();
    }

    public void drawOnCanvas(Canvas canvas) {
        if (!canvas.getDisposableSet().contains(this)) {
            canvas.add(this);
        }
        if (isShouldUpdate()) {
            updateParameters();
            setShouldUpdate(false);
        }
        canvas.getPsychSpriteBatch().begin();
        canvas.getPsychSpriteBatch().setContrast(getContrast()).setOpacity(getOpacity());
        if (sprite != null && sprite.getTexture() != null && maskTexture != null) {
            canvas.getPsychSpriteBatch().draw(sprite, maskTexture);
        } else if (sprite != null && sprite.getTexture() != null) {
            canvas.getPsychSpriteBatch().draw(sprite);
        }
        canvas.getPsychSpriteBatch().end();
    }



    @Override
    public void updateParameters() {
        sprite.setTexture(texture);
        float tmpX = EasyPsycho.graphics.getWidth() / 2f + getPosX();
        float tmpY = EasyPsycho.graphics.getHeight() / 2f + getPosY();
        switch (getAnchorType()) {
            case CENTER:
                sprite.setX(tmpX - getSizeX() / 2);
                sprite.setY(tmpY - getSizeY() / 2);
                break;
            case LEFT_TOP:
                sprite.setX(tmpX);
                sprite.setY(tmpY);
                break;
            case RIGHT_TOP:
                sprite.setX(tmpX - getSizeX());
                sprite.setY(tmpY);
                break;
            case LEFT_BOTTOM:
                sprite.setX(tmpX);
                sprite.setY(tmpY - getSizeY());
                break;
            case RIGHT_BOTTOM:
                sprite.setX(tmpX - getSizeX());
                sprite.setY(tmpY - getSizeY());
                break;
        }
        sprite.setSize(getSizeX(), getSizeY());
        sprite.rotate(getRotation());
        sprite.setFlip(this.flipX, this.flipY);
    }

    public String getName() {
        return name;
    }

    public Image setName(String name) {
        this.name = name;
        return this;
    }

    public Image setInternalPath(String internalPath) {
        this.internalPath = internalPath;
        return this;
    }

    public String getInternalPath() {
        return this.internalPath;
    }


    public Image setFileHandle(FileHandle fileHandle) {
        texture = new Texture(fileHandle);
        sprite.setTexture(texture);
        return this;
    }

    public Image setTexture(Texture texture) {
        this.texture = texture;
        sprite.setTexture(texture);
        return this;
    }

    public Image setMaskTexture(Texture maskTexture) {
        this.maskTexture = maskTexture;
        return this;
    }

    public Image setMask(float[][] mask) {
        this.texture = new AlphaMask(mask).getTexture();
        sprite.setTexture(this.texture);
        return this;
    }

    public Image setMask(Pixmap pixmap, boolean isDisposePixmap) {
        this.maskTexture = new Texture(pixmap);
        if (isDisposePixmap) {
            pixmap.dispose();
        }
        return this;
    }

    public Image setMask(Pixmap pixmap) {
        return setMask(pixmap, false);
    }

    public Image setMask(FileHandle fileHandle) {
        this.maskTexture = new Texture(fileHandle);
        return this;
    }

    public Image setMask(AlphaMask alphaMask) {
        this.maskTexture = alphaMask.getTexture();
        return this;
    }

    public Image setMask(String maskType) {
        // TODO
//        this.maskTexture = alphaMask.getTexture();
        return this;
    }

    public Image setFlipX(boolean flipX) {
        this.flipX = flipX;
        setShouldUpdate(true);
        return this;
    }

    public Image setFlipY(boolean flipY) {
        this.flipY = flipY;
        setShouldUpdate(true);
        return this;
    }

    public Image setFlip(boolean flipX, boolean flipY) {
        this.flipX = flipX;
        this.flipY = flipY;
        setShouldUpdate(true);
        return this;
    }

    public Texture getTexture() {
        return texture;
    }


    public Texture getMaskTexture() {
        return maskTexture;
    }


    public boolean isFlipX() {
        return flipX;
    }


    public boolean isFlipY() {
        return flipY;
    }

}
