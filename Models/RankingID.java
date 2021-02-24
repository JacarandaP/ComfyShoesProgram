package Models;



public enum RankingID {

        BAD(1), OKAY(2), GOOD(3), VERY_GOOD(4);

    private final int rankingID;

    RankingID(int rankingID){
        this.rankingID = rankingID;
    }

    public int getRankingID(){

        return rankingID;
    }



}