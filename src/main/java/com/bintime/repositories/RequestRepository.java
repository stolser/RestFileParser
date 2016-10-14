package com.bintime.repositories;

import com.bintime.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {

}
