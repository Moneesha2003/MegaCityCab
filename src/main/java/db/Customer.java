/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

/**
 *
 * @author monee
 */
public class Customer {
    private String name;
    private String email;
    private String contact;
    private String password;

    public Customer() {
        this.name = "";
        this.email = "";
        this.contact = "";
        this.password = "";
    }
    
    public Customer(String name, String email, String contact, String password) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
