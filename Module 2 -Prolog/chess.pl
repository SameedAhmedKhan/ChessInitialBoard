
random(L, U, R):-
   integer(L), integer(U),
   !,
   R is L+random(U-L).

list_delete(R, [Y|L2], [Y|L1]) :-
   list_delete(R,L2,L1).

len([X|Y], LenResult):-
    len(Y, L),
    LenResult is L + 1.

concat([E|L1],L2,[E|C]):-
        con(L1,L2,C).



generateBoard(Board):-
	emptyEven=[2,4,6,8],
	emptyOdd=[1,3,5,7].
	random(2,7,king).
	list_delete(king,emptyEven).
	list_delete(king,emptyOdd).
	random(1,R-1,rook1).
	random(R+1,8,rook2).
        list_delete(rook1,emptyEven).
	list_delete(rook2,emptyEven).
        list_delete(rook1,emptyOdd).
        list_delete(rook2,emptyOdd).
        len(emptyEven,lenB1).
        random(1,lenB1,bishop1).
        len(emptyOdd,lenB2).
        random(1,lenB2,bishop2).
	list_delete(bishop1,emptyEven).
	list_delete(bishop2,emptyOdd).
        concat(emptyEven,emptyOdd,allList).
        len(allList,A).
	random(1,A,queen1).
        list_delete(allList,queen1).
        allList=[X|[Y|T]],
	 knight1 = X,
	 knight2 = Y,



initial(piece(white, rook,   1, 1)).
initial(piece(white, knight, 2, 1)).
initial(piece(white, bishop, 3, 1)).
initial(piece(white, queen,  4, 1)).
initial(piece(white, king,   5, 1)).
initial(piece(white, bishop, 6, 1)).
initial(piece(white, knight, 7, 1)).
initial(piece(white, rook,   8, 1)).
initial(piece(white, pawn,   X, 2)) :-
    between(1, 8, X).

initial(piece(black, rook,   1, 8)).
initial(piece(black, knight, 2, 8)).
initial(piece(black, bishop, 3, 8)).
initial(piece(black, queen,  4, 8)).
initial(piece(black, king,   5, 8)).
initial(piece(black, bishop, 6, 8)).
initial(piece(black, knight, 7, 8)).
initial(piece(black, rook,   8, 8)).
initial(piece(black, pawn,   X, 7)) :-
    between(1, 8, X).


initial_board(Board) :-
    findall(Piece, initial(Piece), Board).





































