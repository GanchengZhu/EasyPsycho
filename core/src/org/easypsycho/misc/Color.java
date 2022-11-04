package org.easypsycho.misc;

/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

public class Color extends com.badlogic.gdx.graphics.Color {
    private boolean isReadOnly = false;
    public static final Color WHITE = new Color(1, 1, 1, 1);
    public static final Color LIGHT_GRAY = new Color(0xbfbfbfff);
    public static final Color GRAY = new Color(0x7f7f7fff);
    public static final Color DARK_GRAY = new Color(0x3f3f3fff);
    public static final Color BLACK = new Color(0, 0, 0, 1);

    /**
     * Convenience for frequently used <code>WHITE.toFloatBits()</code>
     */
    public static final float WHITE_FLOAT_BITS = WHITE.toFloatBits();

    public static final Color CLEAR = new Color(0, 0, 0, 0);

    public static final Color BLUE = new Color(0, 0, 1, 1);
    public static final Color NAVY = new Color(0, 0, 0.5f, 1);
    public static final Color ROYAL = new Color(0x4169e1ff);
    public static final Color SLATE = new Color(0x708090ff);
    public static final Color SKY = new Color(0x87ceebff);
    public static final Color CYAN = new Color(0, 1, 1, 1);
    public static final Color TEAL = new Color(0, 0.5f, 0.5f, 1);

    public static final Color GREEN = new Color(0x00ff00ff);
    public static final Color CHARTREUSE = new Color(0x7fff00ff);
    public static final Color LIME = new Color(0x32cd32ff);
    public static final Color FOREST = new Color(0x228b22ff);
    public static final Color OLIVE = new Color(0x6b8e23ff);

    public static final Color YELLOW = new Color(0xffff00ff);
    public static final Color GOLD = new Color(0xffd700ff);
    public static final Color GOLDENROD = new Color(0xdaa520ff);
    public static final Color ORANGE = new Color(0xffa500ff);

    public static final Color BROWN = new Color(0x8b4513ff);
    public static final Color TAN = new Color(0xd2b48cff);
    public static final Color FIREBRICK = new Color(0xb22222ff);

    public static final Color RED = new Color(0xff0000ff);
    public static final Color SCARLET = new Color(0xff341cff);
    public static final Color CORAL = new Color(0xff7f50ff);
    public static final Color SALMON = new Color(0xfa8072ff);
    public static final Color PINK = new Color(0xff69b4ff);
    public static final Color MAGENTA = new Color(1, 0, 1, 1);

    public static final Color PURPLE = new Color(0xa020f0ff);
    public static final Color VIOLET = new Color(0xee82eeff);
    public static final Color MAROON = new Color(0xb03060ff);


    public Color(float r, float g, float b, float a) {
        super(r, g, b, a);
    }

    public Color(int rgba8888) {
        super(rgba8888);
    }

    public com.badlogic.gdx.graphics.Color toGdxColor() {
        return new com.badlogic.gdx.graphics.Color(r, g, b, a);
    }
}


