package com.gymohrim.service.statistics;

import com.gymohrim.IntegrationTestBase;
import com.gymohrim.entity.DailyRecord;
import com.gymohrim.entity.User;
import com.gymohrim.repository.DailyRecordRepository;
import com.gymohrim.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DailyRecordServiceIntegrationTest extends IntegrationTestBase {


    @Autowired
    private DailyRecordService dailyRecordService;

    @Autowired
    private DailyRecordRepository dailyRecordRepository;

    @Autowired
    private UserRepository userRepository;


    private User createUniqueUser() {
        User user = new User();
        user.setEmail("testuser" + System.currentTimeMillis() + "@example.com");
        user.setName("Test User " + System.currentTimeMillis());
        user.setPassword("test123");
        return userRepository.save(user);
    }

    @Test
    @Transactional
    void saveDailyRecord_shouldSaveRecordSuccessfully() {

        User user = createUniqueUser();
        Date date = new Date();


        dailyRecordService.saveDailyRecord(date, user);


        Optional<DailyRecord> savedRecord = dailyRecordRepository.findByDateAndUser(date, user);
        assertThat(savedRecord).isPresent();
        assertThat(savedRecord.get().getUser()).isEqualTo(user);
        assertThat(savedRecord.get().getDate()).isEqualTo(date);
    }






}
