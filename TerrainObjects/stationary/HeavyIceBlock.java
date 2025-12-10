package TerrainObjects.stationary;

import TerrainObjects.movable.LightIceBlock;
import TerrainObjects.movable.Penguin;
import TerrainObjects.movable.SeaLion;

public class HeavyIceBlock extends Hazard {
    public HeavyIceBlock(int row, int column){
        super(row, column, "HI");
    }
    @Override
    public String handlePenguinCollision(Penguin penguin) {
        return "PENALIZED: "+ penguin.getName() + " hit a Heavy Ice Block and dropped food";
    }
    @Override
    public String handleSlidingObjectCollision(Object slidingObject) {
        if(slidingObject instanceof SeaLion || slidingObject instanceof LightIceBlock){
            return "STOPPED: Sliding object hit a Heavy Ice Block.";
        }
        return "No effect.";
    }
    
}
