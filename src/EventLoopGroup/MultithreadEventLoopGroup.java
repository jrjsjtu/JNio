package EventLoopGroup;

import Channel.Channel;
import EventLoop.EventLoop;
import FutureAndPromise.ChannelFuture;

/**
 * Created by jrj on 17-7-10.
 */
public abstract class MultithreadEventLoopGroup extends MultithreadEventExecutorGroup {
    private static final int DEFAULT_EVENT_LOOP_THREADS;

    static {
        DEFAULT_EVENT_LOOP_THREADS = Math.max(1,Runtime.getRuntime().availableProcessors() * 2);
    }
    MultithreadEventLoopGroup(){
        super(DEFAULT_EVENT_LOOP_THREADS);
    }

    MultithreadEventLoopGroup(int num){
        super(num);
    }

}
