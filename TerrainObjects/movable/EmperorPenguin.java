package TerrainObjects.movable;

import TerrainObjects.enums.Direction;

public class EmperorPenguin extends Penguin{
    private static final String typeName = "Emperor";
    public EmperorPenguin(String name, int row, int column){
        super(name, row, column);
    }
    @Override 
    public void slide(Direction direction) {
        System.out.println(this.getName() + " (" + typeName+ ") performing standard slide to " + direction);
    }
    @Override
    public void useSpecialAction(Direction direction){
        if(specialActionUsed){
            System.out.println(this.getName() + " cannot use the special action again.");
        }
        System.out.println(this.getName() + "uses special action: attempts to slide on extra after collision.");
        this.specialActionUsed = true;
    }
    @Override
    public boolean hasSpecialAction() {
        return true;
    }
    
}
