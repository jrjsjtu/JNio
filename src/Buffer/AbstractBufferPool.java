package Buffer;

import java.nio.ByteBuffer;

/**
 * Created by jrj on 17-9-15.
 */
public class AbstractBufferPool implements JBufferPool{
    //计划用这个类来管理poolchunklist，tinySubpage，smallSubpage等
    PoolSubpage<ByteBuf>[] tinySubpages,smallSubpages;
    PoolChunkList<ByteBuf> q30 = new PoolChunkList<>(0,30);//0~30
    PoolChunkList<ByteBuf> q50 = new PoolChunkList<>(20,50);//20~50
    PoolChunkList<ByteBuf> q60 = new PoolChunkList<>(30,60);//30~60
    PoolChunkList<ByteBuf> q70 = new PoolChunkList<>(40,70);//40~70
    PoolChunkList<ByteBuf> q80 = new PoolChunkList<>(50,80);//50~80
    PoolChunkList<ByteBuf> q90 = new PoolChunkList<>(60,90);//60~90
    PoolChunkList<ByteBuf> q100 = new PoolChunkList<>(70,100);//70~100
    int pageSize,chunkSize;
    AbstractBufferPool(int pageSize,int chunkSize){
        this.pageSize = pageSize;
        this.chunkSize = chunkSize;
        q30.setNext(q50);q50.setNext(q60);q60.setNext(q70);q70.setNext(q80);q80.setNext(q90);q90.setNext(q100);
    }

    @Override
    public ByteBuf AllocateBuffer(int normCapacity) {
        ByteBuf byteBuf = null;
        //这里是在已经分配的chunk中分配，如果这些list中没有可用的chunk了的话，就申请新的chunk
        if (q50.allocate(byteBuf,normCapacity) || q60.allocate(byteBuf,normCapacity) || q30.allocate(byteBuf,normCapacity)
                || q70.allocate(byteBuf,normCapacity) || q80.allocate(byteBuf,normCapacity) || q90.allocate(byteBuf,normCapacity)){
            return byteBuf;
        }
        JChunk newChunk = new AbstractChunk(pageSize,chunkSize,ByteBuffer.allocateDirect(chunkSize));
        byteBuf = newChunk.ApplyBufferByCapacity(normCapacity);
        q30.addChunk(newChunk);
        return byteBuf;
    }

    @Override
    public void AddBuffer(JBuffer buffer) {

    }

    @Override
    public void AddTinyBuffer(JBuffer buffer) {

    }

    @Override
    public void AddSmallBuffer(JBuffer buffer) {

    }
}
