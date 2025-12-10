package TerrainObjects.movable;

import TerrainObjects.ITerrainObject;
import TerrainObjects.enums.Direction;

public class SeaLion extends AbstractMovable{

    public SeaLion(int row, int column){
        super(row, column, "SL");
    }
    @Override
    public boolean hasSpecialAction() {
        return false;
    }
    @Override
    public void slide(Direction direction) {
        System.out.println("Sea Lion starting sliding in the " + direction);
    }
    public void onCollision(ITerrainObject incomingObject , Direction direction){
        if(incomingObject instanceof LightIceBlock){
            slide(direction);
        }
        else if (incomingObject instanceof Penguin){
            slide(getOppositeDirection(direction));
        }
    }
    public Direction getOppositeDirection(Direction direction){
        switch (direction) {
            case UP: return Direction.DOWN;
            case DOWN: return Direction.UP;
            case LEFT: return Direction.RIGHT;
            case RIGHT: return Direction.LEFT;
            default: return null;
        }
    }
}
