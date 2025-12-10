package TerrainObjects.movable;

import TerrainObjects.AbstractTerrainObject;

public abstract class AbstractMovable extends AbstractTerrainObject implements IMovable{
    protected boolean sliding;
    AbstractMovable(int row, int column , String notation){
        super(row, column, notation);
        this.sliding = false;
    }
    @Override
    public boolean isSliding() {
        return sliding;
    }
    public void setSliding(boolean sliding){
        this.sliding = sliding;
    }

    
}
