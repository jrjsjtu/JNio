package Buffer;

import java.nio.ByteBuffer;

/**
 * Created by jrj on 17-9-15.
 */
public interface JAllocator {
    JBuffer allocator(int capacity);
    static ByteBuffer allocateDirect(int capacity){
        return ByteBuffer.allocateDirect(capacity);
    }
}
