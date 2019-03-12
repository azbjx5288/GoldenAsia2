# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\User\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#butterknife库的：
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# Gson specific classes
-keep class com.google.gson.** { *; }
-keep interface com.google.gson.** { *; }
-dontwarn com.google.gson.**
-keep class sun.misc.Unsafe { *; }

-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-dontwarn android.support.**

-keep class com.android.volley.** { *; }
-keep interface com.android.volley.** { *; }
-dontwarn com.android.volley.**

-keep class com.nostra13.universalimageloader.** { *; }
-keep interface com.nostra13.universalimageloader.** { *; }
-dontwarn com.nostra13.universalimageloader.**

-keep class org.jsoup.** { *; }
-keep interface org.jsoup.** { *; }
-dontwarn org.jsoup.**

-keepattributes *Annotation*
-keepattributes *JavascriptInterface*

# 玩法相关
-keep class * extends com.goldenasia.lottery.game.Game {*;}
-keep class * extends com.goldenasia.lottery.game.LhcGame {*;}

# 网络处理相关类
-keep class com.goldenasia.lottery.base.net.** {*;}

# 网络接口对应的数据类
-keep class com.goldenasia.lottery.data.** { *; }

#给WebView的js接口
-keep class **.**$JsInterface {*;}

-dontwarn java.lang.invoke.*

-dontwarn com.google.zxing.**
-keep class com.google.zxing.** {*;}
-dontwarn in.srain.**
-keep class in.srain.** {*;}
-dontwarn com.itrus.**
-keep class com.itrus.** {*;}
-dontwarn org.bouncycastle.**
-keep class org.bouncycastle.** {*;}

#Webview
-dontwarn org.apache.cordova.**
-keep class org.apache.cordova.** {*;}

#ormlite混淆配置
#-libraryjars libs/ormlite-android-5.0.jar
#-libraryjars libs/ormlite-core-5.0.jar

-dontwarn com.j256.ormlite.**

-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }
-keep class com.goldenasia.lottery.db.** { *; }

#友盟推送混淆配置
-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn org.apache.thrift.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**

-keepattributes *Annotation*

-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class org.apache.thrift.** {*;}

-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}

-keep public class **.R$*{
   public static final int *;
}

#更新
-dontwarn util.**
-keep class util.**{*;}

#（可选）避免Log打印输出
-assumenosideeffects class android.util.Log {
   public static *** v(...);
   public static *** d(...);
   public static *** i(...);
   public static *** w(...);
 }