package org.example;

import org.example.matrix.DenseMatrix;
import org.example.operators.StreamsMatrixMultiplication;
import org.example.operators.ThreadsMatrixMultiplication;

public class Main {
    public static void main(String[] args) {
        double[][] matrixA = {
                {2, 8, 3, 8},
                {5, 6, 0, 9},
                {3, 8, 0, 0},
                {2, 5, 1, 8}
        };

        double[][] matrixB = {
                {8, 2, 0, 6},
                {4, 4, 8, 3},
                {9, 3, 2, 8},
                {5, 3, 0, 7}
        };
        DenseMatrix a = new DenseMatrix(4, matrixA);
        DenseMatrix b = new DenseMatrix(4, matrixB);
        ThreadsMatrixMultiplication matrix = new ThreadsMatrixMultiplication(a, b, 2);
        matrix.multiply();
        DenseMatrix matrix1 = matrix.result();
        for (int i = 0; i < matrix1.getSize(); i++) {
            for (int j = 0; j < matrix1.getSize(); j++) {
                System.out.print(matrix1.getMatrix()[i][j] + " ");
            }
            System.out.println();
        }
        StreamsMatrixMultiplication matrixStream = new StreamsMatrixMultiplication(a, b, 2);
        matrixStream.multiply();
        DenseMatrix matrix2 = matrixStream.result();
        for (int i = 0; i < matrix2.getSize(); i++) {
            for (int j = 0; j < matrix2.getSize(); j++) {
                System.out.print(matrix2.getMatrix()[i][j] + " ");
            }
            System.out.println();
        }
    }
}