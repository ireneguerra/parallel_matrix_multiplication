package org.example.matrix;

import java.util.List;

public class CRSMatrix {
    private final List<Integer> row_ptr;
    private final List<Integer> col;
    private final List<Double> value;

    public CRSMatrix(List<Integer> row_ptr, List<Integer> col, List<Double> value) {
        this.row_ptr = row_ptr;
        this.col = col;
        this.value = value;
    }

    public List<Integer> getRow_ptr() {
        return row_ptr;
    }

    public List<Integer> getCol() {
        return col;
    }

    public List<Double> getValue() {
        return value;
    }

    public int getSize() {
        return row_ptr.size() - 1;
    }
}
