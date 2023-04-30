package inc.fabudi.numericalanalysis.math;

import android.annotation.SuppressLint;

import inc.fabudi.numericalanalysis.interfaces.Function1;
import inc.fabudi.numericalanalysis.interfaces.Function2;

public class NA05Solver {

    public static String solveTrapeze(Function1 function, NumberPair borders) {
        double a = borders.getA(), b = borders.getB();
        return "S_{T}=" + formatString(countIntegralTrapeze(function, (b - a) / 100, 100, a, b));
    }

    public static String solveSimpson(Function1 function, NumberPair borders) {
        double a = borders.getA(), b = borders.getB();
        return "S_{S}=" + formatString(countIntegralSimpson(function, (b - a) / 100, 100, a, b));
    }

    public static String solveSimpson(Function2 function, NumberPair borders) {
        double a = borders.getA(), b = borders.getB(), c = borders.getC(), d = borders.getD();
        return "S_{S}=" + formatString(countIntegralSimpson(function, 100, 100, a, c, (b - a) / (2 * 100), (d - c) / (2 * 100)));
    }

    @SuppressLint("DefaultLocale")
    private static String formatString(Double number) {
        if (Math.abs(number) > 0.00000001) {
            return String.format("%f", number);
        } else {
            return String.format("%6.4e", number);
        }
    }

    private static double countIntegralTrapeze(Function1 function, double h, double n, double a, double b) {
        double integral = h / 2;
        double temp = 0, x = a + h;
        for (int i = 1; i < n; i++) {
            temp += function.count(x);
            x += h;
        }
        integral *= (function.count(x) + 2 * temp + function.count(x));
        return integral;
    }

    private static double countIntegralSimpson(Function1 function, double h, double n, double a, double b) {
        double integral = h / 3, tempEven = 0, tempOdd = 0, x = a;
        for (int i = 1; i <= n / 2; i++) {
            tempOdd += function.count(x + h * (2 * i - 1));
        }
        for (int i = 1; i < n / 2; i++) {
            tempEven += function.count(x + h * (2 * i));
        }
        return integral * (function.count(a) + function.count(b) + 4 * tempOdd + 2 * tempEven);
    }

    private static double countIntegralSimpson(Function2 function, double m, double n, double a, double c, double hx, double hy) {
        double integral = (hx * hy) / 9;
        double tempI = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                tempI +=
                        function.count(a + hx * (2 * i), c + hy * (2 * j))
                                + 4 * function.count(a + hx * (2 * i + 1), c + hy * (2 * j))
                                + function.count(a + hx * (2 * i + 2), c + hy * (2 * j))
                                + 4 * function.count(a + hx * (2 * i), c + hy * (2 * j + 1))
                                + 16 * function.count(a + hx * (2 * i + 1), c + hy * (2 * j + 1))
                                + 4 * function.count(a + hx * (2 * i + 2), c + hy * (2 * j + 1))
                                + function.count(a + hx * (2 * i), c + hy * (2 * j + 2))
                                + 4 * function.count(a + hx * (2 * i + 1), c + hy * (2 * j + 2))
                                + function.count(a + hx * (2 * i + 2), c + hy * (2 * j + 2));
            }
        }
        return integral * tempI;
    }

}