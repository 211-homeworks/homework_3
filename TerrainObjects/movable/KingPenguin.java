package TerrainObjects.movable;

import TerrainObjects.enums.Direction;

public class KingPenguin extends Penguin{
    private static final String typename = "King";
    public KingPenguin(String name , int row, int column){
        super(name, row, column);
    }
    @Override
    public void slide(Direction direction) {
        System.out.println(this.getName() + " (" + typename + ") is performing a standard slide to " + direction);
        return;
    }
    @Override
    public boolean hasSpecialAction() {
        return true;
    }
    @Override
    public void useSpecialAction(Direction direction) {
        if(specialActionUsed){
            System.out.println(this.getName() + "cannot use special action again.");
            return;
        }
        System.out.println(this.getName() + "uses special action : attempts to stop at exactly 5th square.");
        this.specialActionUsed = true;
    }
}
