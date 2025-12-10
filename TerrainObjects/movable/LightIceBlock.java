package TerrainObjects.movable;

import TerrainObjects.enums.Direction;
import TerrainObjects.stationary.IHazard;

public class LightIceBlock extends AbstractMovable implements IHazard{
    public LightIceBlock(int row, int column){
        super(row, column, "LB");
    }
    @Override
    public boolean hasSpecialAction() {
        return false;
    }
    @Override
    public void slide(Direction direction) {
        System.out.println("Light Ice Block at (" + this.row+ ", " + this.column+") starts sliding to " + direction);
    }
    @Override
    public String handlePenguinCollision(Penguin penguin) {
        return "SLIDING: Penguin hit Light Ice Block LB is now sliding.";
    }
    @Override
    public String handleSlidingObjectCollision(Object slidingObject) {
        if(slidingObject instanceof SeaLion){
            return "SLIDING: Sea Lion hit Light Ice Block LB is now sliding.";
        }
        else if( slidingObject instanceof LightIceBlock){
            return "STOPPED: Light Ice Block hit another LB. Both stop.";
        }
        return "No effect.";
    }
    
    
}

