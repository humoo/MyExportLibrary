-keep class com.humo.hradapter.HRListener{
    public *;
}

-dontwarn com.humo.hradapter.**
#第三方(picasso)
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.picasso.*
