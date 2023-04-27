package dev.jeremidigitallab.wimpychatty.dto.request;

import lombok.Data;

@Data
public class AddNewFriendRequest {

	private String targetId;
	private String sourceId;
}
