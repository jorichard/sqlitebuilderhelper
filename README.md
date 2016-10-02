# sqlitebuilderhelper
Simple library to ease the creation of sqlite tables and queries.

##Usage

To use the library you will need add the jitpack.io repository:

allprojects {
 repositories {
    jcenter()
    maven { url "https://jitpack.io" }
 }
}
and:

dependencies {
    compile 'com.github.jorichard:sqlitebuilderhelper:0.1.0'
}
Note: do not add the jitpack.io repository under buildscript
