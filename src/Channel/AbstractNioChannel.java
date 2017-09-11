package Channel;

import EventLoop.EventLoop;
import EventLoop.JEventLoop;
import FutureAndPromise.ChannelPromise;
import JPipeline.ChannelPipeline;
import JPipeline.DefaultChannelPipeline;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by jrj on 17-7-10.
 */
public abstract class AbstractNioChannel extends AbstractChannel{
    protected SelectableChannel selectableChannel;
    int InterestOp;
    Channel parent;
    AbstractNioChannel(Channel parent, SelectableChannel selectableChannel, int readInterestOp){
        this.parent = parent;
        this.selectableChannel = selectableChannel;
        InterestOp = readInterestOp;
        try {
            this.selectableChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected class NioUnsafe implements Unsafe{
        @Override
        public SocketAddress localAddress() {
            return null;
        }

        @Override
        public SocketAddress remoteAddress() {
            return null;
        }

        @Override
        public void bind(SocketAddress localAddress, ChannelPromise promise) {

        }

        @Override
        public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {

        }

        @Override
        public void closeForcibly() {

        }

        @Override
        public void write(Object msg, ChannelPromise promise) {
            try {
                ((SocketChannel)selectableChannel).write((ByteBuffer) msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            promise.setSuccess();
        }

        @Override
        public void register(EventLoop eventLoop, ChannelPromise promise) {
            AbstractNioChannel.this.eventLoop = eventLoop;
            try {
                selectableChannel.register(eventLoop.GetSelector(), InterestOp,AbstractNioChannel.this);
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            }
        }
    }
}