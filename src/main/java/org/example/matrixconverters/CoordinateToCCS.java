package org.example.matrixconverters;

import org.example.matrix.CCSMatrix;
import org.example.matrix.CoordinateMatrix;

import java.util.*;
import java.util.stream.Collectors;

public class CoordinateToCCS {
    public CCSMatrix convert(List<CoordinateMatrix> matrix) {
        Map<Integer, TreeMap<Integer, Double>> colToRowValue = new HashMap<>();

        for (CoordinateMatrix cooMatrix : matrix) {
            colToRowValue.computeIfAbsent(cooMatrix.getCol(), k -> new TreeMap<>()).put(cooMatrix.getRow(), cooMatrix.getValue());
        }

        List<Integer> col_ptr = new ArrayList<>();
        List<Integer> row = new ArrayList<>();
        List<Double> value = new ArrayList<>();

        int ptr = 0;
        col_ptr.add(ptr);

        for (int col : colToRowValue.keySet().stream().sorted().collect(Collectors.toList())) {
            TreeMap<Integer, Double> rowValue = colToRowValue.get(col);
            for (Map.Entry<Integer, Double> entry : rowValue.entrySet()) {
                row.add(entry.getKey());
                value.add(entry.getValue());
            }
            ptr += rowValue.size();
            col_ptr.add(ptr);
        }

        return new CCSMatrix(col_ptr, row, value);
    }
}
