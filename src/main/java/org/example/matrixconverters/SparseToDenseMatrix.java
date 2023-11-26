package org.example.matrixconverters;

import org.example.matrix.CoordinateMatrix;
import org.example.matrix.DenseMatrix;
import org.example.matrix.SparseMatrix;
import org.example.matrixbuilders.DenseMatrixBuilder;

public class SparseToDenseMatrix {
    public DenseMatrix convert(SparseMatrix sparseMatrix){
        DenseMatrixBuilder denseBuilder = new DenseMatrixBuilder(sparseMatrix.getSize());
        for (CoordinateMatrix coordinate : sparseMatrix.getCoordinates()) {
            int row = coordinate.getRow();
            int col = coordinate.getCol();
            double value = coordinate.getValue();
            denseBuilder.set(row, col, value);
        }
        return denseBuilder.toMatrix();
    }


}
