package org.example.operators;

import org.example.matrix.DenseMatrix;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TiledMatrixMultiplication implements MatrixMultiplication{

    private DenseMatrix a;
    private DenseMatrix b;
    private DenseMatrix c;
    private int blockSize;

    private ExecutorService executor;

    public TiledMatrixMultiplication(DenseMatrix a, DenseMatrix b, int blockSize)  {
        this.a = a;
        this.b = b;
        this.blockSize = blockSize;
        this.c = new DenseMatrix(a.getSize(), new double[a.getSize()][a.getSize()]);
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

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
            executor.awaitTermination(1000, TimeUnit.SECONDS);
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

    public DenseMatrix getResult() {
        return c;
    }
}
