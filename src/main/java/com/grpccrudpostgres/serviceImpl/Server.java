package com.grpccrudpostgres.serviceImpl;

import com.grpccrudpostgres.interceptor.GrpcRequestInterceptor;
import com.grpccrudpostgres.server.AdminServer;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;
import io.micronaut.grpc.annotation.GrpcService;
import jakarta.inject.Inject;

@GrpcService
public class Server {
    @Inject
    private AdminServer adminServer;
   io.grpc.Server server =
            ServerBuilder.forPort(50051)
                    .addService(
                            ServerInterceptors.intercept(
                                    adminServer,
                                    new GrpcRequestInterceptor()))
                    .build();

}
