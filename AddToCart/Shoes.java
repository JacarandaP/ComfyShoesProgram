package AddToCart;



public class Shoes {
    private int shoesID;
    private String brand;
    private int size;
    private String color;
    private String model;
    private double price;

    public Shoes(int shoesID, String brand, int size, String color, String model, double price) {
        this.shoesID = shoesID;
        this.brand = brand;
        this.size = size;
        this.color = color;
        this.model = model;
        this.price = price;

    }

    public int getShoesID() {
        return shoesID;
    }

    public void setShoesID(int shoesID) {
        this.shoesID = shoesID;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getModelAndBrand(){
        return "model: " + model + " brand: " + brand;
    }
    public String getModelBrandColor(){
        return "model: " + this.model + " brand: " + this.brand + " color: " + this.color;
    }

    public String getModelBrandColorSize(){
        return "model: " + this.model + " brand: " + this.brand + " color: " + this.color + " size: " + this.size;
    }



}
