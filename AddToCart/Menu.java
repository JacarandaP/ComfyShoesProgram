package AddToCart;


import Models.Order;
import Models.Shoes;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {

    Repository r = new Repository();
    Control c = new Control();


    int clientID;
    int categoryID;
    int shoesID;
    int nyOrder;
    Integer order;

    public void initialMenu(){
        clientID = c.getClient();

        if (clientID == 0) {
            initialMenu();
        } else menu();
    }


    public void showOptionsAfterShop(int clientID, int shoesID, int orderID){
        int temp = -1;
        while (temp != 0) {
            System.out.println("Would you like to do now?");
            System.out.println("1: Review this product");
            System.out.println("2: Read reviews for this product");
            System.out.println("3: Keep shopping");
            System.out.println("4: See your order");
            System.out.println("5: Log out");
            temp = getInfoFromUser();
            switch (temp){
                case 1:
                    System.out.println("Rate this product from 1 to 4 \n1: Bad \n2: Okej \n3:Good \n4:Very Good");
                    Scanner sc = new Scanner(System.in);
                    int inputRate = getInfoFromUser();
                    System.out.println("Write your review (max 350 letters)");
                    String review =sc.nextLine();
                    System.out.println(r.rate(clientID, shoesID, inputRate, review)+"");
                    break;

                case 2:
                   System.out.println(r.getReview(shoesID));

                    break;

                case 3:
                    menu();
                    break;

                case 4:
                    List<Order> finalOrder = r.getOrder(clientID, orderID);
                    List <String> test = finalOrder.stream().map(Order::printOrder).collect(Collectors.toList());
                    System.out.println(test);
                    break;
                case 5:
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


    public void menu() {
        categoryID = c.getCategory();
            if (categoryID == 0) {
            menu();
            }

        List<Shoes> firstSelectionShoesList = c.getShoesListByCategory(categoryID);
            if (firstSelectionShoesList.size() == 0) {
            menu();
            }

        List<Shoes> secondSelectionShoesList = c.getShoesListByBMC(firstSelectionShoesList, categoryID);
            if (secondSelectionShoesList.size() == 0) {
            menu();
            }

        shoesID = c.getShoesFinalSelection(secondSelectionShoesList, categoryID);
            if (shoesID == 0) {
            menu();
            }

            if (order == null) {
            order = r.addToCart(null, clientID, shoesID);
            showOptionsAfterShop(clientID, shoesID, order);

            } else {
            nyOrder = r.addToCart(order, clientID, shoesID);
            showOptionsAfterShop(clientID, shoesID, nyOrder);
        }
    }

}


