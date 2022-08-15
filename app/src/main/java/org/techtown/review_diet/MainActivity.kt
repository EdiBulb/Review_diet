package org.techtown.review_diet

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {

    //데이터 모델 리스트를 만들어준다.
    val dataModelList = mutableListOf<DataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*데이터 베이스에서 데이터를 찾아오는 것을 해보겠다.*/
        //다시 이 코드들을 가져온다.
        val database = Firebase.database
        val myRef = database.getReference("myMemo")

        //리스트 뷰를 가져온다.
        val listView = findViewById<ListView>(R.id.mainLV)
        //이제 어댑터를 만들어준다. -> ListViewAdapter.kt

        //어댑터 만들고 난뒤, 어댑터랑 연결해준다.
        val adapter_list = ListViewAdapter(dataModelList)


        //main에서 어댑터랑 연결해준다.
        listView.adapter = adapter_list


        //myRef에 있는 데이터들을 찾아온다.
        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                dataModelList.clear()

                //snapshot 에 있는 데이터모델을 반복문을 통해서 꺼내온다.
                for(dataModel in snapshot.children){
                    Log.d("Data",dataModel.toString())

                    //데이터가 모델의 형태로 dataModelList 에 잘 들어온다.
                    dataModelList.add(dataModel.getValue(DataModel::class.java)!!)
                }
                adapter_list.notifyDataSetChanged()
                Log.d("DataModel",dataModelList.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        /*순서
        * 1. 스플래시 화면을 만들어서 회원인지 아닌지 판단하게 만든다.
        * 2. 파이어베이스 비회원 인증을 해준다.*/

        /*activity_main.xml에 있는 id : writeBtn을 이미지 뷰에서 찾아온다.*/
        val writeButton = findViewById<ImageView>(R.id.writeBtn)
        writeButton.setOnClickListener {
            /*누르면 다이얼로그를 띄우게 할 것이다.*/
            /*custom_dialog.xml을 만들어서 다이얼로그를 우선 꾸며주겠다.*/

            /*꾸며주고 난 뒤, 이제 다이얼로그를 띄워보겠다.*/
            //배운 점 : 다이얼로그 관련해서 검색하면 많이 나오니까, 찾아보자
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("운동 메모 다이얼로그")

            //질문 : mBuilder.show()가 값으로 있어도 동작하는가?
            val mAlertDialog = mBuilder.show()

            val DateSelectBtn = mAlertDialog.findViewById<Button>(R.id.dateSelectBtn)

            //날짜를 담는 변수로 사용 예정
            var dateText = ""

            /*다이얼로그가 뜨고, 버튼을 눌렀을 떄의 기능을 만들겠다.*/
            //버튼이 울리면 날짜를 선택할 수 있는 다이얼로그를 불러온다.
            //즉, 처음 다이얼로그를 띄우고, 버튼을 누르면 또 다이얼로그를 띄우는 형식이다.
            DateSelectBtn?.setOnClickListener {
                //배운점 : GregorianCalendar 를 가져온다.
                val today = GregorianCalendar()

                val year : Int = today.get(Calendar.YEAR)
                val month : Int = today.get(Calendar.MONTH)
                val date : Int = today.get(Calendar.DATE)

                val dlg = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener{
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        /*여기에 날짜를 선택한 뒤, setText를 통해 버튼의 텍스트를 바꾸는 이벤트를 만들어준다.*/
                        //월은 +1을 해야한다.
                        //배운점 : setText를 통해 기존에 있는 text값을 변경할 수 있다.
                        DateSelectBtn.setText("${year},${month+1},${dayOfMonth}")

                        //날짜가 눌리면 여기다 저장한다.(사용 예정)
                        dateText = "${year},${month+1},${dayOfMonth}"
                    }

                },year,month,date)
                //dlg를 실행한다.
                dlg.show()

            }
            /*이제 저장하기 버튼을 누르면, 데이터 베이스에 저장하고 불러오는 것을 해보겠다. -> 내가 가장 궁금해하던 것!!!!*/
            //왜 여기다 작성하는가? -> writeButton을 눌린 후에, 저장하기 버튼을 누르는 다이얼로그가 뜨는 것이므로 괄호 안에 넣어주는 것이다.


            val saveBtn = mAlertDialog.findViewById<Button>(R.id.saveBtn)
            saveBtn?.setOnClickListener {

                //저장 버튼을 누르면, editText의 text값을 가져온다.
                //배운 것 : editText의 text값을 text.toString()을 쓰면 가져올 수 있다.
                val healMemo = mAlertDialog.findViewById<EditText>(R.id.healthMemo)?.text.toString()

                //저장하기 버튼을 누르면, 데이터베이스에 저장하겠다.

                /*파이어베이스에 가서 realtime database를 생성한다.*/
                /*구글에 realtime database 어떻게 사용하는지 검색하고 코드를 추가한다.*/
                //그래들 가서 코드를 추가한다.

                //데이터 베이스 사용하는 코드 추가한다.
                val database = Firebase.database
                val myRef = database.getReference("myMemo")
                /*우리가 넣고 싶은 데이터가 뭐지?
                * 1. 날짜 데이터
                * 2. 메모 데이터
                * 즉, 데이터가 2개이다. 그래서 데이터를 담는 그릇을 만든다. 즉, 데이터 모델을 만든다. -> DataModel.java 파일 만든다.*/

                /*이제 데이터를 push 할 것인데, 데이터 모델을 가져와서 push할 것이다..*/
                val model = DataModel(dateText,healMemo)

                myRef
                    .push()
                    .setValue(model)
                //저장 버튼을 누르면 다이얼로그가 닫히게 한다.
                mAlertDialog.dismiss()

                /*여기까지 데이터 베이스에 넣는 것을 해봤다. 이제 데이터 베이스에서 불러오는 것을 해보겠다.!!!*/



            }





        }

    }
}