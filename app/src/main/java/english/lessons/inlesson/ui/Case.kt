package english.lessons.inlesson.ui

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import english.lessons.inlesson.ui.models.GameList
import english.lessons.inlesson.ui.models.WhoGame

object Case {
    var chooseList: GameList?=null
    var whoList: WhoGame?=null
    var user: FirebaseUser?= FirebaseAuth.getInstance().currentUser
    var gameType: Int?=null

    var correctAnswers = 0
}