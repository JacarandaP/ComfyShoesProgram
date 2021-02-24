package Models;


import java.util.List;

public class Client {


        private int id;
        private String firstName;
        private String lastName;
        private String e_post;
        private String pwrd;
        private String address;
        private String post_zone;
        private String post_number;
        private List<Order> shoesOrder;

    public Client(int id, String firstName, String lastName, String e_post, String pwrd, String address,
                  String post_zone, String post_number, List<Order> shoesOrder){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.e_post = e_post;
        this.pwrd = pwrd;
        this.address = address;
        this.post_zone = post_zone;
        this.post_number = post_number;
        this.shoesOrder = shoesOrder;

    }

    public Client() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getE_post() {
        return e_post;
    }

    public void setE_post(String e_post) {
        this.e_post = e_post;
    }

    public String getPwrd() {
        return pwrd;
    }

    public void setPwrd(String pwrd) {
        this.pwrd = pwrd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPost_zone() {
        return post_zone;
    }

    public void setPost_zone(String post_zone) {
        this.post_zone = post_zone;
    }

    public String getPost_number() {
        return post_number;
    }

    public void setPost_number(String post_number) {
        this.post_number = post_number;
    }

    public List<Order> getShoesOrder() {
        return shoesOrder;
    }

    public void setShoesOrder(List<Order> shoesOrder) {
        this.shoesOrder = shoesOrder;
    }

    public void printClient(){
        System.out.println(firstName + " " + lastName + " " + " Orders:");
        shoesOrder.forEach(s -> s.printOrderSimple());
    }
    public void printClientOrderNo(){
        System.out.println(firstName + " " + lastName + " " + " Orders:");
        shoesOrder.forEach(Order::printOrder);
    }

}
