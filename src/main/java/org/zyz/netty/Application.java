package org.zyz.netty;

import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zyz.netty.server.NettyServer;

@SpringBootApplication
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Value("${netty.port}")
    private int port;
    @Autowired
    private NettyServer nettyServer;

    @Override
    public void run(String... args) throws Exception {
        ChannelFuture init = nettyServer.init(port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> nettyServer.close()));
        init.channel().closeFuture().syncUninterruptibly();
    }

}
