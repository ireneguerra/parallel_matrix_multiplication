package org.example;

import org.example.operators.*;
import org.example.matrix.DenseMatrix;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInstance;

import java.util.Random;


import static org.junit.jupiter.api.Assertions.assertTrue;
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class MatrixMultiplicationTest {

    @RepeatedTest(10)
    public void testTiledMatrixMultiplication() {
        int matrixSize = 1024;
        int blockSize = 64;

        DenseMatrix matrixA = createRandomMatrix(matrixSize);
        DenseMatrix matrixB = createRandomMatrix(matrixSize);

        MatrixMultiplication multiplication1 = new TiledMatrixMultiplication(matrixA, matrixB, blockSize);
        multiplication1.multiply();
        DenseMatrix result1 = multiplication1.result();

        MatrixMultiplication multiplication2 = new SimpleMatrixMultiplication(matrixA.getMatrix(), matrixB.getMatrix());
        multiplication2.multiply();
        DenseMatrix result2 = multiplication2.result();
        assertTrue(matricesAreEqual(result1, result2));
    }
    @RepeatedTest(10)
    public void testThreadsMatrixMultiplication() {
        int matrixSize = 1024;

        DenseMatrix matrixA = createRandomMatrix(matrixSize);
        DenseMatrix matrixB = createRandomMatrix(matrixSize);

        MatrixMultiplication multiplication1 = new ThreadsMatrixMultiplication(matrixA, matrixB);
        multiplication1.multiply();
        DenseMatrix result1 = multiplication1.result();

        MatrixMultiplication multiplication2 = new SimpleMatrixMultiplication(matrixA.getMatrix(), matrixB.getMatrix());
        multiplication2.multiply();
        DenseMatrix result2 = multiplication2.result();
        assertTrue(matricesAreEqual(result1, result2));
    }

    @RepeatedTest(10)
    public void testStreamsMatrixMultiplication() {
        int matrixSize = 1024;

        DenseMatrix matrixA = createRandomMatrix(matrixSize);
        DenseMatrix matrixB = createRandomMatrix(matrixSize);

        MatrixMultiplication multiplication1 = new StreamsMatrixMultiplication(matrixA, matrixB);
        multiplication1.multiply();
        DenseMatrix result1 = multiplication1.result();

        MatrixMultiplication multiplication2 = new SimpleMatrixMultiplication(matrixA.getMatrix(), matrixB.getMatrix());
        multiplication2.multiply();
        DenseMatrix result2 = multiplication2.result();
        System.out.println();
        assertTrue(matricesAreEqual(result1, result2));
    }

    private DenseMatrix createRandomMatrix(int size) {
        double[][] matrix = new double[size][size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = random.nextDouble() * 100;
            }
        }

        return new DenseMatrix(size, matrix);
    }

    private boolean matricesAreEqual(DenseMatrix matrix1, DenseMatrix matrix2) {
        int size = matrix1.getSize();
        double epsilon = 0.0000001;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (Math.abs(matrix1.getMatrix()[i][j] - matrix2.getMatrix()[i][j]) > epsilon) {
                    return false;
                }
            }
        }

        return true;
    }
}