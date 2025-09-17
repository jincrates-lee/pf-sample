package me.jincrates.sample.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HandoverPayStatus {
    NOT_APPLICABLE("해당없음"),
    WAITING("입금대기"),
    COMPLETED("입금완료"),
    ;
    private final String description;
}
