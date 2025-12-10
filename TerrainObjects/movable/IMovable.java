package TerrainObjects.movable;

import TerrainObjects.enums.Direction;

public interface IMovable {
    void slide(Direction direction);
    boolean isSliding();
    boolean hasSpecialAction();
}
