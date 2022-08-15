package org.techtown.review_diet

/*데이터 모델을 만들 때, kotlin 파일로 만들고, class 앞에 data를 붙여준다.*/
data class DataModel(
    //여기에 우리가 가진 데이터를 적는다.
    val date : String= "",//날짜 데이터
    val memo : String= "" //메모 데이터
)