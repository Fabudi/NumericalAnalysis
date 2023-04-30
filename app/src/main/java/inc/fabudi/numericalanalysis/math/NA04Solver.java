package inc.fabudi.numericalanalysis.math;

public class NA04Solver {

    public static double a, b;

    public static String solve() {
        double[] x = new double[]{0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0};
        double[] fp = new double[]{
                Math.log10(760.0),
                Math.log10(674.8),
                Math.log10(598.0),
                Math.log10(528.9),
                Math.log10(466.6),
                Math.log10(410.6),
                Math.log10(360.2)
        };
        Matrix matrix = new Matrix(new Double[][]{{x[0], 1.0}, {x[6], 1.0}}, 2, 2, new VectorU(2, new Double[]{fp[0], fp[6]}));
        try {
            matrix.makeTriangleMatrix();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        VectorU solution = matrix.solveMatrix();
        a = Math.pow(10, solution.getCoordinateByIndex(1));
        b = solution.getCoordinateByIndex(0);
        return "a=10^{t}=10^{" + solution.getCoordinateByIndex(1) + "} = " + Math.pow(10, solution.getCoordinateByIndex(1)) + "\\\\b = " + solution.getCoordinateByIndex(0);
    }
}