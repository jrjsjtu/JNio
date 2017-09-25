package Buffer;

/**
 * Created by jrj on 17-9-23.
 */
public class PoolSubpage<T> {
    int elementSize;
    int maxAvailableNum;
    int pageSize;
    long[] pageMap;
    //[32,64,96,128,160,192,224,256,288,320,352,384,416,448,480,512)
    //[512,1024,2048,4096]
    PoolSubpage(int elementSize,int pageSize){
        this.elementSize = elementSize;
        this.pageSize = pageSize;
        maxAvailableNum= pageSize/elementSize;
    }
}
