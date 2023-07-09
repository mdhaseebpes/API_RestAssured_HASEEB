package serializeDeserialize.pojoAll;

import java.util.List;

public class MainEmployee {

    private String name;
    private int age;
    private String email;

    private Address address;

    private List<PhoneNumbers> phoneNumbers;

    private boolean active;

    public MainEmployee(String name, int age, String email, Address address, List<PhoneNumbers> phoneNumbers, boolean active) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
        this.phoneNumbers = phoneNumbers;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<PhoneNumbers> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumbers> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
