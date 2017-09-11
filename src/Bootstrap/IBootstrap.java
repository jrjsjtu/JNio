package Bootstrap;

/**
 * Created by jrj on 17-5-10.
 */
public interface IBootstrap {
    void serverStart();
    void registerChannel();
    void sync(int port);
}
