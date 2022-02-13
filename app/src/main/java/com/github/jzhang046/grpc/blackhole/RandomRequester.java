package com.github.jzhang046.grpc.blackhole;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jzhang046.grpc.blackhole.proto.BlackHoleGrpc;
import com.github.jzhang046.grpc.blackhole.proto.Bytes;
import com.github.jzhang046.grpc.blackhole.proto.StreamSize;

import io.grpc.stub.StreamObserver;

public class RandomRequester {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomRequester.class);
    private static final CountDownLatch LATCH = new CountDownLatch(1);

    public static void main(String... args) throws InterruptedException {
        var channel = DefaultChannelSupplier.get();
        var client = BlackHoleGrpc.newStub(channel);

        StreamObserver<Bytes> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(Bytes value) {
                LOGGER.info("received value [{}]", value.getPayload());
            }

            @Override
            public void onError(Throwable t) {
                LOGGER.error("error receiving value", t);
            }

            @Override
            public void onCompleted() {
                LATCH.countDown();
                LOGGER.info("received all values");
            }
        };

        client.emitRandom(StreamSize.newBuilder()
                                    .setCount(4)
                                    .setLength(16)
                                    .build(),
                          responseObserver);
        LATCH.await();
        LOGGER.info("finished, exiting");
    }
}
