package com.jelisavacluka554.rmt_common.domain;

import java.io.Serializable;

/**
 *
 * @author luka
 */
public class ApplicationItem implements Serializable {
    private Application application;
    private Long id;
    private EUCountry country;

    public ApplicationItem(Application application, Long id, EUCountry country) {
        this.application = application;
        this.id = id;
        this.country = country;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EUCountry getCountry() {
        return country;
    }

    public void setCountry(EUCountry country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return country.getName().substring(0, 3).toUpperCase();
    }
    
    
    
}
