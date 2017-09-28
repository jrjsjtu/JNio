package Buffer;


import java.util.ArrayList;

/**
 * Created by jrj on 17-9-28.
 */
public class CacheThreadLocal {
    ArrayList<JBuffer>[] tinySubpages,smallSubpages;
    CacheThreadLocal(){
        tinySubpages = new ArrayList[16];
        smallSubpages = new ArrayList[4];
    }

    boolean appendSubpage(JBuffer byteBuf,int normCapacity){
        boolean result;
        if (AbstractAllocator.isTiny(normCapacity)){
            result = appendTiny(byteBuf,normCapacity);
        }else{
            result = appendSmall(byteBuf,normCapacity);
        }
        return result;
    }
    private boolean appendTiny(JBuffer byteBuf,int normCapacity){
        int idx =normCapacity >>> 4;
        if (tinySubpages[idx]==null){
            tinySubpages[idx] = new ArrayList<>(6);
        }
        if (tinySubpages[idx].size()<6){
            tinySubpages[idx].add(byteBuf);
            return true;
        }else{
            return false;
        }
    }

    private boolean appendSmall(JBuffer byteBuf,int normCapacity){
        int idx =normCapacity >>> 4;
        if (smallSubpages[idx]==null){
            smallSubpages[idx] = new ArrayList<>(6);
        }
        if (smallSubpages[idx].size()<6){
            smallSubpages[idx].add(byteBuf);
            return true;
        }else{
            return false;
        }

    }

    JBuffer getTinyCache(int normCapacity){
        int idx =normCapacity >>> 4;
        if (tinySubpages[idx]!=null && tinySubpages[idx].size()>0){
            return tinySubpages[idx].remove(0);
        }else{
            return null;
        }
    }

    JBuffer getSmallCache(int normCapacity){
        int idx =normCapacity >>> 4;
        if (smallSubpages[idx]!=null && smallSubpages[idx].size()>0){
            return smallSubpages[idx].remove(0);
        }else{
            return null;
        }
    }
}
