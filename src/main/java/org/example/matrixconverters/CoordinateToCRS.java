package org.example.matrixconverters;

import org.example.matrix.CRSMatrix;
import org.example.matrix.CoordinateMatrix;

import java.util.ArrayList;
import java.util.List;

public class CoordinateToCRS {
    List<Integer> row_ptr = new ArrayList<>();
    List<Integer> col = new ArrayList<>();
    List<Double> value = new ArrayList<>();


    public CRSMatrix convert(List<CoordinateMatrix> matrix) {
        int ptr = 0;
        row_ptr.add(ptr);
        int row = matrix.get(0).getRow();
        for (CoordinateMatrix cooMatrix : matrix) {
            col.add(cooMatrix.getCol());
            value.add(cooMatrix.getValue());
            if (cooMatrix.getRow() != row) {
                row_ptr.add(ptr);
                row = cooMatrix.getRow();
            }
            ptr+=1;
        }
        row_ptr.add(ptr);
        return new CRSMatrix(row_ptr, col, value);
    }

}
