/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactsapp;

/**
 *
 * @author aggelos
 */
public class Contact {
    
    private String name;
    private String mobile;
    private String email;

    public Contact() {
    }

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.mobile = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String phone) {
        this.mobile = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name + "\t" + mobile + "\t" + email;
    }
    
       
}
