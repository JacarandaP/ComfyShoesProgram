package Models;


public class Shoes {
    private int shoesID;
    private String brand;
    private int size;
    private String color;
    private String model;
    private double price;
    private int stock;


    public Shoes(int shoesID, String brand, int size, String color, String model, double price, int stock) {
        this.shoesID = shoesID;
        this.brand = brand;
        this.size = size;
        this.color = color;
        this.model = model;
        this.price = price;
        this.stock = stock;

    }

    public Shoes() {

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

    public int getStock(int stock){
        return stock;
    }

    public void setStock(int stock){
        this.stock = stock;
    }

    public void printShoes(){
        System.out.println("model: " + this.model + " brand: " + this.brand + " color: " + this.color +
                " size: " + this.size + " price: " + this.price);
    }

    public String getShoesModelAndBrand(){
        return "model: " + this.model + " brand: " + this.brand;
    }
    public String printShoes2(){
        return "model: " + this.model + " brand: " + this.brand + " color: " + this.color +
                " size: " + this.size + " price: " + this.price;
    }

}
