import java.util.*;
/**
 * This class represents the Trunk in which Players can lay in.
 *
 * @author Erkan Uretener @RIT
 */
public class Trunk implements Configuration {

    private int length;
    private int width;
    private List<Player> toAdd;
    private char[][] theTrunk;

    /**
     * Initial Trunk constructor with nothing in it, but a bunch of Players we hope to add.
     * @param length dimension across, i.e. x, of trunk
     * @param width dimension down, i.e., y, of trunk
     * @param toAdd a bunch of Players that we'll try to put in here
     */
    Trunk(int length, int width,Collection<Player> toAdd){
        this.length = length;
        this.width = width;
        this.toAdd = new ArrayList<>(toAdd);
        theTrunk = new char[length][width];
    }

    /**
     * Copy constructor
     * @param previous - the configuration off of which this one is based
     */
    Trunk(Trunk previous){
        this.length = previous.getLength();
        this.width = previous.getWidth();
        toAdd = new ArrayList<>();
        for (Player x:previous.toAdd) {
            this.toAdd.add(new Player(x));
        }
        this.theTrunk = new char[length][width];
        for (int i = 0; i < theTrunk.length; i++) {
            System.arraycopy(previous.theTrunk[i], 0, this.theTrunk[i], 0, previous.theTrunk[0].length);
        }
    }

    /**
     * Gets the length of the Trunk
     * @return the length
     */
    public int getLength(){
        return length;
    }

    /**
     * Gets the Width of the Trunk
     * @return the Width
     */
    public int getWidth(){
        return width;
    }

    /**
     * Return a list of Trunks with the next Player from the toAdd list added in all possible locations.
     * @return all the Trunks described above, in a Collection
     */
    @Override
    public Iterable<Configuration> getSuccessors() {
        Collection<Configuration> successors = new ArrayList<>();


        Trunk copyTrunk = new Trunk(this);
        Player currentCase = toAdd.remove(0);
        int lengthPlayer = 1;
        int widthPlayer = 1;
        boolean exitFlag = false;

            for (int i = 0; i < theTrunk.length; i++) {
                //row
                for (int j = 0; j < theTrunk[0].length; j++) {
                    //column
                    if ((!(lengthPlayer > theTrunk.length - i || widthPlayer > theTrunk[0].length - j)) && theTrunk[i][j] == '\u0000') {
                        //If it fits, check if the spots are clear
                        for (int x = 0; x < lengthPlayer && exitFlag == false; x++) {
                            int y = 0;
                            for (; y < widthPlayer && exitFlag == false; y++) {
                                if (theTrunk[i + x][j + y] != '\u0000') {
                                    exitFlag = true;
                                } else {
                                    for(int nr = Math.max(0, i - 1); nr <= Math.min(i + 1, length - 1); ++nr){
                                        for (int nc = Math.max(0, j - 1); nc <= Math.min(j + 1, width - 1); ++nc) {
                                            if (!(nr==i && nc==j))  {  // don't process board[r][c] itself
                                                if(currentCase.getName() == theTrunk[nr][nc]){
                                                    exitFlag = true;
                                                }
                                            }
                                        }
                                    }
                                    if(exitFlag == false){
                                        theTrunk[i + x][j + y] = currentCase.getName();
                                    }
                                }
                            }
                        }
                        toAdd.add(currentCase);
                        if (exitFlag != true) {
                            successors.add(new Trunk(this));
                        }
                        this.theTrunk = (new Trunk(copyTrunk)).theTrunk;

                    }

                }
            }

        return successors;
    }



    /**
     * Are there no overlapping Players already in the trunk?
     * @return will always return true, as there never will be overlap
     */
    @Override
    public boolean isValid() {
        return true;
    }


    /**
     * Have we successfully finished the game?
     * @return true if we have
     * -Force true to get all the possible creations
     */
    @Override
    public boolean isGoal() {
        return false;
    }

    /**
     * Show the contents of the trunk and the remaining Players.
     */
    @Override
    public void display() {
        int rows = theTrunk.length;
        int columns = theTrunk[0].length;
        String str = "| ";
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                char chr = theTrunk[i][j]=='\0' ? ' ' : theTrunk[i][j];
                str += chr + " ";
            }

            System.out.println(str + "|");
            str = "| ";
        }
    }

}