/**
 * # Dict of named colours
 * colorNames = {
 * "none": (0, 0, 0),
 * "aliceblue": (0.882352941176471, 0.945098039215686, 1),
 * "antiquewhite": (0.96078431372549, 0.843137254901961, 0.686274509803922),
 * "aqua": (-1, 1, 1),
 * "aquamarine": (-0.00392156862745097, 1, 0.662745098039216),
 * "azure": (0.882352941176471, 1, 1),
 * "beige": (0.92156862745098, 0.92156862745098, 0.725490196078431),
 * "bisque": (1, 0.788235294117647, 0.537254901960784),
 * "black": (-1, -1, -1),
 * "blanchedalmond": (1, 0.843137254901961, 0.607843137254902),
 * "blue": (-1, -1, 1),
 * "blueviolet": (0.0823529411764705, -0.662745098039216, 0.772549019607843),
 * "brown": (0.294117647058824, -0.670588235294118, -0.670588235294118),
 * "burlywood": (0.741176470588235, 0.443137254901961, 0.0588235294117647),
 * "cadetblue": (-0.254901960784314, 0.23921568627451, 0.254901960784314),
 * "chartreuse": (-0.00392156862745097, 1, -1),
 * "chestnut": (0.607843137254902, -0.27843137254902, -0.27843137254902),
 * "chocolate": (0.647058823529412, -0.176470588235294, -0.764705882352941),
 * "coral": (1, -0.00392156862745097, -0.372549019607843),
 * "cornflowerblue": (-0.215686274509804, 0.168627450980392, 0.858823529411765),
 * "cornsilk": (1, 0.945098039215686, 0.725490196078431),
 * "crimson": (0.725490196078431, -0.843137254901961, -0.529411764705882),
 * "cyan": (-1, 1, 1),
 * "darkblue": (-1, -1, 0.0901960784313725),
 * "darkcyan": (-1, 0.0901960784313725, 0.0901960784313725),
 * "darkgoldenrod": (0.443137254901961, 0.0509803921568628, -0.913725490196078),
 * "darkgray": (0.325490196078431, 0.325490196078431, 0.325490196078431),
 * "darkgreen": (-1, -0.215686274509804, -1),
 * "darkgrey": (0.325490196078431, 0.325490196078431, 0.325490196078431),
 * "darkkhaki": (0.482352941176471, 0.435294117647059, -0.16078431372549),
 * "darkmagenta": (0.0901960784313725, -1, 0.0901960784313725),
 * "darkolivegreen": (-0.333333333333333, -0.16078431372549, -0.631372549019608),
 * "darkorange": (1, 0.0980392156862746, -1),
 * "darkorchid": (0.2, -0.607843137254902, 0.6),
 * "darkred": (0.0901960784313725, -1, -1),
 * "darksalmon": (0.827450980392157, 0.176470588235294, -0.0431372549019607),
 * "darkseagreen": (0.12156862745098, 0.474509803921569, 0.12156862745098),
 * "darkslateblue": (-0.435294117647059, -0.52156862745098, 0.0901960784313725),
 * "darkslategray": (-0.631372549019608, -0.380392156862745, -0.380392156862745),
 * "darkslategrey": (-0.631372549019608, -0.380392156862745, -0.380392156862745),
 * "darkturquoise": (-1, 0.615686274509804, 0.63921568627451),
 * "darkviolet": (0.16078431372549, -1, 0.654901960784314),
 * "deeppink": (1, -0.843137254901961, 0.152941176470588),
 * "deepskyblue": (-1, 0.498039215686275, 1),
 * "dimgray": (-0.176470588235294, -0.176470588235294, -0.176470588235294),
 * "dimgrey": (-0.176470588235294, -0.176470588235294, -0.176470588235294),
 * "dodgerblue": (-0.764705882352941, 0.129411764705882, 1),
 * "firebrick": (0.396078431372549, -0.733333333333333, -0.733333333333333),
 * "floralwhite": (1, 0.96078431372549, 0.882352941176471),
 * "forestgreen": (-0.733333333333333, 0.0901960784313725, -0.733333333333333),
 * "fuchsia": (1, -1, 1),
 * "gainsboro": (0.725490196078431, 0.725490196078431, 0.725490196078431),
 * "ghostwhite": (0.945098039215686, 0.945098039215686, 1),
 * "gold": (1, 0.686274509803922, -1),
 * "goldenrod": (0.709803921568627, 0.294117647058824, -0.749019607843137),
 * "gray": (0.00392156862745097, 0.00392156862745097, 0.00392156862745097),
 * "grey": (0.00392156862745097, 0.00392156862745097, 0.00392156862745097),
 * "green": (-1, 0.00392156862745097, -1),
 * "greenyellow": (0.356862745098039, 1, -0.631372549019608),
 * "honeydew": (0.882352941176471, 1, 0.882352941176471),
 * "hotpink": (1, -0.176470588235294, 0.411764705882353),
 * "indigo": (-0.411764705882353, -1, 0.0196078431372548),
 * "ivory": (1, 1, 0.882352941176471),
 * "khaki": (0.882352941176471, 0.803921568627451, 0.0980392156862746),
 * "lavender": (0.803921568627451, 0.803921568627451, 0.96078431372549),
 * "lavenderblush": (1, 0.882352941176471, 0.92156862745098),
 * "lawngreen": (-0.0274509803921569, 0.976470588235294, -1),
 * "lemonchiffon": (1, 0.96078431372549, 0.607843137254902),
 * "lightblue": (0.356862745098039, 0.694117647058824, 0.803921568627451),
 * "lightcoral": (0.882352941176471, 0.00392156862745097, 0.00392156862745097),
 * "lightcyan": (0.756862745098039, 1, 1),
 * "lightgoldenrodyellow": (0.96078431372549, 0.96078431372549, 0.647058823529412),
 * "lightgray": (0.654901960784314, 0.654901960784314, 0.654901960784314),
 * "lightgreen": (0.129411764705882, 0.866666666666667, 0.129411764705882),
 * "lightgrey": (0.654901960784314, 0.654901960784314, 0.654901960784314),
 * "lightpink": (1, 0.427450980392157, 0.513725490196078),
 * "lightsalmon": (1, 0.254901960784314, -0.0431372549019607),
 * "lightseagreen": (-0.749019607843137, 0.396078431372549, 0.333333333333333),
 * "lightskyblue": (0.0588235294117647, 0.615686274509804, 0.96078431372549),
 * "lightslategray": (-0.0666666666666667, 0.0666666666666667, 0.2),
 * "lightslategrey": (-0.0666666666666667, 0.0666666666666667, 0.2),
 * "lightsteelblue": (0.380392156862745, 0.537254901960784, 0.741176470588235),
 * "lightyellow": (1, 1, 0.756862745098039),
 * "lime": (-1, 1, -1),
 * "limegreen": (-0.607843137254902, 0.607843137254902, -0.607843137254902),
 * "linen": (0.96078431372549, 0.882352941176471, 0.803921568627451),
 * "magenta": (1, -1, 1),
 * "maroon": (0.00392156862745097, -1, -1),
 * "mediumaquamarine": (-0.2, 0.607843137254902, 0.333333333333333),
 * "mediumblue": (-1, -1, 0.607843137254902),
 * "mediumorchid": (0.458823529411765, -0.333333333333333, 0.654901960784314),
 * "mediumpurple": (0.152941176470588, -0.12156862745098, 0.717647058823529),
 * "mediumseagreen": (-0.529411764705882, 0.403921568627451, -0.113725490196078),
 * "mediumslateblue": (-0.0352941176470588, -0.184313725490196, 0.866666666666667),
 * "mediumspringgreen": (-1, 0.96078431372549, 0.207843137254902),
 * "mediumturquoise": (-0.435294117647059, 0.63921568627451, 0.6),
 * "mediumvioletred": (0.56078431372549, -0.835294117647059, 0.0431372549019609),
 * "midnightblue": (-0.803921568627451, -0.803921568627451, -0.12156862745098),
 * "mintcream": (0.92156862745098, 1, 0.96078431372549),
 * "mistyrose": (1, 0.788235294117647, 0.764705882352941),
 * "moccasin": (1, 0.788235294117647, 0.419607843137255),
 * "navajowhite": (1, 0.741176470588235, 0.356862745098039),
 * "navy": (-1, -1, 0.00392156862745097),
 * "oldlace": (0.984313725490196, 0.92156862745098, 0.803921568627451),
 * "olive": (0.00392156862745097, 0.00392156862745097, -1),
 * "olivedrab": (-0.16078431372549, 0.113725490196078, -0.725490196078431),
 * "orange": (1, 0.294117647058824, -1),
 * "orangered": (1, -0.458823529411765, -1),
 * "orchid": (0.709803921568627, -0.12156862745098, 0.67843137254902),
 * "palegoldenrod": (0.866666666666667, 0.819607843137255, 0.333333333333333),
 * "palegreen": (0.192156862745098, 0.968627450980392, 0.192156862745098),
 * "paleturquoise": (0.372549019607843, 0.866666666666667, 0.866666666666667),
 * "palevioletred": (0.717647058823529, -0.12156862745098, 0.152941176470588),
 * "papayawhip": (1, 0.874509803921569, 0.670588235294118),
 * "peachpuff": (1, 0.709803921568627, 0.450980392156863),
 * "peru": (0.607843137254902, 0.0431372549019609, -0.505882352941176),
 * "pink": (1, 0.505882352941176, 0.592156862745098),
 * "plum": (0.733333333333333, 0.254901960784314, 0.733333333333333),
 * "powderblue": (0.380392156862745, 0.756862745098039, 0.803921568627451),
 * "purple": (0.00392156862745097, -1, 0.00392156862745097),
 * "red": (1, -1, -1),
 * "rosybrown": (0.474509803921569, 0.12156862745098, 0.12156862745098),
 * "royalblue": (-0.490196078431373, -0.176470588235294, 0.764705882352941),
 * "saddlebrown": (0.0901960784313725, -0.458823529411765, -0.850980392156863),
 * "salmon": (0.96078431372549, 0.00392156862745097, -0.105882352941176),
 * "sandybrown": (0.913725490196079, 0.286274509803922, -0.247058823529412),
 * "seagreen": (-0.63921568627451, 0.0901960784313725, -0.317647058823529),
 * "seashell": (1, 0.92156862745098, 0.866666666666667),
 * "sienna": (0.254901960784314, -0.356862745098039, -0.647058823529412),
 * "silver": (0.505882352941176, 0.505882352941176, 0.505882352941176),
 * "skyblue": (0.0588235294117647, 0.615686274509804, 0.843137254901961),
 * "slateblue": (-0.168627450980392, -0.294117647058823, 0.607843137254902),
 * "slategray": (-0.12156862745098, 0.00392156862745097, 0.129411764705882),
 * "slategrey": (-0.12156862745098, 0.00392156862745097, 0.129411764705882),
 * "snow": (1, 0.96078431372549, 0.96078431372549),
 * "springgreen": (-1, 1, -0.00392156862745097),
 * "steelblue": (-0.450980392156863, 0.0196078431372548, 0.411764705882353),
 * "tan": (0.647058823529412, 0.411764705882353, 0.0980392156862746),
 * "teal": (-1, 0.00392156862745097, 0.00392156862745097),
 * "thistle": (0.694117647058824, 0.498039215686275, 0.694117647058824),
 * "tomato": (1, -0.223529411764706, -0.443137254901961),
 * "turquoise": (-0.498039215686275, 0.756862745098039, 0.631372549019608),
 * "violet": (0.866666666666667, 0.0196078431372548, 0.866666666666667),
 * "wheat": (0.92156862745098, 0.741176470588235, 0.403921568627451),
 * "white": (1, 1, 1),
 * "whitesmoke": (0.92156862745098, 0.92156862745098, 0.92156862745098),
 * "yellow": (1, 1, -1),
 * "yellowgreen": (0.207843137254902, 0.607843137254902, -0.607843137254902)
 * }
 */