package TerrainObjects.stationary;

import TerrainObjects.movable.LightIceBlock;
import TerrainObjects.movable.Penguin;
import TerrainObjects.movable.SeaLion;

public class HoleInIce extends Hazard {
    public HoleInIce(int row, int column){
        super(row, column, "Ho");
    }
    @Override
    public String handlePenguinCollision(Penguin penguin) {
        return "ELIMINATED: "+ penguin.getName()+ " fell into the hole!";
    }
    @Override
    public String handleSlidingObjectCollision(Object slidingObject) {
        if(slidingObject instanceof SeaLion){
            return "ELIMINATED: Sea Lion is fell into the hole!";
        }
        else if(slidingObject instanceof LightIceBlock){
            return "PLUGGED: Hole covered by the Light Ice Block.";
        }
        return "STOPPED: Sliding object stopped before the hole.";
    }
    
}
