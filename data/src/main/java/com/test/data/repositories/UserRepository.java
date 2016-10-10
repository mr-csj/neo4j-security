package com.test.data.repositories;

import com.test.data.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository extends GraphRepository<User> {
    User findByName(String name);

    User findByNameLike(String name);

    List<User> findByNameLike(String name, Pageable pageable);

    @Query("MATCH (n:User) RETURN n;")
    List<User> getUsers(Pageable pageable);

    @Query("MATCH (u:User) WHERE u.name =~ ('(?i).*'+{name}+'.*') RETURN u")
    Collection<User> findByNameContaining(@Param("name") String name);

    @Query("MATCH (u:User)-[:OWNER]->(o:Role) RETURN u.name AS user, collect(o.name) AS cast LIMIT {limit}")
    List<Map<String,Object>> graph(@Param("limit") int limit);

    @Query("MATCH (n:Unit)<-[:BELONG]-(u:User) WHERE ID(n) ={id} RETURN u")
    Iterable<User> getUsersByUnitId(@Param("id") Long id);

}


