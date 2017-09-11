package EventLoopGroup;

import Channel.Channel;
import Factory.JThreadFactory;
import EventLoop.EventLoop;
import EventLoop.JEventLoop;
import FutureAndPromise.ChannelFuture;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jrj on 17-7-10.
 */
public abstract class MultithreadEventExecutorGroup implements EventLoopGroup {
    EventLoop[] children;
    AtomicInteger idx;
    MultithreadEventExecutorGroup(int childrenNum){
        idx = new AtomicInteger(0);
        children = new EventLoop[childrenNum];
        for (int i=0;i<childrenNum;i++){
            children[i] = new JEventLoop(this,new JThreadFactory(),true);
        }
    }

    @Override
    public ChannelFuture register(Channel channel) {
        return next().register(channel);
    }

    @Override
    public EventLoop next(){
        return children[idx.getAndIncrement()%children.length];
    }
}
