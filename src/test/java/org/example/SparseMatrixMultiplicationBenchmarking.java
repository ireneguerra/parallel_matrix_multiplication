package org.example;

import org.example.matrix.CCSMatrix;
import org.example.matrix.CRSMatrix;
import org.example.matrix.CoordinateMatrix;
import org.example.matrixconverters.CoordinateToCCS;
import org.example.matrixconverters.CoordinateToCRS;
import org.example.operators.SparseMatrixMultiplication;
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

public class SparseMatrixMultiplicationBenchmarking {
    @State(Scope.Thread)
    public static class Operands{
        private CRSMatrix a;
        private CCSMatrix b;

        @Setup
        public void setup() {
            Controller controller = new Controller();
            List<CoordinateMatrix> matrix = controller.readMatrix("src\\main\\resources\\testmatrix\\lns_3937.mtx");
            CoordinateToCRS converterCRS = new CoordinateToCRS();
            a = converterCRS.convert(matrix);
            CoordinateToCCS converterCCS = new CoordinateToCCS();
            b = converterCCS.convert(matrix);
        }

    }

    @Benchmark
    public void multiplication(Operands operands){
        new SparseMatrixMultiplication(operands.a, operands.b).multiply();
    }
}
