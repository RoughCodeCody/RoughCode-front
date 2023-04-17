package com.cody.roughcode.code.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CodeId implements Serializable {

    @Column(name = "codes_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codesId;

    @Column(name = "version")
    private int version;

    // equals 메서드 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodeId)) return false;
        CodeId that = (CodeId) o;
        return Objects.equals(codesId, that.codesId) &&
                version == that.version;
    }

    // hashCode 메서드 구현
    @Override
    public int hashCode() {
        return Objects.hash(codesId, version);
    }
}
