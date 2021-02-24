package AddToCart;


import Models.Category;
import Models.Shoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Control {

    Repository r = new Repository();

    int clientID;
    int categoryID;
    int shoesID;
    int nyOrder;
    int order;

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

}
