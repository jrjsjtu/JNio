package Buffer;

/**
 * Created by jrj on 17-9-15.
 */
public interface JBuffer {
    void put();
    void get(byte[] bytes);
    void flip();
    void rewind();
    void clear();
    void free();
    JBuffer allocateSubpage(int startPostion,int size);
}
