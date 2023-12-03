package org.example;

import org.example.operators.*;
import org.example.matrix.DenseMatrix;
import org.junit.Test;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatrixMultiplicationTest {

    @Test
    public void testTiledMatrixMultiplication() {
        int matrixSize = 5;
        int blockSize = 2;

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
    @Test
    public void testThreadsMatrixMultiplication() {
        int matrixSize = 5;

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

    @Test
    public void testStreamsMatrixMultiplication() {
        int matrixSize = 5;

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
        DecimalFormat df = new DecimalFormat("#.##########", DecimalFormatSymbols.getInstance(Locale.US));
        int size = matrix1.getSize();
        double[][] roundedMatrix1 = new double[size][size];
        double[][] roundedMatrix2 = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double originalValue1 = matrix1.getMatrix()[i][j];
                double originalValue2 = matrix2.getMatrix()[i][j];

                String formattedValue1 = df.format(originalValue1);
                String formattedValue2 = df.format(originalValue2);

                roundedMatrix1[i][j] = Double.parseDouble(formattedValue1);
                roundedMatrix2[i][j] = Double.parseDouble(formattedValue2);
            }
        }
        printMatrix(roundedMatrix1);
        System.out.println();
        printMatrix(roundedMatrix2);
        System.out.println();
        return java.util.Arrays.deepEquals(roundedMatrix1, roundedMatrix2);
    }


    private void printMatrix(double[][] matrix) {
        int size = matrix.length;
        for (double[] doubles : matrix) {
            for (int j = 0; j < size; j++) {
                System.out.print(doubles[j] + " ");
            }
            System.out.println();
        }
    }
}