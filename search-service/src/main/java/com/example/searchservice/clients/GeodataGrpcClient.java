package com.example.searchservice.clients;

import com.example.centroid.grpc.CentroidRequest;
import com.example.centroid.grpc.CentroidResponse;
import com.example.centroid.grpc.CentroidServiceGrpc;
import com.example.searchservice.domain.entities.Centroid;
import com.netflix.discovery.EurekaClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeodataGrpcClient {
    private final EurekaClient eurekaClient;

    private CentroidServiceGrpc.CentroidServiceBlockingStub openChannel() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(
                        eurekaClient.getApplication("geodata").getInstances().get(0).getIPAddr(),
                        eurekaClient.getApplication("geodata").getInstances().get(0).getPort() + 10)
                .usePlaintext().build();
        CentroidServiceGrpc.CentroidServiceBlockingStub stub = CentroidServiceGrpc.newBlockingStub(managedChannel);
        return stub;
    }

    public Centroid getCentroidById(String id) {
        CentroidResponse centroidResponse = openChannel().getCentroid(CentroidRequest.newBuilder().setId(id).build());
        return new Centroid(centroidResponse.getX(), centroidResponse.getY());
    }
}
