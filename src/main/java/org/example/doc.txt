NOTES:
board size is 10




this counts as an index away
F HHHHHHHHHHHHHHHH C
C HHHHHHHHHHHHHHHH F

DOCS:
Create a program that would simulate the capture of the fugitive, Alejandro, using a boardgame-like system. The boardgame would follow these instructions:

    The entire boardgame would be within a String array containing different emoticons. These emoticons are:
        🏠 - the house, neutral
        🌳 - the trees, hiding spots
        🏃‍♂️ - for Alejandro, the fugitive
        🚓 - the Cops, the one pursuing the fugitive
        💰 - evidence, left by the fugitive
        ⛔ - restriction zone, left by the cops
    The boardgame array will have the initial values:
        Fill the entire array first with houses.
        Place Alejandro at the 6th index.
        Place the cops at the 1st index.
        Spawn a tree at any random location aside the position of the cop and fugitive.
    Print the initial array, creatively, in a text file.
    The fugitive and the cop will roll a dice (1 - 6) at the start of each loop that would allow them to move through the array.
        They can only move to the right.
        If the either player reach the end of the array, proceed moving at the start of the array.
        After moving to another index, the fugitive will leave the evidence emoticon at the index where it was, except if it is a tree emoticon.
        After moving to another index, the cop will leave a restriction zone at the index where it was, except if it is a tree emoticon.
        The cop can capture the fugitive if they are an index away or they land on the same index.
    If the fugitive, Alejandro, land on a tree, he will gain the hidden buff.
        He can move one index to the right and cannot be captured until the next dice roll.
        The tree cannot be replaced with the evidence emoticon.
    If the fugitive lands on a restriction zone, he will be at a disadvantage.
        He can only roll 1 - 3.
        Removed after the dice roll.
    If the cop lands on evidence, he will be able to capture up to 2 indices away from it.
        removed after the next dice roll.
    Every after moving the two players, display the current array.
    Repeat this process 5 times. If the cop fails to capture Alejandro within 5 turns (movement or dice roll), the fugitive escapes, end the print with "Alejandro escaped". Otherwise, if within 5 turns the cop captures Alejandro, stop playing and print at the end of the text file "Alejandro was captured. Success."