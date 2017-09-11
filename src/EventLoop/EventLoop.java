package EventLoop;

import Channel.Channel;
import FutureAndPromise.ChannelFuture;

import java.nio.channels.Selector;
import java.util.concurrent.ExecutorService;

/**
 * Created by jrj on 17-7-13.
 */
public interface EventLoop extends ExecutorService{
    Selector GetSelector();
    ChannelFuture register(Channel channel);
    boolean inEventLoop();
    boolean hasTask();
    void wakeUpSelector();
}
