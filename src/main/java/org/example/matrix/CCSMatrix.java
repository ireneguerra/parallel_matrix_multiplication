package org.example.matrix;

import java.util.List;

public class CCSMatrix {
    private final List<Integer> col_ptr;
    private final List<Integer> row;
    private final List<Double> value;


    public CCSMatrix(List<Integer> col_ptr, List<Integer> row, List<Double> value) {
        this.col_ptr = col_ptr;
        this.row = row;
        this.value = value;
    }

    public List<Integer> getCol_ptr() {
        return col_ptr;
    }

    public List<Integer> getRow() {
        return row;
    }

    public List<Double> getValue() { return value; }

    public int getSize() { return col_ptr.size() - 1; }
}
