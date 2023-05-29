package com.puj.stepfitnessapp.playersrating;

import java.util.ArrayList;
import java.util.List;

public class PlayersRatingMapper {

    public List<PlayersRatingDto> mapToPlayersRatingDtoList(
            List<PlayersRating> playersRatingList,
            PlayersRating playerRating
    ) {
        var playersRatingDtoList = new ArrayList<PlayersRatingDto>();
        int place = 1;
        for(PlayersRating rating : playersRatingList){
            playersRatingDtoList.add(mapToPlayersRatingDto(rating, place));
            place++;
        }
        if(playerRating == null) return playersRatingDtoList;
        var isPlayerPresent = playersRatingList.stream().anyMatch(
                pr -> pr.getRatingId().equals(playerRating.getRatingId())
        );
        if(isPlayerPresent) return playersRatingDtoList;
        playersRatingDtoList.add(mapToPlayersRatingDto(playerRating, null));
        return playersRatingDtoList;
    }

    public PlayersRatingDto mapToPlayersRatingDto(PlayersRating rating, Integer place) {
        return new PlayersRatingDto(
                rating.getPlayer().getUser().getUsername(),
                rating.getPlayer().getLevel(),
                rating.getPlayer().getImageId(),
                place,
                rating.getAmountOfSteps(),
                rating.getAmountOfDuelsWon()
        );
    }
}
