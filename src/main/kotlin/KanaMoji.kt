
enum class SeiDaku{
    SEI, DAKU, HANDAKU, UNDEFINED
}

class Gojuuon {
    private var colorTable = mutableMapOf<Char, Valid>()
    val initials = listOf(
        'あ',
        'か',
        'さ',
        'た',
        'な',
        'は',
        'ま',
        'や',
        'ら',
        'わ')
    val seiTable = mapOf(
        'あ' to "あ い う え お".split(" "),
        'か' to "か き く け こ".split(" "),
        'さ' to "さ し す せ そ".split(" "),
        'た' to "た ち つ て と".split(" "),
        'な' to "な に ぬ ね の".split(" "),
        'は' to "は ひ ふ へ ほ".split(" "),
        'ま' to "ま み む め も".split(" "),
        'や' to "や ゐ ゆ ゑ よ".split(" "),
        'ら' to "ら り る れ ろ".split(" "),
        'わ' to "わ を ん".split(" "),
    )
    val dakuTable = mapOf(
        'か' to "が ぎ ぐ げ ご".split(" "),
        'さ' to "ざ じ ず ぜ ぞ".split(" "),
        'た' to "だ ぢ づ で ど".split(" "),
        'は' to "ば び ぶ べ ぼ".split(" ")
    )
    val handakuTable = mapOf(
        'は' to "ぱ ぴ ぷ ぺ ぽ".split(" ")
    )
    init{
        for (i in this.initials){
            this.seiTable[i]?.let{
                for(j in it){
                    this.colorTable[j.toCharArray()[0]] = Valid.GRAY
                }
            }
        }
    }
    private fun changeColorTable(letter: Hiragana, iter: Iterable<String>, color: Valid){
        for(j in iter){
            if (letter.isSame(j) && (this.colorTable[j.toCharArray()[0]] ?: Valid.GRAY) < color){
                this.colorTable[j.toCharArray()[0]] = color
            }
        }
    }
    private fun setColor(letter: Hiragana, color: Valid){
        for (i in this.initials){
            this.seiTable[i]?.let{
                changeColorTable(letter, it, color)
            }
            this.dakuTable[i]?.let{
                changeColorTable(letter, it, color)
            }
            this.handakuTable[i]?.let{
                changeColorTable(letter, it, color)
            }
        }
    }
    fun updateColor(word: String, checkList: List<Valid>){
        for ((i, c) in word.withIndex()){
            setColor(Hiragana(c.toString()), checkList.map {
                when(it){
                    Valid.GRAY -> Valid.DELETE
                    else -> it
                }
            }[i])
        }
    }

    private fun printColumn(iter: Iterable<String>){
        for (j in iter){
            val colorCode = this.colorTable[j.toCharArray()[0]] ?: Valid.GRAY
            print(colorCode.coloredLetter(j.toCharArray()[0]))
        }
    }

    fun showGojuuon(){
        for (i in this.initials){
            this.seiTable[i]?.let{
                printColumn(it)
            }
            this.dakuTable[i]?.let{
                print("\t\t")
                printColumn(it)
            }
            this.handakuTable[i]?.let{
                print("\t\t")
                printColumn(it)
            }
            print("\n")
        }
    }
}

class Hiragana(private val letter: String){
    private val goju = Gojuuon()
    private fun getSeiDakuType(word: String = this.letter): SeiDaku{
        return when{
            this.goju.initials.any {
                this.goju.seiTable[it]?.contains(word)==true
            } -> SeiDaku.SEI
            this.goju.initials.any {
                this.goju.dakuTable[it]?.contains(word)==true
            } -> SeiDaku.DAKU
            this.goju.initials.any {
                this.goju.handakuTable[it]?.contains(word)==true
            } -> SeiDaku.HANDAKU
            else -> SeiDaku.UNDEFINED
        }
    }
    private fun getInitial(word: String): String{
        var initialStr = ""
        val table = this.getTable(word) ?: return initialStr
        for (i in this.goju.initials){
            initialStr = when(table[i]?.contains(word)){
                true -> i.toString()
                else -> ""
            }
            if (initialStr!=""){
                break
            }
        }
        return initialStr
    }
    private fun getTable(word: String): Map<Char, List<String>>?{
        return when(this.getSeiDakuType(word)){
            SeiDaku.SEI -> this.goju.seiTable
            SeiDaku.DAKU -> this.goju.dakuTable
            SeiDaku.HANDAKU -> this.goju.handakuTable
            SeiDaku.UNDEFINED -> null
        }
    }
    private fun getPlace(word: String): Int?{
        val table: Map<Char, List<String>> = this.getTable(word) ?: return null
        val key = this.getInitial(word).toCharArray()[0]
        if (table[key] == null) return null
        val looper = table[key]?.withIndex() ?: return null
        var counter: Int = -5
        for ((i, ele) in looper){
            if (word == ele){
                counter = i
                break
            }
        }
        return counter
    }
    // 濁音・半濁音・清音を区別しない
    fun isSame(answer: String): Boolean{
        if(this.getInitial(this.letter) == this.getInitial(answer)){
            this.getPlace(this.letter)?.let{
                if(it == (this.getPlace(answer) ?: -1)){
                    return true
                }
            }
        }
        return false
    }

}
