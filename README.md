 

# ChatKeyboard

[![](https://github.com/yangchaojiang/ChatKeyboard-master) 

A powerful and easy using keyboard lib includes emoticons, audio recording, multi media keyboard, etc.
 

###Import

use maven to import dependency in gradle

```
dependencies {
    compile 'com.github.CPPAlien:ChatKeyboard:1.0.1'
}
```


dependency
```
 compile 'com.android.support:appcompat-v7:23.2.0'
```

###How to use

1, Use ChatKeyboardLayout make your layout contains keyboard.
 
```
FragmentTransaction sss = getSupportFragmentManager().beginTransaction();
        keyboardFragment = new KeyboardFragment();
        sss.add(R.id.sssssssss, keyboardFragment);
        sss.commit();
        keyboardFragment.setKeyBoardLister(this);
```
perhaps
```
<com.yang.keyboard.ChatKeyboardLayout
        android:id="@+id/kv_bar"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:sendBtnBg="@drawable/send_button_bg">
        ...your layout
</com.yang.keyboard.ChatKeyboardLayout>
```

**Notice: ChatKeyboardLayout can only include one child.**
2, custom your emoticon and stick keyboard
```java
if ( !ChatKeyboardLayout.isEmoticonInitSuccess(this) ) {
	List<EmoticonEntity> entities = new ArrayList<>();
	entities.add(new EmoticonEntity("emoticons/xhs", EmoticonBase.Scheme.ASSETS));
	entities.add(new EmoticonEntity("emoticons/tusiji", EmoticonBase.Scheme.ASSETS));
	ChatKeyboardLayout.initEmoticonsDB(this, true, entities);
}
```
**Notice: Add the code above before the ChatKeyboardLayout used, better in onCreate of Application**

other usage
please refer to the demo code

Thanks for [xhsEmoticonsKeyboard](https://github.com/w446108264/XhsEmoticonsKeyboard) powered by w446108264.
Thanks to Chris for ideas   [![](https://github.com/CPPAlien)  email creaspan@gmail.com
