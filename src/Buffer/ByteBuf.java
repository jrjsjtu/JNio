package Buffer;

import java.nio.ByteBuffer;

/**
 * Created by jrj on 17-9-14.
 */
public class ByteBuf extends AbstractBuffer{
    ByteBuf(ByteBuffer innerBuffer,long handle,JChunk chunk) {
        super(innerBuffer,handle,chunk);
    }
    ByteBuf(ByteBuffer innerBuffer,PoolSubpage poolSubpage,int size){
        super(innerBuffer,poolSubpage,size);
    }

}
