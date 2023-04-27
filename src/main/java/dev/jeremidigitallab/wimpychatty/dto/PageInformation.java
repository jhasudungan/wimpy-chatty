package dev.jeremidigitallab.wimpychatty.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageInformation {

	public Integer nextPage;
	public Integer previousPage;
	public Integer currentPage;
	public Integer totalPage;
	
}