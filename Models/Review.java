package Models;


import java.sql.Date;

public class Review {

    private int id;
    private Client client;
    private Shoes shoes;
    private Date reviewDate;
    private int reviewID;
    private String reviewText;


    public Review(int id, Client client, Shoes shoes, Date reviewDate, int reviewID, String reviewText){
        this.id = id;
        this.client = client;
        this.shoes = shoes;
        this.reviewDate= reviewDate;
        this.reviewID = reviewID;
        this.reviewText = reviewText;

    }

    public Review() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClientID(Client client) {
        this.client = client;
    }

    public Shoes getShoes() {
        return shoes;
    }

    public void setShoes(Shoes shoes) {
        this.shoes = shoes;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void printReviewSimple(){
        System.out.println("Product: " + shoes.getBrand() +" " + shoes.getModel() + " Rate:  " + reviewID + " Review: " + reviewText);
    }

    public void printReview(){
        System.out.println("Rate:  " + reviewID + " Review: " + reviewText);
    }




}
