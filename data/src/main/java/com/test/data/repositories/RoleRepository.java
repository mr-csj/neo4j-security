package com.test.data.repositories;

import com.test.data.domain.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends GraphRepository<Role> {
    Role findByName(String name);
}


