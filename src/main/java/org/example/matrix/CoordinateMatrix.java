package org.example.matrix;

public class CoordinateMatrix {
    private final int row;
    private final int col;
    private final double value;

    public CoordinateMatrix(int row, int col, double value) {
        this.row = row;
        this.col = col;
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public double getValue() {
        return value;
    }


}
