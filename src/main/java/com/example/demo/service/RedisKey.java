package com.example.demo.service;

import org.apache.commons.lang3.StringUtils;

public final class RedisKey {

    private final static String USER_BET_SLIP_SPACE = "lbet_slip";

    private final static String USER_BET_IN_GAME_SPACE = "setbet_slip_key";

    private final static String USER_BET_PLACE_AMOUNT = "zbet_place_user_amount";

    private final static String GAME_BET_POOL_AMOUNT_KEY = "zbet_pool_amount_sum";

    private final static String GAME_BET_POOL_PLAYER_KEY = "zbet_pool_player_count";

    private final static String USER_BET_AMOUNT_LIMIT_KEY = "zbet_place_amount_limit";

    private final static String USER_BET_PLACE_SUM = "zbet_place_user_sum";

    private final static String USER_WIN_AMOUNT_RANK = "Zuser_win_amount_rank";

    private final static String USER_VALID_AMOUNT_RANK = "Zuser_valid_amount_rank";

    private static String getKey(String... subKeys) {
        return StringUtils.join(subKeys, ":");
    }

    public static String getBetSlipKey(int gameId, int userId) {
        return getKey(USER_BET_SLIP_SPACE, String.valueOf(gameId), String.valueOf(userId));
    }

    public static String getBetInGameKey(int gameId) {
        return getKey(USER_BET_IN_GAME_SPACE, String.valueOf(gameId));
    }

//    public static String getBetPoolAmountKey(GameCategory gameCategory, String tableNumber) {
//        return getKey(GAME_BET_POOL_AMOUNT_KEY, gameCategory.name(), tableNumber);
//    }
//
//    public static String getBetPlacePlayerCountKey(GameCategory gameCategory, String tableNumber, String betPlaceCode) {
//        return getKey(GAME_BET_POOL_PLAYER_KEY, gameCategory.name(), tableNumber, betPlaceCode);
//    }
//
//    public static String getBetPlaceRankKey(GameCategory gameCategory, String tableNumber, String betPlaceCode) {
//        return getKey(USER_BET_PLACE_AMOUNT, gameCategory.name(), tableNumber, betPlaceCode);
//    }
//
//    public static String getUserBetPlaceSumKey(int gameId, int userId) {
//        return getKey(USER_BET_PLACE_SUM, String.valueOf(gameId), String.valueOf(userId));
//    }
//
//    public static String getUserBetPlaceLimitKey(GameCategory gameCategory, int id) {
//        return getKey(USER_BET_AMOUNT_LIMIT_KEY, gameCategory.name(), String.valueOf(id));
//    }

    public static String getUserWinAmountKey() {
        return getKey(USER_WIN_AMOUNT_RANK);
    }

    public static String getValidAmountKey() {
        return getKey(USER_VALID_AMOUNT_RANK);
    }

}