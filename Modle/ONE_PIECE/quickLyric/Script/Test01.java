package com.geecko.QuickLyric.test;

import com.geecko.QuickLyric.MainActivity;
import com.robotium.solo.Solo;
import com.geecko.QuickLyric.MainActivity;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import java.io.*;
import android.test.ActivityInstrumentationTestCase2;

public class Test01 extends ActivityInstrumentationTestCase2<MainActivity>
{
private Solo solo;

@SuppressLint("NewApi")
public Test0(){
super(MainActivity.class);
}

@Override
public void setUp() throws Exception { 
 solo = new Solo(getInstrumentation(), getActivity()); 
 }

@Override
public void tearDown() throws Exception { 
 solo.finishOpenedActivities(); 
 }

/*------ Test Core Function ------*/
public void testOnClick()
{
// Click-TestAction-In-TestState
solo.clickOnText("");

solo.sleep(1000);

// Assert-Text
boolean test1 = solo.searchText("Search");

boolean test_result;
test_result = test1;
assertTrue("Test: Failed.", test_result);
}
/*--------------------------------*/
}
