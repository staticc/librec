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

import java.io.*;

public class DataOutputBuffer extends DataOutputStream {

    private static class Buffer extends ByteArrayOutputStream {

        public byte[] getData() {
            return buf;
        }

        public int getLength() {
            return count;
        }

        public Buffer() {
            super();
        }

        public Buffer(int size) {
            super(size);
        }

        public void write(DataInput in, int len) throws IOException {
            int newcount = count + len;
            if (newcount > buf.length) {
                byte newbuf[] = new byte[Math.max(buf.length << 1, newcount)];
                System.arraycopy(buf, 0, newbuf, 0, count);
                buf = newbuf;
            }
            in.readFully(buf, count, len);
            count = newcount;
        }
    }

    private Buffer buffer;

    public DataOutputBuffer() {
        this(new Buffer());
    }

    public DataOutputBuffer(int size) {
        this(new Buffer(size));
    }

    private DataOutputBuffer(Buffer buffer) {
        super(buffer);
        this.buffer = buffer;
    }

    public byte[] getData() {
        return buffer.getData();
    }

    public int getLength() {
        return buffer.getLength();
    }

    public DataOutputBuffer reset() {
        this.written = 0;
        buffer.reset();
        return this;
    }

    public void write(DataInput in, int length) throws IOException {
        buffer.write(in, length);
    }
}
