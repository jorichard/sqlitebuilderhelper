# sqlitebuilderhelper
Simple library to ease the creation of sqlite tables and queries.

##Usage

To use the library you will need add the jitpack.io repository:

allprojects {  
&nbsp;&nbsp;repositories {  
    &nbsp;&nbsp;&nbsp;&nbsp;jcenter()  
&nbsp;&nbsp;&nbsp;&nbsp;maven { url "https://jitpack.io" }  
&nbsp;&nbsp;}  
}   
and:  
  
dependencies {  
&nbsp;&nbsp;compile 'com.github.jorichard:sqlitebuilderhelper:0.1.0'  
}
  
Note: do not add the jitpack.io repository under buildscript  
