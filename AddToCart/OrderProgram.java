package AddToCart;


import Objects.Category;
import Objects.Order;
import Objects.Shoes;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OrderProgram {

    Repository r = new Repository();

    public OrderProgram() throws InterruptedException {

        Repository r = new Repository();
        int clientID;
        int categoryID;

        int shoesID;
        int nyOrder;



        while (true) {

            clientID = getClient();

                if (clientID == 0) {
                    getClient();
                }

            categoryID = getCategory();

                if(categoryID == 0) {
                    getCategory();
                }

            List<Shoes> firstSelectionShoesList = getShoesListByCategory(categoryID);

                if(firstSelectionShoesList.size() == 0){
                    getShoesListByCategory(categoryID);
                }

            List<Shoes> secondSelectionShoesList = getShoesListByBMC(firstSelectionShoesList, categoryID);
            if(secondSelectionShoesList.size() == 0){
                getShoesListByBMC(firstSelectionShoesList, categoryID);
            }

            shoesID = getShoesFinalSelection(secondSelectionShoesList, categoryID);
                if (shoesID == 0){
                    getShoesFinalSelection(secondSelectionShoesList, categoryID);
                }

                nyOrder = r.addToCart(null, clientID, shoesID);
               if (nyOrder == 0){
                   getCategory();
               }

               showOptionsAfterShop(clientID, shoesID, nyOrder);


/*




            }
*/}

        }

    public int getClient() {
        String inputUserName;
        String inputPassword;
        int clientID;
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome, please write your username");
        inputUserName = sc.nextLine().trim();
        System.out.println("And now your password");
        inputPassword = sc.nextLine().trim();
        clientID = r.getClientID(inputUserName, inputPassword);

        return clientID;

    }

    public int getCategory() {
        String inputCategory;
        int categoryID;
        Scanner sc = new Scanner(System.in);
        System.out.println("Which category of shoes would you like to check?");
        List<Category> categoryList = r.showCategories();
        System.out.println(r.showCategories().stream().map(Category::getDescription).collect(Collectors.toList()));
        inputCategory = sc.nextLine().trim();
        categoryID = r.getCategoryID(inputCategory);

        return categoryID;
    }

    public List<Shoes> getShoesListByCategory(int categoryID) {

        System.out.println("The brands and models available");
        List<Shoes> shoesbyCat = r.getShoesByCategoryID(categoryID);

        return shoesbyCat;
    }

    public List<Shoes> getShoesListByBMC(List<Shoes> shoesListCategoy, int categoryID){
        List<Shoes> shoesList = new ArrayList<>();
        String inputBrand;
        String inputModel;
        String inputColor;
        Scanner sc = new Scanner(System.in);

        shoesListCategoy.forEach(o -> System.out.println("Model: " + o.getModel() + " Brand: " + o.getBrand() + " Color: " + o.getColor()));
        System.out.println("Please introduce a brand");
        inputBrand = sc.nextLine().trim();
        System.out.println("Please introduce a desired model");
        inputModel = sc.nextLine().trim();
        System.out.println("Please introduce a desired color");
        inputColor = sc.nextLine().trim();
        shoesList = r.getShoesColor(categoryID, inputBrand, inputModel, inputColor);


        return shoesList;

    }

    public int getShoesFinalSelection(List<Shoes> firstList, int categoryID){
        int inputSize;
        int shoesID;
        Scanner sc = new Scanner(System.in);

        String brand =firstList.stream().map(Shoes::getBrand).collect(Collectors.toList()).get(0);
        String model = firstList.stream().map(Shoes::getModel).collect(Collectors.toList()).get(0);
        String color = firstList.stream().map(Shoes::getColor).collect(Collectors.toList()).get(0);

        System.out.println("These shoes are available in the following sizes");
        firstList.forEach(b -> System.out.println(b.getSize()));
        System.out.println("Please choose a size");
        inputSize = sc.nextInt();
        shoesID = r.getShoesIDbyBMCS(categoryID, brand, model, color, inputSize);

        return shoesID;

    }

    public void showFirstMenu(){

    }

    public void showOptionsAfterShop(int clientID, int shoesID, int orderID){
        int temp = -1;
        while (temp != 0) {
            System.out.println("Would you like to do now?");
            System.out.println("1: Review this product");
            System.out.println("2: Keep shopping");
            System.out.println("3: See your order");
            System.out.println("4: Log out");
            temp = getInfoFromUser();
            switch (temp){
                case 1:
                    System.out.println("Rate this product from 1 to 4 \t1: Bad \t2: Okej \t3:Good \t 4:Very Good");
                    Scanner sc = new Scanner(System.in);
                    int inputRate = getInfoFromUser();
                    System.out.println("Write your review (max 350 letters)");
                    String review =sc.nextLine();
                    System.out.println(r.rate(clientID, shoesID, inputRate, review)+"");
                    break;

                case 2:
                    return;

                case 3:
                    List<Order> finalOrder = r.getOrder(clientID, orderID);
                    finalOrder.forEach(o -> o.printOrder());
                    break;
                case 4:
                    System.out.println("Thank you! You are being logged out");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    public int getInfoFromUser(){
        int input = -2;
        while (input == -2) {
            try {
                Scanner s = new Scanner(System.in);
                input = s.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Just numbers allowed. Please try again \n");
            }
        }
        return input;
    }


    public static void main(String[] args) throws InterruptedException {
        OrderProgram oP = new OrderProgram();
    }
}
