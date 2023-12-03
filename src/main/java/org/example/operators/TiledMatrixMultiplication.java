package org.example.operators;

import org.example.matrix.DenseMatrix;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TiledMatrixMultiplication implements MatrixMultiplication {

    private final DenseMatrix a;
    private final DenseMatrix b;
    private final DenseMatrix c;
    private final int blockSize;
    private final ExecutorService executor;


    public TiledMatrixMultiplication(DenseMatrix a, DenseMatrix b, int blockSize) {
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
            for (int i = 0; i < size; i += blockSize) {
                for (int j = 0; j < size; j += blockSize) {
                    for (int k = 0; k < size; k += blockSize) {
                        final int ii = i;
                        final int jj = j;
                        final int kk = k;
                        executor.execute(() -> multiplyBlock(ii, jj, kk));
                    }
                }
            }

            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void multiplyBlock(int row, int col, int i2) {
        int endRow = Math.min(row + blockSize, a.getSize());
        int endCol = Math.min(col + blockSize, b.getSize());
        int endI2 = Math.min(i2 + blockSize, a.getSize());
        double[][] subResult = new double[endRow - row][endCol - col];

        for (int i = row; i < endRow; i++) {
            for (int j = col; j < endCol; j++) {
                for (int k = i2; k < endI2; k++) {
                    double product = a.getMatrix()[i][k] * b.getMatrix()[k][j];
                    subResult[i - row][j - col] += product;
                }
            }
        }
        synchronized (c) {
            combineSubResults(subResult, row, col);
        }
    }

    private void combineSubResults(double[][] subResult, int rowStart, int colStart) {
        for (int i = 0; i < subResult.length; i++) {
            for (int j = 0; j < subResult[i].length; j++) {
                c.getMatrix()[rowStart + i][colStart + j] += subResult[i][j];
            }
        }
    }

    @Override
    public DenseMatrix result() {
        return c;
    }
}
