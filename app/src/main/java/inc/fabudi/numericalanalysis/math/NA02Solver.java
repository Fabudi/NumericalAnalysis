package inc.fabudi.numericalanalysis.math;

import java.util.ArrayList;

import inc.fabudi.numericalanalysis.interfaces.Function;
import inc.fabudi.numericalanalysis.interfaces.Function2;
import inc.fabudi.numericalanalysis.interfaces.Function3;

public final class NA02Solver {

    private static final Function[][] functions;
    private static final ArrayList<NumberPair> numberPairs;
    private static final double epsilon = 0.0001;
    private static final double M = 0.001;
    private static Function3 function31, function32, function33;
    private static Function2 function21, function22;

    private NA02Solver() {
    }

    static {
        functions = new Function[][]{
                {(Function2) (x, y) -> Math.log(1 + (x + y) / 5) - Math.sin(y / 3) - x + 1.1, (Function2) (x, y) -> Math.cos(x * y / 6) - y + 0.5},

                {(Function2) (x, y) -> x - y - 6 * Math.log10(x) - 1, (Function2) (x, y) -> x - 3 * y - 6 * Math.log10(y) - 2},

                {(Function2) (x, y) -> Math.sin(x) - y - 1.32, (Function2) (x, y) -> Math.cos(y) - x + 0.85},

                {(Function2) (x, y) -> 2 * x * x * x - y * y - 1, (Function2) (x, y) -> x * y * y * y - y - 4},

                {(Function2) (x, y) -> 1.5 * x * x * x - y * y - 1, (Function2) (x, y) -> x * y * y * y - y - 4},

                {(Function2) (x, y) -> Math.sin(x + 1) - y - 1, (Function2) (x, y) -> 2 * x + Math.cos(y) - 2},

                {(Function2) (x, y) -> x * x - y + 1, (Function2) (x, y) -> x - Math.cos(Math.PI * y / 2)},

                {(Function2) (x, y) -> Math.tan(x * y + 0.2) - x * x, (Function2) (x, y) -> 0.5 * x * x + 2 * y * y - 1},

                {(Function2) (x, y) -> x * x * x - y * y - 1, (Function2) (x, y) -> x * y * y * y - y - 4},

                {(Function2) (x, y) -> 2 * x - Math.sin((x - y) / 2), (Function2) (x, y) -> 2 * y - Math.cos((x + y) / 2)},

                {(Function2) (x, y) -> Math.cos((x - y) / 3) - 2 * y, (Function2) (x, y) -> Math.sin((x + y) / 3) - 2 * x},

                {(Function3) (x, y, z) -> x + x * x - 2 * y * z - 0.1, (Function3) (x, y, z) -> y - y * y + 3 * x * z + 0.2, (Function3) (x, y, z) -> z + z * z + 2 * x * y - 0.3},

                {(Function3) (x, y, z) -> x * x + y * y + z * z - 1, (Function3) (x, y, z) -> 2 * x * x + y * y - 4 * z, (Function3) (x, y, z) -> 3 * x * x - 4 * y + z},

                {(Function3) (x, y, z) -> Math.log10(y / z) - x + 1, (Function3) (x, y, z) -> x * y / 20 - z + 2, (Function3) (x, y, z) -> 2 * x * x + y - z - 0.4}
        };
        numberPairs = new ArrayList<>();

        numberPairs.add(new NumberPair(1, 1));
        numberPairs.add(new NumberPair(0.5, 0.2));
        numberPairs.add(new NumberPair(1, 0.000001));
        numberPairs.add(new NumberPair(1, 1));
        numberPairs.add(new NumberPair(1, 1));
        numberPairs.add(new NumberPair(1, 1));
        numberPairs.add(new NumberPair(1, 0.000001));
        numberPairs.add(new NumberPair(1, 1));
        numberPairs.add(new NumberPair(1.2, 1.3));
        numberPairs.add(new NumberPair(0.000001, 1));
        numberPairs.add(new NumberPair(1, 1));

        numberPairs.add(new NumberPair(0.000001, 0.000001, 0.000001));
        numberPairs.add(new NumberPair(1, 1, 1));
        numberPairs.add(new NumberPair(1, 2.2, 2));
    }

