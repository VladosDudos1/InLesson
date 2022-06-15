package english.lessons.inlesson.ui

import com.afollestad.materialdialogs.MaterialDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import java.util.*

object Case {
    var user: FirebaseUser?= FirebaseAuth.getInstance().currentUser
    var gameType: Int?=null

    var correctAnswers = 0
}