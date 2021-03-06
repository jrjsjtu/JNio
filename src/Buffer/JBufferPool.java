package Buffer;

/**
 * Created by jrj on 17-9-15.
 */
public interface JBufferPool {
    ByteBuf AllocateBuffer(int normCapacity);
    void AddBuffer(JBuffer buffer);
    void AddTinyBuffer(JBuffer buffer);
    void AddSmallBuffer(JBuffer buffer);
    void addPool(PoolSubpage poolSubpage,int elementSize);
    void removePool(PoolSubpage poolSubpage,int elementSize);
}
