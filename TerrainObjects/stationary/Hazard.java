package TerrainObjects.stationary;

import TerrainObjects.AbstractTerrainObject;

public abstract class Hazard extends AbstractTerrainObject implements IHazard {
    public Hazard(int row, int column , String notation){
        super(row, column, notation);
    }
}
