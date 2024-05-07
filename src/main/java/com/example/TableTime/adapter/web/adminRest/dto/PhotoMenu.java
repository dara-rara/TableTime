package com.example.TableTime.adapter.web.adminRest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class PhotoMenu {
    private String url;
    private String contentType;
}
