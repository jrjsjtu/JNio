package Buffer;

import java.nio.ByteBuffer;

/**
 * Created by jrj on 17-9-15.
 */
public class AbstractChunk implements JChunk {
    //这里简化一下实现，当要申请的页大于pageSize时，只能申请pageSize的整数倍。
    //考虑到实现的复杂度，先做这样的简化。
    int pageSize;
    int[] memoryMap;
    int usage = 0;
    int pageNum;
    int pageShifts;
    ByteBuffer chunkByteBuffer;
    JBufferPool jBufferPool;
    AbstractChunk(int pageSize,int chunkSize,JBufferPool jBufferPool){
        this.pageSize = pageSize;
        pageNum = chunkSize / pageSize;
        pageShifts = log2(pageSize);
        memoryMap = new int[pageNum<<1];
        chunkByteBuffer = JAllocator.allocateDirect(chunkSize);
        this.jBufferPool = jBufferPool;
        for(int i=1;i<memoryMap.length;i++){
            memoryMap[i] = log2(i)+1;
        }
    }

    @Override
    public int GetUsage() {
        return usage*100/pageNum;
    }

    @Override
    public JBuffer ApplyBuffer(int capacity) {
        return null;
    }

    protected int getDepth(int normalCapacity){
        return -1;
    }

    protected int searchIdx(int Idx){
        return -1;
    }

    static int log2(int num){
        return 31-Integer.numberOfLeadingZeros(num);
    }

    public static void main(String[] args){
    }
}
