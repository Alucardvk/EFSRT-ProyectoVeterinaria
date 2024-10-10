package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CitaEntity;

@Repository
public interface CitaRepository extends JpaRepository<CitaEntity,Integer> {

}
