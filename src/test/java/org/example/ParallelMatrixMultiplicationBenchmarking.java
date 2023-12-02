package org.example;
import org.example.matrix.CoordinateMatrix;
import org.example.matrix.DenseMatrix;
import org.example.matrixconverters.CoordinateToDense;
import org.example.operators.ThreadsMatrixMultiplication;
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
public class ParallelMatrixMultiplicationBenchmarking {

    @State(Scope.Thread)
    public static class Operands{
        private int block_size = 64;
        private DenseMatrix a;
        private DenseMatrix b;

        @Setup
        public void setup() {
            Controller controller = new Controller();
            List<CoordinateMatrix> matrix = controller.readMatrix("src\\main\\resources\\testmatrix\\1138_bus.mtx");
            int matrix_size = controller.getMatrixSize();
            CoordinateToDense converter = new CoordinateToDense();
            a = converter.convertToDenseMatrix(matrix, matrix_size);
            b = converter.convertToDenseMatrix(matrix, matrix_size);
        }

    }

    @Benchmark
    public void multiplication(Operands operands){
        new ThreadsMatrixMultiplication(operands.a, operands.b, operands.block_size).multiply();
    }
}










