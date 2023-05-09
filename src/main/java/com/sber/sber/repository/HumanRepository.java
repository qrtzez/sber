package com.sber.sber.repository;

import com.sber.sber.entity.Human;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface HumanRepository extends JpaRepository<Human, Long> {

    List<Human> findByName(String name);

    Human findHumanByName(String name);

    Human findHumanByEmail(String email);

    @Query(value = "select email from Human")
    Set<String> findAllEmail();
}
