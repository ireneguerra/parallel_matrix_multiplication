package org.example;

import org.example.matrix.CoordinateMatrix;
import org.example.matrix.DenseMatrix;
import org.example.matrixconverters.CoordinateToDense;
import org.example.operators.SimpleMatrixMultiplication;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1)
@Warmup(iterations = 1, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 10, timeUnit = TimeUnit.SECONDS)
@Timeout(time = 10, timeUnit = TimeUnit.MINUTES)
@Threads(1)
public class SimpleMatrixMultiplicationBenchmarking {
    @State(Scope.Thread)
    public static class Operands{
        private double[][] a;
        private double[][] b;

        @Setup
        public void setup() {
            Controller controller = new Controller();
            List<CoordinateMatrix> matrix = controller.readMatrix("src\\main\\resources\\testmatrix\\bayer03.mtx");
            int matrix_size = controller.getMatrixSize();
            CoordinateToDense converter = new CoordinateToDense();
            DenseMatrix denseMatrix = converter.convertToDenseMatrix(matrix, matrix_size);
            a = denseMatrix.getMatrix();
            b = denseMatrix.getMatrix();

        }

    }

    @Benchmark
    public void multiplication(Operands operands){
        new SimpleMatrixMultiplication(operands.a, operands.b).multiply();
    }
}

