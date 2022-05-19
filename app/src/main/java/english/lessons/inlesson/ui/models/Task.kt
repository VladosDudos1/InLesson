package english.lessons.inlesson.ui.models

data class Task(
    val answer: Answer,
    val problem: String,
    val words: List<String>
)