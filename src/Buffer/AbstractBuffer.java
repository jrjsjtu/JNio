package Buffer;

import java.nio.ByteBuffer;

/**
 * Created by jrj on 17-9-15.
 */
public class AbstractBuffer implements JBuffer {
    //代理模式的AbstractBuffer
    ByteBuffer innerBuffer;
    long handle;
    JChunk chunk;
    //这个parentBuffer只有在当前buffer为subpage时才用的到
    JBuffer parentBuffer;
    AbstractBuffer(ByteBuffer innerBuffer,long handle,JChunk chunk){
        this.innerBuffer = innerBuffer;
        this.handle = handle;
        this.chunk = chunk;
    }
    //这个构造函数只有在当前buffer为subpage时才用的到
    AbstractBuffer(JBuffer jBuffer,ByteBuffer innerBuffer){
        this.innerBuffer = innerBuffer;
        this.parentBuffer = jBuffer;
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

    @Override
    public void free() {
        chunk.free(handle);
    }

    @Override
    public ByteBuf allocateSubpage(int startPostion, int size) {
        innerBuffer.position(startPostion);
        innerBuffer.limit(startPostion+size);
        ByteBuffer tmp = innerBuffer.slice();
        return new ByteBuf(this,tmp);
    }
}
