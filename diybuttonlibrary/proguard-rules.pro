# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in H:\AndroidSDK/tools/proguard/proguard-android.txt
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
-optimizationpasses 5                                                           # 指定代码的压缩级别
#-dontusemixedcaseclassnames                                                     # 是否使用大小写混合
#-dontskipnonpubliclibraryclasses                                                # 是否混淆第三方jar
#-dontpreverify                                                                  # 混淆时是否做预校验
#-verbose                                                                        # 混淆时是否记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*        # 混淆时所采用的算法

#保持自定义组件不被混淆
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.widget.LinearLayout {                        # 保持自定义控件类不被混淆
   public void *(android.view.View);
}

