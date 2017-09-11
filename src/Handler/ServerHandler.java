package Handler;

import EventLoop.EventLoop;
import EventLoopGroup.EventLoopGroup;
import Channel.Channel;
import java.nio.channels.SocketChannel;
import Channel.JSocketChannel;
import Channel.ChannelInitializer;
/**
 * Created by jrj on 17-9-8.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    EventLoopGroup eventLoopGroup;
    ChannelInitializer channelInitializer;
    public ServerHandler(EventLoopGroup eventLoopGroup, ChannelInitializer channelInitializer){
        this.eventLoopGroup = eventLoopGroup;
        this.channelInitializer = channelInitializer;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        JSocketChannel channel = (JSocketChannel) (msg);
        eventLoopGroup.register(channel);
        channelInitializer.initChannel(channel);
    }
}
