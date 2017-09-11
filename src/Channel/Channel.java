package Channel;

import EventLoop.EventLoop;
import JPipeline.ChannelPipeline;

import java.net.SocketAddress;

/**
 * Created by jrj on 17-7-11.
 */
public interface Channel {
    SocketAddress remoteAddress();
    Unsafe unsafe();
    ChannelPipeline pipeline();
    EventLoop getEventLoop();
}
