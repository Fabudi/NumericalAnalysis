package inc.fabudi.numericalanalysis.math;

import java.util.ArrayList;

public class NA03Solver {

    private static final double epsilon = 0.0001;
    private static final double tauMax = 0.01;

    private static int index = 0;

    public static double getA() {
        while (25 + index > 48) {
            index -= 23;
        }
        index++;
        return 2.5 + (25.0 + index) / 40;
    }

    public static double[] getVector(double u1, double u2, double t) {
        return new double[]{
                -u1 * u2 + Math.sin(t) / t,
                -(u2 * u2) + getA() * t / (1 + t * t)
        };
    }

    public static float[][] solve() {
        ArrayList<Double> xArray = new ArrayList<>(), yArray = new ArrayList<>();
        double x = 0.0, y = -0.412, tMax = 1.0, tk = 0.000001, tau = tMax / 500;
        xArray.add(x);
        yArray.add(y);
        while (tk < tMax) {
            double[] vector = getVector(x, y, tk);
            tk += tau;
            x += vector[0] * tau;
            y += vector[1] * tau;
            xArray.add(tk);
            yArray.add(y);
        }
        float[][] coords = new float[xArray.size()][2];
        for (int i = 0; i < xArray.size(); i++) {
            coords[i][0] = xArray.get(i).floatValue();
            coords[i][1] = yArray.get(i).floatValue();
        }
        return coords;
    }
}