package org.example;

import org.example.matrix.CoordinateMatrix;
import org.example.matrix.DenseMatrix;
import org.example.matrixconverters.CoordinateToDense;
import org.example.operators.StreamsMatrixMultiplication;
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
public class StreamsMatrixMultiplicationBenchmarking {
    @State(Scope.Thread)
    public static class Operands{
        private DenseMatrix a;
        private DenseMatrix b;

        @Setup
        public void setup() {
            Controller controller = new Controller();
            List<CoordinateMatrix> matrix = controller.readMatrix("src\\main\\resources\\testmatrix\\gre_512.mtx");
            int matrix_size = controller.getMatrixSize();
            CoordinateToDense converter = new CoordinateToDense();
            a = converter.convertToDenseMatrix(matrix, matrix_size);
            b = converter.convertToDenseMatrix(matrix, matrix_size);
        }

    }

        @Benchmark
        public void multiplication(Operands operands){
            new StreamsMatrixMultiplication(operands.a, operands.b).multiply();
        }
    }

