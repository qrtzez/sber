package com.sber.sber.repository;

import com.sber.sber.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {


    @Query(value = "select title from news limit 80", nativeQuery = true)
    List<String> findLastNews();
}
