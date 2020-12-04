package com.example.demo.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheService {

    @Value("${transaction.rank.max-total:50}")
    private int totalCount;

    private RedisTemplate<String, Object> redisTemplate;

    public RedisCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

//    public void pushBetSlip(int userId, int gameId, List<BetSlipDto> betSlips) {
//        String key = RedisKey.getBetSlipKey(gameId, userId);
//
//        redisTemplate.opsForList().rightPushAll(key, betSlips.toArray());
//    }

//    public List<BetSlipDto> getBetSlips(int userId, int gameId) {
//        String key = RedisKey.getBetSlipKey(gameId, userId);
//
//        return (List<BetSlipDto>) (List<?>) redisTemplate.opsForList().range(key, 0, -1);
//    }


    public void addUserBetInGame(int userId, int gameId) {
        String key = RedisKey.getBetInGameKey(gameId);
        redisTemplate.opsForSet().add(key, userId);
    }

    public Set<Integer> getUserBetInGame(int gameId) {
        String key = RedisKey.getBetInGameKey(gameId);

        return (Set<Integer>) (Set<?>) redisTemplate.opsForSet().members(key);
    }

    public void deleteBetSlipsCache(int gameId, int userId) {
        String key = RedisKey.getBetSlipKey(gameId, userId);
        String keyUser = RedisKey.getBetInGameKey(gameId);
        redisTemplate.delete(key);
        redisTemplate.opsForSet().remove(keyUser, userId);
    }

//    public void addBetAmountOfUser(int userId, GameCategory gameCategory, String tableNumber, List<BetSlip> betSlips) {
//        betSlips.forEach(betSlip -> {
//            String userBetPlaceAmountKey = RedisKey.getBetPlaceRankKey(gameCategory, tableNumber, betSlip.getBetPlace().getCode());
//            redisTemplate.opsForZSet().incrementScore(userBetPlaceAmountKey, userId, betSlip.getBetAmount().doubleValue());
//            putToBetPoolRedisKeysMap(gameCategory, tableNumber, userBetPlaceAmountKey);
//        });
//    }

//    public MaxBettingUserDto getMaxBetAmountOfUser(GameCategory gameCategory, String tableNumber, String betPlaceCode) {
//        String userBetPlaceAmountKey = RedisKey.getBetPlaceRankKey(gameCategory, tableNumber, betPlaceCode);
//        Set<ZSetOperations.TypedTuple<Object>> maxBettingUserSet = redisTemplate.opsForZSet().reverseRangeWithScores(userBetPlaceAmountKey, 0, 0);
//        Iterator<ZSetOperations.TypedTuple<Object>> maxBettingUserSetIt = maxBettingUserSet.iterator();
//        if (maxBettingUserSetIt.hasNext()) {
//            ZSetOperations.TypedTuple<Object> maxBettingUser = maxBettingUserSetIt.next();
//            return maxBettingUser != null ? new MaxBettingUserDto(new BigDecimal(maxBettingUser.getScore()), (Integer) maxBettingUser.getValue()) : null;
//        }
//        return null;
//    }

//    public void updateBetPool(int userId, GameCategory gameCategory, String tableNumber, String betPlaceCode, BigDecimal betAmount) {
//        String tableBetPlaceAmountKey = RedisKey.getBetPoolAmountKey(gameCategory, tableNumber);
//        redisTemplate.opsForHash().increment(tableBetPlaceAmountKey, betPlaceCode, betAmount.doubleValue());
//
//        String userBetPlaceAmountKey = RedisKey.getBetPlacePlayerCountKey(gameCategory, tableNumber, betPlaceCode);
//        redisTemplate.opsForSet().add(userBetPlaceAmountKey, String.valueOf(userId));
//    }

//    public Map<String, BettingPool.BetPlaceRoll> getBetPool(GameCategory gameCategory, String tableNumber) {
//        String key = RedisKey.getBetPoolAmountKey(gameCategory, tableNumber);
//        Map<Object, Object> totalAmountByBetPlaceCode = redisTemplate.opsForHash().entries(key);
//
//        return totalAmountByBetPlaceCode.entrySet().stream()
//                .collect(Collectors.toMap(e -> (String) e.getKey(), e -> {
//                    String betPlacePlayerCountKey = RedisKey.getBetPlacePlayerCountKey(gameCategory, tableNumber, (String) e.getKey());
//                    long playerCount = redisTemplate.opsForSet().size(betPlacePlayerCountKey);
//                    return BettingPool.BetPlaceRoll.builder()
//                            .bettingTotal(new BigDecimal(String.valueOf(e.getValue())))
//                            .betPersonCount((int) playerCount)
//                            .build();
//                }));
//    }

//    public void clearBetPool(GameCategory gameCategory, String tableNumber) {
//        String betPoolAmountKey = RedisKey.getBetPoolAmountKey(gameCategory, tableNumber);
//        String betPlacePlayerCountKey = RedisKey.getBetPlacePlayerCountKey(gameCategory, tableNumber, "*");
//
//        redisTemplate.delete(betPoolAmountKey);
//        deleteByPattern(betPlacePlayerCountKey);
//    }

//    public BigDecimal addBetAmountByBetPlace(int gameId, int userId, String betPlaceCode, BigDecimal betAmount) {
//        BigDecimal betPlaceAmount = BigDecimal.valueOf(redisTemplate.opsForHash()
//                .increment(RedisKey.getUserBetPlaceSumKey(gameId, userId), betPlaceCode, betAmount.doubleValue()));
//        setExpiredTime(RedisKey.getUserBetPlaceSumKey(gameId, userId), 15, TimeUnit.MINUTES);
//        return betPlaceAmount;
//    }

//    public Optional<BigDecimal> getUserBetLimitAmount(GameCategory gameCategory, int userId, String betPlaceCode) {
//        String userBetPlaceLimitKey = RedisKey.getUserBetPlaceLimitKey(gameCategory, userId);
//        String s = (String) redisTemplate.opsForHash().get(userBetPlaceLimitKey, betPlaceCode);
//        if (s == null) {
//            return Optional.empty();
//        }
//        return Optional.of(new BigDecimal(s));
//    }

//    public void cacheUserBetLimitAmount(GameCategory gameCategory, int userId, Map<String, String> map) {
//        redisTemplate.opsForHash().putAll(RedisKey.getUserBetPlaceLimitKey(gameCategory, userId), map);
//        setExpiredTime(RedisKey.getUserBetPlaceLimitKey(gameCategory, userId), 1, TimeUnit.DAYS);
//    }

//    public void deductBetAmountByBetPlace(int gameId, int userId, String betPlaceCode, BigDecimal betAmount) {
//        betAmount = betAmount.multiply(new BigDecimal(-1));
//        redisTemplate.opsForHash().increment(RedisKey.getUserBetPlaceSumKey(gameId, userId), betPlaceCode, betAmount.doubleValue());
//    }

    public void addWinAmount(int userId, BigDecimal winAmount) {
        redisTemplate.opsForZSet().incrementScore(RedisKey.getUserWinAmountKey(), userId, winAmount.doubleValue());
    }

    public void addValidAmount(int userId, BigDecimal validAmount) {
        redisTemplate.opsForZSet().incrementScore(RedisKey.getValidAmountKey(), userId, validAmount.doubleValue());
    }

//    @Override
//    public Page<WinAmountUserDto> getWinAmountRank(Pageable pageable) {
//        long count = pageable.getOffset() + pageable.getPageSize() <= totalCount ? pageable.getPageSize() : (totalCount - pageable.getOffset()) <= 0 ? 0 : totalCount - pageable.getOffset();
//        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet()
//                .reverseRangeByScoreWithScores(RedisKey.getUserWinAmountKey(), 0, Integer.MAX_VALUE, pageable.getOffset(), count);
//        ArrayList<WinAmountUserDto> winAmountUserDtoList = new ArrayList<>();
//        if (set != null && set.size() > 0) {
//            for (ZSetOperations.TypedTuple<Object> objectTypedTuple : set) {
//                if (objectTypedTuple.getValue() != null && objectTypedTuple.getScore() != null && objectTypedTuple.getScore() > 0) {
//                    winAmountUserDtoList.add(WinAmountUserDto.builder()
//                            .userId((Integer) objectTypedTuple.getValue())
//                            .amount(new BigDecimal(objectTypedTuple.getScore().toString()).setScale(2, RoundingMode.FLOOR))
//                            .build());
//                }
//            }
//        }
//        int itemTotals = getRankItemTotals(RedisKey.getUserWinAmountKey(), pageable.getPageSize());
//
//        return new PageImpl<>(winAmountUserDtoList, pageable, itemTotals);
//    }

//    @Override
//    public Page<ValidAmountUserDto> getValidAmountRank(Pageable pageable) {
//        long count = pageable.getOffset() + pageable.getPageSize() <= totalCount ? pageable.getPageSize() : (totalCount - pageable.getOffset()) <= 0 ? 0 : totalCount - pageable.getOffset();
//        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet()
//                .reverseRangeByScoreWithScores(RedisKey.getValidAmountKey(), 0, Integer.MAX_VALUE, pageable.getOffset(), count);
//        ArrayList<ValidAmountUserDto> validAmountUserList = new ArrayList<>();
//        if (set != null && set.size() > 0) {
//            for (ZSetOperations.TypedTuple<Object> validAmountUser : set) {
//                if (validAmountUser.getValue() != null && validAmountUser.getScore() != null && validAmountUser.getScore() > 0) {
//                    validAmountUserList.add(ValidAmountUserDto.builder()
//                            .userId((Integer) validAmountUser.getValue())
//                            .amount(new BigDecimal(validAmountUser.getScore().toString()).setScale(2, RoundingMode.FLOOR)).build());
//                }
//            }
//        }
//        int itemTotals = getRankItemTotals(RedisKey.getValidAmountKey(), pageable.getPageSize());
//        return new PageImpl<>(validAmountUserList, pageable, itemTotals);
//    }

    private int getRankItemTotals(String key, int pageSize) {
        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet()
                .reverseRangeByScoreWithScores(key, 0, Integer.MAX_VALUE, 0, totalCount);
        int count = 0;
        if (set != null && set.size() > 0) {
            count = (int) set.parallelStream()
                    .map(dto -> dto.getScore() == null ? 0 : dto.getScore())
                    .filter(score -> score > 0)
                    .count();
        }
        return count;
    }

    /**
     * scheduled task which clear rank cache at 00:00 every monday;
     */
    @Scheduled(cron = "0 0 0 ? * 2 ")
    public void clearRankCache() {
        redisTemplate.delete(Arrays.asList(RedisKey.getUserWinAmountKey(), RedisKey.getValidAmountKey()));
    }

    private void deleteByPattern(String pattern) {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            try (Cursor<byte[]> c = connection.scan(ScanOptions.scanOptions().match(pattern).count(100).build())) {
                Set<String> keys = new HashSet<>();
                c.forEachRemaining(bytes -> keys.add(new String(bytes)));
                redisTemplate.delete(keys);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    private void setExpiredTime(String key, long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(key, timeout, timeUnit);
    }
}
