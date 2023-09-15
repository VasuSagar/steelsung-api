package com.vasu.steelsungapi.games.domain.util;

public class MatchWinnerService {
    public static Integer coinFlipMatchWinnerSide(){
        return Math.random() < 0.5 ? 0 : 1;
    }
}
