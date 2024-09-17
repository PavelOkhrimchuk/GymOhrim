package com.gymohrim.service.statistics;


import com.gymohrim.repository.DailyRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DailyRecordService {
    private  final DailyRecordRepository dailyRecordRepository;

    
}
