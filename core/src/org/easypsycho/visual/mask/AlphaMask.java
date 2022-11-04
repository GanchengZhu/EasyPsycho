/*******************************************************************************
 * Copyright (C) 2022 Gancheng Zhu
 * org.easypsycho.EasyPsycho is a Free Software project under the GNU Affero General
 * Public License v3, which means all its code is available for everyone 
 * to download, examine, use, modify, and distribute, subject to the usual 
 * restrictions attached to any GPL software. If you are not familiar with the AGPL, 
 * see the COPYING file for for more details on license terms and other legal issues.
 ******************************************************************************/
package org.easypsycho.visual.mask;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;
import java.nio.ByteBuffer;

public class AlphaMask {
    private Texture texture;

    public AlphaMask(float[][] mask) {
        genTexture(mask);
    }

    public AlphaMask(FileHandle maskFileHandle) {
        this.texture = new Texture(maskFileHandle);
    }

    private void genTexture(float[][] mask) {
        int width = mask[1].length;
        int height = mask.length;
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.Alpha);
        ByteBuffer byteBuffer = BufferUtils.newByteBuffer(width * height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                byteBuffer.put(i * height + j, (byte) ((mask[i][j] + 1) * 255 / 2));
            }
        }
        byteBuffer.flip();
        pixmap.setPixels(byteBuffer);
        this.texture = new Texture(pixmap);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
