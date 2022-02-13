package com.github.jzhang046.grpc.blackhole;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;

public class DefaultChannelSupplier {

    public static Channel get() {
        return ManagedChannelBuilder.forAddress(Constants.DEFAULT_HOST,
                                                Constants.DEFAULT_PORT)
                                    .usePlaintext()
                                    .build();
    }
}