    private static Matrix getJakobian(double x, double y) {
        return new Matrix(new Double[][]{
                {limit(function21, x, y, M * x, 0), limit(function21, x, y, 0, M * y)},
                {limit(function22, x, y, M * x, 0), limit(function22, x, y, 0, M * y)}
        }, 2, 2);
    }

    private static Matrix getJakobian(double x, double y, double z) {
        return new Matrix(new Double[][]{
                {limit(function31, x, y, z, M * x, 0, 0), limit(function31, x, y, z, 0, M * y, 0), limit(function31, x, y, z, 0, 0, M * z)},
                {limit(function32, x, y, z, M * x, 0, 0), limit(function32, x, y, z, 0, M * y, 0), limit(function32, x, y, z, 0, 0, M * z)},
                {limit(function33, x, y, z, M * x, 0, 0), limit(function33, x, y, z, 0, M * y, 0), limit(function33, x, y, z, 0, 0, M * z)}
        }, 3, 3);
    }

    public static ArrayList<NumberPair> solve(int taskId, int size) {
        ArrayList<NumberPair> iterationList = new ArrayList<>();
        Double[] solutionCoords = new Double[size];
        double x, y, z, dx, dy, dz;
        x = numberPairs.get(taskId - 1).getA();
        y = numberPairs.get(taskId - 1).getB();
        if (size > 2) {
            z = numberPairs.get(taskId - 1).getC();
            function31 = (Function3) functions[taskId - 1][0];
            function32 = (Function3) functions[taskId - 1][1];
            function33 = (Function3) functions[taskId - 1][2];
            iterationList.add(new NumberPair(x, y, z));
            do {
                Matrix J = getJakobian(x, y, z);
                VectorU Fx = new VectorU(3, new Double[]{-function31.count(x, y, z),
                        -function32.count(x, y, z),
                        -function33.count(x, y, z)
                });
                J.setVector(Fx);
                try {
                    J.makeTriangleMatrix();
                } catch (Exception e) {
                    System.out.println(e);
                }
                VectorU solution = J.solveMatrix();
                dx = solution.coordinates[0];
                dy = solution.coordinates[1];
                dz = solution.coordinates[2];
                x += dx;
                y += dy;
                z += dz;
                iterationList.add(new NumberPair(x, y, z));
            } while (Math.max(Math.abs(dz), Math.max(Math.abs(dx), Math.abs(dy))) > epsilon);
        } else {
            function21 = (Function2) functions[taskId - 1][0];
            function22 = (Function2) functions[taskId - 1][1];
            iterationList.add(new NumberPair(x, y));
            do {
                Matrix J = getJakobian(x, y);
                VectorU Fx = new VectorU(2, new Double[]{-function21.count(x, y),
                        -function22.count(x, y)
                });
                J.setVector(Fx);
                try {
                    J.makeTriangleMatrix();
                } catch (Exception e) {
                    System.out.println(e);
                }
                VectorU solution = J.solveMatrix();
                dx = solution.coordinates[0];
                dy = solution.coordinates[1];
                x += dx;
                y += dy;
                iterationList.add(new NumberPair(x, y));
            } while (Math.max(Math.abs(dx), Math.abs(dy)) > epsilon);
        }
        return iterationList;
    }

    private static double limit(Function3 f1, double x, double y, double z, double dx, double dy, double dz) {
        return (f1.count(x + dx, y + dy, z + dz) - f1.count(x, y, z)) / (dx + dy + dz);
    }

    private static double limit(Function2 f1, double x, double y, double dx, double dy) {
        return (f1.count(x + dx, y + dy) - f1.count(x, y)) / (dx + dy);
    }
}
