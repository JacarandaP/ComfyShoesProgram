package Models;


public class ReviewDefinition {
    private int id;
    private String rankingID;
    private String description;


    public ReviewDefinition(int id, String rankingID, String description) {
        this.id = id;
        this.rankingID = rankingID;
        this.description = description;
    }

    public ReviewDefinition() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRankingID() {
        return rankingID;
    }

    public void setRankingID(int rankingID) {
        rankingID = rankingID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public void printDefinition(){
        System.out.println("ID: "+ id + " Description: " +description + " RankingID string value: " + rankingID);
    }
}
