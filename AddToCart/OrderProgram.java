package AddToCart;


import Models.Category;
import Models.Order;
import Models.Shoes;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OrderProgram {

    Menu m = new Menu();

    public OrderProgram() throws InterruptedException {

        while (true) {
            m.initialMenu();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        OrderProgram oP = new OrderProgram();
    }

}
