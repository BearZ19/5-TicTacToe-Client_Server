package FindingConnectedComponentsinaGraphAlgorithm_Java;

import java.util.HashSet;
import java.util.Set;

public class DFSConnectedFive implements IAlgo_Shortest_Path_in_a_Graph {

    private final int[][] directions = {
            {1, 0}, {-1, 0},   // vertical
            {0, 1}, {0, -1},   // horizontal
            {1, 1}, {-1, -1},  // diagonal \
            {1, -1}, {-1, 1}   // diagonal /
    };

    @Override
    public boolean hasConnectedFive(String[][] board, int row, int col, String player) {
        for (int d = 0; d < directions.length; d += 2) {
            int count = 1;
            count += dfs(board, row, col, player, directions[d][0], directions[d][1], new HashSet<>());
            count += dfs(board, row, col, player, directions[d + 1][0], directions[d + 1][1], new HashSet<>());
            if (count >= 5) return true;
        }
        return false;
    }

    private int dfs(String[][] board, int row, int col, String player, int dx, int dy, Set<String> visited) {
        int newRow = row + dx;
        int newCol = col + dy;
        String key = newRow + "," + newCol;

        if (newRow < 0 || newCol < 0 || newRow >= board.length || newCol >= board[0].length
                || visited.contains(key) || !player.equals(board[newRow][newCol])) {
            return 0;
        }

        visited.add(key);
        return 1 + dfs(board, newRow, newCol, player, dx, dy, visited);
    }
}
