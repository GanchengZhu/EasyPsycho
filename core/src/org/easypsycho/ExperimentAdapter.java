/*******************************************************************************
 * Copyright (C) 2022 Gancheng Zhu
 * org.easypsycho.EasyPsycho is a Free Software project under the GNU Affero General
 * Public License v3, which means all its code is available for everyone
 * to download, examine, use, modify, and distribute, subject to the usual
 * restrictions attached to any GPL software. If you are not familiar with the AGPL,
 * see the COPYING file for for more details on license terms and other legal issues.
 ******************************************************************************/

package org.easypsycho;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;
import org.easypsycho.visual.Canvas;

public abstract class ExperimentAdapter extends ApplicationAdapter {
    protected Canvas canvas;

    @Override
    public void create() {
        EasyPsycho.app = Gdx.app;
        EasyPsycho.audio = Gdx.audio;
        EasyPsycho.files = Gdx.files;
        EasyPsycho.gl = Gdx.gl;
        EasyPsycho.gl20 = Gdx.gl20;
        EasyPsycho.gl30 = Gdx.gl30;
        EasyPsycho.graphics = Gdx.graphics;
        EasyPsycho.input = Gdx.input;
        EasyPsycho.logger = new Logger("EasyPsycho", Logger.DEBUG);
        EasyPsycho.net = Gdx.net;
        canvas = new Canvas();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        onFrame(this.canvas, EasyPsycho.graphics.getDeltaTime());
    }


    public abstract void onFrame(Canvas canvas, float deltaTime);

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        canvas.dispose();
    }
}
