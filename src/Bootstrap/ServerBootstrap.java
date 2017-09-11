package Bootstrap;
import Channel.Channel;
import EventLoopGroup.EventLoopGroup;
import Factory.ChannelFactory;
import FutureAndPromise.ChannelFuture;
import FutureAndPromise.DefaultChannelPromise;
import Handler.ServerHandler;
import JPipeline.ChannelPipeline;
import Channel.JServerSocketChannel;
import Channel.ChannelInitializer;
import java.net.InetSocketAddress;
import java.rmi.ServerError;

/**
 * Created by jrj on 17-9-6.
 */
public class ServerBootstrap extends AbstractBootstrap<ServerBootstrap,Channel> {
    EventLoopGroup childGroup;
    ChannelInitializer channelInitializer;
    public void bind(int port){
        Channel tmpChannel = channelFactory.newChannel();
        ChannelPipeline channelPipeline = tmpChannel.pipeline();
        JServerSocketChannel jServerSocketChannel = (JServerSocketChannel)tmpChannel;
        //jServerSocketChannel
        jServerSocketChannel.unsafe().bind(new InetSocketAddress(port),new DefaultChannelPromise());
        ChannelFuture regFuture = Group().register(tmpChannel);
        channelPipeline.addLast(new ServerHandler(childGroup,channelInitializer));
    }

    public ServerBootstrap group(EventLoopGroup bossGroup,EventLoopGroup workerGroup){
        group = bossGroup;
        childGroup = workerGroup;
        return this;
    }

    public ServerBootstrap childHandler(ChannelInitializer channelInitializer){
        this.channelInitializer = channelInitializer;
        return this;
    }
}
