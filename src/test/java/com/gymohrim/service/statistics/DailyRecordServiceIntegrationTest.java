package com.gymohrim.service.statistics;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DailyRecordServiceIntegrationTest  {

//
//    @Autowired
//    private DailyRecordService dailyRecordService;
//
//    @Autowired
//    private DailyRecordRepository dailyRecordRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    private User createUniqueUser() {
//        User user = new User();
//        user.setEmail("testuser" + System.currentTimeMillis() + "@example.com");
//        user.setName("Test User " + System.currentTimeMillis());
//        user.setPassword("test123");
//        return userRepository.save(user);
//    }
//
//    @Test
//    @Transactional
//    void saveDailyRecord_shouldSaveRecordSuccessfully() {
//
//        User user = createUniqueUser();
//        Date date = new Date();
//
//
//        dailyRecordService.saveDailyRecord(date, user);
//
//
//        Optional<DailyRecord> savedRecord = dailyRecordRepository.findByDateAndUser(date, user);
//        assertThat(savedRecord).isPresent();
//        assertThat(savedRecord.get().getUser()).isEqualTo(user);
//        assertThat(savedRecord.get().getDate()).isEqualTo(date);
//    }






}
