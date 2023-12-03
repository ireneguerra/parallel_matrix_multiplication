package org.example;


import org.example.matrix.*;
import org.example.matrixbuilders.CoordinateBuilder;

import java.io.IOException;
import java.util.List;

public class Controller {
    CoordinateBuilder reader = new CoordinateBuilder();

    public List<CoordinateMatrix> readMatrix(String path) {
        try {
            return reader.matrixReader(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getMatrixSize() {
        return reader.numRows;
    }
}