package com.sber.sber.repository;

import com.sber.sber.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Photo findByOriginalFileName(String originalName);
}
