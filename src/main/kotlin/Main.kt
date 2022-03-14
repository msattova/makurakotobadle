
import java.security.SecureRandom

enum class Valid {
    GRAY, YELLOW, GREEN
}

enum class ANSIColor(val escapeSequence: String) {
    GREEN("\u001b[32;1m"),
    YELLOW("\u001b[33;1m"),
    RESET("\u001b[0m")
}

fun wordCheck(word: String, answer: String): Array<Valid>{
    val checkArray: Array<Valid> = Array(word.length){Valid.GRAY}
    for (i in word.indices){
        for (j in word.indices){
            if (word[i] == answer[j]){
                if (i == j){
                    checkArray[i] = Valid.GREEN
                }else{
                    checkArray[i] = Valid.YELLOW
                }
            }
        }
    }
    return checkArray
}

fun putColor(word: String, checkList: List<Valid>): String{
    var coloredString = ""
    for ((index, ele) in checkList.withIndex()){
        coloredString += when(ele){
            Valid.GREEN -> ANSIColor.GREEN.escapeSequence+word[index]+ANSIColor.RESET.escapeSequence
            Valid.YELLOW -> ANSIColor.YELLOW.escapeSequence+word[index]+ANSIColor.RESET.escapeSequence
            Valid.GRAY -> word[index]
        }
    }
    return coloredString
}

fun main() {
    val canTry = 7
    var cleared = false
    val answer = makurakotoba[SecureRandom().nextInt(makurakotoba.size)]
    var checkArray: Array<Valid>
    val message = "${answer.length}文字の枕詞を「ひらがな」で入力してください。"
    println(message)
    println("${canTry}回までトライできます。")
    for ( i in 0 until canTry ){
        print("> ")
        var word = readLine()
        while (word.isNullOrEmpty() || word.length != answer.length || (!makurakotoba.contains(word))){
            when{
                word.isNullOrEmpty() -> {
                    println(message)
                }
                word.length != answer.length -> {
                    println("${answer.length}文字の枕詞を入力してください。")
                }
                !makurakotoba.contains(word) -> {
                    println("入力されたことばが本プログラムの辞書にありません。")
                }
            }
            print("> ")
            word = readLine()
        }
        checkArray = wordCheck(word, answer)
        val colored = putColor(word, checkArray.toList())
        println(colored)
        if (word.equals(answer, ignoreCase = true)){
            cleared = true
            break
        }
    }
    if (cleared){
        println("おめでとうございます！")
    }else{
        println("こたえはこれでした：　$answer")
    }

}