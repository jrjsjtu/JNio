package Buffer;

import java.nio.ByteBuffer;

/**
 * Created by jrj on 17-9-15.
 */
public class AbstractChunk implements JChunk {
    //这里简化一下实现，当要申请的页大于pageSize时，只能申请pageSize的整数倍。
    //考虑到实现的复杂度，先做这样的简化。
    //实际上通过我看的博客，感觉netty也是这么实现的。那么8193的申请内存就会被安排一块8192×2的内存。。惊了
    int pageSize;
    byte[] memoryMap,depthMap;
    int usage = 0;
    int pageNum;
    int pageShifts;
    int maxOrder;
    int log2ChunkSize;
    ByteBuffer chunkByteBuffer;
    JBufferPool jBufferPool;
    private final byte unusable;
    //AbstractChunk(int pageSize,int chunkSize,JBufferPool jBufferPool){
    AbstractChunk(int pageSize,int chunkSize){
        this.pageSize = pageSize;
        pageNum = chunkSize / pageSize;
        maxOrder = log2(pageNum);
        unusable = (byte) (maxOrder + 1);
        pageShifts = log2(pageSize);
        //memoryMap中的值代表第几层可用
        memoryMap = new byte[pageNum<<1];
        depthMap = new byte[pageNum<<1];
        log2ChunkSize = log2(chunkSize);
        chunkByteBuffer = JAllocator.allocateDirect(chunkSize);
        //this.jBufferPool = jBufferPool;
        int memoryMapIndex = 1;
        for (int d = 0; d <= maxOrder; ++ d) { // move down the tree one level at a time
            int depth = 1 << d;
            for (int p = 0; p < depth; ++ p) {
                // in each level traverse left to right and set value to the depth of subtree
                memoryMap[memoryMapIndex] = (byte) d;
                depthMap[memoryMapIndex] = (byte) d;
                memoryMapIndex ++;
            }
        }
    }

    @Override
    public int GetUsage() {
        return usage*100/pageNum;
    }

    @Override
    public JBuffer ApplyBuffer(int capacity) {
        int depth = getDepth(capacity);
        int handle = allocateNode(depth);
        return null;
    }

    private int allocateNode(int d) {
        int id = 1;
        int initial = - (1 << d); // has last d bits = 0 and rest all = 1
        byte val = value(id);
        if (val > d) { // unusable
            return -1;
        }
        while (val < d || (id & initial) == 0){ // id & initial == 1 << d for all ids at depth d, for < d it is 0
            id <<= 1;
            val = value(id);
            if (val > d) {
                //这里的id ^= 1等价于id += 1,因为可以确定这里的id是个偶数不用考虑进位的问题
                //但同时，如果id是右节点，id^=1等价与id -= 1,总之起到了找兄弟节点的目的
                id ^= 1;
                val = value(id);
            }
        }
        byte value = value(id);
        assert value == d && (id & initial) == 1 << d : String.format("val = %d, id & initial = %d, d = %d",
                value, id & initial, d);
        setValue(id, unusable); // mark as unusable
        updateParentsAlloc(id);
        return id;
    }

    private void updateParentsAlloc(int id) {
        while (id > 1) {
            int parentId = id >>> 1;
            byte val1 = value(id);
            byte val2 = value(id ^ 1);
            byte val = val1 < val2 ? val1 : val2;
            setValue(parentId, val);
            id = parentId;
        }
    }

    private byte value(int id){
        return memoryMap[id];
    }

    private void setValue(int id, byte val) {
        memoryMap[id] = val;
    }

    private int getDepth(int normalCapacity){
        return maxOrder - (log2(normalCapacity) - pageShifts);
    }

    static int log2(int num){
        return 31-Integer.numberOfLeadingZeros(num);
    }

    private int runLength(int id) {
        // represents the size in #bytes supported by node 'id' in the tree
        return 1 << log2ChunkSize - depth(id);
    }

    private byte depth(int id) {
        return depthMap[id];
    }

    private void updateParentsFree(int id) {
        int logChild = depth(id) + 1;
        while (id > 1) {
            int parentId = id >>> 1;
            byte val1 = value(id);
            byte val2 = value(id ^ 1);
            logChild -= 1;
            // in first iteration equals log, subsequently reduce 1 from logChild as we traverse up
            if (val1 == logChild && val2 == logChild) {
                //当两个子节点都可分配时，该节点变回自己所在层的depth，表示该节点也可被分配
                setValue(parentId, (byte) (logChild - 1));
            } else {
                // 否则与上面的updateParentsAlloc逻辑相同
                byte val = val1 < val2 ? val1 : val2;
                setValue(parentId, val);
            }
            id = parentId;
        }
    }
    public static void main(String[] args){
        AbstractChunk abstractChunk = new AbstractChunk(8192,8192*2048);
        System.out.println(abstractChunk.allocateNode(abstractChunk.getDepth(8192*2)));
    }
}
