package Buffer;

/**
 * Created by jrj on 17-9-15.
 */
public class PoolChunkList<T> {
    PoolChunkList<T> next,prev;
    int maxUsage;
    int minUsage;
    PoolChunkList(int maxUsage,int minUsage){
        this.maxUsage = maxUsage;
        this.minUsage = minUsage;
    }
}
