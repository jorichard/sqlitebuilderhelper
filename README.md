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

# License
Copyright (c) 2016-present, sqlitebuilderhelper Contributors.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
