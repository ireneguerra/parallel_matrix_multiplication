package org.example.operators;

import org.example.matrix.CCSMatrix;
import org.example.matrix.CRSMatrix;
import org.example.matrix.DenseMatrix;
import org.example.matrix.SparseMatrix;
import org.example.matrixbuilders.SparseMatrixBuilder;
import org.example.matrixconverters.SparseToDenseMatrix;

public class SparseMatrixMultiplication implements MatrixMultiplication{
    private final CRSMatrix a;
    private final CCSMatrix b;
    private SparseMatrix sparseMatrix;

    public SparseMatrixMultiplication(CRSMatrix a, CCSMatrix b) {
        this.a = a;
        this.b = b;
    }
    @Override
    public void multiply(){
        SparseMatrixBuilder builder = new SparseMatrixBuilder(a.getSize());

        for (int i = 0; i < a.getSize(); i++) {
            for (int j = 0; j < b.getSize(); j++) {
                int ii = a.getRow_ptr().get(i);
                int iEnd = a.getRow_ptr().get(i + 1);
                int jj = b.getCol_ptr().get(j);
                int jEnd = b.getCol_ptr().get(j + 1);
                double s = 0;
                while (ii < iEnd && jj < jEnd) {
                    int aa = a.getCol().get(ii);
                    int bb = b.getRow().get(jj);
                    if (aa == bb) {
                        s += a.getValue().get(ii) * b.getValue().get(jj);
                        ii++;
                        jj++;
                    } else if (aa < bb) {
                        ii++;
                    } else {
                        jj++;
                    }

                    }
                    if (s != 0) {
                    builder.set(i, j, s);
                }

            }
        }
        sparseMatrix = builder.toMatrix();
    }
    @Override
    public DenseMatrix result() {
        SparseToDenseMatrix denseConverter = new SparseToDenseMatrix();
        return denseConverter.convert(sparseMatrix);
    }
}
