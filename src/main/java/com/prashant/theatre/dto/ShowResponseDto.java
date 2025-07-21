package com.prashant.theatre.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowResponseDto {
	private String showId;
	private String showName;
	private String showLength;
}
