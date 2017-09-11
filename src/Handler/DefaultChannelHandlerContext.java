package Handler;

import EventLoop.EventLoop;
import FutureAndPromise.ChannelFuture;
import FutureAndPromise.ChannelPromise;
import JPipeline.ChannelPipeline;
import JPipeline.DefaultChannelPipeline;

/**
 * Created by jrj on 17-9-7.
 */
public class DefaultChannelHandlerContext extends AbstractChannelHandlerContext {
    private final ChannelHandler handler;

    public DefaultChannelHandlerContext(
            ChannelPipeline pipeline, EventLoop executor, ChannelHandler handler) {
        super(pipeline, executor, isInbound(handler), isOutbound(handler));
        if (handler == null) {
            throw new NullPointerException("handler");
        }
        this.handler = handler;
    }


    @Override
    public ChannelHandlerContext fireChannelRead(Object msg) {
        try {
            handler.channelRead(this,msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public ChannelHandler handler() {
        return handler;
    }

    @Override
    public ChannelFuture write(Object msg) {
        return null;
    }

    @Override
    public ChannelFuture write(Object msg, ChannelPromise promise) {
        return null;
    }

    private static boolean isInbound(ChannelHandler handler) {
        return handler instanceof ChannelInboundHandler;
    }

    private static boolean isOutbound(ChannelHandler handler) {
        return handler instanceof ChannelOutboundHandler;
    }
}
