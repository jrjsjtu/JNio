package Handler;

import FutureAndPromise.ChannelFuture;
import FutureAndPromise.ChannelPromise;
import JPipeline.ChannelPipeline;
import Channel.Channel;
import EventLoop.EventLoop;
/**
 * Created by jrj on 17-9-7.
 */
public interface ChannelHandlerContext {

    /**
     * Return the {@link Channel} which is bound to the {@link ChannelHandlerContext}.
     */
    Channel channel();

    EventLoop executor();

    /**
     * The unique name of the {@link ChannelHandlerContext}.The name was used when then {@link ChannelHandler}
     * was added to the {@link ChannelPipeline}. This name can also be used to access the registered
     * {@link ChannelHandler} from the {@link ChannelPipeline}.
     */
    String name();

    /**
     * The {@link ChannelHandler} that is bound this {@link ChannelHandlerContext}.
     */
    ChannelHandler handler();

    /**
     * Return {@code true} if the {@link ChannelHandler} which belongs to this context was removed
     * from the {@link ChannelPipeline}. Note that this method is only meant to be called from with in the
     * {@link EventLoop}.
     */
    boolean isRemoved();

    ChannelHandlerContext fireChannelRegistered();

    ChannelHandlerContext fireChannelUnregistered();

    ChannelHandlerContext fireChannelActive();

    ChannelHandlerContext fireChannelInactive();

    ChannelHandlerContext fireExceptionCaught(Throwable cause);

    ChannelHandlerContext fireUserEventTriggered(Object evt);

    ChannelHandlerContext fireChannelRead(Object msg);

    ChannelHandlerContext fireChannelReadComplete();

    ChannelHandlerContext fireChannelWritabilityChanged();

    ChannelHandlerContext read();

    ChannelHandlerContext flush();

    ChannelFuture write(Object msg);

    /**
     * Request to write a message via this {@link ChannelHandlerContext} through the {@link ChannelPipeline}.
     * This method will not request to actual flush, so be sure to call {@link #flush()}
     * once you want to request to flush all pending data to the actual transport.
     */
    ChannelFuture write(Object msg, ChannelPromise promise);

    /**
     * Shortcut for call {@link #write(Object, ChannelPromise)} and {@link #flush()}.
     */
    ChannelFuture writeAndFlush(Object msg, ChannelPromise promise);

    /**
     * Shortcut for call {@link #write(Object)} and {@link #flush()}.
     */
    ChannelFuture writeAndFlush(Object msg);
    /**
     * Return the assigned {@link ChannelPipeline}
     */
    ChannelPipeline pipeline();
}
