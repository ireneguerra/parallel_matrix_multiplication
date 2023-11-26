package org.example;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.readMatrix();
        controller.tiledMultiplication();
    }
    /*CoordinateToCRS crsConverter = new CoordinateToCRS();
    CRSMatrix crsMatrix = crsConverter.convert(matrix);
    CoordinateToCCS ccsConverter = new CoordinateToCCS();
    CCSMatrix ccsMatrix = ccsConverter.convert(matrix);*/

}