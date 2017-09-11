package Channel;

import FutureAndPromise.ChannelPromise;
import JPipeline.ChannelPipeline;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import EventLoop.EventLoop;
/**
 * Created by jrj on 17-7-10.
 */
public class JSocketChannel extends AbstractNioChannel {
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
    private static SocketChannel NewNioChannel(){
        try {
            return DEFAULT_SELECTOR_PROVIDER.openSocketChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSocketChannel(){
        this(NewNioChannel());
    }

    public JSocketChannel(Channel parent, SocketChannel socketChannel,int InterestOp){
        super(parent,socketChannel,InterestOp);
        innerUnsafe = new NioUnsafe();
    }
    private JSocketChannel(SocketChannel socketChannel){
        super(null,socketChannel, SelectionKey.OP_READ);
        innerUnsafe = new NioUnsafe();
    }

    @Override
    public Unsafe unsafe() {
        return innerUnsafe;
    }

}
