package com.prashant.theatre.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketSummaryDto {
    private String userName;
    private String userEmail;
    private String showName;
    private String showId;
    private String showLength;
    private List<String> seatIds;
    private int totalPrice;
}

