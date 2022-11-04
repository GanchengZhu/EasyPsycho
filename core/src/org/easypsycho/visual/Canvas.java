/*******************************************************************************
 * Copyright (C) 2022 Gancheng Zhu
 * org.easypsycho.EasyPsycho is a Free Software project under the GNU Affero General
 * Public License v3, which means all its code is available for everyone 
 * to download, examine, use, modify, and distribute, subject to the usual 
 * restrictions attached to any GPL software. If you are not familiar with the AGPL, 
 * see the COPYING file for for more details on license terms and other legal issues.
 ******************************************************************************/
package org.easypsycho.visual;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Disposable;
import org.easypsycho.EasyPsycho;
import org.easypsycho.misc.Color;

import java.util.HashSet;
import java.util.Set;

/**
 * A {@code Canvas} component is an area of the screen or the entire screen onto
 * which stimulus can be drawn.
 * <p>
 * This component includes custom SpriteBatches. It will be automatically created by
 * {@link org.easypsycho.ExperimentAdapter} at the experiment starting. You need
 * recycle stimuli objects drawn and SpriteBatches when an experiment exits via
 * {@link Canvas#dispose()} manually.
 *</p>
 * @author    Gancheng Zhu
 */
public class Canvas implements Disposable {

    Color backgroundColor;
    PsychSpriteBatch psychSpriteBatch;
    Set<Disposable> disposableSet;

    public Canvas() {
        this(Color.GRAY);
    }

    public void setViewport(int x, int y, int width, int height) {
        EasyPsycho.gl.glViewport(x, y, width, height);
    }

    public Canvas(Color backgroundColor) {
        psychSpriteBatch = new PsychSpriteBatch();
        this.backgroundColor = backgroundColor;
        disposableSet = new HashSet<>();
    }

    /**
     * Clear the stimulus of the screen.
     */
    public void clearCanvas() {
        clearCanvas(backgroundColor);
    }

    public void clearCanvas(Color color) {
        clearCanvas(color.r, color.g, color.b, color.a);
    }

    public void clearCanvas(float r, float g, float b, float a) {
        EasyPsycho.gl.glClearColor(r, g, b, a);
        EasyPsycho.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_STENCIL_BUFFER_BIT);
    }

    public void add(Disposable disposable) {
        disposableSet.add(disposable);
    }

    @Override
    public void dispose() {
        if (psychSpriteBatch != null) psychSpriteBatch.dispose();
        for (Disposable disposable : disposableSet) {
            disposable.dispose();
        }
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public PsychSpriteBatch getPsychSpriteBatch() {
        return psychSpriteBatch;
    }

    public void setPsychSpriteBatch(PsychSpriteBatch psychSpriteBatch) {
        this.psychSpriteBatch = psychSpriteBatch;
    }

    public Set<Disposable> getDisposableSet() {
        return disposableSet;
    }

    public void setDisposableSet(Set<Disposable> disposableSet) {
        this.disposableSet = disposableSet;
    }
}
