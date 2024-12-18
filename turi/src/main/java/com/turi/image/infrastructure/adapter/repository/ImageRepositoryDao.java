package com.turi.image.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepositoryDao extends JpaRepository<ImageEntity, Long>
{

}
