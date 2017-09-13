package JPipeline;

import FutureAndPromise.ChannelFuture;
import FutureAndPromise.ChannelProgressivePromise;
import FutureAndPromise.ChannelPromise;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

import Channel.Channel;
import Handler.*;

/**
 * Created by jrj on 17-7-12.
 */
public class DefaultChannelPipeline implements  ChannelPipeline{
    Channel channel;
    private HeadContext head;
    private TailContext tail;
    public DefaultChannelPipeline(Channel channel){
        this.channel = channel;
        head = new HeadContext(this,channel.getEventLoop(),channel);
        tail = new TailContext(this,channel.getEventLoop(),channel);
        head.setTail(tail);
        tail.setHead(head);
    }
    @Override
    public ChannelFuture bind(SocketAddress localAddress) {
        return null;
    }

    @Override
    public ChannelFuture connect(SocketAddress remoteAddress) {
        return null;
    }

    @Override
    public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
        return null;
    }

    @Override
    public ChannelFuture disconnect() {
        return null;
    }

    @Override
    public ChannelFuture close() {
        return null;
    }

    @Override
    public ChannelFuture deregister() {
        return null;
    }

    @Override
    public ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
        return null;
    }

    @Override
    public ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
        return null;
    }

    @Override
    public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
        return null;
    }

    @Override
    public ChannelFuture disconnect(ChannelPromise promise) {
        return null;
    }

    @Override
    public ChannelFuture close(ChannelPromise promise) {
        return null;
    }

    @Override
    public ChannelFuture deregister(ChannelPromise promise) {
        return null;
    }

    @Override
    public ChannelOutboundInvoker read() {
        return null;
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
    public ChannelPipeline fireChannelRegistered() {
        return null;
    }

    @Override
    public ChannelPipeline fireChannelUnregistered() {
        return null;
    }

    @Override
    public ChannelPipeline fireChannelActive() {
        return null;
    }

    @Override
    public ChannelPipeline fireChannelInactive() {
        head.fireChannelInactive();
        return this;
    }

    @Override
    public ChannelPipeline fireExceptionCaught(Throwable cause) {
        return null;
    }

    @Override
    public ChannelPipeline fireUserEventTriggered(Object event) {
        return null;
    }

    @Override
    public ChannelPipeline fireChannelRead(Object msg) {
        head.fireChannelRead(msg);
        return this;
    }

    @Override
    public ChannelPipeline fireChannelReadComplete() {
        return null;
    }

    @Override
    public ChannelPipeline fireChannelWritabilityChanged() {
        return null;
    }

    @Override
    public ChannelPipeline flush() {
        return null;
    }

    @Override
    public ChannelPipeline addLast(ChannelHandler channelHandler) {
        tail.addLast(channelHandler);
        return this;
    }

    @Override
    public ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
        return null;
    }

    @Override
    public ChannelFuture writeAndFlush(Object msg) {
        return null;
    }

    @Override
    public ChannelPromise newPromise() {
        return null;
    }

    @Override
    public ChannelProgressivePromise newProgressivePromise() {
        return null;
    }

    @Override
    public ChannelFuture newSucceededFuture() {
        return null;
    }

    @Override
    public ChannelFuture newFailedFuture(Throwable cause) {
        return null;
    }

    @Override
    public ChannelPromise voidPromise() {
        return null;
    }
}
