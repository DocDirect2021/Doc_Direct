package com.projetdam.docdirect.commons;

public class ModelRecipient {
    private String name;
    private String phone;
    private String email;
    private int contactId;
    private boolean checked;

    public ModelRecipient(String name, String phone, String email, int contactId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.contactId = contactId;
        this.checked = checked;
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

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
