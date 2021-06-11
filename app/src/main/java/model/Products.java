package model;

public class Products
{
    private String pname, Description,Price , Image1, Image2,Image3,Image4,category ,pid, date, time;

    public Products(String product_Name, String description, String price, String image,String image2,String image3,String image4, String category, String pid, String date, String time) {
        pname = product_Name;
        this.Description = description;
        Price = price;
        this.Image1 = image;
        this.Image2 = image2;
        this.Image3 = image3;
        this.Image4 = image4;
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

    public String getImage2() { return Image2; }

    public String getImage3() {
        return Image3;
    }

    public String getImage4() {
        return Image4;
    }


    public void setImage1(String image) {
        this.Image1 = image;
    }

    public void setImage2(String image2) { this.Image2 = image2; }

    public void setImage3(String image3) {
        this.Image3 = image3;
    }

    public void setImage4(String image4) {
        this.Image4 = image4;
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
