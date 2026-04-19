package com.training.client.grpc;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserGrpcService {
    private static final Logger logger = LoggerFactory.getLogger(UserGrpcService.class);
    
    private final List<String> userList = new CopyOnWriteArrayList<>();

    public void addUser(String user) {
        userList.add(user);
        logger.info("User added via gRPC: {}", user);
    }

    public List<String> getAllUsers() {
        return userList;
    }

    public StreamObserver<String> getUserStream() {
        return new StreamObserver<String>() {
            @Override
            public void onNext(String user) {
                userList.add(user);
            }

            @Override
            public void onError(Throwable t) {
                logger.error("gRPC stream error", t);
            }

            @Override
            public void onCompleted() {
                logger.info("gRPC stream completed");
            }
        };
    }
}