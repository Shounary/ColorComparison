import java.awt.*;

public class MultiformColor extends Color {

    private static double colorDifferenceThreshold = 50;

    private double L;
    private double a;
    private double b;

    public MultiformColor(int r, int g, int b) {
        super(r, g, b);
        processLabConversion();
    }

    public MultiformColor(Color color) {
        super(color.getRed(), color.getGreen(), color.getBlue());
        processLabConversion();
    }

    public double[] processLabConversion() {

        double[] rgb = {super.getRed(), super.getGreen(), super.getBlue()};
        for (int i = 0; i < rgb.length; i++) {
            rgb[i] /= 255;
            if (rgb[i] > 0.04045) {
                rgb[i] = Math.pow(((rgb[i] + 0.055) / 1.055), 2.4);
            } else {
                rgb[i] /= 12.92;
            }
            rgb[i] *= 100;
        }

        double[] xyz = new double[3];
        xyz[0] = rgb[0] * 0.4124 + rgb[1] * 0.3576 + rgb[2] * 0.1805;
        xyz[1] = rgb[0] * 0.2126 + rgb[1] * 0.7152 + rgb[2] * 0.0722;
        xyz[2] = rgb[0] * 0.0193 + rgb[1] * 0.1192 + rgb[2] * 0.9504;

        xyz[0] /= 95.047;
        xyz[1] /= 100;
        xyz[2] /= 108.883;
        for (int i = 0; i < xyz.length; i++) {
            if (xyz[i] > 0.008856) {
                xyz[i] = Math.pow(xyz[i], (double) 1/3);
            } else {
                xyz[i] = (7.787 * xyz[i]) + (double) 16 / 116;
            }
        }

        double[] lab = new double[3];
        lab[0] = (116 * xyz[1]) - 16;
        lab[1] = 500 * (xyz[0] - xyz[1]);
        lab[2] = 200 * (xyz[1] - xyz[2]);

        L = lab[0];
        a = lab[1];
        b = lab[2];

        return lab;
    }

    public double getMultiformColorDifference(MultiformColor color) {
        return Math.sqrt(Math.pow((L - color.L), 2) + Math.pow((a - color.a), 2) + Math.pow((b - color.b), 2));
    }

    public boolean compareTo(MultiformColor color) {
        return getMultiformColorDifference(color) <= colorDifferenceThreshold;
    }

    public String toString() {
        return "--- Color ---\nRed: " + getRed() + "\nGreen: " + getGreen() + "\nBlue: " + getBlue()
                + "\n\nL: " + L + "\na: " + a + "\nb: " + b + "\n\n\n";
    }
}
