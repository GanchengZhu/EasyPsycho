/*******************************************************************************
 * Copyright (C) 2022 Gancheng Zhu
 * org.easypsycho.EasyPsycho is a Free Software project under the GNU Affero General
 * Public License v3, which means all its code is available for everyone
 * to download, examine, use, modify, and distribute, subject to the usual
 * restrictions attached to any GPL software. If you are not familiar with the AGPL,
 * see the COPYING file for for more details on license terms and other legal issues.
 ******************************************************************************/

package org.easypsycho.visual;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import org.easypsycho.misc.AnchorType;
import org.easypsycho.misc.Rect;

/**
 * Base class of all visual stimuli
 *
 * @param <T> Specific Class which extends BaseVisual
 */
public abstract class BaseVisual<T extends BaseVisual> {
    private float posX; // Position X of stimulus. Unit is pixel and the center of the screen is (0, 0) of Cartesian grids
    private float posY; // Position Y of stimulus. See posX
    private float sizeX; // X size or width of stimulus. Unit is pixel.
    private float sizeY; // Y size or height of stimulus. Unit is pixel.
    private float opacity = 1;  // opacity of stimulus is between 0 and 1, and default of 1
    private float rotation = 0; // rotation of stimulus, default of 0. Unit is degree
    private float contrast = 1; // contrast of stimulus is between 0 and 2, and default of 1
    private AnchorType anchorType = AnchorType.CENTER; // position layout type of stimulus
    private boolean shouldUpdate;


    public T setPosX(float posX) {
        this.posX = posX;
        shouldUpdate = true;
        return (T) this;
    }

    public T setPosY(float posY) {
        this.posY = posY;
        shouldUpdate = true;
        return (T) this;
    }

    public T setPosition(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        shouldUpdate = true;
        return (T) this;
    }

    public T setSizeX(float sizeX) {
        if (sizeX < 0) {
            throw new GdxRuntimeException("Unexpected value: Value sizeX cannot be less than 0, but given parameter is " + sizeX + ".");
        }
        this.sizeX = sizeX;
        shouldUpdate = true;
        return (T) this;
    }

    public T setSizeY(float sizeY) {
        if (sizeY < 0) {
            throw new GdxRuntimeException("Unexpected value: Value sizeY cannot be less than 0, but given parameter is " + sizeY + ".");
        }
        this.sizeY = sizeY;
        shouldUpdate = true;
        return (T) this;
    }

    public T setSize(float sizeX, float sizeY) {
        if (sizeX < 0 || sizeY < 0) {
            throw new GdxRuntimeException("Unexpected value: Values sizeX and sizeY cannot be less than 0, but given parameters are " + sizeX + " and " + sizeY + ".");
        }
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        shouldUpdate = true;
        return (T) this;
    }

    public T setSize(float size) {
        if (size < 0) {
            throw new GdxRuntimeException("Value size cannot be less than 0");
        }
        this.sizeX = size;
        this.sizeY = size;
        shouldUpdate = true;
        return (T) this;
    }


    public T setAnchorType(AnchorType anchorType) {
        this.anchorType = anchorType;
        shouldUpdate = true;
        return (T) this;
    }

    public T setRotation(float rotation) {
        this.rotation = rotation;
        shouldUpdate = true;
        return (T) this;
    }

    public T setShouldUpdate(boolean shouldUpdate) {
        this.shouldUpdate = shouldUpdate;
        return (T) this;
    }

    public T setOpacity(float opacity) {
        if (opacity < 0 || opacity > 1) {
            throw new GdxRuntimeException("Unexpected value: Value opacity cannot be less than 0 or greater than 1, but given parameters is " + opacity + ".");
        }
        this.opacity = opacity;
        return (T) this;
    }


    public float getSizeX() {
        return sizeX;
    }


    public float getSizeY() {
        return sizeY;
    }


    public float getRotation() {
        return rotation;
    }


    public AnchorType getAnchorType() {
        return anchorType;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getOpacity() {
        return opacity;
    }

    public boolean isShouldUpdate() {
        return shouldUpdate;
    }

    public abstract void dispose();

    public boolean overlaps(BaseVisual baseVisual) {
        Rect rect = baseVisual.getRect();
        return overlaps(rect, getRect());
    }

    private boolean overlaps(Rect rect1, Rect rect2) {
        return rect1.x + rect1.width > rect2.x && rect2.x + rect2.width > rect1.x && rect1.y + rect1.height > rect2.y && rect2.y + rect2.height > rect1.y;
    }

    public Rect getRect() {
        return new Rect(getPosX(), getPosY(), getSizeX(), getSizeY());
    }

    public abstract void updateParameters();

    public boolean contains(float x, float y) {
        Rect rect = getRect();
        return x < (rect.x + rect.width) && x > rect.x && y < (rect.y + rect.height) && y > rect.y;
    }

    public float getContrast() {
        return contrast;
    }

    public void setContrast(float contrast) {
        this.contrast = contrast;
    }
}
