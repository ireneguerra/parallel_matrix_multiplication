package org.example.matrix;

public class DenseMatrix {
    private final int size;
    private final double[][] matrix;

    public DenseMatrix(int size, double[][] matrix) {
        this.size = size;
        this.matrix = matrix;
    }

    public int getSize() {
        return size;
    }

    public double[][] getMatrix() {
        return matrix;
    }
}
