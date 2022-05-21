package english.lessons.inlesson.ui

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import english.lessons.inlesson.ui.models.GamesList
import english.lessons.inlesson.ui.models.User
import english.lessons.inlesson.ui.models.WhoGame

object Case {
    var user: FirebaseUser?= FirebaseAuth.getInstance().currentUser
}