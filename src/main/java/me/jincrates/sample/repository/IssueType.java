package me.jincrates.sample.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IssueType {
    DEFAULT("일반"),
    ISSUE("이슈");

    private final String description;
}
