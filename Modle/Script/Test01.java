import com.robotium.solo.Solo;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import java.io.*;
import android.test.ActivityInstrumentationTestCase2;

public class ScriptTest extends ActivityInstrumentationTestCase2<MainMenu>
{
private Solo solo;

@SuppressLint("NewApi")
public ScriptTest(){
super(MainMenu.class);
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
solo.clickOnText("总计:28 新卡片:27 复习:1");

// Click-TestAction-In-TestState
solo.clickOnText("?
					显示答案
				");

solo.sleep(1000);

// Assert-Text
boolean test1 = solo.searchText("1.0");

boolean test_result;
test_result = test1;
assertTrue("Test: Failed.", test_result);
}
/*--------------------------------*/
}
