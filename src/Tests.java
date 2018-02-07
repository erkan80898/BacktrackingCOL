import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author James Heliotis
 * @author Erkan Uretener @ RIT CS - Removed somethings
 * and change printstream to the file.
 */
public class Tests {

    public static final int MIN_LEN = 8;
    public static final int MIN_WID = 5;
    public static final String RANDOM_ARG_OPTION = "--random";
    private static String LS = System.lineSeparator();
    private static int MSEC_PER_SEC = 1000;

    /**
     * Choose which kind of test to run based on command line arguments.
     * @param args <br>
     *             <i><small>none</small></i> &rArr; Run 3 fixed tests.<br>
     *             <code>--random n</code> &rArr; Generate a random test of size
     *             at most <code>n</code>&times;<code>n</code>.<br>
     *             <i>fileName</i> &rArr; Run the problem instance stored in
     *             <i>fileName</i>.
     */
    public static void main( String[] args ) {

        Backtracker solver = new Backtracker( true );
        try {
            if ( args.length == 2 && args[ 0 ].equals( RANDOM_ARG_OPTION ) ) {
                final int dim = Integer.parseInt( args[ 1 ] );
                if ( dim >= MIN_LEN && dim >= MIN_WID ) {
                }
                else {
                    throw new Exception( "Dim. for random test too small." );
                }
            }
            else if ( args.length == 1 ) {
                Tests.fromFile( solver, args[ 0 ] );
            }
            else if ( args.length > 0 ) {
                throw new Exception( "Illegal command line arguments." );
            }
            else {
                System.out.println( "Fixed Tests" + LS );
            }
        }
        catch( Exception e ) {
            System.err.println( "Problem: " + e );
            e.printStackTrace();
        }
    }

    /**
     * Before running a test, print out details of the problem instance.
     * This includes the trunk size and a graphical list of the Players
     * to be put in the trunk.
     * @param cases a collection of Players
     * @param length the trunk's length
     * @param width the trunk's width
     * @rit.pre Problem instance must have already been formulated and
     *          stored in the parameters described here.
     */
    public static void testPreamble(
            Iterable< Player > cases, int length, int width ) {
        System.out.println( "TEST COMMENCING" + LS );
        System.out.println( "Players:" + LS );
        for ( Player s : cases ) {
            s.display();
            System.out.println();
        }
        System.out.println(
                "...into a " +  length +  " x " +  width +  " trunk" +  LS
        );
    }

    /**
     * Run a single test of the Trunks backtracking solver
     * @param solver an initialized Backtracker instance
     * @param starting the initial, empty Trunk configuration
     */
    private static void oneTest( Backtracker solver, Trunk starting ) {
        System.out.println( "SOLVING..." + LS );
        Instant begin = Instant.now();
        Optional< Configuration > maybeSol = solver.solve( starting );
        long execTime = Duration.between( begin, Instant.now() ).toMillis();
        if ( maybeSol.isPresent() ) {
            System.out.println( "YES!" );
            maybeSol.get().display();
        }
        else {
        }
        System.out.println("Total Creation: " +Backtracker.counter);
        System.out.printf(
                "Execution time %d.%03d seconds",
                execTime / MSEC_PER_SEC, execTime % MSEC_PER_SEC
        );

        System.out.println();
    }

    /**
     * Run a Trunks problem based on data from a file. The first line
     * in the file contains the length, then a space, then the width,
     * of the trunk. Each successive line contains the name (one character),
     * length, and width of a Player.
     * @param solver an initialized Backtracker instance
     * @param fileName the name of the file containing the problem instance
     *                 description
     * @throws FileNotFoundException if the file cannot be opened for reading
     */
    private static void fromFile( Backtracker solver, String fileName )
            throws FileNotFoundException {
        int trunkL;
        int trunkW;
        List< Player > cases = new LinkedList<>();
        try ( Scanner file = new Scanner( new File( fileName ) ) ) {
            String[] dims = file.nextLine().split( "\\s+" );

            trunkL = Integer.parseInt( dims[ 0 ] );
            trunkW = Integer.parseInt( dims[ 1 ] );
            try {
                System.setOut(new PrintStream(new File("output/"+trunkL+
                        "x"+trunkW+".txt")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while ( file.hasNextLine() ) {
                String[] scParams = file.nextLine().split( "\\s+" );
                cases.add( new Player(
                        scParams[ 0 ].charAt( 0 )
                    )
                );
            }
        }
        testPreamble( cases, trunkL, trunkW );
        oneTest( solver, new Trunk( trunkL, trunkW, cases ) );
    }


}
