package Models;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class Order {
    private int id;
    private List<Shoes> shoesList;
    private Date date;

    public Order(int id, List<Shoes> shoesList, Date date){
        this.id = id;
        this.shoesList = shoesList;
        this.date = date;
    }

    public Order() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Shoes> getShoesList() {
        return shoesList;
    }

    public void setShoesList(List<Shoes> shoesList) {
        this.shoesList = shoesList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



    public void printOrderSimple(){
        System.out.println("order id: "+ id + " Date for order: " + date + "\nShoes in order: \n" +
                shoesList.stream().map(Shoes::printShoes2).collect(Collectors.toList()) + "\n");

    }

    public String printOrder(){
        return "order id: "+ id + " Date for order: " + date + "\nShoes in order: \n" +
                shoesList.stream().map(Shoes::printShoes2).collect(Collectors.toList()) + "\n";
    }

}
