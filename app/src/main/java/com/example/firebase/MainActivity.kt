package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.firebase.model.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.PhantomReference

class MainActivity : AppCompatActivity() {

    private  lateinit var dbReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase

    private  var userId:String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseDatabase = FirebaseDatabase.getInstance()
        dbReference = firebaseDatabase.getReference("user")
        userId = dbReference.push().key.toString()
       btnsave.setOnClickListener(){
            creatUser(txtname.text.toString(),txtnumber.text.toString())
           Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()
            txtname.text.clear()
            txtnumber.text.clear()

        }
       // btnsave.setOnClickListener {
//            if(TextUtils.isEmpty(userId)){
//                creatUser("James","5666")
//            }else{
//                UpdateUser((txtname.text.toString()),
//                    txtnumber.text.toString())
//            }
     //   }


    }
    fun creatUser(name:String,mobile:String){
        val user = User(name, mobile)
      //  userId = dbReference.push().key.toString()
        dbReference.child(userId).setValue(user)
    }
    fun UpdateUser(name: String,mobile: String){
        if (!TextUtils.isEmpty(name)){
            dbReference.child(userId).child(name).setValue(name)
        }
        if (!TextUtils.isEmpty(mobile)){
            dbReference.child(userId).child(mobile).setValue(mobile)

        }
        dbReference.child(userId).addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                var user = snapshot.getValue(User::class.java)
                txtname.setText(user?.name)
                txtnumber.setText(user?.mobile)
            }
        })
    }
}
