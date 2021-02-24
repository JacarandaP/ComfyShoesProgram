package AddToCart;


import Models.Category;
import Models.Order;
import Models.Shoes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {
    private Properties p = new Properties();


    public Repository() {
        try {
            p.load(new FileInputStream("src/Settings/Properties"));
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public int getClientID(String username, String password) {
        int count = 0;
        ResultSet rs;
        String clientName;
        String clientLastName;
        int clientID = 0;

        String getClient = "SELECT ID, First_name, Last_name from Client WHERE e_post = ? " +
                "and pwrd = ?";

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement isClientstm = con.prepareStatement("select count(*)  as count from Client where e_post = ? and pwrd = ?");
             PreparedStatement getClientstm = con.prepareStatement(getClient);) {

            isClientstm.setString(1, username);
            isClientstm.setString(2, password);
            rs = isClientstm.executeQuery();

            while (rs.next()) {
                count = rs.getInt("count");

                if (count == 1) {
                    getClientstm.setString(1, username);
                    getClientstm.setString(2, password);
                    rs = getClientstm.executeQuery();

                    while (rs.next()) {
                        clientID = rs.getInt("ID");
                        clientName = rs.getString("First_Name");
                        clientLastName = rs.getString("Last_Name");

                        System.out.println("Welcome back " + clientName + " " + clientLastName);
                        return clientID;
                    }
                }

                if (count == 0) {
                    System.out.println("Client not found. Wrong username or password");
                    return clientID = 0;
                } else if (count > 1) {
                    System.out.println("Something went wrong");
                    return clientID = 0;
                }
            }

        } catch (SQLException throwables) {
            System.out.println("Something went wrong. You are being logged out. Please try again in a minute");
            throwables.printStackTrace();

        }
        return clientID;
    }

    public List<Category> showCategories() {

        List<Category> categories = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             Statement showCategorystm = con.createStatement();
             ResultSet rs = showCategorystm.executeQuery("select * from Category");) {

            while (rs.next()) {
                int categoryID = rs.getInt("id");
                String name = rs.getString("description");
                categories.add(new Category(categoryID, name, null));
            }
        } catch (SQLException throwables) {
            System.out.println("Something went wrong. You are being logged out. Please try again in a minute");
            throwables.printStackTrace();

        }
        return categories;
    }

    public int getCategoryID(String chosenCat) {

        int count = 0;
        int catID = 0;
        ResultSet rs;

        String getCatID = "SELECT ID FROM Category WHERE description = ?";

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement isCatstm = con.prepareStatement("select count(*) as count from Category where description = ?");
             PreparedStatement getCatIDstm = con.prepareStatement(getCatID);) {

            isCatstm.setString(1, chosenCat);
            rs = isCatstm.executeQuery();

            while (rs.next()) {
                count = rs.getInt("count");

                if (count == 1) {
                    getCatIDstm.setString(1, chosenCat);
                    rs = getCatIDstm.executeQuery();

                    while (rs.next()) {

                        catID = rs.getInt("ID");

                    }
                }
                if (count == 0) {
                    System.out.println("Category not found");
                    return 0;
                } else if (count > 1) {
                    System.out.println("Something went wrong");
                    return 0;
                }
            }

        } catch (SQLException throwables) {
            System.out.println("Something went wrong. You are being logged out. Please try again in a minute");
            throwables.printStackTrace();

        }
        return catID;
    }

    public List<Shoes> getShoesByCategoryID(int catID) {

        ResultSet rs;
        List<Shoes> shoesByCat = new ArrayList<>();

        String getShoesbyCat = "SELECT * FROM shoes, shoes_categorydetails WHERE shoes_categorydetails.ShoesID = shoes.id " +
                "AND shoes_categorydetails.CategoryID = ?";
        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement getShoesbyCatstm = con.prepareStatement(getShoesbyCat);) {

            getShoesbyCatstm.setInt(1, catID);
            rs = getShoesbyCatstm.executeQuery();

            while (rs.next()) {

                int shoesID = rs.getInt("ID");
                String brand = rs.getString("Brand");
                int size = rs.getInt("Size");
                String color = rs.getString("Color");
                String model = rs.getString("Model");
                double price = rs.getDouble("Price");
                int inStock = rs.getInt("In_Stock");
                shoesByCat.add(new Shoes(shoesID, brand, size, color, model, price, inStock));

            }

            if (shoesByCat.size() == 0) {
                System.out.println("Sorry, we don't have shoes of this category right now");
            }


        } catch (SQLException e) {
            System.out.println("Something went wrong. You are being logged out. Please try again in a minute");
            e.printStackTrace();
            System.exit(0);
        }
        return shoesByCat;
    }

    public List<Shoes> getShoesColor(int catID, String inputBrand, String inputModel, String inputColor) {

        ResultSet rs;
        List<Shoes> shoesbyBrandM = new ArrayList<>();
        String getShoesByBrandandM = "Select * from shoes inner join shoes_categorydetails" +
                " on shoes_categorydetails.shoesID = shoes.ID" +
                " where shoes.brand = ? and shoes.model = ? and shoes.color = ?" +
                " and shoes_categorydetails.CategoryID = ?";

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement getShoesbyBrandAndMsmt = con.prepareStatement(getShoesByBrandandM);) {

            getShoesbyBrandAndMsmt.setString(1, inputBrand);
            getShoesbyBrandAndMsmt.setString(2, inputModel);
            getShoesbyBrandAndMsmt.setString(3, inputColor);
            getShoesbyBrandAndMsmt.setInt(4, catID);

            rs = getShoesbyBrandAndMsmt.executeQuery();

            while (rs.next()) {
                int shoesID = rs.getInt("ID");
                String brand = rs.getString("Brand");
                int size = rs.getInt("Size");
                String color = rs.getString("Color");
                String model = rs.getString("Model");
                double price = rs.getDouble("Price");
                int inStock = rs.getInt("In_Stock");
                shoesbyBrandM.add(new Shoes(shoesID, brand, size, color, model, price, inStock));

            }

            if (shoesbyBrandM.size() == 0) {
                System.out.println("Sorry, we don't have shoes with that description right now");

            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong. You are being logged out. Please try again in a minute");
            System.exit(0);
        }
        return shoesbyBrandM;
    }

    public int getShoesIDbyBMCS(int catID, String inputBrand, String inputModel, String inputColor, int inputSize) {
        ResultSet rs;
        int selectedShoesID = 0;
        Shoes selectedShoes = new Shoes();

        String getShoesIDbyBMCS = "Select * from shoes inner join shoes_categorydetails" +
                " on shoes_categorydetails.shoesID = shoes.ID" +
                " where shoes.brand = ? and shoes.model = ? and shoes.color = ?" +
                " and shoes_categorydetails.CategoryID = ?" +
                " and shoes.size = ?";

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement getShoesIDbyBMCSstm = con.prepareStatement(getShoesIDbyBMCS);) {

            getShoesIDbyBMCSstm.setString(1, inputBrand);
            getShoesIDbyBMCSstm.setString(2, inputModel);
            getShoesIDbyBMCSstm.setString(3, inputColor);
            getShoesIDbyBMCSstm.setInt(4, catID);
            getShoesIDbyBMCSstm.setInt(5, inputSize);

            rs = getShoesIDbyBMCSstm.executeQuery();

            while (rs.next()) {
                int shoesID = rs.getInt("ID");
                String brand = rs.getString("Brand");
                int size = rs.getInt("Size");
                String color = rs.getString("Color");
                String model = rs.getString("Model");
                double price = rs.getDouble("Price");
                int inStock = rs.getInt("In_Stock");
                selectedShoes = new Shoes(shoesID, brand, size, color, model, price, inStock);
                selectedShoesID = selectedShoes.getShoesID();
            }

            if (selectedShoes == null) {
                System.out.println("Sorry, we don't have shoes with that description right now");
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong. You are being logged out. Please try again in a minute");
            System.exit(0);
        }
        return selectedShoesID;
    }

    public int addToCart(Integer order, int clientID, int shoesID) {
        int nyOrderID = 0;
        String sp = "Call AddToCart(?, ? , ?, ?)";

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement cstmt = con.prepareCall(sp);){

            if (order == null) {

                cstmt.setString(1, null);
                cstmt.setInt(2, clientID);
                cstmt.setInt(3, shoesID);
                cstmt.registerOutParameter(4, Types.INTEGER);
                cstmt.execute();
                nyOrderID = cstmt.getInt(4);

            } else {

                cstmt.setInt(1, order);
                cstmt.setInt(2, clientID);
                cstmt.setInt(3, shoesID);
                cstmt.execute();
                System.out.println("Product added to your order");
                return order;

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage() + "(" + e.getErrorCode() + ")");
            return order;


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong. You are being logged out. Please try again in a minute");
            System.exit(0);
        }

        System.out.println("Product added to your order");
        return nyOrderID;
    }

    public List<Order> getOrder(int inputClientID, int inputOrderID) {
        List<Order> clientOrder = new ArrayList<>();
        List<Shoes> shoesInOrder = new ArrayList<>();
        ResultSet rs;
        String getClientOrder = "Select shoes.id, shoes.brand, shoes.model, shoes.color, shoes.size, shoes.price," +
                " shoes.in_stock, order_.date from shoes" +
                " inner join order_details on Shoes.ID = order_details.shoesid inner join order_" +
                " on order_.ID = order_details.OrderID where order_.id = ? and order_.ClientID = ?";

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement getOrderstm = con.prepareStatement(getClientOrder);) {

            getOrderstm.setInt(1, inputOrderID);
            getOrderstm.setInt(2, inputClientID);

            rs = getOrderstm.executeQuery();

            while (rs.next()) {
                int shoesID = rs.getInt("ID");
                String brand = rs.getString("Brand");
                String model = rs.getString("Model");
                String color = rs.getString("Color");
                int size = rs.getInt("Size");
                double price = rs.getDouble("Price");
                int inStock = rs.getInt("In_stock");
                Date orderDate = rs.getDate("Date");
                shoesInOrder.add(new Shoes(shoesID, brand, size, color, model, price, inStock));
                clientOrder.add(new Order(inputOrderID, shoesInOrder, orderDate));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    return clientOrder;
    }

    public String rate(int inputClientID, int inputShoesID, int inputRate, String inputComment){
        String makeReview = "Call Rate(?,?,?,?)";


        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement makeReviewstm = con.prepareCall(makeReview);) {

            makeReviewstm.setInt(1, inputClientID);
            makeReviewstm.setInt(2, inputShoesID);
            makeReviewstm.setInt(3, inputRate);
            makeReviewstm.setString(4, inputComment);
            makeReviewstm.executeQuery();

        } catch (SQLException e) {
            return e.getMessage() + "(" + e.getErrorCode() + ")";

        }
        return "your review has been added";
    }

    public List<String> getReview(int shoesID){
        List<String> reviewList = new ArrayList<>();
        String getReview = "Select comment from review_definition, review where review_definition.id = review.reviewid" +
                " and review.shoesid = ?";
        String getAvg = "{? = Call getReview(?)}";
        double avgRate;
        ResultSet rs;

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement getAveragestm = con.prepareCall(getAvg);
             PreparedStatement getReviewstm = con.prepareStatement(getReview);) {


            getAveragestm.registerOutParameter(1,Types.DOUBLE);
            getAveragestm.setInt(2, shoesID);
            getAveragestm.execute();
            avgRate = getAveragestm.getDouble(1);
            if( avgRate == 0.0){
                System.out.println("No reviews yet");
            }
            else {
                System.out.println("Average rating for this product is " + avgRate);

                getReviewstm.setInt(1, shoesID);
                rs = getReviewstm.executeQuery();

                while (rs.next()) {
                    String review = rs.getString("comment");
                    if (review != null) {
                        reviewList.add(review);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return reviewList;
    }

}