package com.projetdam.docdirect.commons;

public class ModelRecipient {
    private String name;
    private String phone;
    private String email;
    private int contactId;

    public ModelRecipient(String name, String phone, String email, int contactId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.contactId = contactId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

}
