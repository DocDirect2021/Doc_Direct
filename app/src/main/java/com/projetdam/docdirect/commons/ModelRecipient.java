package com.projetdam.docdirect.commons;

public class ModelRecipient implements Comparable<ModelRecipient> {
    private String name;
    private String phone;
    private String email;
    private Long contactId;
    private boolean checked;

    public ModelRecipient(String name, String phone, String email, Long contactId) {
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

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public int compareTo(ModelRecipient r) {
        if (this.checked == r.checked) {
            return 0;
        } else if (this.checked) {
            return -1;
        } else {
            return 1;
        }
    }
}
