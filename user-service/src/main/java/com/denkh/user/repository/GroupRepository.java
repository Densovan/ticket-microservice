package com.denkh.user.repository;

import com.denkh.user.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByNameIn(Set<String> groupName);

    boolean existsByName(String name);
}
