package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MascotaEntity;

@Repository
public interface MascotaRepository extends JpaRepository<MascotaEntity,Integer> {
    List<MascotaEntity> findByCliente_CodCliente(Integer codCliente);

}
