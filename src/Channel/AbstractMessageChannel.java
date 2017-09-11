package Channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.*;

import EventLoop.EventLoop;
import FutureAndPromise.ChannelPromise;

/**
 * Created by jrj on 17-9-6.
 */
public abstract class AbstractMessageChannel extends AbstractNioChannel{
    AbstractMessageChannel(SelectableChannel selectableChannel){
        this(null,selectableChannel,SelectionKey.OP_ACCEPT);
    }
    AbstractMessageChannel(Channel parent, SelectableChannel selectableChannel, int readInterestOp) {
        super(parent, selectableChannel, readInterestOp);
        innerUnsafe = new NioServerUnsafe();
    }

    protected class NioServerUnsafe extends NioUnsafe{
        @Override
        public void register(EventLoop eventLoop, ChannelPromise promise) {
            AbstractMessageChannel.this.eventLoop = eventLoop;

            try {
                selectableChannel.register(eventLoop.GetSelector(), InterestOp,AbstractMessageChannel.this);
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            }
            int a = 0;
            System.out.println(a);
        }

        @Override
        public void bind(SocketAddress localAddress, ChannelPromise promise) {
            try {
                ((ServerSocketChannel)selectableChannel).socket().bind(localAddress);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
