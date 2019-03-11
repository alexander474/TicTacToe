package no.ab.tictactoev02.Board

class BotOLD{

    fun drawNewField(player1Fields: List<Int>, player2Fields: List<Int>): Int{
        var tries = 0;
        var randNum = getRandomNumber(player1Fields, player2Fields, tries)
        val checkedFields = player1Fields+player2Fields
        while(checkedFields.contains(randNum)){
            randNum = getRandomNumber(player1Fields, player2Fields, tries)
            tries++
        }
        return randNum
    }

    fun getRandomNumber(player1Fields: List<Int>, player2Fields: List<Int>, tries: Int): Int{
        if(tries <= 30) {
            val choice1 = goodChoices(player1Fields)
            val choice2 = goodChoices(player2Fields)
            if(choice1 != -1 && choice2 != -1 && choice1==choice2) return choice1
            else if(choice1 != -1) return choice1
            else if(choice2 != -1) return choice2
        }
        return (1..9).random()
    }

    fun goodChoices(fields: List<Int>): Int{
        if (fields.contains(1) && fields.contains(2)) return 3
        else if (fields.contains(2) && fields.contains(3)) return 1
        else if (fields.contains(1) && fields.contains(3)) return 2
        else if (fields.contains(4) && fields.contains(5)) return 6
        else if (fields.contains(5) && fields.contains(6)) return 4
        else if (fields.contains(4) && fields.contains(6)) return 5
        else if (fields.contains(7) && fields.contains(8)) return 9
        else if (fields.contains(8) && fields.contains(9)) return 7
        else if (fields.contains(7) && fields.contains(9)) return 8
        else if (fields.contains(1) && fields.contains(9)) return 5
        else if (fields.contains(5) && fields.contains(9)) return 1
        else if (fields.contains(1) && fields.contains(5)) return 9
        else if (fields.contains(3) && fields.contains(5)) return 7
        else if (fields.contains(3) && fields.contains(7)) return 5
        else if (fields.contains(5) && fields.contains(7)) return 3
        else if (fields.contains(1)) return 2
        else if (fields.contains(2)) return 3
        else if (fields.contains(3)) return 4
        else if (fields.contains(5)) return 6
        else if (fields.contains(6)) return 7
        else if (fields.contains(7)) return 8
        else if (fields.contains(8)) return 9
        else if (fields.contains(9)) return 8
        else if (fields.contains(8)) return 7
        else if (fields.contains(7)) return 6
        else if (fields.contains(6)) return 5
        else if (fields.contains(5)) return 4
        else if (fields.contains(4)) return 3
        else if (fields.contains(3)) return 2
        else if (fields.contains(2)) return 1
        else if (fields.contains(1)) return 5
        else if (fields.contains(5)) return 9
        else if (fields.contains(9)) return 5
        else if (fields.contains(5)) return 1
        else if (fields.contains(3)) return 5
        else if (fields.contains(5)) return 7
        else if (fields.contains(7)) return 5
        else if (fields.contains(5)) return 3
        return -1
    }

}