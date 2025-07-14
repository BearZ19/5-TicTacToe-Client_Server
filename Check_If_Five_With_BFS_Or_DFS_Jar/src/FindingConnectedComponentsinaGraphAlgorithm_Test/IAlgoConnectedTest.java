package FindingConnectedComponentsinaGraphAlgorithm_Test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IAlgoConnectedTest {

    private final FindingConnectedComponentsinaGraphAlgorithm_Java.BFSConnectedFive bfs = new FindingConnectedComponentsinaGraphAlgorithm_Java.BFSConnectedFive();
    private final FindingConnectedComponentsinaGraphAlgorithm_Java.DFSConnectedFive dfs = new FindingConnectedComponentsinaGraphAlgorithm_Java.DFSConnectedFive();

    @Test
    public void Find() {
        String[][] board = new String[10][10];
        for (int i = 0; i < 5; i++) {
            board[4][i] = "X";
        }

        assertTrue(bfs.hasConnectedFive(board, 4, 0, "X"));
        assertTrue(dfs.hasConnectedFive(board, 4, 0, "X"));
    }

    @Test
    public void testBrokenConnection() {
        String[][] board = new String[10][10];
        board[0][0] = "O";
        board[1][1] = "O";
        board[2][2] = "X"; // Interruption
        board[3][3] = "O";
        board[4][4] = "O";

        assertFalse(bfs.hasConnectedFive(board, 0, 0, "O"));
        assertFalse(dfs.hasConnectedFive(board, 0, 0, "O"));
    }
}
