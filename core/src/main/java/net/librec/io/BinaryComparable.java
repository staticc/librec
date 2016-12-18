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

/**
 * @author WangYuFeng 
 */
public abstract class BinaryComparable implements Comparable<BinaryComparable> {

    public abstract int getLength();

    public abstract byte[] getBytes();


    @Override
    public boolean equals(Object other) {
        if (!(other instanceof BinaryComparable)) {
            return false;
        }
        BinaryComparable that = (BinaryComparable) other;
        if (this.getLength() != that.getLength()) {
            return false;
        }
        return this.compareTo(that) == 0;
    }
    
    public int compareTo(BinaryComparable other) {
        if (this == other) {
            return 0;
        }
        return compareBytes(getBytes(), 0, getLength(),
                other.getBytes(), 0, other.getLength());
    }
    
    public static int compareBytes(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
        int end1 = s1 + l1;
        int end2 = s2 + l2;
        for (int i = s1, j = s2; i < end1 && j < end2; i++, j++) {
            int a = (b1[i] & 0xff);
            int b = (b2[j] & 0xff);
            if (a != b) {
                return a - b;
            }
        }
        return l1 - l2;
    }
}