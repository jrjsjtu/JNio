package Handler;

import Channel.Channel;
import EventLoop.EventLoop;
import FutureAndPromise.ChannelFuture;
import FutureAndPromise.ChannelPromise;
import JPipeline.ChannelPipeline;
import JPipeline.DefaultChannelPipeline;

import java.net.SocketAddress;

/**
 * Created by jrj on 17-9-7.
 */
public class HeadContext extends DefaultChannelHandlerContext{
    public HeadContext(DefaultChannelPipeline channelPipeline, EventLoop eventLoop,Channel channel){
        super(channelPipeline,eventLoop,new HeadHandler(channel));
    }
    public void setTail(TailContext tailContext){
        next = tailContext;
    }

    @Override
    public ChannelHandlerContext fireChannelRead(Object msg) {
        ChannelHandlerContext channelHandlerContext = findNextInbound();
        channelHandlerContext.fireChannelRead(msg);
        return this;
    }

    @Override
    public ChannelFuture write(Object msg) {
        return null;
    }

    @Override
    public ChannelFuture write(Object msg, ChannelPromise promise) {
        return null;
    }


    @Override
    public ChannelFuture writeAndFlush(Object msg){
        AbstractChannelHandlerContext ctx = findNextOutbound();
        ctx.writeAndFlush(msg);
        return null;
    }

    private static class HeadHandler implements ChannelInboundHandler,ChannelOutboundHandler{
        Channel channel;
        HeadHandler(Channel channel){
            this.channel = channel;
        }
        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ctx.fireChannelRead(msg);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {

        }

        @Override
        public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {

        }

        @Override
        public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {

        }

        @Override
        public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {

        }

        @Override
        public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {

        }

        @Override
        public void read(ChannelHandlerContext ctx) throws Exception {

        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            channel.unsafe().write(msg,promise);
        }

        @Override
        public void flush(ChannelHandlerContext ctx) throws Exception {

        }
    }
}
