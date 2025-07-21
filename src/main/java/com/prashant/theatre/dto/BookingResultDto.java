package com.prashant.theatre.dto;

import java.util.List;

public class BookingResultDto {

    private List<BookingResponseDto> bookings;
    private int totalCost;

    public BookingResultDto() {}

    public BookingResultDto(List<BookingResponseDto> bookings, int totalCost) {
        this.bookings = bookings;
        this.totalCost = totalCost;
    }

    public List<BookingResponseDto> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingResponseDto> bookings) {
        this.bookings = bookings;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
}

