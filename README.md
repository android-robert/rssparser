# rssparser
To import 'Parse a RSS Feed' lib into your build:

#Step 1. Add the JitPack repository to your build file
```groovy
allprojects {

.....

  maven { url ‘https://jitpack.io’ }

.....

}

#Step 2. Add the dependency

``` groovy
dependencies {
  implementation 'com.github.ios-robert:rssparser:1.0'
  .....
}
```
#Step 3. Reload Grade Project and Enjoy!
