package me.jincrates.sample.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.jincrates.sample.dto.SampleRequest;
import me.jincrates.sample.repository.SampleEntity;
import me.jincrates.sample.repository.SampleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleRepository repository;
    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void bulkInsertWithSpringDataJpa(final List<SampleRequest> request) {
        List<SampleEntity> entities = request.stream()
            .map(this::toEntity)
            .toList();

        repository.saveAll(entities);
    }

    @Transactional
    public void bulkInsertWithPureJpa(final List<SampleRequest> request) {
        List<SampleEntity> entities = request.stream()
            .map(this::toEntity)
            .toList();

        for (SampleEntity entity : entities) {
            entityManager.persist(entity);
        }

        entityManager.flush();
    }

    @Transactional
    public void bulkInsertWithJdbc(List<SampleRequest> request) {
        List<SampleEntity> entities = request.stream()
            .map(this::toEntity)
            .toList();

        String sql = "INSERT INTO sample (message) VALUES (?)";
        jdbcTemplate.batchUpdate(
            sql,
            entities,
            entities.size(),
            (ps, item) -> ps.setString(
                1,
                item.getMessage()
            )
        );
    }

    private SampleEntity toEntity(final SampleRequest sample) {
        return SampleEntity.builder()
            .message(sample.message())
            .build();
    }
}
