/*******************************************************************************
 * Copyright (C) 2022 Gancheng Zhu
 * org.easypsycho.EasyPsycho is a Free Software project under the GNU Affero General
 * Public License v3, which means all its code is available for everyone
 * to download, examine, use, modify, and distribute, subject to the usual
 * restrictions attached to any GPL software. If you are not familiar with the AGPL,
 * see the COPYING file for for more details on license terms and other legal issues.
 ******************************************************************************/

package org.easypsycho.misc;

import com.badlogic.gdx.Gdx;
import org.easypsycho.EasyPsycho;

public class Unit {
    // 转成OpenGL格式的，目前不支持CM，后续支持CM
    public static Point convertToRaw(float[] point, UnitType unitType) {
        return convertToRaw(new Point(point[0], point[1]), unitType);
    }

    public static Point convertToRaw(Point point, UnitType unitType) {
//        Gdx.graphics.getDensity();
        int height = EasyPsycho.graphics.getHeight();
        int width = EasyPsycho.graphics.getWidth();
        switch (unitType) {
            case HEIGHT:
                return new Point(width / 2d + height * point.x, height / 2d + height * point.y);
            case PIXELS:
                return new Point(width / 2d + point.x, height / 2d + point.y);
//            case PERCENTAGE:
//                return new Point(width / 2d + width * point.x, height / 2d + height * point.y);
        }

        return new Point(width / 2d, height / 2d);
    }

    public static Point convertToRaw(float posX, float posY, UnitType unitType) {
//        Gdx.graphics.getDensity();
        int height = EasyPsycho.graphics.getHeight();
        int width = EasyPsycho.graphics.getWidth();
        switch (unitType) {
            case HEIGHT:
                return new Point(width / 2d + height * posX, height / 2d + height * posY);
            case PIXELS:
                return new Point(width / 2d + posX, height / 2d + posY);
//            case PERCENTAGE:
//                return new Point(width / 2d + width * posX, height / 2d + height * posY);
        }
        return new Point(width / 2d, height / 2d);
    }

    public static float covertToPixels(float size, UnitType unitType){
        if (unitType == UnitType.HEIGHT){
            return Gdx.graphics.getHeight() * size;
        }else {
            return size;
        }
    }

}


