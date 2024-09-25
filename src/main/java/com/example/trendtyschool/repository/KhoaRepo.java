package com.example.trendtyschool.repository;

import com.example.trendtyschool.model.Entity.Khoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KhoaRepo extends JpaRepository<Khoa, Integer> {
    Optional<Khoa> findByNameKhoa(String nameKhoa);
}
