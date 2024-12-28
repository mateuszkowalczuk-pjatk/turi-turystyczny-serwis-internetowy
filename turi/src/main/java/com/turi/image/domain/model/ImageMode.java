package com.turi.image.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageMode
{
    ACCOUNT("ACCOUNT"),
    TOURISTICPLACE("TOURISTICPLACE"),
    STAY("STAY"),
    ATTRACTION("ATTRACTION");

    private final String name;
}
