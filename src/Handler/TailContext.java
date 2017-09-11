package Handler;

import Channel.Channel;
import EventLoop.EventLoop;
import JPipeline.DefaultChannelPipeline;

/**
 * Created by jrj on 17-9-7.
 */
public class TailContext extends DefaultChannelHandlerContext {
    Channel channel;
    public TailContext(DefaultChannelPipeline pipeline, EventLoop executor, Channel channel) {
        super(pipeline, executor, new TailHandler(channel));
        this.channel = channel;
    }
    public void setHead(HeadContext headContext){
        prev = headContext;
    }
    public void addLast(ChannelHandler channelHandler){
        AbstractChannelHandlerContext ctx = new DefaultChannelHandlerContext(channel.pipeline(),channel.getEventLoop(),channelHandler);
        AbstractChannelHandlerContext tmp = prev;
        ctx.next = this;
        prev = ctx;
        tmp.next = ctx;
        ctx.prev = tmp;
    }
    private static class TailHandler implements ChannelInboundHandler{
        Channel channel;
        TailHandler(Channel channel){
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
    }
}
