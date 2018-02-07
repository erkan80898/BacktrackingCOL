/**
 * A description of a Player for the game
 * @author Erkan Uretener
 */
public class Player{

    private char name;

    /**
     * Make a new Player.
     * @param name the Player's <em>one-letter</em> name,
     *             <em>assumed to be unique</em>
     */
    public Player( char name) {
        this.name = name;
    }

    public Player(Player suit){
        this.name = suit.getName();
    }

    /**
     * What is the name of this Player?
     * @return this Player's name, as provided in the constructor call
     */
    public char getName() {
        return this.name;
    }


    /**
     * for all collections of Player objects
     * @param other the object to which to compare this Player
     * @return true iff the other object is a Player with the same data
     *         (we assume all Player objects have unique names)
     */
    @Override
    public boolean equals( Object other ) {
        if ( this == other ) return true;
        try {
            Player otherSC = (Player)other;
            return this.name == otherSC.name;
        }
        catch( ClassCastException cce ) {
            return false;
        }
    }

    /**
     * Print, on standard output, a rendering of a Player
     * in the following format.
     * <pre>
     *     NNN
     *     NNN
     *     NNN
     *     NNN
     * </pre>
     * <p>
     *     The horizontal dimension is the length,
     *     and the vertical dimension is the Player's width.
     * </p>
     */
    public void display() {
        for ( int y = 0 ; y < 1; ++y ) {
            System.out.print( '\t' );
            for ( int x = 0; x < 1; ++x ) {
                System.out.print( this.name );
            }
            System.out.println();
        }
    }

}
