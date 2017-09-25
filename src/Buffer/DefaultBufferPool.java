package Buffer;

import java.nio.ByteBuffer;

/**
 * Created by jrj on 17-9-15.
 */
public class DefaultBufferPool extends AbstractBufferPool {

    DefaultBufferPool(int pageSize, int chunkSize, ByteBuffer bufferInAll) {
        super(pageSize, chunkSize, bufferInAll);
    }
}
