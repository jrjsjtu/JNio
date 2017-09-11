package Factory;

import Channel.Channel;

/**
 * Created by jrj on 17-9-4.
 */
public interface ChannelFactory<T extends Channel> {
    T newChannel();
}
