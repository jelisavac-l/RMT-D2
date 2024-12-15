package com.jelisavacluka554.rmt_common.domain;

/**
 *
 * @author luka
 */
public class EUCountry {
    private Long id;
    private String name;

    public EUCountry(Long id, String name) {
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
        return "EUCountry{" + "name=" + name + '}';
    }
    
    
}