package com.jelisavacluka554.rmt_common.domain;

import java.io.Serializable;

/**
 *
 * @author luka
 */
public class Transport implements Serializable {
    private Long id;
    private String name;

    public Transport(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
}
