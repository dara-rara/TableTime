package com.example.TableTime.adapter.web.adminRest.dto.reserval;

import java.util.List;

public record ReservalRestRequest(String name,
                                  String phone,
                                  String date,
                                  String timeStart,
                                  String timeEnd,
                                  Integer persons,
                                  String message,
                                  List<Integer> tables) {
}