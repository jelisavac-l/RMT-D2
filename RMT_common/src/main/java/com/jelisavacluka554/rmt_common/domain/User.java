package com.jelisavacluka554.rmt_common.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author luka
 */
public class User implements Serializable {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String jmbg;
    private String passport;
    private String username;
    private String password;
    private Date birthday;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public User(Long id, String firstName, String lastName, String jmbg, String passport, String username, String password, Date birtday) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jmbg = jmbg;
        this.passport = passport;
        this.username = username;
        this.password = password;
        this.birthday = birtday;
    }

    public User(Long id, String firstName, String lastName, String jmbg, String passport, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jmbg = jmbg;
        this.passport = passport;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getAge() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthday);
        return LocalDate.now().getYear() - calendar.get(Calendar.YEAR);
        
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + passport + ")";
    }
    
    
    
}
