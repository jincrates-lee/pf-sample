package me.jincrates.sample.dto;

import lombok.Builder;

@Builder
public record SampleResponse(
    Long id,
    String message
) {

}
