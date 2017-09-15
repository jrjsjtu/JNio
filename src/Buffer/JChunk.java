package Buffer;

/**
 * Created by jrj on 17-9-15.
 */
public interface JChunk {
    //tiny = 512; =   0000 0000 0010 0000 0000
    int tinyMask = 0x000003FF;
    int rTinyMask = 0xFFFFFE00;
    //small = 8192; = 0000 0010 0000 0000 0000
    int smallMask = 0x00003FFF;
    int rSmallMask = 0xFFFFE000;
    int GetUsage();
    JBuffer ApplyBuffer(int capacity);

}
