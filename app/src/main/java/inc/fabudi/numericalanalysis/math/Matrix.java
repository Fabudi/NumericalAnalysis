package inc.fabudi.numericalanalysis.math;

import java.io.Serializable;

public class Matrix implements Serializable {
    private Double[][] coefficients;
    public int cols, rows, maxRow;
    double max;
    public VectorU vectorU;

    public Double getCoefficientByID(int i, int j) {
        return coefficients[i][j];
    }

    public Matrix() {
        rows = 3;
        cols = 3;
        coefficients = new Double[rows][cols];
        vectorU = new VectorU();
    }

    public Matrix(int cols, int rows, VectorU vectorU) {
        this.cols = cols;
        this.rows = rows;
        coefficients = new Double[cols][rows];
        this.vectorU = vectorU;
    }
    public Matrix(Double[][] coefficients, int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        this.coefficients = coefficients;
    }

    public Matrix(Double[][] coefficients, int cols, int rows, VectorU vectorU) {
        this.coefficients = coefficients;
        this.cols = cols;
        this.rows = rows;
        this.vectorU = vectorU;
    }

    public void setCoefficients(Double[][] coefficients) {
        this.coefficients = coefficients;
    }

    @Override
    public Object clone() {
        Matrix newMatrix = new Matrix(new Double[][]{}, this.cols, this.rows, (VectorU) this.vectorU.clone());
        Double[][] coef = new Double[this.cols][this.rows];
        for (int i = 0; i < this.cols; i++) {
            for (int j = 0; j < this.rows; j++) {
                coef[i][j] = this.coefficients[i][j];
            }
        }
        newMatrix.setCoefficients(coef);
        return newMatrix;
    }

    public VectorU multiplyByVector(VectorU vectorU) {
        Double[] newCoords = new Double[vectorU.size];
        for (int i = 0; i < vectorU.size; i++) {
            double tempSummary = 0;
            for (int j = 0; j < vectorU.size; j++) {
                tempSummary += coefficients[i][j] * vectorU.getCoordinateByIndex(j);
            }
            newCoords[i] = tempSummary;
        }
        return new VectorU(vectorU.size, newCoords);
    }

    public void makeTriangleMatrix() throws Exception {
        for (int i = 0; i < this.rows; i++) {
            this.findMaxElemRow(i, i);
            if (this.max == 0.0) {
                throw new Exception("Вырожденная");
            }
            this.changeRows(i);
            this.findMaxElemRow(i, i);
            this.divideByMax(i);
            for (int j = i + 1; j < this.rows; j++) {
                this.subtractRow(i, j);
            }
        }
        this.removeNegativeZeros();
    }

    public VectorU solveMatrix() {
        Double[] solution = new Double[rows];
        solution[rows - 1] = vectorU.getCoordinateByIndex(rows - 1) / coefficients[rows - 1][cols - 1];
        for (int i = rows - 2; i >= 0; i--) {
            double summary = 0;
            for (int j = i + 1; j < rows; j++) {
                summary += coefficients[i][j] * solution[j];
            }
            solution[i] = vectorU.getCoordinateByIndex(i) - summary;
        }
        return new VectorU(rows, solution);
    }

    public void findMaxElemRow(int col, int row) {
        this.max = coefficients[row][col];
        this.maxRow = row;
        for (int y = rows - 1; y >= row; y--) {
            if (Math.abs(this.max) < Math.abs(coefficients[y][col])) {
                this.max = coefficients[y][col];
                this.maxRow = y;
            }
        }
    }

    public void changeRows(int fromRowIndex) {
        if (fromRowIndex == maxRow) {
            return;
        }
        Double[] temp = coefficients[fromRowIndex];
        coefficients[fromRowIndex] = coefficients[maxRow];
        coefficients[maxRow] = temp;
        Double tempCoordinate = vectorU.getCoordinateByIndex(fromRowIndex);
        vectorU.setCoordinateByIndex(vectorU.getCoordinateByIndex(maxRow), fromRowIndex);
        vectorU.setCoordinateByIndex(tempCoordinate, maxRow);
    }

    public void divideByMax(int row) {
        for (int x = 0; x < cols; x++) {
            coefficients[row][x] /= this.max;
        }
        vectorU.setCoordinateByIndex(vectorU.getCoordinateByIndex(row) / this.max, row);
    }

    public void subtractRow(int from, int to) {
        // i, j, i
        double tempCoefficient = coefficients[to][from];
        double multiplier = Math.signum(coefficients[from][from]);
        for (int i = 0; i < cols; i++) {
            coefficients[to][i] -= coefficients[from][i] * multiplier * tempCoefficient;
        }
        vectorU.setCoordinateByIndex(vectorU.getCoordinateByIndex(to) - vectorU.getCoordinateByIndex(from) * multiplier * tempCoefficient, to);
    }

    public void removeNegativeZeros() {
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                if (coefficients[y][x] == 0.0) {
                    coefficients[y][x] *= Math.signum(coefficients[y][x]);
                }
            }
        }
    }

    public void printMatrix() {
        for (int row = 0; row < cols; row++) {
            for (int col = 0; col < rows; col++) {
                System.out.printf("%10.8f", coefficients[row][col]);
                System.out.print(" ");
            }
            System.out.printf("%10.8f", vectorU.getCoordinateByIndex(row));
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public String toString() {
        String output = "";
        for (Double[] column : coefficients) {
            for (Double element : column) {
                output += element.toString() + " ";
            }
            output += "\n";
        }
        return output;
    }

    public void setVector(VectorU fx) {
        vectorU = fx;
    }
}
