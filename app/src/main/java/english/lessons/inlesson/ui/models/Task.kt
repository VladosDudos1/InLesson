package english.lessons.inlesson.ui.models

data class Task(
    val answer: Answer,
    val problem: String,
    var question: String,
    val words: List<String>
)