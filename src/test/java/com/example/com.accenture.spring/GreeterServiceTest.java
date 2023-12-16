package com.example.com.accenture.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import com.example.com.accenture.spring.proto.GreeterOuterClass;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GreeterServiceTest {

  @Mock private StreamObserver<GreeterOuterClass.HelloReply> responseObserver;

  @Captor private ArgumentCaptor<GreeterOuterClass.HelloReply> helloReplyCaptor;

  private GreeterService greeterService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    greeterService = new GreeterService();
  }

  @Test
  public void sayHello_sendsExpectedReply() {
    GreeterOuterClass.HelloRequest request =
        GreeterOuterClass.HelloRequest.newBuilder().setName("Test User").build();

    greeterService.sayHello(request, responseObserver);

    verify(responseObserver).onNext(helloReplyCaptor.capture());
    verify(responseObserver).onCompleted();

    GreeterOuterClass.HelloReply reply = helloReplyCaptor.getValue();
    assertEquals("Hello from Accenture to Test User", reply.getMessage());
    System.out.print("entra por aqui 1 ");
  }

  @Test
  public void sayHello_withEmptyName_sendsExpectedReply() {
    GreeterOuterClass.HelloRequest request =
        GreeterOuterClass.HelloRequest.newBuilder().setName("").build();

    greeterService.sayHello(request, responseObserver);

    verify(responseObserver).onNext(helloReplyCaptor.capture());
    verify(responseObserver).onCompleted();

    GreeterOuterClass.HelloReply reply = helloReplyCaptor.getValue();
    assertEquals("Hello from Accenture to ", reply.getMessage());
    System.out.print("entra por aqui 2");
    System.out.print("entra por aqui 3");
  }
}
