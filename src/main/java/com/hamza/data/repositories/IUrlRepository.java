package com.hamza.data.repositories;

import com.hamza.data.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUrlRepository extends JpaRepository<UrlEntity, String> { // automatic generation of CRUD ops
    Optional<UrlEntity> findByFullUrl(String fullUrl); // to handle duplication
}
