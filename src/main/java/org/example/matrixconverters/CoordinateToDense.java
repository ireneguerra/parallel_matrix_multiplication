package org.example.matrixconverters;

import org.example.matrix.CoordinateMatrix;
import org.example.matrix.DenseMatrix;

import java.util.List;

public class CoordinateToDense {

    public DenseMatrix convertToDenseMatrix(List<CoordinateMatrix> coordinateMatrixList, int size) {
        double[][] denseMatrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                denseMatrix[i][j] = 0.0;
            }
        }
        for (CoordinateMatrix coordinate : coordinateMatrixList) {
            int row = coordinate.getRow();
            int col = coordinate.getCol();
            double value = coordinate.getValue();
            denseMatrix[row][col] = value;
        }
        return new DenseMatrix(size, denseMatrix);
    }
}