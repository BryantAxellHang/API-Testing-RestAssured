package model;

public class RegisterRequest {
    public String email;
    public String full_name;
    public String password;
    public String department;
    public String phone_number;

    public RegisterRequest() {}

    public RegisterRequest(String email, String full_name, String password, String department, String phone_number) {
        this.email = email;
        this.full_name = full_name;
        this.password = password;
        this.department = department;
        this.phone_number = phone_number;
    }
}

