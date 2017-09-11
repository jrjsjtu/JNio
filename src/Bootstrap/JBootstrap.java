package Bootstrap;

import Channel.Channel;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by jrj on 17-5-10.
 */
public class JBootstrap extends  AbstractBootstrap<JBootstrap,Channel>{
    Selector selector;
    private ServerSocket serverSocket;
    private ServerSocketChannel ssc = null;
    public JBootstrap(){
        try {
            selector = Selector.open();
            ssc= ServerSocketChannel.open();
            //检索与此通道关联的服务器套接字
            serverSocket=ssc.socket();
            //将 ServerSocket 绑定到特定地址（IP 地址和端口号）
            //serverSocket.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect(String host,int port){

    }

}
