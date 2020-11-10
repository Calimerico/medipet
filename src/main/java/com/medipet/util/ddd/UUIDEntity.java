package com.medipet.util.ddd;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class UUIDEntity implements Serializable {
    @Id
    private final UUID id = UUID.randomUUID();
    @Version
    private Long version;

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UUIDEntity that = (UUIDEntity) o;
        return id.equals(that.id);
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}