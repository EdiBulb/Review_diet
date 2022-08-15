package org.techtown.review_diet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {

    /*익명 로그인 할 때 쓰는 코드 - */
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        /*익명 로그인 할 때 쓰는 코드*/
        /*auth가 import가 안돼서 여기서 좀 헤맸다. 파이어베이스 코드 복사할 때, 복사하는 코드가 안 맞을 수도 있다.*/
        auth = Firebase.auth

        try{/*이것을 실행하다가 null 에러가 뜬다면 try 구문을 실행한다.*/

            /*사용자 로그인 정보 있니??? 라고 물어본다.*/
            Log.d("SPLASH",auth.currentUser!!.uid)

            Toast.makeText(this, "원래 비회원 로그인이 되어있는 사람입니다.", Toast.LENGTH_LONG).show()

            /*try가 무사히 실행되어도 화면을 전환해준다.*/
            Handler().postDelayed({
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            },3000)

        /*안되어있으면 회원가입을 시켜준다.*/
        }catch (e:Exception){
            Log.d("SPLASH","회원가입 시켜줘야함")

            /*익명 로그인 할 떄 쓰는 코드*/
            /*즉, null에러가 뜨면 익명 로그인을 해야한다.*/

            //회원가입을 시켜준다.
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(this, "비회원 로그인 성공", Toast.LENGTH_LONG).show()

                        /*로그인이 성공하면 스플래시를 통해서 화면을 이동시켜준다.*/
                        /*스플래시는 어떻게 만들었지?
                        * 이렇게 만들었다. 그리고 매니페스트 가서 intent-filter 위치 바꿔줬다.*/
                        Handler().postDelayed({
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        },3000)

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "비회원 로그인 실패", Toast.LENGTH_LONG).show()
                        
                    }
                }
        }
        /*스플래시 화면에서 이 사람이 익명인지 아닌지 확인을 해줄 것이다.*/
    }
}