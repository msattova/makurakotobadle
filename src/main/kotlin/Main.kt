
import java.security.SecureRandom

fun wordCheck(word: String, answer: String): Array<Valid>{
    val checkArray: Array<Valid> = Array(word.length){Valid.GRAY}
    for (i in word.indices){
        for (j in answer.indices){
            if (Hiragana(word[i].toString()).isSame(answer[j].toString())){
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
            Valid.GRAY -> word[index]
            Valid.GREEN, Valid.YELLOW, Valid.DELETE -> ele.coloredLetter(word[index])
        }
    }
    return coloredString
}

fun main() {
    val goju = Gojuuon()
    val canTry = 8
    var cleared = false
    val answer = makurakotoba[SecureRandom().nextInt(makurakotoba.size)]
    var checkArray: Array<Valid>
    val systemExplain = """
        |正しい位置に正しい文字がある場合：${Valid.GREEN.coloredLetter("みどり")}
        |位置は違うが正解に含まれる文字がある場合：${Valid.YELLOW.coloredLetter("きいろ")}
        |で文字が表示されます。
        |ただし、清音と濁音と半濁音は区別されません。
        |例えば、正解が「ゆふづくよ」、回答が「こまつるぎ」の場合：
        |${putColor("こまつるぎ", wordCheck("こまつるぎ", "ゆふづくよ").toList())}
    """.trimMargin()
    val message = "${answer.length}文字の枕詞を「ひらがな」で入力してください。"
    println("---\n${systemExplain}\n---")
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
        }else{
            print("\n")
            goju.updateColor(word, checkArray.toList())
            goju.showGojuuon()
        }
    }
    print("\n")
    if (cleared){
        println("おめでとうございます！")
    }else{
        println("こたえはこれでした：　$answer")
    }

}