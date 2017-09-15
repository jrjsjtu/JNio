package Buffer;

import static Buffer.JChunk.rSmallMask;
import static Buffer.JChunk.rTinyMask;

/**
 * Created by jrj on 17-9-15.
 */
public abstract class AbstractAllocator implements JAllocator {
    static JBufferPool jBufferPool;
    static{

    }
    @Override
    public JBuffer allocator(int capacity) {
        int normalCapacity = normalizeCapacity(capacity);
        if (isTiny(normalCapacity)){
            JBuffer outBuffer = findTinyInThreadlocal(normalCapacity);
            return outBuffer==null?allocatTiny(normalCapacity):outBuffer;
        }else if(isSmall(normalCapacity)){
            JBuffer outBuffer = findSmallInThreadlocal(normalCapacity);
            return outBuffer==null?allocatSmall(normalCapacity):outBuffer;
        }else{
            JBuffer outBuffer = findNormalInThreadlocal(normalCapacity);
            return outBuffer==null?allocatNormal(normalCapacity):outBuffer;
        }
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
