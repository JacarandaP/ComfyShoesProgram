package Objects;

import java.util.List;

public class Category {

     private int id;
     private String description;
     private List<Shoes> shoesListBelongingCat;

     public Category(int id, String description, List<Shoes> shoesListBelongingCat) {
         this.id = id;
         this.description = description;
         this.shoesListBelongingCat = shoesListBelongingCat;
     }

     public int getId() {
         return id;
     }

     public void setId(int id) {
         this.id = id;
     }

     public String getDescription() {
         return description;
     }

    public List<Shoes> getShoesListBelongingCat() {
        return shoesListBelongingCat;
    }

    public void setShoesListBelongingCat(List<Shoes> shoesListBelongingCat) {
        this.shoesListBelongingCat = shoesListBelongingCat;
    }

    public void setDescription(String description) {
         this.description = description;
     }
 }
