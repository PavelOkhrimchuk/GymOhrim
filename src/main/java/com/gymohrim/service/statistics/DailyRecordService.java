package com.gymohrim.service.statistics;


import com.gymohrim.entity.DailyRecord;
import com.gymohrim.entity.User;
import com.gymohrim.repository.DailyRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class DailyRecordService {
    private  final DailyRecordRepository dailyRecordRepository;

    public void saveDailyRecord(Date date, User user) {
        DailyRecord dailyRecord = DailyRecord.builder()
                .user(user)
                .date(date)
                .build();

        dailyRecordRepository.save(dailyRecord);
    }

    public boolean existsByDateAndUser(Date date, User user) {
       return dailyRecordRepository.findByDateAndUser(date, user).isPresent();
    }



    
}
