/*
 * Traveling Politician
 * 10543
 * tags: graph, powers of adjacency matrix, single source
 * accepted with time 0.028s
 *
 * by jamesob
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h> /* memset */


/* return a square matrix */
int **sqMatrix(int n) {
    int i;
    int **mat = malloc(n * sizeof(int *));
    for(i=0; i<n; i++) {
        mat[i] = (int *)malloc(n * sizeof(int));
        memset(mat[i], 0, n * sizeof(int));
    }
    return mat;
}

int **adjMatrixMult(int **A, int **B, int n)
{
    int i, j, k, res;
    int **C = sqMatrix(n);

    /* instead of dotting (+ / *), we accumulate (| / &) */
    for(i=0; i<n; i++) {
        for(j=0; j<n; j++) {
            res = 0;
            for(k=0; k<n; k++)
                res |= A[i][k] & B[k][j];
            C[i][j] = res;
        }
    }
    return C;
} 

int campaign(int **mat, int minSpeeches, int n) {
    int s_to_d, i, path_len;
    int **orig_mat = sqMatrix(n);

    for(i=0; i<n; i++) /* copy mat -> mat_orig */
        memcpy(orig_mat[i], mat[i], n * sizeof(int));

    for(path_len = 2; path_len <= 22; path_len++) {
        s_to_d = mat[0][n - 1]; /* path from source->dest? */
        if(s_to_d == 1 && path_len >= minSpeeches)
            return path_len;
        mat = adjMatrixMult(orig_mat, mat, n); /* A^(path_len-1) * A */
    }
    return -1; /* loser */
}


int **getCase(int numC, int numR)
{
    int i, r, c;
    int **mat = sqMatrix(numC);
    for(i=0; i<numR; i++) {
        fscanf(stdin, "%d %d\n", &r, &c);
        mat[r][c] = 1;
    }
    return mat;
}

int main(int argc, const char *argv[])
{
    int numCities, numRoads, minSpeeches = -1;
    int i, j, answer;
    int **adj_mat;

    while(1) {
        fscanf(stdin, "%d %d %d\n", &numCities, &numRoads, &minSpeeches);
        if(numCities == 0)
            break;
        
        adj_mat = getCase(numCities, numRoads);
        answer  = campaign(adj_mat, minSpeeches, numCities);

        if(answer != -1)
            printf("%d\n", answer);
        else
            printf("LOSER\n");
    }
    return 0;
}

