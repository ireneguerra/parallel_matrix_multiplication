package org.example.matrixbuilders;
import org.example.matrix.CoordinateMatrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoordinateBuilder {
    List<CoordinateMatrix> matrix = new ArrayList<>();
    int numRows;
    int numCols;

    public List<CoordinateMatrix> matrixReader(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("%")) {
                continue;
            }
            String[] parts = line.trim().split("\\s+");
            if (parts.length >= 2) {
                numRows = Integer.parseInt(parts[0]);
                numCols = Integer.parseInt(parts[1]);
                System.out.println("Matrix size: " + numRows + "x" + numCols);
                break;
            }
        }
        while ((line = reader.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length >= 3) {
                int row = Integer.parseInt(parts[0]) - 1;
                int col = Integer.parseInt(parts[1]) - 1;
                double value = Double.parseDouble(parts[2]);
                matrix.add(new CoordinateMatrix(row, col, value));
            }
        }
        reader.close();
        System.out.println("Density: " + (matrix.size() / (double) (numRows * numCols)) * 100 + "%");
        return matrix;
    }


}
