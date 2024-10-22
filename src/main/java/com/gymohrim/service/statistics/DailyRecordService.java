package com.gymohrim.service.statistics;


import com.gymohrim.entity.DailyRecord;
import com.gymohrim.entity.User;
import com.gymohrim.exception.DailyRecordNotFoundException;
import com.gymohrim.repository.DailyRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DailyRecordService {
    private  final DailyRecordRepository dailyRecordRepository;



    @Transactional
    public void saveDailyRecord(Date date, User user) {
        DailyRecord dailyRecord = DailyRecord.builder()
                .user(user)
                .date(date)
                .build();

        dailyRecordRepository.save(dailyRecord);
        log.info("Daily record saved successfully for user: {} on date: {}", user.getName(), date);
    }

    public boolean existsByDateAndUser(Date date, User user) {
       return dailyRecordRepository.findByDateAndUser(date, user).isPresent();
    }

    public Optional<DailyRecord> findByDateAndUser(Date date, User user) {
        if (date == null || user == null) {
            log.error("Date or user is null when trying to find daily record");
            throw new IllegalArgumentException("Date and user must not be null.");
        }

        return dailyRecordRepository.findByDateAndUser(date, user);
    }

    public DailyRecord findById(Integer id) {
        return dailyRecordRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Daily record with ID {} not found", id);
                    return new DailyRecordNotFoundException("Record with ID " + id + " not found");
                });
    }





}
