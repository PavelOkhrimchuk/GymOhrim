package com.gymohrim.service.statistics;

import com.gymohrim.IntegrationTestBase;
import com.gymohrim.entity.DailyRecord;
import com.gymohrim.entity.User;
import com.gymohrim.repository.DailyRecordRepository;
import com.gymohrim.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest

class DailyRecordServiceTest extends IntegrationTestBase {

    @Autowired
    private DailyRecordRepository dailyRecordRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Date date;

    @BeforeEach
    void setUp() {
        Optional<User> optionalUser = userRepository.findByEmail("user1@example.com");
        user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
        date = new Date();
    }

    @Test
    void saveDailyRecord_shouldSaveRecordSuccessfully() {
        DailyRecord dailyRecord = DailyRecord.builder()
                .user(user)
                .date(date)
                .build();

        DailyRecord savedRecord = dailyRecordRepository.save(dailyRecord);

        assertThat(savedRecord).isNotNull();
        assertThat(savedRecord.getId()).isNotNull();
        assertThat(savedRecord.getUser()).isEqualTo(user);
    }






}