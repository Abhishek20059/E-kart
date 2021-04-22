package model;

public class Products
{
    private String pname, Description,Price , Image1, category ,pid, date, time;

    public Products(String product_Name, String description, String price, String image, String category, String pid, String date, String time) {
        pname = product_Name;
        this.Description = description;
        Price = price;
        this.Image1 = image;
        this.category = category;
        this.pid = pid;
        this.date = date;
        this.time = time;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String product_Name) {
        pname = product_Name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage1() {
        return Image1;
    }

    public void setImage1(String image) {
        this.Image1 = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Products()
    {

    }

}
