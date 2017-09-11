package EventLoopGroup;

import Channel.Channel;
import EventLoop.EventLoop;
import FutureAndPromise.ChannelFuture;
import FutureAndPromise.ChannelPromise;

/**
 * Created by jrj on 17-7-10.
 */
public class JNioEventLoopGroup extends MultithreadEventLoopGroup {
    public JNioEventLoopGroup(int threadNum){
        super(threadNum);
    }

    public JNioEventLoopGroup(){
        super();
    }
    @Override
    public ChannelFuture register(ChannelPromise promise) {
        return null;
    }

    @Override
    public ChannelFuture register(Channel channel, ChannelPromise promise) {
        return null;
    }
}
