package Channel;

import EventLoop.EventLoop;
import FutureAndPromise.ChannelPromise;
import JPipeline.ChannelPipeline;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;


/**
 * Created by jrj on 17-4-19.
 */
public class JServerSocketChannel extends AbstractMessageChannel{
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
    private static ServerSocketChannel newSocket(SelectorProvider provider) {
        try {
            return provider.openServerSocketChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public JServerSocketChannel(){
        super(newSocket(DEFAULT_SELECTOR_PROVIDER));
    }

    @Override
    public Unsafe unsafe() {
        return innerUnsafe;
    }
}
