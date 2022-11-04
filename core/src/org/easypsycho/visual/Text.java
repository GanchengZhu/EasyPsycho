package org.easypsycho.visual; /*******************************************************************************
 * Copyright (C) 2022 Gancheng Zhu
 * org.easypsycho.EasyPsycho is a Free Software project under the GNU Affero General
 * Public License v3, which means all its code is available for everyone
 * to download, examine, use, modify, and distribute, subject to the usual
 * restrictions attached to any GPL software. If you are not familiar with the AGPL,
 * see the COPYING file for for more details on license terms and other legal issues.
 ******************************************************************************/

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;
import org.easypsycho.EasyPsycho;
import org.easypsycho.misc.Color;
import org.easypsycho.misc.FontType;
import org.easypsycho.misc.Rect;

public class Text extends BaseVisual<Text> implements Disposable {
    private String drawText = "";
    private FileHandle fontFile;
    private FontType fontType = FontType.FreeSans;
    private Color color;
    private int fontSize;
    private float posX; // Position X of stimulus. Unit is pixel and the center of the screen is (0, 0) of Cartesian grids
    private float posY; // Position Y of stimulus. See posX
    private float opacity = 1;  // opacity of stimulus is between 0 and 1, and default of 1
    private float rotation = 0; // rotation of stimulus, default of 0. Unit is degree
    private boolean flipX = false;
    private boolean flipY = false;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont bitmapFont;


    public Text(String drawText, float posX, float posY, int fontSize, Color color) {
        this.drawText = drawText;
        setPosition(posX, posY);
        this.fontSize = fontSize;
        this.color = color;
        fontFile = EasyPsycho.files.internal(fontType2StringPath(FontType.FreeSans));
        generator = new FreeTypeFontGenerator(fontFile);
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        parameter.color = color;
        bitmapFont = generator.generateFont(parameter); // font size 12 pixels
    }

    public Text(String drawText, float posX, float posY) {
        this(drawText, posX, posY, 24, Color.WHITE);
    }

    public Text(String drawText, Color color) {
        this(drawText, 0, 0, 24, color);
    }


    public Text(String drawText) {
        this(drawText, 0, 0, 24, Color.WHITE);
    }


    public Text() {
        this("", 0, 0, 24, Color.WHITE);
    }

    private void initFontGenerator() {
    }
    
    public Text setFont(FontType fontType){
        return setFont(EasyPsycho.files.internal(fontType2StringPath(fontType)));
    }

    public Text setFont(FileHandle fontFile){
        this.fontFile = fontFile;
        initFontGenerator();
        return this;
    }

    public Text setFontSize(){
        parameter.size = fontSize;
        bitmapFont = generator.generateFont(parameter);
        return this;
    }

    public void drawOnCanvas(Canvas canvas){
//        if (isShouldUpdate()){
//            updateParameters();
//            setShouldUpdate(false);
//        }
//
//        if (!canvas.getDisposableSet().contains(this)) {
//            canvas.add(this);
//        }
//
//        canvas.getPsychSpriteBatch().begin();
//        canvas.getPsychSpriteBatch().setContrast(getContrast()).setOpacity(getOpacity());
//        if (sprite != null && sprite.getTexture() != null && maskTexture != null) {
//            canvas.getPsychSpriteBatch().draw(sprite, maskTexture);
//        } else if (sprite != null && sprite.getTexture() != null) {
//            canvas.getPsychSpriteBatch().draw(sprite);
//        }
//        canvas.getPsychSpriteBatch().end();
    }

//    public void draw() {
//        mGlyphLayout = mBitmapFont.draw(Config.SPRITE_BATCH, mDrawText, glPosX, glPosY);
//    }

    public String getDrawText() {
        return drawText;
    }

    public Text setDrawText(String mDrawText) {
        this.drawText = mDrawText;
        return this;
    }

    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }

    public Text setBitmapFont(BitmapFont mBitmapFont) {
        this.bitmapFont = mBitmapFont;
        return this;
    }

    public Color getColor() {
        return color;
    }

    public Text setColor(Color color) {
        this.color = color;
        bitmapFont.setColor(color);
        return this;
    }

    private static String fontType2StringPath(FontType fontType) {
        String fontClassPath = "org/easypsycho/font/";
        switch (fontType) {
            case FreeMono:
                fontClassPath += "FreeMono.ttf";
                break;
            case FreeMonoBold:
                fontClassPath += "FreeMonoBold.ttf";
                break;
            case FreeMonoBoldOblique:
                fontClassPath += "FreeMonoBoldOblique.ttf";
                break;
            case FreeMonoOblique:
                fontClassPath += "FreeMonoOblique.ttf";
                break;
            case FreeSans:
                fontClassPath += "FreeSans.ttf";
                break;
            case FreeSansBold:
                fontClassPath += "FreeSansBold.ttf";
                break;
            case FreeSansBoldOblique:
                fontClassPath += "FreeSansBoldOblique.ttf";
                break;
            case FreeSansOblique:
                fontClassPath += "FreeSansOblique.ttf";
                break;
            case FreeSerif:
                fontClassPath += "FreeSerif.ttf";
                break;
            case FreeSerifBold:
                fontClassPath += "FreeSerifBold.ttf";
                break;
            case FreeSerifBoldItalic:
                fontClassPath += "FreeSerifBoldItalic.ttf";
                break;
            case FreeSerifItalic:
                fontClassPath += "FreeSerifItalic.ttf";
                break;
            default:
                break;
        }
        return fontClassPath;
    }


    public void dispose() {
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        bitmapFont.dispose();
    }

    @Override
    public void updateParameters() {
        parameter.size = fontSize;
        parameter.color = color;
        bitmapFont = generator.generateFont(parameter); // font size 12 pixels
    }


    public FileHandle getFontFile() {
        return fontFile;
    }

    public void setFontFile(FileHandle fontFile) {
        this.fontFile = fontFile;
    }

    public FontType getFontType() {
        return fontType;
    }

    public void setFontType(FontType fontType) {
        this.fontType = fontType;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

}


