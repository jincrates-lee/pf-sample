package me.jincrates.sample.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HandoverStatus {
    IN_PROGRESS("진행중"),
    COMPLETED("완료"),
    CANCELLED("취소");

    private final String description;
}
