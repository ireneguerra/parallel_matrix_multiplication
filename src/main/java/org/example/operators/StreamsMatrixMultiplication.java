package org.example.operators;

import org.example.matrix.DenseMatrix;
import java.util.stream.IntStream;


public class StreamsMatrixMultiplication implements MatrixMultiplication {

    private final DenseMatrix a;
    private final DenseMatrix b;
    private final DenseMatrix c;
    private int size;

    public StreamsMatrixMultiplication(DenseMatrix a, DenseMatrix b) {
        this.a = a;
        this.b = b;
        this.c = new DenseMatrix(a.getSize(), new double[a.getSize()][a.getSize()]);
    }

    @Override
    public void multiply() {
        size = a.getSize();
        IntStream.range(0, size).parallel().forEach(i -> {
            for (int k = 0; k < size; k++)
                for (int j = 0; j < size; j++)
                    c.getMatrix()[i][j] += a.getMatrix()[i][k] * b.getMatrix()[k][j];
        });

    }

    @Override
    public DenseMatrix result() {
        return c;
    }
}
