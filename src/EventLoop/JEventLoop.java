package EventLoop;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.*;
import EventLoopGroup.EventLoopGroup;
import Channel.Channel;
import FutureAndPromise.ChannelFuture;
import Channel.JSocketChannel;
import Channel.AbstractNioChannel;
import FutureAndPromise.DefaultChannelPromise;

/**
 * Created by jrj on 17-5-9.
 */
public class JEventLoop extends  SingleThreadEventLoop {
    public Selector selector;
    private SelectorProvider selectorProvider;
    private int STATE = 0;
    private static final int HAS_START = 1;
    private static final int NOT_START = 0;

    public JEventLoop(EventLoopGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp) {
        super(parent, threadFactory, addTaskWakesUp);
        selectorProvider = SelectorProvider.provider();
        try {
            selector = selectorProvider.openSelector();
        } catch (IOException e) {
            e.printStackTrace();
        }
        thread.start();
    }

    @Override
    public void run() {
        int nKeys = -1;
        for (; ; ) {
            try {
                nKeys = selector.select(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (nKeys > 0) {
                for (SelectionKey key : selector.selectedKeys()) {
                    if (key.isAcceptable()){
                        try {
                            SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
                            clientChannel.configureBlocking(false);
                            Channel channel = (Channel) key.attachment();
                            JSocketChannel jSocketChannel = new JSocketChannel(channel,clientChannel,SelectionKey.OP_READ);
                            channel.pipeline().fireChannelRead(jSocketChannel);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024*64);
                        SocketChannel sc = (SocketChannel) key.channel();
                        Channel channel = (Channel) key.attachment();
                        try {
                            sc.read(buffer);
                            channel.pipeline().fireChannelRead(buffer);
                        }catch (Exception e){
                            e.printStackTrace();
                        } finally{
                            if (buffer != null)
                                buffer.clear();
                        }
                    }
                }
            }
            while (hasTask()){
                try {
                    Runnable curTask = taskQueue.take();
                    curTask.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Selector GetSelector() {
        return selector;
    }

    @Override
    public boolean inEventLoop() {
        return false;
    }

    @Override
    public void wakeUpSelector() {
        selector.wakeup();
    }

    @Override
    public ChannelFuture register(Channel channel) {
        DefaultChannelPromise defaultChannelPromise = new DefaultChannelPromise();
        EventLoop curLoop = this;
        submit(new Runnable() {
            @Override
            public void run() {
                channel.unsafe().register(curLoop,defaultChannelPromise);
            }
        });
        return defaultChannelPromise;
    }
}
