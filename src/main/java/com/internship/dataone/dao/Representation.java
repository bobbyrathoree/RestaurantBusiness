package com.internship.dataone.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class Representation<T> {
    private long code;

    @Length(max = 3)
    private T data;

    public Representation() {
        // Jackson deserialization
    }

    public Representation(long code, T data) {
        this.code = code;
        this.data = data;
    }

    @JsonProperty
    public long getCode() {
        return code;
    }

    @JsonProperty
    public T getData() {
        return data;
    }
}
