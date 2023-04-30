package inc.fabudi.numericalanalysis.math;

public class NumberPair {
    double a, b, c, d;
    int size;

    public NumberPair(double a, double b) {
        this.a = a;
        this.b = b;
        size = 2;
    }

    public NumberPair(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        size = 3;
    }

    public NumberPair(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        size = 4;
    }

    public int getSize() {
        return size;
    }

    public double getByIndex(int index) {
        switch (index) {
            case 0:
                return a;
            case 1:
                return b;
            case 2:
                return c;
            case 3:
                return d;
        }
        return 0;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getD() {
        return d;
    }
}
