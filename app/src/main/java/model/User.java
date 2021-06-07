package model;

public class User
{

private String UserName,Number, Password,image,address;

    public User()
    {

    }

    public User(String userName, String number, String password, String image, String address) {
        UserName = userName;
        Number = number;
        Password = password;
        this.image = image;
        this.address = address;
    }


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
