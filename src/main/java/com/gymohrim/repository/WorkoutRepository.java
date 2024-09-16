package com.gymohrim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkoutRepository extends JpaRepository<WorkoutRepository, Integer> {
}
