package com.example.demo.redis;

import com.example.demo.config.RedisConfig;
import com.example.demo.service.RedisCacheService;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RedisCacheServiceTest.Config .class)
@TestPropertySource(properties = "transaction.rank.max-total=200")
@Testcontainers
public class RedisCacheServiceTest {
    @Autowired
    private RedisCacheService cacheService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Container
    private static final GenericContainer container = new GenericContainer<>("redis:5.0.3-alpine")
            .withExposedPorts(6379);

    static {
        container.start();
        container.waitingFor(Wait.forHealthcheck());
    }

    @After
    public void tearUp() {
        redisTemplate.keys("*").forEach(key -> redisTemplate.delete(key));
    }

//    @Test
//    public void testAddGameBetPoolInfo() {
//        cacheService.updateBetPool(100, GameCategory.BACCARAT, "T1", "p", BigDecimal.valueOf(100));
//        cacheService.updateBetPool(100, GameCategory.BACCARAT, "T1", "pp", BigDecimal.valueOf(200));
//        cacheService.updateBetPool(100, GameCategory.BACCARAT, "T1", "b", BigDecimal.valueOf(1000));
//        cacheService.updateBetPool(100, GameCategory.BACCARAT, "T1", "p", BigDecimal.valueOf(500));
//
//
//        Map<String, BettingPool.BetPlaceRoll> betPlaceRollMap = cacheService.getBetPool(GameCategory.BACCARAT, "T1");
//        Assert.assertEquals(0, betPlaceRollMap.get("p").getBettingTotal().compareTo(BigDecimal.valueOf(600)));
//        Assert.assertEquals(0, betPlaceRollMap.get("pp").getBettingTotal().compareTo(BigDecimal.valueOf(200)));
//        Assert.assertEquals(0, betPlaceRollMap.get("b").getBettingTotal().compareTo(BigDecimal.valueOf(1000)));
//        Assert.assertNull(betPlaceRollMap.get("bp"));
//    }
//
//    @Test
//    public void testClearBetPool() {
//        cacheService.updateBetPool(1, GameCategory.BACCARAT, "T1", "p", BigDecimal.TEN);
//        cacheService.updateBetPool(2, GameCategory.BACCARAT, "T1", "pp", BigDecimal.TEN);
//        cacheService.clearBetPool(GameCategory.BACCARAT, "T1");
//        String tableBetPlaceAmountKey = RedisKey.getBetPoolAmountKey(GameCategory.BACCARAT, "T1");
//        String userBetPlaceAmountKey = RedisKey.getBetPlacePlayerCountKey(GameCategory.BACCARAT, "T1", "*");
//        Assert.assertFalse(redisTemplate.hasKey(tableBetPlaceAmountKey));
//        Assert.assertTrue(redisTemplate.keys(userBetPlaceAmountKey).isEmpty());
//    }
//
//    @Test
//    public void testPushBetSlipThenGetBetSlip() {
//        List<BetSlipDto> originBetSlips = Arrays.asList(
//                BetSlipDto.builder()
//                        .betCode("41337556")
//                        .betPlaceCode(BET_PLACE_PLAYER)
//                        .betAmount(new BigDecimal("500"))
//                        .deposit(BigDecimal.ZERO)
//                        .sideBet(true)
//                        .betDateTime(LocalDateTime.now())
//                        .build(),
//                BetSlipDto.builder()
//                        .betCode("41337557")
//                        .betPlaceCode(BET_PLACE_PLAYER_PAIR)
//                        .betAmount(new BigDecimal("300"))
//                        .deposit(BigDecimal.ZERO)
//                        .sideBet(false)
//                        .betDateTime(LocalDateTime.now())
//                        .build()
//        );
//
//        cacheService.pushBetSlip(165, 12345, originBetSlips);
//        String betSlipKey = RedisKey.getBetSlipKey(12345, 165);
//        Assert.assertTrue(redisTemplate.hasKey(betSlipKey));
//
//        List<BetSlipDto> betSlips = cacheService.getBetSlips(165, 12345);
//
//        Assert.assertEquals(2, betSlips.size());
//        Assert.assertEquals("41337556", betSlips.get(0).getBetCode());
//        Assert.assertEquals(new BigDecimal("500"), betSlips.get(0).getBetAmount());
//        Assert.assertEquals(BigDecimal.ZERO, betSlips.get(0).getDeposit());
//        Assert.assertEquals(BET_PLACE_PLAYER, betSlips.get(0).getBetPlaceCode());
//        Assert.assertTrue(betSlips.get(0).isSideBet());
//        Assert.assertNotNull(betSlips.get(0).getBetDateTime());
//        Assert.assertNull(betSlips.get(0).getStatus());
//    }
//
//    @Test
//    public void WhenBetSlipsNotFound_ThenGetBetSlips_ShouldReturnEmptyList() {
//        List<BetSlipDto> betSlips = cacheService.getBetSlips(11111, 222222);
//
//        Assert.assertTrue(betSlips.isEmpty());
//    }
//
//    @Test
//    public void testAddWinAmountThenGetWinAmountRank1() {
//        cacheService.addWinAmount(1, new BigDecimal("100"));
//        cacheService.addWinAmount(1, new BigDecimal("200"));
//        cacheService.addWinAmount(1, new BigDecimal("300"));
//        cacheService.addWinAmount(2, new BigDecimal("500"));
//        cacheService.addWinAmount(3, new BigDecimal("100"));
//        cacheService.addWinAmount(3, new BigDecimal("100"));
//        cacheService.addWinAmount(4, new BigDecimal("800"));
//        cacheService.addWinAmount(5, new BigDecimal("900"));
//        cacheService.addWinAmount(6, new BigDecimal("700"));
//        cacheService.addWinAmount(7, new BigDecimal("1000"));
//        cacheService.addWinAmount(8, new BigDecimal("800"));
//        cacheService.addWinAmount(8, new BigDecimal("100"));
//        cacheService.addWinAmount(9, new BigDecimal("1100"));
//        cacheService.addWinAmount(10, new BigDecimal("1200"));
//        cacheService.addWinAmount(11, new BigDecimal("1300"));
//        cacheService.addWinAmount(12, new BigDecimal("100"));
//        cacheService.addWinAmount(13, new BigDecimal("0"));
//        cacheService.addWinAmount(14, new BigDecimal("10.15"));
//
//        Page<WinAmountUserDto> page = cacheService.getWinAmountRank(PageRequest.of(0, 10));
//        Assert.assertEquals(2, page.getTotalPages());
//        Assert.assertEquals(10, page.getContent().size());
//        Assert.assertEquals(new BigDecimal("1300.00"), page.getContent().get(0).getAmount());
//
//        page = cacheService.getWinAmountRank(PageRequest.of(1, 10));
//        Assert.assertEquals(2, page.getTotalPages());
//        Assert.assertEquals(3, page.getNumberOfElements());
//        Assert.assertEquals(new BigDecimal("10.15"), page.getContent().get(2).getAmount());
//    }
//
//    @Test
//    public void testAddWinAmountThenGetWinAmountRank2() {
//        cacheService.addWinAmount(1, new BigDecimal("-100"));
//        cacheService.addWinAmount(1, new BigDecimal("-200"));
//        cacheService.addWinAmount(1, new BigDecimal("-300"));
//        cacheService.addWinAmount(2, new BigDecimal("-500"));
//        cacheService.addWinAmount(3, new BigDecimal("-100"));
//        cacheService.addWinAmount(3, new BigDecimal("-100"));
//        cacheService.addWinAmount(4, new BigDecimal("-800"));
//        cacheService.addWinAmount(5, new BigDecimal("-900"));
//        cacheService.addWinAmount(6, new BigDecimal("-700"));
//        cacheService.addWinAmount(7, new BigDecimal("-1000"));
//        cacheService.addWinAmount(8, new BigDecimal("-800"));
//        cacheService.addWinAmount(8, new BigDecimal("-100"));
//        cacheService.addWinAmount(9, new BigDecimal("-1100"));
//        cacheService.addWinAmount(10, new BigDecimal("-1200"));
//        cacheService.addWinAmount(11, new BigDecimal("-1300"));
//        cacheService.addWinAmount(12, new BigDecimal("-100"));
//        cacheService.addWinAmount(13, new BigDecimal("0"));
//        cacheService.addWinAmount(14, new BigDecimal("10.15"));
//
//        Page<WinAmountUserDto> page = cacheService.getWinAmountRank(PageRequest.of(0, 10));
//
//        Assert.assertEquals(1, page.getTotalPages());
//        Assert.assertEquals(1, page.getContent().size());
//        Assert.assertEquals(new BigDecimal("10.15"), page.getContent().get(0).getAmount());
//
//    }
//
//    @Test
//    public void testAddWinAmountThenGetWinAmountRank3() {
//        Page<WinAmountUserDto> page = cacheService.getWinAmountRank(PageRequest.of(0, 10));
//        Assert.assertEquals(0, page.getTotalPages());
//        Assert.assertEquals(0, page.getContent().size());
//    }
//
//
//    @Test
//    public void testAddWinAmount_Count500_ThenGetWinAmountRank_ShouldReturn_Count200() {
//        IntStream.range(0, 500).forEach(i -> cacheService.addWinAmount(i, new BigDecimal(String.valueOf(RandomUtils.nextDouble(1, 50000)))));
//
//        Page<WinAmountUserDto> page = cacheService.getWinAmountRank(PageRequest.of(0, 8));
//        Assert.assertEquals(25, page.getTotalPages());
//        Assert.assertEquals(8, page.getContent().size());
//
//        page = cacheService.getWinAmountRank(PageRequest.of(22, 9));
//        Assert.assertEquals(23, page.getTotalPages());
//        Assert.assertEquals(2, page.getContent().size());
//    }
//
//    @Test
//    public void testAddWinAmount_Count200_ThenGetWinAmountRank_ShouldReturn_Count150() {
//        IntStream.range(0, 150).forEach(i -> cacheService.addWinAmount(i, new BigDecimal(String.valueOf(RandomUtils.nextDouble(1, 50000)))));
//        IntStream.range(150, 200).forEach(i -> cacheService.addWinAmount(i, new BigDecimal(String.valueOf(RandomUtils.nextDouble(0, 50000))).multiply(new BigDecimal("-1"))));
//
//        Page<WinAmountUserDto> page = cacheService.getWinAmountRank(PageRequest.of(18, 8));
//        Assert.assertEquals(19, page.getTotalPages());
//        Assert.assertEquals(6, page.getContent().size());
//
//        page = cacheService.getWinAmountRank(PageRequest.of(16, 9));
//        Assert.assertEquals(17, page.getTotalPages());
//        Assert.assertEquals(6, page.getContent().size());
//    }
//
//    @Test
//    public void testAddValidAmountAndThenGetValidAmountRank1() {
//        cacheService.addValidAmount(1, new BigDecimal("100"));
//        cacheService.addValidAmount(1, new BigDecimal("200"));
//        cacheService.addValidAmount(1, new BigDecimal("300"));
//        cacheService.addValidAmount(2, new BigDecimal("500"));
//        cacheService.addValidAmount(3, new BigDecimal("100"));
//        cacheService.addValidAmount(3, new BigDecimal("100"));
//        cacheService.addValidAmount(4, new BigDecimal("800"));
//        cacheService.addValidAmount(5, new BigDecimal("900"));
//        cacheService.addValidAmount(6, new BigDecimal("700"));
//        cacheService.addValidAmount(7, new BigDecimal("1000"));
//        cacheService.addValidAmount(8, new BigDecimal("800"));
//        cacheService.addValidAmount(8, new BigDecimal("100"));
//        cacheService.addValidAmount(9, new BigDecimal("1100"));
//        cacheService.addValidAmount(10, new BigDecimal("1200"));
//        cacheService.addValidAmount(11, new BigDecimal("1300"));
//        cacheService.addValidAmount(12, new BigDecimal("10.05"));
//        cacheService.addValidAmount(13, new BigDecimal("1500"));
//        cacheService.addValidAmount(14, new BigDecimal("13.15"));
//
//
//        Page<ValidAmountUserDto> page = cacheService.getValidAmountRank(PageRequest.of(0, 10));
//        Assert.assertEquals(2, page.getTotalPages());
//        Assert.assertEquals(10, page.getContent().size());
//        Assert.assertEquals(new BigDecimal("1500.00"), page.getContent().get(0).getAmount());
//
//        page = cacheService.getValidAmountRank(PageRequest.of(1, 10));
//        Assert.assertEquals(2, page.getTotalPages());
//        Assert.assertEquals(4, page.getContent().size());
//        Assert.assertEquals(new BigDecimal("13.15"), page.getContent().get(2).getAmount());
//        Assert.assertEquals(new BigDecimal("10.05"), page.getContent().get(3).getAmount());
//
//    }
//
//    @Test
//    public void testAddValidAmountAndThenGetValidAmountRank2() {
//        Page<ValidAmountUserDto> page = cacheService.getValidAmountRank(PageRequest.of(0, 10));
//        Assert.assertEquals(0, page.getTotalPages());
//        Assert.assertEquals(0, page.getContent().size());
//    }
//
//    @Test
//    public void testAddValidAmount_Count500_ThenGetValidAmountRank_ShouldReturn_Count200() {
//        IntStream.range(0, 500).forEach(i -> cacheService.addValidAmount(i, new BigDecimal(String.valueOf(RandomUtils.nextDouble(1, 50000)))));
//
//        Page<ValidAmountUserDto> page = cacheService.getValidAmountRank(PageRequest.of(0, 8));
//        Assert.assertEquals(25, page.getTotalPages());
//        Assert.assertEquals(8, page.getContent().size());
//
//        page = cacheService.getValidAmountRank(PageRequest.of(22, 9));
//        Assert.assertEquals(23, page.getTotalPages());
//        Assert.assertEquals(2, page.getContent().size());
//    }
//
//    @Test
//    public void testAddValidAmount_Count150_ThenGetValidAmountRank_ShouldReturn_Count150() {
//        IntStream.range(0, 150).forEach(i -> cacheService.addValidAmount(i, new BigDecimal(String.valueOf(RandomUtils.nextDouble(1, 50000)))));
//
//        Page<ValidAmountUserDto> page = cacheService.getValidAmountRank(PageRequest.of(18, 8));
//        Assert.assertEquals(19, page.getTotalPages());
//        Assert.assertEquals(6, page.getContent().size());
//
//        page = cacheService.getValidAmountRank(PageRequest.of(16, 9));
//        Assert.assertEquals(17, page.getTotalPages());
//        Assert.assertEquals(6, page.getContent().size());
//    }
//
//    @Test
//    public void test__SaveGameBetLimit_ThenGetGameBetLimit_shouldReturn_empty() {
//        BettingLimit.BetLimitRoll betLimitRoll = BettingLimit.BetLimitRoll.builder().minLimit(null).maxLimit(null).build();
//        cacheService.saveGameBettingLimit(1, GameCategory.BACCARAT, betLimitRoll);
//        Optional<BettingLimit.BetLimitRoll> gameBetLimitOptional = cacheService.getGameBettingLimit(1, GameCategory.BACCARAT);
//
//        Assert.assertFalse(gameBetLimitOptional.isPresent());
//        Assert.assertEquals(Optional.empty(), gameBetLimitOptional);
//    }

    @Configuration
    @TestComponent
    @Import(RedisConfig.class)
    public static class Config {
        @Bean
        public LettuceConnectionFactory redisConnectionFactory() {
            return new LettuceConnectionFactory(new RedisStandaloneConfiguration(container.getHost(), container.getFirstMappedPort()));
        }

        @Bean
        public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
            return new PropertySourcesPlaceholderConfigurer();
        }

        @Bean
        public RedisCacheService cacheService(RedisTemplate<String, Object> redisTemplate) {
            return new RedisCacheService(redisTemplate);
        }
    }
}