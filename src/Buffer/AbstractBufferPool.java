package Buffer;

import java.nio.ByteBuffer;

/**
 * Created by jrj on 17-9-15.
 */
public class AbstractBufferPool implements JBufferPool{
    //计划用这个类来管理poolchunklist，tinySubpage，smallSubpage等
    PoolChunkList<ByteBuf> q40 = new PoolChunkList<>(0,40);//0~40
    PoolChunkList<ByteBuf> q50 = new PoolChunkList<>(20,50);//20~50
    PoolChunkList<ByteBuf> q60 = new PoolChunkList<>(30,60);//30~60
    PoolChunkList<ByteBuf> q70 = new PoolChunkList<>(40,70);//40~70
    PoolChunkList<ByteBuf> q80 = new PoolChunkList<>(50,80);//50~80
    PoolChunkList<ByteBuf> q90 = new PoolChunkList<>(60,90);//60~90
    PoolChunkList<ByteBuf> q100 = new PoolChunkList<>(70,100);//70~100
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
