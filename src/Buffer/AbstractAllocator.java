package Buffer;

import java.nio.ByteBuffer;

import static Buffer.JChunk.rSmallMask;
import static Buffer.JChunk.rTinyMask;

/**
 * Created by jrj on 17-9-15.
 */
public class AbstractAllocator implements JAllocator {
    //chunklist之间的调整还没有做
    public static final ThreadLocal<CacheThreadLocal> value = new ThreadLocal<CacheThreadLocal>() {
        @Override
        protected CacheThreadLocal initialValue() {
            return new CacheThreadLocal();
        }
    };

    JBufferPool jBufferPool;

    AbstractAllocator(int pageSize, int chunkSize){
        jBufferPool = new DefaultBufferPool(pageSize,chunkSize);
    }

    @Override
    public JBuffer allocator(int capacity) {
        int normalCapacity = normalizeCapacity(capacity);
        JBuffer outBuffer = null;
        //先尝试在本地缓存取得内存，失败后向BufferPool申请内存
        //本地缓存用ThreadLocal实现
        if (isTiny(normalCapacity)){
            outBuffer = findTinyInThreadlocal(normalCapacity);
        }else if(isSmall(normalCapacity)){
            outBuffer = findSmallInThreadlocal(normalCapacity);
        }
        if (outBuffer == null){
            outBuffer = jBufferPool.AllocateBuffer(normalCapacity);
        }
        return outBuffer;
    }

    public JBuffer findTinyInThreadlocal(int capacity){
        return value.get().getTinyCache(capacity);
    }

    public JBuffer findSmallInThreadlocal(int capacity){
        return value.get().getSmallCache(capacity);
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

    static boolean isTiny(int capacity){
        return ((capacity&rTinyMask)==0)?true:false;
    }

    static boolean isSmall(int capacity){
        return ((capacity&rSmallMask)==0)?true:false;
    }

    public static void main(String[] args){
        AbstractAllocator abstractAllocator = new AbstractAllocator(8192,8192*1024);
        abstractAllocator.allocator(32);
    }
}
