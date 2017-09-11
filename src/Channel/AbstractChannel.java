package Channel;

import FutureAndPromise.ChannelPromise;
import JPipeline.ChannelPipeline;
import JPipeline.DefaultChannelPipeline;
import EventLoop.EventLoop;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created by jrj on 17-7-10.
 */
public abstract class AbstractChannel implements Channel {
    protected DefaultChannelPipeline pipeline;
    protected Unsafe innerUnsafe;
    protected EventLoop eventLoop;
    public AbstractChannel(){
        pipeline = new DefaultChannelPipeline(this);
    }
    @Override
    public SocketAddress remoteAddress() {
        return null;
    }
    @Override
    public ChannelPipeline pipeline(){
        return pipeline;
    }
    @Override
    public EventLoop getEventLoop(){
        return eventLoop;
    }
}
