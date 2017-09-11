package Factory;

import Channel.Channel;

/**
 * Created by jrj on 17-9-4.
 */
public class ReflectiveChannelFactory<T extends Channel> implements ChannelFactory {
    Class<? extends T> clazz;
    public ReflectiveChannelFactory(Class<? extends T> clazz){
        this.clazz = clazz;
    }
    @Override
    public T newChannel() {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
