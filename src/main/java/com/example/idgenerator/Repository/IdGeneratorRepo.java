package com.example.idgenerator.Repository;

import com.example.idgenerator.Model.IdGenerator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdGeneratorRepo extends JpaRepository<IdGenerator, Long> {

    @Query(value = "SELECT * FROM id_generator ORDER BY time_stamp DESC LIMIT 1", nativeQuery = true)
    Optional<IdGenerator> getLastInsertedRecord();
}
