package TerrainObjects.stationary;

import java.util.Random;

import TerrainObjects.AbstractTerrainObject;
import TerrainObjects.enums.FoodType;

public class Food extends AbstractTerrainObject{
    private final FoodType type;
    private final int weight;
    private static final Random random = new Random();

    public Food(int row, int column){
        super(row, column, FoodType.getRandomFoodType().getNotation());
        this.weight = random.nextInt(5)+1;
        this.type = FoodType.getRandomFoodType();
    }
    public FoodType getType() {
        return type;
    }
    public int getWeight() {
        return weight;
    }
    @Override
    public String toString(){
        return String.format("%s (%d units) ", type.getNotation(), weight);
    }
}
