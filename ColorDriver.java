import java.awt.*;

public class ColorDriver {
    public static void main(String[] args) {
        Color color = new Color(90, 90, 0);
        Color color2 = new Color(10,10,255);

        MultiformColor mc1 = new MultiformColor(color);
        MultiformColor mc2 = new MultiformColor(color2);

        System.out.println(mc1);
        System.out.println(mc2);

        System.out.println(mc1.getRGB());

        System.out.println("Color Difference: " + mc1.getMultiformColorDifference(mc2));
        System.out.println("Colors are the same? " + mc1.compareTo(mc2));
    }
}
