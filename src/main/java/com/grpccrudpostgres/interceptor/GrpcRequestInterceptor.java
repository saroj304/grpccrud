package com.grpccrudpostgres.interceptor;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
//
public class GrpcRequestInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> next) {
        System.out.println("token is validating");
        var userToken = metadata.get(Metadata.Key.of("JWT", Metadata.ASCII_STRING_MARSHALLER));

        validateUserToken(userToken);
        ServerCall.Listener listener= next.startCall(serverCall, metadata);
        return listener;
    }

    private void validateUserToken(String userToken) {

    }
}
