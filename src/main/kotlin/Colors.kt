


enum class Valid{
    GRAY, DELETE, YELLOW, GREEN;
    fun toANSIColor(): ANSIColor{
        return when(this){
            GRAY -> ANSIColor.DEFAULT
            DELETE -> ANSIColor.BLACK
            YELLOW -> ANSIColor.YELLOW
            GREEN -> ANSIColor.GREEN
        }
    }
    fun<T> coloredLetter(word: T): String{
        return "${this.toANSIColor().escapeSequence}${word}${ANSIColor.RESET.escapeSequence}"
    }

    operator fun Valid.compareTo(c: Valid): Int {
        return when {
            this == c -> 0
            this == GREEN -> 1
            this == GRAY -> -1
            this == YELLOW && c == GREEN -> -1
            this == YELLOW && c == GRAY -> 1
            else -> -1
        }
    }
}

enum class ANSIColor(val escapeSequence: String) {
    GREEN("\u001b[32;1m"),
    YELLOW("\u001b[33;1m"),
    BLACK("\u001b[30;1m"),
    DEFAULT(""),
    RESET("\u001b[0m")
}

