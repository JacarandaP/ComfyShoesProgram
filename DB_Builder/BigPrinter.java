package DB_Builder;


import Models.Client;
import Models.Review;
import Models.ReviewDefinition;

public class BigPrinter {

    Generator generator = new Generator();

    public BigPrinter(){

        //generator.getAllShoes().forEach(s -> s.printShoes());
        //generator.getAllCategories().forEach(c -> c.printCategory());
        //generator.getAllOrders().forEach(o -> o.printOrderSimple());
        //generator.getClientByID(1).printClient();
        //generator.getAllClients().forEach(Client::printClient);
        //generator.getClientReviews(1).forEach(Review::printReviewSimple);
        //generator.getShoesReviews(10).forEach(Review::printReview);
        //generator.getAllReviews().forEach(Review::printReviewSimple);
        //generator.getReviewDefinitionbyRankingID(1).printDefinition();
        //generator.getAllReviewDefinition().forEach(ReviewDefinition::printDefinition);


    }

    public static void main(String[] args) {
        BigPrinter bp = new BigPrinter();
    }
}
