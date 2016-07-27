package com.github.wdkapps.fillup.test;

import com.github.wdkapps.fillup.StartupActivity;
import com.robotium.solo.Solo;
import com.github.wdkapps.fillup.StartupActivity;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import java.io.*;
import android.test.ActivityInstrumentationTestCase2;

public class Test01 extends ActivityInstrumentationTestCase2<StartupActivity>
{
private Solo solo;

@SuppressLint("NewApi")
public Test0(){
super(StartupActivity.class);
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
solo.clickOnImageButton("2");

// EnterText-TestAction-In-TestState
solo.enterText(1,"ad");

// Click-TestAction-In-TestState
solo.clickOnButton("OK");

solo.sleep(1000);

// Assert-Text
boolean test1 = solo.searchText("ad");

boolean test_result;
test_result = test1;
assertTrue("Test: Failed.", test_result);
}
/*--------------------------------*/
}
