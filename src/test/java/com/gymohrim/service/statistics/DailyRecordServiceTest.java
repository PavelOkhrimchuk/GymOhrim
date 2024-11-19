package com.gymohrim.service.statistics;

import com.gymohrim.entity.DailyRecord;
import com.gymohrim.entity.User;
import com.gymohrim.exception.statistics.DailyRecordNotFoundException;
import com.gymohrim.exception.user.InvalidDateOrUserException;
import com.gymohrim.repository.DailyRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DailyRecordServiceTest {

    @Mock
    private DailyRecordRepository dailyRecordRepository;

    @InjectMocks
    private DailyRecordService dailyRecordService;


    @Test
    @DisplayName("Test saveDailyRecord functionality")
    void testSaveDailyRecord() {
        User user = new User();
        user.setName("Test User");
        Date date = new Date();

        DailyRecord dailyRecord = DailyRecord.builder().user(user).date(date).build();

        dailyRecordService.saveDailyRecord(date, user);

        verify(dailyRecordRepository, times(1)).save(argThat(record ->
                record.getUser().equals(user) && record.getDate().equals(date)
        ));
    }



    @Test
    @DisplayName("Test existsByDateAndUser functionality")
    void testExistsByDateAndUser() {
        User user = new User();
        Date date = new Date();
        when(dailyRecordRepository.findByDateAndUser(date, user)).thenReturn(Optional.of(new DailyRecord()));

        boolean exists = dailyRecordService.existsByDateAndUser(date, user);

        assertTrue(exists);
    }

    @Test
    @DisplayName("Test findByDateAndUser with null values")
    void testFindByDateAndUserWithNullValues() {
        Date date = null;
        User user = null;

        assertThrows(InvalidDateOrUserException.class, () -> {
            dailyRecordService.findByDateAndUser(date, user);
        });
    }

    @Test
    @DisplayName("Test findById with non-existent ID")
    void testFindByIdWithNonExistentId() {
        Integer id = 1;

        when(dailyRecordRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DailyRecordNotFoundException.class, () -> {
            dailyRecordService.findById(id);
        });
    }

    @Test
    @DisplayName("Test findById with existing ID")
    void testFindByIdWithExistingId() {
        Integer id = 1;
        User user = new User();
        user.setName("Test User");
        Date date = new Date();
        DailyRecord dailyRecord = DailyRecord.builder().id(id).user(user).date(date).build();

        when(dailyRecordRepository.findById(id)).thenReturn(Optional.of(dailyRecord));

        DailyRecord result = dailyRecordService.findById(id);

        assertEquals(dailyRecord, result);
    }











}