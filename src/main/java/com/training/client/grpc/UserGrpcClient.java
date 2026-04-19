package com.training.client.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserGrpcClient {
    private static final Logger logger = LoggerFactory.getLogger(UserGrpcClient.class);
    
    private final ManagedChannel channel;
    private final String target;

    public UserGrpcClient() {
        this.target = "localhost:9090";
        this.channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();
    }

    public void connect() {
        logger.info("Connecting to gRPC server at: {}", target);
    }

    public void shutdown() {
        try {
            channel.shutdown();
            logger.info("gRPC channel shutdown");
        } catch (Exception e) {
            logger.error("Error shutting down gRPC channel", e);
        }
    }

    public ManagedChannel getChannel() {
        return channel;
    }
}