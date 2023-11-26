package org.example.matrixbuilders;
import org.example.matrix.DenseMatrix;

public class DenseMatrixBuilder {
    private final int size;
    private final double[][] matrix;

    public DenseMatrixBuilder(int size) {
        this.size = size;
        this.matrix = new double[size][size];
    }

    public void set(int row, int col, double value) {
        matrix[row][col] = value;
    }

    public DenseMatrix toMatrix() {
        return new DenseMatrix(size, matrix);
    }
}
