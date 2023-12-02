package org.example.operators;

import org.example.matrix.DenseMatrix;

public class SimpleMatrixMultiplication implements MatrixMultiplication{
    private final double[][] a;
    private final double[][] b;
    private final double[][] c;

    public SimpleMatrixMultiplication(double[][] a, double[][] b) {
        this.a = a;
        this.b = b;
        this.c = new double[a.length][a.length];
    }
    @Override
    public void multiply() {
        assert a.length == b.length;
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
    }

    @Override
    public DenseMatrix result() {
        return new DenseMatrix(c.length, c);
    }
}
