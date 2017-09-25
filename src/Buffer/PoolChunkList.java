package Buffer;

import java.nio.ByteBuffer;

/**
 * Created by jrj on 17-9-15.
 */
public class PoolChunkList<T> {
    PoolChunkList<T> next,prev;
    JChunk head;
    int maxUsage;
    int minUsage;
    PoolChunkList(int maxUsage,int minUsage){
        this.maxUsage = maxUsage;
        this.minUsage = minUsage;
    }

    boolean allocate(ByteBuf buffer,int normCapacity){
        if (head == null){
            return false;
        }
        JChunk tmp = head;
        while (tmp!=null){
            int handle = tmp.getHandle(normCapacity);
            if (handle >= 0){
                buffer = head.ApplyBufferByHandle(handle);
                return true;
            }else{
                tmp = tmp.getNext();
            }
        }
        return false;
    }

    void setNext(PoolChunkList next){
        this.next = next;
    }

    void addChunk(JChunk chunk){
        if(chunk.getUsage()>maxUsage && next != null){
            next.addChunk(chunk);
        }else{
            if (head == null){
                head = chunk;
            }else{
                JChunk tmp = head;
                head = chunk;
                chunk.setNext(tmp);
                tmp.setPrev(chunk);
            }
        }
    }
}
