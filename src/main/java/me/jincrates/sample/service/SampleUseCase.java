package me.jincrates.sample.service;

import java.util.List;
import me.jincrates.sample.dto.SampleRequest;
import me.jincrates.sample.dto.SampleResponse;

public interface SampleUseCase {

    List<SampleResponse> saveAll(List<SampleRequest> request);
}
