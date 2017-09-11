package Bootstrap;
import EventLoopGroup.EventLoopGroup;
import Factory.ChannelFactory;
import Factory.ReflectiveChannelFactory;
import Channel.Channel;
import FutureAndPromise.ChannelFuture;

/**
 * Created by jrj on 17-9-4.
 */
public abstract class AbstractBootstrap<B extends AbstractBootstrap<B,C>,C extends Channel> {
    EventLoopGroup group;
    ChannelFactory channelFactory;
    public B channel(Class<? extends C> channelClass) {
        if (channelClass == null) {
            throw new NullPointerException("channelClass");
        }
        return channelFactory(new ReflectiveChannelFactory<C>(channelClass));
    }

    public B group(EventLoopGroup group) {
        if (group == null) {
            throw new NullPointerException("group");
        }
        if (this.group != null) {
            throw new IllegalStateException("group set already");
        }
        this.group = group;
        return (B)this;
    }

    private B channelFactory(ChannelFactory channelFactory){
        this.channelFactory = channelFactory;
        return (B)this;
    }

    protected EventLoopGroup Group(){
        return group;
    }
    final ChannelFuture initAndRegister() {
        final Channel channel = channelFactory.newChannel();
        init(channel);
        ChannelFuture regFuture = Group().register(channel);
        return regFuture;
    }

    private void init(Channel channel){

    }
}
