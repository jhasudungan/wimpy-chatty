package dev.jeremidigitallab.wimpychatty.dto;

import lombok.Data;

@Data
public class ResponseWithData<T> {

	PageInformation pageInformation;
	T data;
}
