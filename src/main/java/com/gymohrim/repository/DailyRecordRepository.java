package com.gymohrim.repository;

import com.gymohrim.entity.DailyRecord;
import com.gymohrim.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface DailyRecordRepository extends JpaRepository<DailyRecord, Integer> {
    Optional<DailyRecord> findByDateAndUser(Date date, User user);

}
