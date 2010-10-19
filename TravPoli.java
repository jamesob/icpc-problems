// Traveling Politician
// 10543
// tags: single-source, powers of adjacency matrices
//
// by jamesob

import java.util.*;

public class TravPoli {
    private static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        int[][] mat;
        int numCities, numRoads, minSpeeches = -1;
        int answer;

        while(true) {
            numCities   = sc.nextInt();
            numRoads    = sc.nextInt();
            minSpeeches = sc.nextInt();

            if(numCities == 0)
                break;

            mat = new int[numCities][numCities];
            zeroMat(mat);
            getCase(numRoads, mat);
            answer = campaignShoutin(mat, minSpeeches);

            if(answer != -1)
                System.out.printf("%d\n", answer);
            else
                System.out.println("LOSER");
        }
    }

    private static void getCase(int numR, int[][] mat) {
        for(int i=0; i<numR; i++)
            mat[sc.nextInt()][sc.nextInt()] = 1;
    }

    /* ``multiply'' two adjacency matrices -- we | instead of + */
    private static int[][] adjMatMult(int[][] A, int[][] B) {
        int[][] C = new int[A.length][A.length]; 
        int res;

        for(int i=0; i<A.length; i++) {
            for(int j=0; j<A.length; j++) {
                res = 0;
                for(int k=0; k<A.length; k++)
                    res |= (A[i][k] * B[k][j]); // nb: we accumulate via |
                C[i][j] = res;
            }
        }
        return C;
    }

    /* zero out a matrix */
    private static void zeroMat(int[][] mat) {
        for(int i=0; i<mat.length; i++)
            for(int j=0; j<mat.length; j++)
                mat[i][j] = 0;
    }

    /* walk that baby around */
    private static int campaignShoutin(int[][] mat, int minSpeeches) {
        int sToD; // is there a path from source -> dest of length i?
        int[][] origMat = mat.clone();

		/* `mat` initially gives us all paths of length 2 */
        for(int pathLen=2; pathLen<=22; pathLen++) {
            sToD = mat[0][mat.length - 1]; // mat[0][n-1]
            if(sToD == 1 && pathLen >= minSpeeches)
                return pathLen;
            mat = adjMatMult(origMat, mat);
        }
        return -1; // LOSER!
    }
}
