package com.grpccrudpostgres.service;

import com.grpccrudpostgres.models.DoctorEntity;

import java.util.List;

public interface DoctorEntityService {
    public DoctorEntity saveDoctor(DoctorEntity doctorEntity);

    public List<DoctorEntity> getAllDoctor();

    public void saveAllDoctors(DoctorEntity doctorEntity);

    public void deleteDoctorById(Integer id);
}
