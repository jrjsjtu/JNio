package Buffer;

import java.nio.ByteBuffer;

import static Buffer.JChunk.rSmallMask;
import static Buffer.JChunk.rTinyMask;

/**
 * Created by jrj on 17-9-15.
 */
public abstract class AbstractAllocator implements JAllocator {
    JBufferPool jBufferPool;

    AbstractAllocator(int pageSize, int chunkSize, ByteBuffer byteBuffer){
        jBufferPool = new DefaultBufferPool(pageSize,chunkSize,byteBuffer);
    }

    @Override
    public JBuffer allocator(int capacity) {
        int normalCapacity = normalizeCapacity(capacity);
        JBuffer outBuffer;
        //先尝试在本地缓存取得内存，失败后向BufferPool申请内存
        //本地缓存用ThreadLocal实现
        if (isTiny(normalCapacity)){
            outBuffer = findTinyInThreadlocal(normalCapacity);
        }else if(isSmall(normalCapacity)){
            outBuffer = findSmallInThreadlocal(normalCapacity);
        }else{
            outBuffer = findNormalInThreadlocal(normalCapacity);
        }
        if (outBuffer == null){
            outBuffer = jBufferPool.AllocateBuffer(normalCapacity);
        }
        return outBuffer;
    }

    public JBuffer allocatTiny(int capacity){
        return null;
    }

    public JBuffer allocatSmall(int capacity){
        return null;
    }

    public JBuffer allocatNormal(int capacity){
        return null;
    }

    public JBuffer findTinyInThreadlocal(int capacity){
        return null;
    }

    public JBuffer findSmallInThreadlocal(int capacity){
        return null;
    }

    public JBuffer findNormalInThreadlocal(int capacity){
        return null;
    }

    protected int normalizeCapacity(int reqCapacity){
        if (!isTiny(reqCapacity)) { // >= 512
            // Doubled
            //这里就是
            int normalizedCapacity = reqCapacity;
            normalizedCapacity --;
            normalizedCapacity |= normalizedCapacity >>>  1;
            normalizedCapacity |= normalizedCapacity >>>  2;
            normalizedCapacity |= normalizedCapacity >>>  4;
            normalizedCapacity |= normalizedCapacity >>>  8;
            normalizedCapacity |= normalizedCapacity >>> 16;
            normalizedCapacity ++;

            if (normalizedCapacity < 0) {
                normalizedCapacity >>>= 1;
            }
            return normalizedCapacity;
        }

        if ((reqCapacity & 15) == 0) {
            return reqCapacity;
        }

        return (reqCapacity & ~15) + 16;
    }

    protected boolean isTiny(int capacity){
        return ((capacity&rTinyMask)==0)?true:false;
    }

    protected boolean isSmall(int capacity){
        return ((capacity&rSmallMask)==0)?true:false;
    }
}
