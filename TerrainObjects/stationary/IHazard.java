package TerrainObjects.stationary;

import TerrainObjects.movable.Penguin;

public interface IHazard {
    String handlePenguinCollision(Penguin penguin);
    String handleSlidingObjectCollision(Object slidingObject);
}
