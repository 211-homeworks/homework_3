package TerrainObjects.enums;

public enum Direction {
    UP('U'),
    DOWN('D'),
    RIGHT('R'),
    LEFT('L');

    private final char direction;
    Direction(char  shorthand){
        direction = shorthand;
    }
    public char getDirection() {
        return direction;
    }

    public static Direction fromChar(char input){
        char upperInput = Character.toUpperCase(input);
        for(Direction direction : Direction.values()){
            if(upperInput == direction.getDirection()){
                return direction;
            }
        }
        return null;
    }
}

