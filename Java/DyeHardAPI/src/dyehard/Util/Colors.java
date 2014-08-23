package dyehard.Util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Colors {
    // DyeHard Dye Colors
    public static int colorCount = 6;
    public static Color Green = new Color(38, 153, 70);
    public static Color Red = new Color(193, 24, 30);
    public static Color Yellow = new Color(228, 225, 21);
    public static Color Teal = new Color(90, 184, 186);
    public static Color Pink = new Color(215, 59, 148);
    public static Color Blue = new Color(50, 75, 150);

    private static Color colorPicker(int choice) {
        switch (choice) {
        case 0:
            return Green;
        case 1:
            return Red;
        case 2:
            return Yellow;
        case 3:
            return Teal;
        case 4:
            return Pink;
        case 5:
            return Blue;
        }
        return Color.black;
    }

    public static Color randomColor() {
        Random rand = new Random();
        return colorPicker(rand.nextInt(6));
    }

    public static ArrayList<Color> randomColorSet(int count) {
        // get a random and unique subset of the available colors
        ArrayList<Integer> range = new ArrayList<Integer>();
        for (int i = 0; i < Colors.colorCount; i++) {
            range.add(i);
        }
        Collections.shuffle(range);
        // get the colors from the indexes in the sample list
        ArrayList<Color> colors = new ArrayList<Color>();
        for (int i : range) {
            colors.add(colorPicker(i));
        }
        return colors;
    }
}
