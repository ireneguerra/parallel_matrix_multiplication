package org.example;


import org.example.matrix.*;
import org.example.matrixbuilders.CoordinateBuilder;
import org.example.matrixconverters.CoordinateToCCS;
import org.example.matrixconverters.CoordinateToCRS;
import org.example.matrixconverters.CoordinateToDense;
import org.example.operators.TiledMatrixMultiplication;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    CoordinateBuilder reader = new CoordinateBuilder();
    List<CoordinateMatrix> matrix = new ArrayList<>();

    public void readMatrix() {
        try {
            matrix = reader.matrixReader("src\\main\\resources\\mymatrix.mtx");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void tiledMultiplication() {
        CoordinateToDense converter = new CoordinateToDense();
        System.out.println("Matrix size: " + Math.sqrt(matrix.size()));
        DenseMatrix denseMatrix = converter.convertToDenseMatrix(matrix, 4);
        for (int i = 0; i < denseMatrix.getSize(); i++) {
            for (int j = 0; j < denseMatrix.getSize(); j++) {
                System.out.print(denseMatrix.getMatrix()[i][j] + " ");
            }
            System.out.println();
        }
        TiledMatrixMultiplication tiledMatrixMultiplication = new TiledMatrixMultiplication(denseMatrix, denseMatrix, 4);
        double startTime = System.currentTimeMillis();
        tiledMatrixMultiplication.multiply();
        double endTime = System.currentTimeMillis();
        double elapsedTime = endTime - startTime;
        DenseMatrix result = tiledMatrixMultiplication.getResult();
        System.out.println(elapsedTime / 1000 + " seconds");
        System.out.println("Result:");
        for (int i = 0; i < result.getSize(); i++) {
            for (int j = 0; j < result.getSize(); j++) {
                System.out.print(result.getMatrix()[i][j] + " ");
            }
            System.out.println();
        }


    }
}
