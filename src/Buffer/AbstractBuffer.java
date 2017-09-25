package Buffer;

import java.nio.ByteBuffer;

/**
 * Created by jrj on 17-9-15.
 */
public abstract class AbstractBuffer implements JBuffer {
    //代理模式的AbstractBuffer
    ByteBuffer innerBuffer;
    long handle;
    AbstractBuffer(ByteBuffer innerBuffer,long handle){
        this.innerBuffer = innerBuffer;
        this.handle = handle;
    }
    @Override
    public void put() {

    }
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
