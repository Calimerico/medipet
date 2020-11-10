package com.medipet.util.ddd;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UUIDAggregateRepository<T extends UUIDAggregate> extends CrudRepository<T, UUID> {
}
