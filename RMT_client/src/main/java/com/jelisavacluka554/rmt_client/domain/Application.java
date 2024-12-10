package com.jelisavacluka554.rmt_server.domain;

import java.util.Date;
import java.util.List;

/**
 *
 * @author luka
 */
public class Application {
    private Long id;
    private User user;
    private Transport transport;
    private Date dateOfApplication;
    private Date dateOfEntry;
    private Integer duration;
    
    private List<ApplicationItem> items;
    
    
    /**
     * Constructor <b>with</b> item list parameter.
     */
    public Application(Long id, User user, Transport transport, Date dateOfApplication, Date dateOfEntry, Integer duration, List<ApplicationItem> items) {
        this.id = id;
        this.user = user;
        this.transport = transport;
        this.dateOfApplication = dateOfApplication;
        this.dateOfEntry = dateOfEntry;
        this.duration = duration;
        this.items = items;
    }

    /**
     * Constructor <b>without</b> item list parameter.
     */
    public Application(Long id, User user, Transport transport, Date dateOfApplication, Date dateOfEntry, Integer duration) {
        this.id = id;
        this.user = user;
        this.transport = transport;
        this.dateOfApplication = dateOfApplication;
        this.dateOfEntry = dateOfEntry;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public Date getDateOfApplication() {
        return dateOfApplication;
    }

    public void setDateOfApplication(Date dateOfApplication) {
        this.dateOfApplication = dateOfApplication;
    }

    public Date getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(Date dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<ApplicationItem> getItems() {
        return items;
    }

    public void setItems(List<ApplicationItem> items) {
        this.items = items;
    }
    
    
    
}
