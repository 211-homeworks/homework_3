package TerrainObjects.game;

import TerrainObjects.ITerrainObject;
import TerrainObjects.enums.Direction;
import TerrainObjects.movable.*;
import TerrainObjects.stationary.*;
import TerrainObjects.stationary.IHazard;
import TerrainObjects.AbstractTerrainObject; 

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class IcyTerrain {
    private final List<List<ITerrainObject>> terrainGrid;
    private final int size = 10;

    private final List<Penguin> allPenguins;
    private Penguin player;
    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);

    public IcyTerrain(){
        this.terrainGrid = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            List<ITerrainObject> row = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                row.add(null);
            }
            this.terrainGrid.add(row);
        }
        this.allPenguins = new ArrayList<>();
        initializeGridObjects();
        assignPlayerPenguin();
    }
    
    

    private void initializeGridObjects(){
        placePenguins();
        placeRandomObjects(20, Food.class);
        placeRandomObjects(15, HeavyIceBlock.class, HoleInIce.class, LightIceBlock.class);
        placeRandomObjects(2, SeaLion.class);
    }

    @SafeVarargs
    private final void placeRandomObjects(int count, Class<? extends ITerrainObject>... classes){
        for (int i = 0; i < count; i++) {
            int row,column;
            do{
                row = random.nextInt(size);
                column = random.nextInt(size);
            }while(getObjectAt(row, column)!= null);

            try{
                Class<? extends ITerrainObject> selectedClass = classes[random.nextInt(classes.length)];
                
                ITerrainObject obj = selectedClass.getConstructor(int.class, int.class).newInstance(row,column);
                setObjectAt(row,column,obj);
            }
            catch(Exception e){
                System.err.println("Error placing object: " + e.getMessage());
            }
        }
    }
    
    private void placePenguins(){
        @SuppressWarnings("unchecked")
        Class<? extends Penguin>[] penguinTypes = new Class[]{
            RockhopperPenguin.class, KingPenguin.class, EmperorPenguin.class, RoyalPenguin.class};
            
        for (int i = 1; i <= 3; i++) {
            int row, column;
            do{
                row = random.nextInt(size);
                column = random.nextInt(size);
            }while(getObjectAt(row,column) != null);
            
            Class<? extends Penguin> type = penguinTypes[random.nextInt(penguinTypes.length)];
            
            try{
                
                Penguin penguin = type.getConstructor(String.class, int.class, int.class).newInstance("P"+i, row, column);
                allPenguins.add(penguin);
                setObjectAt(row, column, penguin); 
            }
            catch(Exception e){
                System.err.println("Error placing penguin: " + e.getMessage());
            }
        }
    }
    
    private void assignPlayerPenguin(){
        this.player = allPenguins.get(random.nextInt(allPenguins.size()));
        System.out.println("You have been assigned to control "+ this.player.getName()+" (a "+ this.player.getClass().getSimpleName()+ ").");
    }

    

    public ITerrainObject getObjectAt(int row, int column){
        if(row<0 || row>=size || column<0 || column>=size) return null;
        return terrainGrid.get(row).get(column);
    }
    
    public void setObjectAt(int row, int column, ITerrainObject obj){
        if(row >= 0 && row < size && column >= 0 && column < size){
            terrainGrid.get(row).set(column, obj);
        }
    }

    

    public void startGame(){ 
        for (int turn = 1; turn <= 4; turn++) {
            System.out.println("\n====================== TURN " + turn + " ======================");

            for(Penguin currentPenguin : allPenguins){
                if(!currentPenguin.isEliminated() && currentPenguin.getTurnsLeft() > 0){
                    
                    System.out.println("\n--- " + currentPenguin.getName() + "'s Turn (" + currentPenguin.getTurnsLeft() + " moves left) ---");
                    
                    if(currentPenguin == player){
                        handlePlayerTurn(currentPenguin);
                    }
                    else {
                        handleAllTurn(currentPenguin);
                    }

                    if (!currentPenguin.isEliminated()) {
                        currentPenguin.decrementTurn();
                    }

                    if(allPenguins.stream().allMatch(p->p.isEliminated() || p.getTurnsLeft()<=0)){
                        endGame();
                        return;
                    }
                }
            displayTerrain();
            }
        }
        endGame();
    }

    

    private void handlePlayerTurn(Penguin penguin){
        boolean moveExecuted = false;
        
        while (!moveExecuted) {
            displayMenu(penguin);
            String choice = scanner.nextLine().toUpperCase();
            
            if (choice.equals("M")) {
                System.out.print("Enter direction (U/D/L/R): ");
                Direction direction = Direction.fromChar(scanner.nextLine().charAt(0));
                if (direction != null) {
                    executeMove(penguin, direction, false);
                    moveExecuted = true;
                } else {
                    System.out.println("Invalid direction. Try again.");
                }
            }
            else if (choice.equals("S")) {
                if (!penguin.isSpecialActionUsed()) {
                    System.out.print("Enter direction for special action (U/D/L/R): ");
                    Direction dir = Direction.fromChar(scanner.nextLine().charAt(0));
                    if (dir != null) {
                        executeMove(penguin, dir, true);
                        moveExecuted = true;
                    } else {
                        System.out.println("Invalid direction. Try again.");
                    }
                } else {
                    System.out.println("Special action already used. Choose M for move.");
                }
            }
            else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }
    
    private void handleAllTurn(Penguin penguin){
        
        Direction randomDirection = Direction.values()[random.nextInt(4)];
        System.out.println(penguin.getName() + " chooses to move " + randomDirection);
        executeMove(penguin, randomDirection, false);
    }
    
    public void executeMove(Penguin penguin , Direction direction , boolean useSpecial){
        if(useSpecial){
            penguin.useSpecialAction(direction);
            moveSlidingObject(penguin, direction);
        }
        else {
            penguin.slide(direction);
            moveSlidingObject(penguin,direction);
        }
    }
    
    

    private void moveSlidingObject(AbstractMovable movable, Direction direction){
        movable.setSliding(true);
        int row = movable.getRow();
        int column = movable.getColumn();
        int dr = 0, dc = 0;
        int distance = 0; 
    
        switch(direction){
            case UP: dr = -1; break;
            case DOWN: dr = 1; break;
            case LEFT: dc = -1; break;
            case RIGHT: dc = 1; break;
        }
    
        while(true){
            int nextRow = row + dr;
            int nextColumn = column + dc;
            distance++; 
    
            
            if(nextRow < 0 || nextRow >= size || nextColumn < 0 || nextColumn >= size){
                
                
                if(movable instanceof RoyalPenguin && ((RoyalPenguin)movable).hasSpecialAction() && !((RoyalPenguin)movable).isSpecialActionUsed()){
                    
                    handleRoyalPenguinBounce((RoyalPenguin)movable, direction, row, column);
                    break; 
                }

                setObjectAt(movable.getRow(), movable.getColumn(), null);
                if(movable instanceof Penguin){
                    Penguin penguin = (Penguin)movable;
                    penguin.setEliminated(true);
                    allPenguins.remove(penguin);
                    System.out.println(penguin.getName() + " fell off the edge and was eliminated.");
                }
                break;
            }
    
            ITerrainObject obstacle = getObjectAt(nextRow, nextColumn);
            
            if(obstacle == null){
                row = nextRow;
                column = nextColumn;
                
                if (movable instanceof KingPenguin && ((KingPenguin)movable).isSpecialActionUsed() && distance == 5) {
                    break;
                }
                
                
                if(movable instanceof RockhopperPenguin && !((RockhopperPenguin)movable).isSpecialActionUsed()){
                    break;
                }
                
                continue;
            }

            setObjectAt(movable.getRow(), movable.getColumn(), null);
            movable.setPosition(row, column);
            setObjectAt(row, column, movable);
            
            if (movable instanceof EmperorPenguin && ((EmperorPenguin)movable).isSpecialActionUsed()) {
                System.out.println(((EmperorPenguin)movable).getName() + " uses special ability: moving one extra square after collision.");
                
                int followUpRow = nextRow + dr;
                int followUpCol = nextColumn + dc;
                
                if (getObjectAt(followUpRow, followUpCol) == null) {
                    
                    setObjectAt(movable.getRow(), movable.getColumn(), null);
                    movable.setPosition(followUpRow, followUpCol);
                    setObjectAt(followUpRow, followUpCol, movable);
                    break; 
                }
            }
    
            if(obstacle instanceof Food){
                if(movable instanceof Penguin){
                    Penguin penguin = (Penguin)movable;
                    penguin.collectFood((Food)obstacle);
                    System.out.println(penguin.getName() +" collected "+ ((Food)obstacle).toString());
                }            
                row = nextRow;
                column = nextColumn;
                continue;
            }
    
            
            else if(obstacle instanceof HoleInIce || obstacle instanceof HeavyIceBlock){
                String status;
                
                if(movable instanceof Penguin){
                    status = ((IHazard)obstacle).handlePenguinCollision((Penguin)movable);
                    
                    
                    if (obstacle instanceof HeavyIceBlock) {
                        Food droppedFood = ((Penguin)movable).dropRandomFood();
                        if (droppedFood != null) {
                            findEmptySpotAndPlaceObject(droppedFood);
                            System.out.println("...and randomly dropped " + droppedFood.toString() + " elsewhere on the ice.");
                        }
                    }
                } else {
                    status = ((IHazard)obstacle).handleSlidingObjectCollision(movable);
                }
                
                System.out.println(status);
                
                
                if (status.contains("ELIMINATED")) {
                    setObjectAt(movable.getRow(), movable.getColumn(), null);
                }
                if (status.contains("PLUGGED")) {
                    setObjectAt(nextRow, nextColumn, null); 
                }
                break; 
            }
            else if(obstacle instanceof SeaLion){
                SeaLion seaLion = (SeaLion)obstacle;
                
                if (movable instanceof Penguin) {
                    Penguin penguin = (Penguin)movable;
                    penguin.setEliminated(true);
                    setObjectAt(penguin.getRow(), penguin.getColumn(), null); 
                    System.out.println(penguin.getName() + " was eliminated by the Sea Lion!");
       
                    Direction seaLionDir = getOppositeDirection(direction);
                    System.out.println("Sea Lion at (" + seaLion.getRow() + "," + seaLion.getColumn() + ") starts sliding in the " + seaLionDir + " direction.");
                                        
                    setObjectAt(seaLion.getRow(), seaLion.getColumn(), null); 
                    seaLion.setPosition(row, column); 
                    setObjectAt(row, column, seaLion);
                    moveSlidingObject(seaLion, seaLionDir);
                }

                else if (movable instanceof LightIceBlock) {
                    
                    Direction seaLionDir = direction;
                    System.out.println("Sea Lion at (" + seaLion.getRow() + "," + seaLion.getColumn() + ") starts sliding in the " + seaLionDir + " direction.");
                    seaLion.onCollision(movable, direction); 
                    moveSlidingObject(seaLion, seaLionDir);
                }
                break;
            }
            else if(obstacle instanceof LightIceBlock){
                ((LightIceBlock)obstacle).handleSlidingObjectCollision(movable);   
                break;
            }
            break;
        }
        movable.setSliding(false);
    }

    private Direction getOppositeDirection(Direction direction) {
        switch (direction) {
            case UP: return Direction.DOWN;
            case DOWN: return Direction.UP;
            case LEFT: return Direction.RIGHT;
            case RIGHT: return Direction.LEFT;
            default: return null;
        }
    }

    private void findEmptySpotAndPlaceObject(ITerrainObject obj) {
        int row, column;
        do {
            row = random.nextInt(size);
            column = random.nextInt(size);
        } while (getObjectAt(row, column) != null);

        if (obj instanceof AbstractTerrainObject) {
            ((AbstractTerrainObject) obj).setPosition(row, column);
        }
        setObjectAt(row, column, obj);
        System.out.println("Food placed at (" + row + "," + column + ")");
    }
    
    private void handleRoyalPenguinBounce(RoyalPenguin penguin, Direction direction, int row, int column) {
        penguin.useSpecialAction(direction); 
        setObjectAt(penguin.getRow(), penguin.getColumn(), null);
        penguin.setPosition(row, column);
        setObjectAt(row, column, penguin);

        System.out.println(penguin.getName() + " Bounces! Now sliding one square in the reverse direction.");
        Direction reverseDirection = getOppositeDirection(direction);

        int dr = 0, dc = 0;
        switch(reverseDirection){
            case UP: dr = -1; break;
            case DOWN: dr = 1; break;
            case LEFT: dc = -1; break;
            case RIGHT: dc = 1; break;
        }

        int nextRow = row + dr;
        int nextColumn = column + dc;
        
        if (getObjectAt(nextRow, nextColumn) == null) {    
            setObjectAt(row, column, null);
            penguin.setPosition(nextRow, nextColumn);
            setObjectAt(nextRow, nextColumn, penguin);
        } 
        else {
            System.out.println("...but the square behind was blocked, movement stopped.");
        }
    }

    public void displayTerrain(){
        System.out.println("\n  " + "-".repeat(31));
        System.out.print("   ");
        for (int j = 0; j < size; j++) {
            System.out.printf(" %-2s", j);
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.print(i + "|");
            for (int j = 0; j < size; j++) {
                ITerrainObject obj = terrainGrid.get(i).get(j);
                String notation = obj != null ? obj.getNotation() : "  ";
                System.out.printf(" %-2s", notation);
            }
            System.out.println("|");
        }
        System.out.println("  " + "-".repeat(31));
    }
    
    private void displayMenu(Penguin penguin) {
        System.out.println("\nPlayer Penguin: " + penguin.getName() + " (" + penguin.getClass().getSimpleName() + ")");
        System.out.println("Food Collected: " + penguin.getTotalFoodWeight() + " units");
        System.out.println("Turns Left: " + penguin.getTurnsLeft());    
        System.out.print("Action: (M)ove");
        
        if (!penguin.isSpecialActionUsed()) {
            System.out.print(" | (S)pecial Action");
        }
        System.out.print(": ");
    }
    
    private void endGame() {
        allPenguins.removeIf(p -> p.isEliminated());
        
        System.out.println("\n\n====================== GAME OVER ======================");
        System.out.println("Final Scores:");
        
        Penguin winner = null;
        int maxWeight = -1;
        boolean allEliminated = true;
        
        for (Penguin p : allPenguins) {
            if (p.isEliminated()) {
                System.out.printf("%s (%s): ELIMINATED\n", p.getName(), p.getClass().getSimpleName());
            } else {
                allEliminated = false;
                int score = p.getTotalFoodWeight();
                System.out.printf("%s (%s): %d total food units\n", p.getName(), p.getClass().getSimpleName(), score);
                
                if (score > maxWeight) {
                    maxWeight = score;
                    winner = p;
                } else if (score == maxWeight && winner != null) {
                    
                    winner = p;
                }
            }
        }
        if (winner != null && !allEliminated) {
            System.out.println("\n*** WINNER: " + winner.getName() + " with " + maxWeight + " food units! ***");
        } else {
            System.out.println("\nNo winner determined (all penguins eliminated or no food collected).");
        }
        scanner.close();
    }
}