package Buffer;

/**
 * Created by jrj on 17-9-15.
 */
public interface JBufferPool {
    void AddBuffer(JBuffer buffer);
    void AddTinyBuffer(JBuffer buffer);
    void AddSmallBuffer(JBuffer buffer);
}
