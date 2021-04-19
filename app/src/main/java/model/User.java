package model;

public class User
{

private String UserName,Number, Password;

    public User()
    {

    }

    public User(String userName, String number, String password) {
        this.UserName = userName;
        this.Number = number;
        this.Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getNumber() { return Number; }

    public void setNumber(String number) {
        Number = number;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
