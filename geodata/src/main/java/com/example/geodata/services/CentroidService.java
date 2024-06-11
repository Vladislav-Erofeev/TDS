package com.example.geodata.services;

import com.example.centroid.grpc.CentroidRequest;
import com.example.centroid.grpc.CentroidResponse;
import com.example.centroid.grpc.CentroidServiceGrpc;
import com.example.geodata.mappers.PropsMapper;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CentroidService extends CentroidServiceGrpc.CentroidServiceImplBase {
    private final ItemService itemService;
    @Override
    public void getCentroid(CentroidRequest request, StreamObserver<CentroidResponse> responseObserver) {
        Long id = PropsMapper.decodeId(request.getId());
        Point p = itemService.getById(id).getGeometry().getCentroid();
        CentroidResponse centroidResponse = CentroidResponse.newBuilder().setX(p.getX()).setY(p.getY()).build();
        responseObserver.onNext(centroidResponse);
        responseObserver.onCompleted();
    }
}
