package com.grpccrudpostgres.server;

import com.grpccrudpostgres.grpc.*;
import com.grpccrudpostgres.models.DoctorEntity;
import com.grpccrudpostgres.service.DoctorEntityService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.micronaut.grpc.annotation.GrpcService;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class AdminServer extends AdminserviceGrpc.AdminserviceImplBase {
    @Inject
    DoctorEntityService doctorEntityService;


    @Override
    public StreamObserver<Doctors> setListOfDoctorInfo(StreamObserver<Empty> responseObserver) {

        return new StreamObserver<>() {

            @Override
            public void onNext(Doctors doctors) {

                List<Doctor> doctorEntity = doctors.getDoctorList();
                System.out.println(doctorEntity);
                List<DoctorEntity> doctorEntityList = new ArrayList<>();
                for (Doctor doctor : doctorEntity) {
                    DoctorEntity doctorEntity1 = DoctorEntity.builder().name(doctor.getName())
                            .specialist(doctor.getSpecialist())
                            .experience_year(doctor.getExperienceYear()).build();
                    //converting the doctor to DoctorEntity object to store in the database and adding to list doctorEntityList
                    doctorEntityList.add(doctorEntity1);
                }
//               call a service until there is object in the list and add each list of doctors to the database
                for (DoctorEntity doctor : doctorEntityList) {
                    doctorEntityService.saveAllDoctors(doctor);
                }

            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(new RuntimeException());
            }
//this method is called at the end when there is no error in the incoming stream of data  server

            @Override
            public void onCompleted() {
                responseObserver.onNext(Empty.getDefaultInstance());
                responseObserver.onCompleted();
            }
        };


    }


    @Override
    public void getAllDoctorInfo(Empty request, StreamObserver<Doctor> responseObserver) {
        List<DoctorEntity> doctorEntityList = doctorEntityService.getAllDoctor();
        System.out.println(doctorEntityList);
        try {
            for (DoctorEntity doctor : doctorEntityList) {
                System.out.println(doctor.getName());
                Doctor doctor1 = Doctors.newBuilder()
                        .addDoctorBuilder()
                        .setName(doctor.getName())
                        .setExperienceYear(doctor.getExperience_year())
                        .setSpecialist(doctor.getSpecialist())
                        .build();
                System.out.println(doctor1);

                // This will send the doctor1 object to the client
                responseObserver.onNext(doctor1);
            }
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
            return;
        }

        responseObserver.onCompleted();
    }

    @Override
    public void setDoctorInfo(Doctor request, StreamObserver<Doctor> responseObserver) {
        DoctorEntity doctorEntity = DoctorEntity.builder().name(request.getName()).specialist(request.getSpecialist()).experience_year(request.getExperienceYear()).build();
        DoctorEntity doctorEntity1 = doctorEntityService.saveDoctor(doctorEntity);
        //convert this response into Doctor type as there in grpc proto java files folder to send
//the response back to client
        Doctor resp = Doctor.newBuilder().setName(doctorEntity1.getName()).setExperienceYear(doctorEntity1.getExperience_year()).setSpecialist(doctorEntity1.getSpecialist()).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void updateDoctorInfo(Doctor request, StreamObserver<StatusMessage> responseObserver) {
        super.updateDoctorInfo(request, responseObserver);
    }

    @Override
    public void deleteDoctorInfo(Doctor request, StreamObserver<StatusMessage> responseObserver) {
        try {
            Integer id = request.getId();
            doctorEntityService.deleteDoctorById(id);
            responseObserver.onNext(StatusMessage.newBuilder().setStatMessage("Doctor deleted successfully").build());
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
        responseObserver.onCompleted();
    }
}