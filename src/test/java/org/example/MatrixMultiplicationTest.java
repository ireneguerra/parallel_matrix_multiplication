package org.example;

import org.example.operators.MatrixMultiplication;
import org.example.matrix.DenseMatrix;
import org.example.operators.SimpleMatrixMultiplication;
import org.example.operators.StreamsMatrixMultiplication;
import org.example.operators.ThreadsMatrixMultiplication;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatrixMultiplicationTest {
    @Test
    public void testThreadsMatrixMultiplication() {
        int matrixSize = 5;
        int blockSize = 2;

        DenseMatrix matrixA = createRandomMatrix(matrixSize);
        DenseMatrix matrixB = createRandomMatrix(matrixSize);

        MatrixMultiplication multiplication1 = new ThreadsMatrixMultiplication(matrixA, matrixB, blockSize);
        multiplication1.multiply();
        DenseMatrix result1 = multiplication1.result();

        MatrixMultiplication multiplication2 = new SimpleMatrixMultiplication(matrixA.getMatrix(), matrixB.getMatrix());
        multiplication2.multiply();
        DenseMatrix result2 = multiplication2.result();
        assertTrue(matricesAreEqual(result1, result2));
    }

    @Test
    public void testStreamsMatrixMultiplication() {
        int matrixSize = 5;
        int blockSize = 2;

        DenseMatrix matrixA = createRandomMatrix(matrixSize);
        DenseMatrix matrixB = createRandomMatrix(matrixSize);

        MatrixMultiplication multiplication1 = new StreamsMatrixMultiplication(matrixA, matrixB, blockSize);
        multiplication1.multiply();
        DenseMatrix result1 = multiplication1.result();

        MatrixMultiplication multiplication2 = new SimpleMatrixMultiplication(matrixA.getMatrix(), matrixB.getMatrix());
        multiplication2.multiply();
        DenseMatrix result2 = multiplication2.result();
        assertTrue(matricesAreEqual(result1, result2));
    }

    private DenseMatrix createRandomMatrix(int size) {
        return new DenseMatrix(size, new double[size][size]);
    }

    private boolean matricesAreEqual(DenseMatrix matrix1, DenseMatrix matrix2) {
        return java.util.Arrays.deepEquals(matrix1.getMatrix(), matrix2.getMatrix());
    }
}
