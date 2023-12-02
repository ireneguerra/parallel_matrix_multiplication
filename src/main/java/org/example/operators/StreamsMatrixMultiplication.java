package org.example.operators;

import org.example.matrix.DenseMatrix;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class StreamsMatrixMultiplication implements MatrixMultiplication {

    private final DenseMatrix a;
    private final DenseMatrix b;
    private final DenseMatrix c;
    private final int blockSize;

    private final ExecutorService executor;

    public StreamsMatrixMultiplication(DenseMatrix a, DenseMatrix b, int blockSize) {
        this.a = a;
        this.b = b;
        this.blockSize = blockSize;
        this.c = new DenseMatrix(a.getSize(), new double[a.getSize()][a.getSize()]);
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public void multiply() {
        int size = a.getSize();
        try {
            IntStream.range(0, size).parallel().forEach(i -> {
                IntStream.range(0, size).parallel().forEach(j -> {
                    IntStream.range(0, size).parallel().forEach(k -> {
                        multiplyBlock(i * blockSize, j * blockSize, k * blockSize);
                    });
                });
            });

            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void multiplyBlock(int row, int col, int i2) {
        int endRow = Math.min(row + blockSize, a.getSize());
        int endCol = Math.min(col + blockSize, b.getSize());
        int endI2 = Math.min(i2 + blockSize, a.getSize());

        for (int i = row; i < endRow; i++) {
            for (int j = col; j < endCol; j++) {
                for (int k = i2; k < endI2; k++) {
                    c.getMatrix()[i][j] += a.getMatrix()[i][k] * b.getMatrix()[k][j];
                }
            }
        }
    }
    @Override
    public DenseMatrix result() {
        return c;
    }
}
