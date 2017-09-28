package Buffer;

/**
 * Created by jrj on 17-9-23.
 */
public class PoolSubpage<T> {
    int elementSize;
    int maxAvailableNum,curAvailable;
    int pageSize;
    ByteBuf byteBuf;
    int handle;
    long[] pageMap;
    PoolSubpage<T> next,prev;
    //[32,64,96,128,160,192,224,256,288,320,352,384,416,448,480,512)
    //[512,1024,2048,4096]
    PoolSubpage(ByteBuf byteBuf,int handle,int elementSize,int pageSize){
        this.elementSize = elementSize;
        this.pageSize = pageSize;
        curAvailable = maxAvailableNum= pageSize/elementSize;
        this.byteBuf = byteBuf;
        this.handle = handle;
        init();
        byteBuf.chunk.getBufferPool().addPool(this,elementSize);
    }

    void init(){
        //如果除以64有余的话那么要用多一个long来存储，
        if ((maxAvailableNum & 0x3F)>0){
            pageMap = new long[maxAvailableNum>>>6 + 1];
        }else{
            pageMap = new long[maxAvailableNum>>>6];
        }
    }

    private int findNextAvailable(){
        if (curAvailable==0){
            return -1;
        }
        for (int i=0;i<pageMap.length;i++){
            int tmpIdx = findNextAvailable0(i,pageMap[i]);
            if (tmpIdx != -1){
                curAvailable --;
                return tmpIdx;
            }
        }
        return -1;
    }

    private int findNextAvailable0(int idxOfPagemap,long curBitmap){
        if (~curBitmap == 0){
            return -1;
        }
        int baseVal = idxOfPagemap<<6;
        for (int i=0;i<64;i++){
            if ((curBitmap & 1 )!=0){
                pageMap[idxOfPagemap] |= 1<<i;
                curAvailable --;
                if (curAvailable == 0){
                    byteBuf.chunk.getBufferPool().removePool(this,elementSize);
                }
                return baseVal | i;
            }
            curBitmap>>>=1;
        }
        return -1;
    }

    ByteBuf allocate(){
        int idx = findNextAvailable();
        return byteBuf.allocateSubpage(idx*elementSize,elementSize);
    }

    void freeSubpage(long handle){
        curAvailable++;
        if (curAvailable == maxAvailableNum){
            byteBuf.free();
        }else{
            int subpageIdx = (int)(handle>>>32);
            int idx = subpageIdx>>>6;
            long tmpIdx = subpageIdx & 0x3F;
            pageMap[idx] ^= (1l<<tmpIdx);
        }
        if (curAvailable == 1){
            byteBuf.chunk.getBufferPool().addPool(this,elementSize);
        }
    }
}
