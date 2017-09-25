package Buffer;

import java.nio.ByteBuffer;

/**
 * Created by jrj on 17-9-14.
 */
public class ByteBuf extends AbstractBuffer{
    ByteBuf(ByteBuffer innerBuffer,long handle) {
        super(innerBuffer,handle);
    }
}
