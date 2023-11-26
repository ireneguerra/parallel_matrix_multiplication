package org.example.matrix;
import java.util.List;

public class SparseMatrix {
    protected final int size;
    protected final List<CoordinateMatrix> coordinates;

    public SparseMatrix(int size, List<CoordinateMatrix> coordinates) {
        this.size = size;
        this.coordinates = coordinates;
    }

    public int getSize() {
        return size;
    }

    public List<CoordinateMatrix> getCoordinates() {
        return coordinates;
    }
}