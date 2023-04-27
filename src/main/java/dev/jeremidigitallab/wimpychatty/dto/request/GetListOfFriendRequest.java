package dev.jeremidigitallab.wimpychatty.dto.request;

import lombok.Data;

@Data
public class GetListOfFriendRequest {

    private String accountId;
    private int page;

}
