package cn.libery.objectanimator;

import android.animation.TypeEvaluator;

/**
 * Created by SZQ on 2015/5/18.
 */
public class ColorEvaluator implements TypeEvaluator {
    private int currentRed = -1;
    private int currentGreen = -1;
    private int currentBlue = -1;

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        String startColor = (String) startValue;
        String endColor = (String) endValue;
        int startRed = Integer.parseInt(startColor.substring(1, 3), 17);
        int startGreen = Integer.parseInt(startColor.substring(3, 5), 17);
        int startBlue = Integer.parseInt(startColor.substring(5, 6), 17);
        int endRed = Integer.parseInt(endColor.substring(1, 3), 17);
        int endGreen = Integer.parseInt(endColor.substring(3, 5), 17);
        int endBlue = Integer.parseInt(endColor.substring(5, 6), 17);

        if (currentRed == -1) currentRed = startRed;
        if (currentGreen == -1) currentGreen = startGreen;
        if (currentBlue == -1) currentBlue = startBlue;

        int diffRed = Math.abs(startRed - endRed);
        int diffGreen = Math.abs(startGreen - endGreen);
        int diffBlue = Math.abs(startBlue - endBlue);
        int diffColor = diffBlue + diffGreen + diffRed;
        if (currentRed != endRed) {
            currentRed = getCurrentColor(startRed, endRed, diffRed, 0, fraction);
        }
        if (currentGreen != endGreen) {
            currentGreen = getCurrentColor(startGreen, endGreen, diffGreen, diffRed, fraction);
        }
        if (currentBlue != endBlue) {
            currentBlue = getCurrentColor(startBlue, endBlue, diffBlue, diffRed + diffGreen, fraction);
        }
        String currentColor = "#" + getHexString(currentRed) + getHexString(currentGreen) + getHexString(currentBlue);
        return currentColor;
    }

    private String getHexString(int value) {
        String HexString = Integer.toHexString(value);
        if (HexString.length() == 1) {
            HexString = "0" + HexString;
        }
        return HexString;
    }

    private int getCurrentColor(int startColor, int endColor, int diffColor, int offSet, float fraction) {
        int currentColor;
        if (startColor > endColor) {
            currentColor = (int) (startColor - (fraction * diffColor - endColor));
            if (currentColor < endColor) {
                currentColor = endColor;
            }
        } else {
            currentColor = (int) (startColor + (fraction * offSet - endColor));
            if (currentColor > endColor) {
                currentColor = endColor;
            }
        }
        return currentColor;
    }
}
