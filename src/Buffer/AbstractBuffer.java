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
    PoolSubpage parentPoolSubpage;
    int size;
    AbstractBuffer(ByteBuffer innerBuffer,long handle,JChunk chunk){
        this.innerBuffer = innerBuffer;
        this.handle = handle;
        this.chunk = chunk;
    }
    //这个构造函数只有在当前buffer为subpage时才用的到
    AbstractBuffer(ByteBuffer innerBuffer,PoolSubpage parentPoolSubpage,int size){
        this.innerBuffer = innerBuffer;
        this.parentPoolSubpage = parentPoolSubpage;
        this.size = size;
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
        //此处应该有threadLocal的考虑
        if (parentPoolSubpage == null){
            chunk.free(handle);
        }else{
            if (!AbstractAllocator.value.get().appendSubpage(this,size)){
                parentPoolSubpage.freeSubpage(handle);
            }
        }
    }

    @Override
    public ByteBuf allocateSubpage(int startPostion, int size) {
        innerBuffer.position(startPostion);
        innerBuffer.limit(startPostion+size);
        ByteBuffer tmp = innerBuffer.slice();
        return new ByteBuf(tmp,parentPoolSubpage,size);
    }
}
