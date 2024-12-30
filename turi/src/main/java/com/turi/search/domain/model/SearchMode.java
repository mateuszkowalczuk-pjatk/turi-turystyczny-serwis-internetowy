package com.turi.search.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchMode
{
    ALL("ALL"),
    STAY("STAY"),
    ATTRACTION("ATTRACTION");

    private final String name;
}
