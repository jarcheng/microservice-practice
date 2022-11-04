package io.qifan.microservice.common.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 基础repository，其他实体类的repository只需继承这个类就拥有增删改查，动态sql的功能。
 * @param <T>
 */
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T>, QuerydslPredicateExecutor<T> {
}