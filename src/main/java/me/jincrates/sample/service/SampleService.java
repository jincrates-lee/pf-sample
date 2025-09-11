package me.jincrates.sample.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.jincrates.sample.dto.SampleRequest;
import me.jincrates.sample.dto.SampleResponse;
import me.jincrates.sample.global.aop.LogExecutionTime;
import me.jincrates.sample.repository.SampleEntity;
import me.jincrates.sample.repository.SampleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class SampleService implements SampleUseCase {

    private final SampleRepository repository;

    @LogExecutionTime
    @Transactional
    public List<SampleResponse> saveAll(final List<SampleRequest> request) {
        List<SampleEntity> entities = request.stream()
            .map(this::toEntity)
            .toList();

        List<SampleEntity> saved = repository.saveAll(entities);

        return saved.stream()
            .map(this::toResponse)
            .toList();
    }

    private SampleEntity toEntity(final SampleRequest sample) {
        return SampleEntity.builder()
            .message(sample.message())
            .build();
    }

    private SampleResponse toResponse(final SampleEntity sample) {
        return SampleResponse.builder()
            .id(sample.getId())
            .message(sample.getMessage())
            .build();
    }
}
