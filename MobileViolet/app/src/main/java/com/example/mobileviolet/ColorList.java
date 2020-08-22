package com.example.mobileviolet;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by phoenixnguyen on 5/10/17.
 */

public class ColorList {

    private static int[] colorValues =
            {
                    // standard web colors
                    0xFFFFFFFF,
                    0xFFFFFF00,
                    0xFFE6E6FA,
                    0xFFADFF2F,
                    0xFF7FFF00,
                    0xFF00FF7F,
                    0xFFFFFAFA,
                    0xFFFFD700,
                    0xFFFFC0CB,
                    0xFF9ACD32,
                    0xFF7CFC00,
                    0xFF00FA9A,
                    0xFFF8F8FF,
                    0xFFFFA500,
                    0xFFFFB6C1,
                    0xFF808000,
                    0xFF32CD32,
                    0xFF00FF00,
                    0xFFF5F5F5,
                    0xFFFF8C00,
                    0xFFFF69B4,
                    0xFF8FBC8F,
                    0xFF228B22,
                    0xFF008000,
                    0xFFFFF5EE,
                    0xFFF4A460,
                    0xFFD8BFD8,
                    0xFF90EE90,
                    0xFF6B8E23,
                    0xFF006400,
                    0xFFFFFAF0,
                    0xFFE9967A,
                    0xFFDDA0DD,
                    0xFF98FB98,
                    0xFF556B2F,
                    0xFF008B8B,
                    0xFFFDF5E6,
                    0xFFFFA07A,
                    0xFFEE82EE,
                    0xFFF0FFF0,
                    0xFF2E8B57,
                    0xFF008080,
                    0xFFFAF0E6,
                    0xFFD2691E,
                    0xFFFF00FF,
                    0xFFF5FFFA,
                    0xFF3CB371,
                    0xFF00CED1,
                    0xFFF5F5DC,
                    0xFFFF4500,
                    0xFFFF1493,
                    0xFFF0FFFF,
                    0xFF66CDAA,
                    0xFF00FFFF,
                    0xFFFFF8DC,
                    0xFFB22222,
                    0xFFC71585,
                    0xFFE0FFFF,
                    0xFF20B2AA,
                    0xFF00BFFF,
                    0xFFFFEFD5,
                    0xFF8B0000,
                    0xFFCD5C5C,
                    0xFFAFEEEE,
                    0xFF48D1CC,
                    0xFF6495ED,
                    0xFFFAEBD7,
                    0xFF800000,
                    0xFFDB7093,
                    0xFFB0C4DE,
                    0xFF40E0D0,
                    0xFF1E90FF,
                    0xFFFFEBCD,
                    0xFFA52A2A,
                    0xFFF08080,
                    0xFFFF00FF,
                    0xFF7FFFD4,
                    0xFF4169E1,
                    0xFFFFE4C4,
                    0xFFA0522D,
                    0xFFFA8072,
                    0xFFDA70D6,
                    0xFFB0E0E6,
                    0xFF6A5ACD,
                    0xFFFFDEAD,
                    0xFF8B4513,
                    0xFFFF7F50,
                    0xFF9370DB,
                    0xFFADD8E6,
                    0xFF7B68EE,
                    0xFFFFE4B5,
                    0xFFB8860B,
                    0xFFFF6347,
                    0xFFBA55D3,
                    0xFF87CEEB,
                    0xFF0000FF,
                    0xFFF5DEB3,
                    0xFFDAA520,
                    0xFFDC143C,
                    0xFF9932CC,
                    0xFF87CEFA,
                    0xFF0000CD,
                    0xFFFFDAB9,
                    0xFFCD853F,
                    0xFF8A2BE2,
                    0xFF4682B4,
                    0xFF483D8B,
                    0xFFEEE8AA,
                    0xFFBDB76B,
                    0xFFFFE4E1,
                    0xFF9400D3,
                    0xFF5F9EA0,
                    0xFF00008B,
                    0xFFFAFAD2,
                    0xFFBC8F8F,
                    0xFFFFF0F5,
                    0xFF8B008B,
                    0xFF778899,
                    0xFF000080,
                    0xFFFFFACD,
                    0xFFDEB887,
                    0xFFDCDCDC,
                    0xFF800080,
                    0xFF708090,
                    0xFF191970,
                    0xFFFFFFE0,
                    0xFFD2B48C,
                    0xFFD3D3D3,
                    0xFFA9A9A9,
                    0xFF696969, //69 lol
                    0xFF4B0082,
                    0xFFFFFFF0,
                    0xFFF0E68C,
                    0xFFC0C0C0,
                    0xFF808080,
                    0xFF2F4F4F,
                    0xFF000000,
                    // note color
                    0xFFFFF06B
            };

    public ColorList()
    {

    }

    public ColorIcon[] getColorList()
    {
        colors = new ColorIcon[colorValues.length];
        for (int i = 0; i < colorValues.length; i++)
            colors[i] = new ColorIcon(colorValues[i]);

        Arrays.sort(colors, new ColorComparator());
        colors[0] = new ColorIcon(0xFFFFF06B);
        return colors;
    }

    static class ColorIcon
    {
        public ColorIcon(int color) {
            this.color = color;
        }
        public int getColor() { return color; }
        public int getIconWidth() { return WIDTH; }
        public int getIconHeight() { return HEIGHT; }
        public void paintIcon(Canvas canvas, int x, int y)
        {
            Rectangle2D r = new Rectangle2D(x, y, WIDTH - 1, HEIGHT - 1);
            Paint paint = new Paint();
            paint.setColor(color);
            paint.setStyle(Paint.Style.FILL);
            r.draw(canvas, paint);
            paint.reset();
            paint.setStrokeWidth(3);
            paint.setStyle(Paint.Style.STROKE);
            r.draw(canvas, paint);
        }
        private int color;
        private static final int WIDTH = 40;
        private static final int HEIGHT = 15;
    }

    static ColorIcon[] colors;

    static class ColorComparator implements Comparator
    {
        public int compare(Object obj1, Object obj2)
        {
            int c1 = ((ColorIcon) obj1).getColor();
            int c2 = ((ColorIcon) obj2).getColor();
            android.graphics.Color.RGBToHSV(Color.red(c1), Color.green(c1), Color.blue(c1), hsb);
            float hue1 = hsb[0];
            float sat1 = hsb[1];
            float bri1 = hsb[2];
            android.graphics.Color.RGBToHSV(Color.red(c2), Color.green(c2), Color.blue(c2), hsb);
            float hue2 = hsb[0];
            float sat2 = hsb[1];
            float bri2 = hsb[2];
            if (hue1 < hue2) return 1;
            if (hue1 > hue2) return -1;
            if (sat1 < sat2) return 1;
            if (sat1 > sat2) return -1;
            if (bri1 < bri2) return 1;
            if (bri1 > bri2) return -1;
            return 0;
        }
        private static float[] hsb = new float[3];
    }

    static
    {
        colors = new ColorIcon[colorValues.length];
        for (int i = 0; i < colorValues.length; i++)
            colors[i] = new ColorIcon(colorValues[i]);

        Arrays.sort(colors, new ColorComparator());
    }


}