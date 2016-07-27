package test.test;

import test.SummationList;
import com.robotium.solo.Solo;
import test.SummationList;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import java.io.*;
import android.test.ActivityInstrumentationTestCase2;

public class Test01 extends ActivityInstrumentationTestCase2<SummationList>
{
private Solo solo;

@SuppressLint("NewApi")
public Test0(){
super(SummationList.class);
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

// EnterText-TestAction-In-TestState
solo.enterText(0,"test");

// EnterText-TestAction-In-TestState
solo.enterText(1,"6");

solo.sleep(1000);

// Assert-Text
boolean test1 = solo.searchText("9.99");

boolean test_result;
test_result = test1;
assertTrue("Test: Failed.", test_result);
}
/*--------------------------------*/
}
