package com.puj.stepfitnessapp.guildenterrequest;

import java.util.ArrayList;
import java.util.List;

public class GuildEnterRequestMapper {

    public List<GuildEnterRequestDto> mapToGuildEnterRequestDtoList(List<GuildEnterRequest> guildEnterRequests) {
        ArrayList<GuildEnterRequestDto> list = new ArrayList<>();
        for(GuildEnterRequest request : guildEnterRequests){
            list.add(
                    new GuildEnterRequestDto(
                            request.getGuildEnterRequestId(),
                            request.getPlayer().getUser_id(),
                            request.getPlayer().getUser().getUsername(),
                            request.getPlayer().getLevel()
                    )
            );
        }
        return list;
    }
}
