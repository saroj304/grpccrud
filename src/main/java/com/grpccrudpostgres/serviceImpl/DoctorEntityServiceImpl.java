package com.grpccrudpostgres.serviceImpl;

import com.grpccrudpostgres.models.DoctorEntity;
import com.grpccrudpostgres.repository.DoctorEntityRepo;
import com.grpccrudpostgres.service.DoctorEntityService;
import jakarta.inject.Inject;

import java.util.List;

public class DoctorEntityServiceImpl implements DoctorEntityService {
    @Inject
    DoctorEntityRepo doctorEntityRepo;

    @Override
    public DoctorEntity saveDoctor(DoctorEntity doctorEntity) {
       return  doctorEntityRepo.save(doctorEntity);
    }

    @Override
    public List<DoctorEntity> getAllDoctor() {
        return null;
    }

    @Override
    public void saveAllDoctors(DoctorEntity doctorEntity) {
       doctorEntityRepo.save(doctorEntity);
    }

    @Override
    public void deleteDoctorById(Integer id) {
     doctorEntityRepo.deleteById(id);
    }
}
