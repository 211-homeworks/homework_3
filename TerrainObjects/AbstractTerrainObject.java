package TerrainObjects;
public abstract class AbstractTerrainObject implements ITerrainObject {
    protected int row;
    protected int column;
    protected String notation;

    public AbstractTerrainObject(int row, int column, String notation){
        this.row = row;
        this.column = column;
        this.notation = notation;
    }
    @Override
    public int getRow(){
        return row;
    }
    @Override
    public int getColumn(){
        return column;
    }
    @Override
    public String getNotation(){
        return notation;
    }
    public void setPosition(int row, int column){
        this.row = row;
        this.column  = column;
    }

}
