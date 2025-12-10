package TerrainObjects.enums;

public enum FoodType {
    KRILL("Kr"),
    CRUSTACEAN("Cr"),
    ANCHOVY("An"),
    SQUID("Sq"),
    MACKERAL("Ma");

    private final String notation;
    FoodType(String notation){
        this.notation = notation;
    }
    public String getNotation() {
        return notation;
    }
    public static FoodType getRandomFoodType(){
        FoodType[] values = FoodType.values();
        int index = (int)(Math.random()*values.length);
        return values[index];
    }
}

