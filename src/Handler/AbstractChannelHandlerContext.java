package Handler;

import Channel.Channel;
import EventLoop.EventLoop;
import FutureAndPromise.ChannelFuture;
import FutureAndPromise.ChannelPromise;
import JPipeline.ChannelPipeline;
import JPipeline.DefaultChannelPipeline;

/**
 * Created by jrj on 17-9-7.
 */
public abstract class AbstractChannelHandlerContext implements ChannelHandlerContext{
    boolean inbound,outbound;
    protected AbstractChannelHandlerContext prev,next;
    EventLoop eventLoopBinded;
    ChannelPipeline pipeLineBinded;
    AbstractChannelHandlerContext(ChannelPipeline pipeline, EventLoop executor,boolean isInbound, boolean isOutbound){
        this.eventLoopBinded = executor;
        this.pipeLineBinded = pipeline;
        this.inbound = isInbound;
        this.outbound = isOutbound;
    }

    @Override
    public Channel channel() {
        return null;
    }

    @Override
    public EventLoop executor() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public ChannelHandler handler() {
        return null;
    }

    @Override
    public boolean isRemoved() {
        return false;
    }

    @Override
    public ChannelHandlerContext fireChannelRegistered() {
        return null;
    }

    @Override
    public ChannelHandlerContext fireChannelUnregistered() {
        return null;
    }

    @Override
    public ChannelHandlerContext fireChannelActive() {
        return null;
    }

    @Override
    public ChannelHandlerContext fireChannelInactive() {
        return null;
    }

    @Override
    public ChannelHandlerContext fireExceptionCaught(Throwable cause) {
        return null;
    }

    @Override
    public ChannelHandlerContext fireUserEventTriggered(Object evt) {
        return null;
    }

    @Override
    public ChannelHandlerContext fireChannelRead(Object msg) {
        AbstractChannelHandlerContext nextInbound = findNextInbound();
        nextInbound.fireChannelRead(msg);
        return this;
    }

    @Override
    public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise){
        AbstractChannelHandlerContext nextOutbound = findNextOutbound();
        nextOutbound.write(msg,promise);
        return null;
    }

    protected AbstractChannelHandlerContext findNextOutbound(){
        AbstractChannelHandlerContext ctx = this;
        do {
            ctx = ctx.prev;
        } while (!ctx.outbound);
        return ctx;
    }

    protected AbstractChannelHandlerContext findNextInbound(){
        AbstractChannelHandlerContext ctx = this;
        do {
            ctx = ctx.next;
        } while (!ctx.inbound);
        return ctx;
    }

    @Override
    public ChannelHandlerContext fireChannelReadComplete() {
        return null;
    }

    @Override
    public ChannelHandlerContext fireChannelWritabilityChanged() {
        return null;
    }

    @Override
    public ChannelHandlerContext read() {
        return null;
    }

    @Override
    public ChannelHandlerContext flush() {
        return null;
    }

    /**
     * Request to write a message via this {@link ChannelHandlerContext} through the {@link ChannelPipeline}.
     * This method will not request to actual flush, so be sure to call {@link #flush()}
     * once you want to request to flush all pending data to the actual transport.
     */
    @Override
    public ChannelFuture write(Object msg, ChannelPromise promise){
        return null;
    }

    /**
     * Shortcut for call {@link #write(Object, ChannelPromise)} and {@link #flush()}.
     */

    /**
     * Shortcut for call {@link #write(Object)} and {@link #flush()}.
     */
    @Override
    public ChannelFuture writeAndFlush(Object msg){
        return null;
    }
    @Override
    public ChannelPipeline pipeline() {
        return null;
    }
}
