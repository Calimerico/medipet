package com.medipet.policy.domain;

import com.medipet.util.ddd.UUIDAggregate;

import javax.persistence.Entity;

@Entity
public class Policy extends UUIDAggregate {
    private String name;
}
