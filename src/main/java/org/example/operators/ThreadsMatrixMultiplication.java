package org.example.operators;

import org.example.matrix.DenseMatrix;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadsMatrixMultiplication implements MatrixMultiplication {

    private final DenseMatrix a;
    private final DenseMatrix b;
    private final double[][] c;
    private int size;

    private final ExecutorService executor;

    public ThreadsMatrixMultiplication(DenseMatrix a, DenseMatrix b) {
        this.a = a;
        this.b = b;
        this.c = new double[a.getSize()][a.getSize()];
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public void multiply() {
        size = a.getSize();
        for (int i = 0; i < size; i++) {
            submit(a.getMatrix(), b.getMatrix(), c, i);
        }
        try {
            executor.shutdown();
            executor.awaitTermination(1000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void submit(double[][] a, double[][] b, double[][] c, int i) {
        executor.submit(() -> {
            for (int k = 0; k < size; k++) {
                for (int j = 0; j < size; j++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        });
    }

    @Override
    public DenseMatrix result() {
        return new DenseMatrix(size, c);
    }
}
