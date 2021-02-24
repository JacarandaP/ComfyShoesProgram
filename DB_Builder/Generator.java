package DB_Builder;


import AddToCart.Repository;
import Models.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Generator {

    private Properties p = new Properties();
    Repository repo = new Repository();

    public Generator() {
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

    public Shoes getShoesByID(int shoesID) {
        ResultSet rs;
        Shoes selectedShoes = new Shoes();

        String getShoesbyID = "Select * from shoes where shoes.id = ?";

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement getShoesbyIDstm = con.prepareStatement(getShoesbyID);) {

            getShoesbyIDstm.setInt(1, shoesID);


            rs = getShoesbyIDstm.executeQuery();

            while (rs.next()) {
                int shoesId = rs.getInt("ID");
                String brand = rs.getString("Brand");
                int size = rs.getInt("Size");
                String color = rs.getString("Color");
                String model = rs.getString("Model");
                double price = rs.getDouble("Price");
                int inStock = rs.getInt("In_Stock");
                selectedShoes = new Shoes(shoesId, brand, size, color, model, price, inStock);
            }


        } catch (SQLException e) {
            e.printStackTrace();

        }
        return selectedShoes;
    }

    public List<Shoes> getAllShoes() {
        List<Shoes> shoesList = new ArrayList<>();
        List<Integer> allshoesid = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));

             Statement smt = con.createStatement();
             ResultSet rs = smt.executeQuery("Select id from shoes")) {

            while (rs.next()) {
                allshoesid.add(rs.getInt("id"));
            }

            shoesList = allshoesid.stream().map(s -> getShoesByID(s)).collect(Collectors.toList());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return shoesList;
    }

    public Category getCategorybyID(int catID) {
        Category category = new Category();
        String getCatbyID = "SELECT * FROM Category WHERE id = ?";
        ResultSet rs;

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));

             PreparedStatement getCatByIDstm = con.prepareStatement(getCatbyID);) {

            getCatByIDstm.setInt(1, catID);
            rs = getCatByIDstm.executeQuery();

            while (rs.next()) {

                int categoryID = rs.getInt("ID");
                String description = rs.getString("Description");
                category = new Category(categoryID, description, repo.getShoesByCategoryID(categoryID));

            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return category;
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        List<Integer> allCategoriesID = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             Statement smt = con.createStatement();
             ResultSet rs = smt.executeQuery("Select id from category")) {

            while (rs.next()) {
                allCategoriesID.add(rs.getInt("id"));
            }

            categoryList = allCategoriesID.stream().map(c -> getCategorybyID(c)).collect(Collectors.toList());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return categoryList;
    }

    public Order getOrderbyID(int inputOrderID) {

        List<Shoes> shoesInOrder = new ArrayList<>();
        Order order = new Order();
        ResultSet rs;
        String getOrderByID = "Select order_.id, shoes.id, shoes.brand, shoes.model, shoes.color, shoes.size, shoes.price," +
                " shoes.in_stock, order_.date from shoes" +
                " inner join order_details on Shoes.ID = order_details.shoesid inner join order_" +
                " on order_.ID = order_details.OrderID where order_.id = ?";

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement getOrderByIDstm = con.prepareStatement(getOrderByID);) {

            getOrderByIDstm.setInt(1, inputOrderID);

            rs = getOrderByIDstm.executeQuery();

            while (rs.next()) {
                int orderID = rs.getInt("ID");
                int shoesID = rs.getInt("ID");
                String brand = rs.getString("Brand");
                int size = rs.getInt("Size");
                String color = rs.getString("Color");
                String model = rs.getString("Model");
                double price = rs.getDouble("Price");
                Date orderDate = rs.getDate("Date");
                int inStock = rs.getInt("In_stock");
                shoesInOrder.add(new Shoes(shoesID, brand, size, color, model, price, inStock));
                order = new Order(orderID, shoesInOrder, orderDate);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return order;
    }

    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        List<Integer> allOrdersID = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             Statement smt = con.createStatement();
             ResultSet rs = smt.executeQuery("Select id from order_")) {

            while (rs.next()) {
                allOrdersID.add(rs.getInt("id"));
            }

            orderList = allOrdersID.stream().map(or -> getOrderbyID(or)).collect(Collectors.toList());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return orderList;
    }

    public List<Order> getOrdersGivenClient(int clientID) {
        List<Integer> orderslist = new ArrayList<>();
        List<Order> orders = new ArrayList<>();

        int orderID = 0;
        String getOrdersGivenClient = "Select distinct order_.id  from order_details, order_ where order_.ID = " +
                "order_details.OrderID and order_.ClientID= ?";

        ResultSet rs;

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement getOrderByGivenClientstm = con.prepareStatement(getOrdersGivenClient);) {

            getOrderByGivenClientstm.setInt(1, clientID);

            rs = getOrderByGivenClientstm.executeQuery();

            while (rs.next()) {
                orderID = rs.getInt("ID");
                orderslist.add(orderID);
            }

            orders = orderslist.stream().map(order -> getOrderbyID(order)).collect(Collectors.toList());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return orders;
    }


    public Client getClientByID(int clientID) {
        String getClientByID = "Select * from client where client.id = ?";
        Client client = new Client();
        ResultSet rs;

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement getClientByIDstm = con.prepareStatement(getClientByID);) {

            getClientByIDstm.setInt(1, clientID);

            rs = getClientByIDstm.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt("ID");
                String firstName = rs.getString("First_name");
                String lastName = rs.getString("Last_name");
                String epost = rs.getString("e_post");
                String pwrd = rs.getString("pwrd");
                String address = rs.getString("Address");
                String postZone = rs.getString("Post_zone");
                String postNumber = rs.getString("Post_number");
                client = new Client(ID, firstName, lastName, epost, pwrd, address, postZone, postNumber,
                        getOrdersGivenClient(ID));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return client;
    }

    public List<Client> getAllClients() {
        List<Client> clientList = new ArrayList<>();
        List<Integer> allClientsID = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             Statement smt = con.createStatement();
             ResultSet rs = smt.executeQuery("Select id from client")) {

            while (rs.next()) {
                allClientsID.add(rs.getInt("id"));
            }

            clientList = allClientsID.stream().map(c -> getClientByID(c)).collect(Collectors.toList());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return clientList;
    }

    public List<Review> getClientReviews(int clientID) {
        List<Review> reviewsList = new ArrayList<>();
        Review review = null;
        String getReviewbyClient = "Select * from review where clientID = ?";
        ResultSet rs;

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement getReviewByClientstm = con.prepareStatement(getReviewbyClient);) {

            getReviewByClientstm.setInt(1, clientID);
            rs = getReviewByClientstm.executeQuery();

            while (rs.next()) {

                int IDreview = rs.getInt("ID");
                int customerId = rs.getInt("ID");
                int shoesID = rs.getInt("ID");
                Date date = rs.getDate("Date");
                int reviewID = rs.getInt("ReviewID");
                String comment = rs.getString("Comment");
                review = new Review(IDreview, getClientByID(customerId), getShoesByID(shoesID), date, reviewID, comment);
                reviewsList.add(review);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return reviewsList;
    }

    public List<Review> getShoesReviews(int shoesID) {
        List<Review> reviewsList = new ArrayList<>();
        Review review = null;
        String getReviewbyShoes = "Select * from review where shoesid = ?";
        ResultSet rs;

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement getReviewByShoesstm = con.prepareStatement(getReviewbyShoes);) {

            getReviewByShoesstm.setInt(1, shoesID);
            rs = getReviewByShoesstm.executeQuery();

            while (rs.next()) {

                int reviewId = rs.getInt("ID");
                int customerId = rs.getInt("ID");
                int shoesIDn = rs.getInt("ID");
                Date date = rs.getDate("Date");
                int reviewID = rs.getInt("ReviewID");
                String comment = rs.getString("Comment");
                review = new Review(reviewId, getClientByID(customerId), getShoesByID(shoesIDn), date, reviewID, comment);
                reviewsList.add(review);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return reviewsList;
    }

    public Review getReviewByID(int reviewID) {
        Review review = new Review();
        String getReviewByID= "Select * from review where ID = ?";
        ResultSet rs;

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement getReviewByIDstm = con.prepareStatement(getReviewByID);) {

            getReviewByIDstm.setInt(1, reviewID);
            rs = getReviewByIDstm.executeQuery();

            while (rs.next()) {

                int idreview = rs.getInt("ID");
                int customerId = rs.getInt("ID");
                int shoesID = rs.getInt("ID");
                Date date = rs.getDate("Date");
                int reviewIDn = rs.getInt("ReviewID");
                String comment = rs.getString("Comment");
                review = new Review(idreview, getClientByID(customerId), getShoesByID(shoesID), date, reviewIDn, comment);

            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return review;
    }



    public List<Review> getAllReviews() {
        List<Review> reviewList = new ArrayList<>();
        List<Integer> allReviewsID = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             Statement smt = con.createStatement();
             ResultSet rs = smt.executeQuery("Select id from review")) {

            while (rs.next()) {
                allReviewsID.add(rs.getInt("id"));
            }

            reviewList = allReviewsID.stream().map(r -> getReviewByID(r)).collect(Collectors.toList());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return reviewList;
    }

    public ReviewDefinition getReviewDefinitionbyRankingID(int id){
        ReviewDefinition reviewDefinition = new ReviewDefinition();
        String getDefinition = "Select * from review_definition where rankingid = ?";
        ResultSet rs;

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement getDefinitionByIDstm = con.prepareStatement(getDefinition);) {

            getDefinitionByIDstm.setInt(1, id);
            rs = getDefinitionByIDstm.executeQuery();

            while (rs.next()) {

                int reviewId = rs.getInt("ID");
                String rankingId = rs.getString("RankingID");
                String description = rs.getString("Description");
                reviewDefinition = new ReviewDefinition(reviewId, rankingId, description);

            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return reviewDefinition;
    }

    public List<ReviewDefinition> getAllReviewDefinition() {
        List<ReviewDefinition> definitionList = new ArrayList<>();
        List<Integer> alldefinitionIDs = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             Statement smt = con.createStatement();
             ResultSet rs = smt.executeQuery("Select id from review_definition")) {

            while (rs.next()) {
                alldefinitionIDs.add(rs.getInt("id"));
            }

            definitionList = alldefinitionIDs.stream().map(d -> getReviewDefinitionbyRankingID(d)).collect(Collectors.toList());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return definitionList;
    }


    }



















