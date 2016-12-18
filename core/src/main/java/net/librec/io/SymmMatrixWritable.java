/**
 * Copyright (C) 2016 LibRec
 * <p>
 * This file is part of LibRec.
 * LibRec is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * LibRec is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with LibRec. If not, see <http://www.gnu.org/licenses/>.
 */
package net.librec.io;

import net.librec.math.structure.SymmMatrix;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * SymmMatrix Writable
 *
 * @author YuFeng Wang
 */
public class SymmMatrixWritable implements Writable {

    private SymmMatrix value;

    public SymmMatrixWritable() {
    }

    public SymmMatrixWritable(SymmMatrix symmMatrix) {
        this.value = symmMatrix;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        if (value != null && value.getData() != null && value.getData().size() > 0 && value.getDim() > 0) {
            out.writeInt(value.getDim());
            out.writeInt(value.getData().size());
            Set<Integer> rowKeySet = value.getData().rowKeySet();
            for (Integer row : rowKeySet) {
                Map<Integer, Double> colMap = value.getData().row(row);
                for (Map.Entry<Integer, Double> entry : colMap.entrySet()) {
                    out.writeInt(row);
                    out.writeInt(entry.getKey());
                    out.writeDouble(entry.getValue());
                }
            }
        }
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        int dim = in.readInt();
        if (dim > 0) {
            value = new SymmMatrix(dim);
            int dataSize = in.readInt();
            for (int i = 0; i < dataSize; i++) {
                int row = in.readInt();
                int col = in.readInt();
                double val = in.readDouble();
                value.set(row, col, val);
            }
        }
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(Object value) {
        this.value = (SymmMatrix) value;
    }

}
