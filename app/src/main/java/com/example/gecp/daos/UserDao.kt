package com.example.gecp.daos

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDao {
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")
    private val usersCollection2 = db.collection("role")
    fun addUser(user: com.example.gecp.models.User?) {
        user?.let {
            GlobalScope.launch(Dispatchers.IO) {
                usersCollection.document(user.uid).set(it)
            }
        }
        fun addRole(user: com.example.gecp.models.User?) {
            user?.let {
                GlobalScope.launch(Dispatchers.IO) {
                    usersCollection2.document(user.role).set(it)
                }
            }
        }
    }
    fun getUserById(uId: String): Task<DocumentSnapshot> {
        return usersCollection.document(uId).get()
    }
    fun getRoleByrole(role: String): Task<DocumentSnapshot> {
        return usersCollection2.document(role).get()
    }


}