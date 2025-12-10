package TerrainObjects.movable;

import TerrainObjects.enums.Direction;

public class RockhopperPenguin extends Penguin {
    private static final String typename = "Rockhopper";

    public RockhopperPenguin(String name, int row, int column){
        super(name, row, column);
    }
    @Override
    public void slide(Direction direction) {
        System.out.println(this.getName() + " (" + typename + ") is performing a standard slide to " + direction);
    }
    @Override
    public void useSpecialAction(Direction direction) {
        if(specialActionUsed){
            System.out.println(this.getName() + " is cannot use special action again.");
            return;
        }
        System.out.println(this.getName() + " uses special action: attempts to slide two squares, ignoring the first obstacle.");
        specialActionUsed = true;
    }
    @Override
    public boolean hasSpecialAction() {
        return true;
    }
}
