package com.example.TableTime.adapter.web.adminRest.dto.reserval;

public record ReservalRestFreeTable(String name,
                                    String phone,
                                    String date,
                                    String timeStart,
                                    String timeEnd,
                                    Integer persons,
                                    String message) {
}
