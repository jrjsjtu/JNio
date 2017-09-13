import Bootstrap.ServerBootstrap;
import Channel.Channel;
import Channel.ChannelInitializer;
import Channel.JServerSocketChannel;
import EventLoopGroup.EventLoopGroup;
import EventLoopGroup.JNioEventLoopGroup;
import Handler.ChannelHandlerContext;
import Handler.ChannelInboundHandlerAdapter;



/**
 * Created by jrj on 17-4-18.
 */
public class test {
    public static void main(String[] args){
        EventLoopGroup boosGroup = new JNioEventLoopGroup(1);
        EventLoopGroup workerGroup = new JNioEventLoopGroup(2);
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(boosGroup,workerGroup)
                    .channel(JServerSocketChannel.class)
                    .childHandler(new ChannelInitializer() {
                        @Override
                        public void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new echoServer());
                        }
                    });
            b.bind(11030);
        }finally{

        }
    }

    public static class echoServer extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ctx.writeAndFlush(msg);
            System.out.println(msg.toString());
        }
    }
}
