package me.jincrates.sample.service;

import java.util.List;
import java.util.stream.IntStream;
import me.jincrates.sample.dto.SampleRequest;
import me.jincrates.sample.repository.SampleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SampleServiceIntegrationTest {

    @Autowired
    private SampleService service;

    @Autowired
    private SampleRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @DisplayName("Spring Data JPA 삽입 성능")
    @ParameterizedTest(name = "[{0}건] 성능")
    @ValueSource(ints = {100, 1_000, 5_000, 10_000, 20_000, 30_000, 40_000, 50_000})
    void performanceSpringDataJpaTest(int dataSize) {
        List<SampleRequest> testData = generateTestData(dataSize);

        long executionTime = measureExecutionTime(() -> {
            service.bulkInsertWithSpringDataJpa(testData);
        });

        String result = """
            === Spring Data JPA 벌크 삽입 결과 (%,d건) ===
            Spring Data JPA: %,d ms
            ========================================
            """.formatted(
            dataSize,
            executionTime
        );

        System.out.println(result);
    }

    @DisplayName("Pure JPA 삽입 성능")
    @ParameterizedTest(name = "[{0}건] 성능")
    @ValueSource(ints = {100, 1_000, 5_000, 10_000, 20_000, 30_000, 40_000, 50_000})
    void performancePureJpaTest(int dataSize) {
        List<SampleRequest> testData = generateTestData(dataSize);

        long executionTime = measureExecutionTime(() -> {
            service.bulkInsertWithPureJpa(testData);
        });

        String result = """
            === Pure JPA 벌크 삽입 결과 (%,d건) ===
            Pure JPA: %,d ms
            ========================================
            """.formatted(
            dataSize,
            executionTime
        );

        System.out.println(result);
    }

    @DisplayName("Jdbc 삽입 성능")
    @ParameterizedTest(name = "[{0}건] 성능")
    @ValueSource(ints = {100, 1_000, 5_000, 10_000, 20_000, 30_000, 40_000, 50_000})
    void performanceJdbcTest(int dataSize) {
        List<SampleRequest> testData = generateTestData(dataSize);

        long executionTime = measureExecutionTime(() -> {
            service.bulkInsertWithJdbc(testData);
        });

        String result = """
            === Jdbc 벌크 삽입 결과 (%,d건) ===
            Jdbc: %,d ms
            ========================================
            """.formatted(
            dataSize,
            executionTime
        );

        System.out.println(result);
    }

    @DisplayName("Spring Data JPA vs Pure JPA 벌크 삽입 성능 비교")
    @ParameterizedTest(name = "[{0}건] 성능 비교")
    @ValueSource(ints = {100, 1_000, 5_000, 10_000, 20_000, 30_000, 40_000, 50_000})
    void performanceComparisonTest(int dataSize) {
        List<SampleRequest> testData = generateTestData(dataSize);

        repository.deleteAll();
        long springTime = measureExecutionTime(() -> {
            service.bulkInsertWithSpringDataJpa(testData);
        });

        repository.deleteAll();
        long pureJpaTime = measureExecutionTime(() -> {
            service.bulkInsertWithPureJpa(testData);
        });

        // 결과 출력
        printPerformanceResult(
            dataSize,
            springTime,
            pureJpaTime
        );
    }

    private List<SampleRequest> generateTestData(int dataSize) {
        return IntStream.range(
                0,
                dataSize
            )
            .mapToObj(i -> new SampleRequest("message-" + i))
            .toList();
    }

    private long measureExecutionTime(Runnable operation) {
        long startTime = System.currentTimeMillis();
        operation.run();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private void printPerformanceResult(
        int dataSize,
        long springTime,
        long pureJpaTime
    ) {
        String result = """
            === 벌크 삽입 성능 비교 결과 (%,d건) ===
            Spring Data JPA: %,d ms
            Pure JPA: %,d ms
            시간 차이: %,d ms
            
            결과: %s
            =====================================
            """.formatted(
            dataSize,
            springTime,
            pureJpaTime,
            Math.abs(springTime - pureJpaTime),
            getPerformanceWinner(
                springTime,
                pureJpaTime
            )
        );

        System.out.println(result);
    }

    private String getPerformanceWinner(
        long springTime,
        long pureJpaTime
    ) {
        if (springTime < pureJpaTime) {
            double improvement = (pureJpaTime - springTime) * 100.0 / pureJpaTime;
            return String.format(
                "Spring Data JPA가 %.2f%% 더 빠름",
                improvement
            );
        } else {
            double improvement = (springTime - pureJpaTime) * 100.0 / springTime;
            return String.format(
                "Pure JPA가 %.2f%% 더 빠름",
                improvement
            );
        }
    }
}
