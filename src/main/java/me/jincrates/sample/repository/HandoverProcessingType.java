package me.jincrates.sample.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HandoverProcessingType {
    DAILY("데일리"),
    WEEKLY("주간"),
    NIGHT("야간"),
    WEEKLY_NIGHT("주/야간"),
    DUTY("당직");

    private final String description;
}
