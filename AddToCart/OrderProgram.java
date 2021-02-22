package AddToCart;


import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OrderProgram {

    public OrderProgram() throws InterruptedException {

        Repository r = new Repository();
        int clientID;
        int categoryID;
        String inputUserName;
        String inputPassword;
        String inputCategory;
        String inputBrand;
        String inputModel;
        String inputColor;
        int inputSize;
        int shoesID;
        int nyOder;
        Scanner sc = new Scanner(System.in);


            while (true) {
                System.out.println("Welcome, please write your username");
                inputUserName = sc.nextLine().trim();
                System.out.println("And now your password");
                inputPassword = sc.nextLine().trim();
                clientID = r.getClientID(inputUserName, inputPassword);
                    if (clientID == 0) {
                    return;//avbryta om client not found. client = 0
                    }
                   //TODO: make a method of this.
                System.out.println("Which category of shoes would you like to check?");
                System.out.println(r.showCategories().stream().map(Category::getDescription).collect(Collectors.toList()));
                inputCategory = sc.nextLine().trim();
                categoryID = r.getCategoryID(inputCategory); //if(categoryID == 0) return;
                System.out.println("The brands and models available");
                List <Shoes> s = r.getShoesByCategoryID(categoryID).stream().distinct().collect(Collectors.toList());
                s.forEach(o -> System.out.println("Model: " + o.getModel() + " Brand: " + o.getBrand() + " Color: " + o.getColor()));
                System.out.println("Please introduce a brand");
                inputBrand = sc.nextLine().trim();
                System.out.println("Please introduce a desired model");
                inputModel = sc.nextLine().trim();
                System.out.println("Please introduce a desired color");
                inputColor = sc.nextLine().trim();
                List<Shoes> s2 = r.getShoesColor(categoryID, inputBrand, inputModel, inputColor).stream().collect(Collectors.toList());
                System.out.println("These shoes are available in the following sizes");
                s2.forEach(b-> System.out.println(b.getSize()));
                System.out.println("Please choose a size");
                inputSize = sc.nextInt();
                shoesID = r.getShoesIDbyBMCS(categoryID, inputBrand, inputModel, inputColor, inputSize).getShoesID();
                nyOder = r.addToCart(null,clientID, shoesID);
                //System.out.println(nyOder + "");

                //TODO: this is another method
                System.out.println("Would you like to keep shoping");
                System.out.println("Which category of shoes would you like to check?");
                System.out.println(r.showCategories().stream().map(Category::getDescription).collect(Collectors.toList()));
                Scanner sc2 = new Scanner(System.in);

                inputCategory = sc2.nextLine().trim();
                categoryID = r.getCategoryID(inputCategory); //if(categoryID == 0) return;
                System.out.println("The brands and models available");
                List <Shoes> test = r.getShoesByCategoryID(categoryID).stream().distinct().collect(Collectors.toList());
                s.forEach(o -> System.out.println("Model: " + o.getModel() + " Brand: " + o.getBrand() + " Color: " + o.getColor()));
                System.out.println("Please introduce a brand");
                inputBrand = sc2.nextLine().trim();
                System.out.println("Please introduce a desired model");
                inputModel = sc2.nextLine().trim();
                System.out.println("Please introduce a desired color");
                inputColor = sc2.nextLine().trim();
                List<Shoes> test2 = r.getShoesColor(categoryID, inputBrand, inputModel, inputColor).stream().collect(Collectors.toList());
                System.out.println("These shoes are available in the following sizes");
                s2.forEach(b-> System.out.println(b.getSize()));
                System.out.println("Please choose a size");
                inputSize = sc2.nextInt();
                shoesID = r.getShoesIDbyBMCS(categoryID, inputBrand, inputModel, inputColor, inputSize).getShoesID();
                r.addToCart(nyOder,clientID, shoesID);


            }


        }


    public static void main(String[] args) throws InterruptedException {
        OrderProgram oP = new OrderProgram();
    }
}
