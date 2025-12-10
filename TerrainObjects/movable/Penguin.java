package TerrainObjects.movable;

import java.util.ArrayList;
import java.util.List;

import TerrainObjects.enums.Direction;
import TerrainObjects.stationary.Food;

public abstract class Penguin extends AbstractMovable{
    private final String name;
    private List<Food> collectedFood;
    private int turnsLeft;
    protected boolean specialActionUsed;
    private boolean eliminated;

    public Penguin(String name, int row, int column){
        super(row, column, name);
        this.name = name;
        this.collectedFood = new ArrayList<>();
        this.turnsLeft = 4;
        this.eliminated = false;
    }
    public abstract void useSpecialAction(Direction direction);

    public void collectFood(Food food){
        collectedFood.add(food);
    }
    public void decrementTurn(){
        if(turnsLeft > 0) turnsLeft--;
    }
    public int getTotalFoodWeight(){
        int sum = 0;
        for (Food food : collectedFood) {
            sum += food.getWeight();
        }
        return sum;
    }
    public String getName() {
        return name;
    }
    public int getTurnsLeft() {
        return turnsLeft;
    }
    public boolean isEliminated(){
        return eliminated;
    }
    public void setEliminated(boolean eliminated){
        this.eliminated = eliminated;
    }
    public boolean isSpecialActionUsed(){
        return specialActionUsed;
    }
    public Food dropRandomFood(){

        if(collectedFood.isEmpty()){
            return null;
        }
        int index = (int)(Math.random()*collectedFood.size());
        return collectedFood.remove(index);
    }
}
