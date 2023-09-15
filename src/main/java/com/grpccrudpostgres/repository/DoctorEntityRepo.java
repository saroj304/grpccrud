package com.grpccrudpostgres.repository;

import com.grpccrudpostgres.models.DoctorEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
@Repository
public interface DoctorEntityRepo extends JpaRepository<DoctorEntity,Integer> {
}
