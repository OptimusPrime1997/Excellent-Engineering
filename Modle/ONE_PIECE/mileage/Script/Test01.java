package java.lang.test;

import java.lang.mainmenu;
import com.robotium.solo.Solo;
import java.lang.mainmenu;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import java.io.*;
import android.test.ActivityInstrumentationTestCase2;

public class Test01 extends ActivityInstrumentationTestCase2<mainmenu>
{
private Solo solo;

@SuppressLint("NewApi")
public Test0(){
super(mainmenu.class);
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
// EnterText-TestAction-In-TestState
solo.enterText(0,"1");

// EnterText-TestAction-In-TestState
solo.enterText(1,"1");

// EnterText-TestAction-In-TestState
solo.enterText(0,"1");

// Click-TestAction-In-TestState
solo.clickOnButton("Save Fillup");

solo.sleep(1000);

// Assert-Text
boolean test1 = solo.searchText("1.00");

boolean test_result;
test_result = test1;
assertTrue("Test: Failed.", test_result);
}
/*--------------------------------*/
}
