package com.gymohrim.service.statistics;

class DailyRecordServiceTest {

//    @Mock
//    private DailyRecordRepository dailyRecordRepository;
//
//    @InjectMocks
//    private DailyRecordService dailyRecordService;
//
//    private User user;
//    private Date date;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        user = new User();
//        user.setId(1);
//        user.setEmail("user1@example.com");
//        date = new Date();
//    }
//
//    @Test
//    void saveDailyRecord_shouldSaveRecordSuccessfully() {
//        DailyRecord dailyRecord = DailyRecord.builder()
//                .user(user)
//                .date(date)
//                .build();
//
//        when(dailyRecordRepository.save(any(DailyRecord.class))).thenReturn(dailyRecord);
//
//        dailyRecordService.saveDailyRecord(date, user);
//
//        verify(dailyRecordRepository, times(1)).save(any(DailyRecord.class));
//    }
//
//    @Test
//    void existsByDateAndUser_shouldReturnTrueWhenRecordExists() {
//        when(dailyRecordRepository.findByDateAndUser(date, user)).thenReturn(Optional.of(new DailyRecord()));
//
//        boolean exists = dailyRecordService.existsByDateAndUser(date, user);
//
//        assertThat(exists).isTrue();
//        verify(dailyRecordRepository, times(1)).findByDateAndUser(date, user);
//    }
//
//    @Test
//    void existsByDateAndUser_shouldReturnFalseWhenRecordDoesNotExist() {
//        when(dailyRecordRepository.findByDateAndUser(date, user)).thenReturn(Optional.empty());
//
//        boolean exists = dailyRecordService.existsByDateAndUser(date, user);
//
//        assertThat(exists).isFalse();
//        verify(dailyRecordRepository, times(1)).findByDateAndUser(date, user);
//    }
//
//    @Test
//    void findByDateAndUser_shouldReturnRecordWhenExists() {
//        DailyRecord record = DailyRecord.builder().user(user).date(date).build();
//        when(dailyRecordRepository.findByDateAndUser(date, user)).thenReturn(Optional.of(record));
//
//        Optional<DailyRecord> foundRecord = dailyRecordService.findByDateAndUser(date, user);
//
//        assertThat(foundRecord).isPresent();
//        assertThat(foundRecord.get().getUser()).isEqualTo(user);
//    }
//
//    @Test
//    void findByDateAndUser_shouldThrowExceptionWhenUserOrDateIsNull() {
//        assertThrows(IllegalArgumentException.class, () -> dailyRecordService.findByDateAndUser(null, user));
//        assertThrows(IllegalArgumentException.class, () -> dailyRecordService.findByDateAndUser(date, null));
//    }
//
//    @Test
//    void findById_shouldReturnRecordWhenExists() {
//        DailyRecord record = new DailyRecord();
//        record.setId(1);
//        record.setUser(user);
//        record.setDate(date);
//
//        when(dailyRecordRepository.findById(1)).thenReturn(Optional.of(record));
//
//        DailyRecord foundRecord = dailyRecordService.findById(1);
//
//        assertThat(foundRecord).isNotNull();
//        assertThat(foundRecord.getUser()).isEqualTo(user);
//    }
//
//    @Test
//    void findById_shouldThrowExceptionWhenRecordNotFound() {
//        when(dailyRecordRepository.findById(9999)).thenReturn(Optional.empty());
//
//        assertThrows(DailyRecordNotFoundException.class, () -> dailyRecordService.findById(9999));
//    }











}