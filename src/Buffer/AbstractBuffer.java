package Buffer;

import java.nio.ByteBuffer;

/**
 * Created by jrj on 17-9-15.
 */
public abstract class AbstractBuffer implements JBuffer {
    ByteBuffer innerBuffer;
    AbstractBuffer(ByteBuffer innerBuffer){
        this.innerBuffer = innerBuffer;
    }
    @Override
    public void put() {

    }
    ThreadLocal
    @Override
    public void get(byte[] bytes) {
        innerBuffer.get(bytes);
    }

    @Override
    public void flip() {
        innerBuffer.flip();
    }

    @Override
    public void rewind() {
        innerBuffer.rewind();
    }

    @Override
    public void clear() {
        innerBuffer.clear();
    }
}
