package me.jincrates.sample.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "HANDOVER")
@Comment(value = "인수인계")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class HandoverEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment(value = "PK")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private HandoverType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private IssueType issueType;

    @Column(nullable = false)
    private String requestItem;

    @Column(nullable = false)
    private String requestDetail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private HandoverProcessingType processingType;

    @Column(nullable = false)
    private LocalDate processingRequiredDate;

    @Column(nullable = false)
    private String reconfirmRequest;

    private String inputUrl;

    private String processingUrl;

    private String memo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private HandoverPayStatus payStatus;

    @Column(nullable = false)
    private Long payAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private HandoverStatus status;

    @Column(nullable = false)
    private Integer orderId;

    private Integer userId;
    private Integer workerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
