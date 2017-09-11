package JPipeline;

import Handler.ChannelHandler;

/**
 * Created by jrj on 17-7-12.
 */
public interface ChannelPipeline extends  ChannelInboundInvoker, ChannelOutboundInvoker{
    ChannelPipeline fireChannelRegistered();

    ChannelPipeline fireChannelUnregistered();

    ChannelPipeline fireChannelActive();

    ChannelPipeline fireChannelInactive();

    ChannelPipeline fireExceptionCaught(Throwable cause);

    ChannelPipeline fireUserEventTriggered(Object event);

    ChannelPipeline fireChannelRead(Object msg);

    ChannelPipeline fireChannelReadComplete();

    ChannelPipeline fireChannelWritabilityChanged();

    ChannelPipeline flush();

    ChannelPipeline addLast(ChannelHandler channelHandler);
}
