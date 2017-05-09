#指定代码的压缩级别
-optimizationpasses 5

#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#把混淆类中的方法名也混淆了
-useuniqueclassmembernames

#优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification