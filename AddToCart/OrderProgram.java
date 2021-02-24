package AddToCart;


import Objects.Category;
import Objects.Order;
import Objects.Shoes;

import java.util.ArrayList;
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
        Scanner sc = new Scanner(System.in);


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

            shoesID = getShoesFinalSelection(firstSelectionShoesList, categoryID);
                if (shoesID == 0){
                    getShoesFinalSelection(firstSelectionShoesList, categoryID);
                }

                nyOrder = r.addToCart(null, clientID, shoesID);
                //if nyOrder = -1, return

                //TODO: this is another method
                System.out.println("Would you like to keep shoping");
                System.out.println("Which category of shoes would you like to check?");
                System.out.println(r.showCategories().stream().map(Category::getDescription).collect(Collectors.toList()));
                Scanner sc2 = new Scanner(System.in);

                inputCategory = sc2.nextLine().trim();
                categoryID = r.getCategoryID(inputCategory); //if(categoryID == 0) return;
                System.out.println("The brands and models available");
                List<Shoes> test = r.getShoesByCategoryID(categoryID);
                test.forEach(o -> System.out.println("Model: " + o.getModel() + " Brand: " + o.getBrand() + " Color: " + o.getColor()));
                System.out.println("Please introduce a brand");
                inputBrand = sc2.nextLine().trim();
                System.out.println("Please introduce a desired model");
                inputModel = sc2.nextLine().trim();
                System.out.println("Please introduce a desired color");
                inputColor = sc2.nextLine().trim();
                List<Shoes> test2 = r.getShoesColor(categoryID, inputBrand, inputModel, inputColor);
                System.out.println("These shoes are available in the following sizes");
                test2.forEach(b -> System.out.println(b.getSize()));
                System.out.println("Please choose a size");
                inputSize = sc2.nextInt();
                shoesID = r.getShoesIDbyBMCS(categoryID, inputBrand, inputModel, inputColor, inputSize).getShoesID();
                r.addToCart(nyOrder, clientID, shoesID);
                List<Order> finalOrder = r.getOrder(clientID, nyOrder);
                finalOrder.forEach(o -> o.printOrderSimple());

            }


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

    public static void main(String[] args) throws InterruptedException {
        OrderProgram oP = new OrderProgram();
    }
}
