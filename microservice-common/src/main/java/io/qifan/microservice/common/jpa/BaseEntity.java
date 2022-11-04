package io.qifan.microservice.common.jpa;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * 基础的entity。
 * 有如下作用
 * 1. 每次插入前调用{@link #prePersist()}来初始化创建时间和更新时间并完成数据校验。
 * 2. 每次更新前调用{@link #preUpdate()}来初始化更新时间并完成数据校验
 * 3. id自增
 * 4. version乐观锁，并发控制
 */
@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public abstract class BaseEntity extends AbstractAggregateRoot<BaseEntity> {
    static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Integer version;

    @PrePersist
    public void prePersist() {
        doValidate(this);
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate() {
        doValidate(this);
        this.setUpdatedAt(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseEntity that = (BaseEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public <T> void doValidate(T t) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t, Default.class);
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
