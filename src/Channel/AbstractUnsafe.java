package Channel;

import FutureAndPromise.ChannelPromise;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created by jrj on 17-7-12.
 */
public abstract class AbstractUnsafe implements Unsafe  {
    @Override
    public SocketAddress localAddress() {
        return null;
    }

    @Override
    public SocketAddress remoteAddress() {
        return null;
    }

    @Override
    abstract public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise);

    @Override
    public void closeForcibly() {

    }
}
