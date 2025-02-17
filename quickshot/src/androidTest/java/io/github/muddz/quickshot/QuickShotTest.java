package io.github.muddz.quickshot;

import static android.view.View.MeasureSpec.EXACTLY;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;


@RunWith(AndroidJUnit4.class)
public class QuickShotTest {

    private Context context;
    private View testView;

    @Before
    public void setup() {
        testView = generateTestView();
        context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getContext();
    }


    @Test
    public void testCallbackPathNotNull() {
        QuickShot.of(testView).setResultListener(new QuickShot.QuickShotListener() {
            @Override
            public void onQuickShotSuccess(String path) {
                Assert.assertNotNull(path);
            }

            @Override
            public void onQuickShotFailed(String path, String errorMsg) {

            }
        }).save();
        sleepThread();
    }


    @Test
    public void testIfSavedInJPG() {
        QuickShot.of(testView).setResultListener(new QuickShot.QuickShotListener() {
            @Override
            public void onQuickShotSuccess(String path) {
                Assert.assertTrue(path.contains(".jpg"));
            }

            @Override
            public void onQuickShotFailed(String path, String errorMsg) {

            }

        }).save();
        sleepThread();
    }

    @Test
    public void testIfSavedInPNG() {
        QuickShot.of(testView).toPNG().setResultListener(new QuickShot.QuickShotListener() {
            @Override
            public void onQuickShotSuccess(String path) {
                Assert.assertTrue(path.contains(".png"));
            }

            @Override
            public void onQuickShotFailed(String path, String errorMsg) {

            }
        }).save();
        sleepThread();
    }

    @Test
    public void testIfSavedInNomedia() {
        QuickShot.of(testView).toNomedia().setResultListener(new QuickShot.QuickShotListener() {
            @Override
            public void onQuickShotSuccess(String path) {
                Assert.assertTrue(path.contains(".nomedia"));
            }

            @Override
            public void onQuickShotFailed(String path, String errorMsg) {

            }
        }).save();
        sleepThread();
    }


    @Test
    public void testIfDirectoryWasCreated() {
        QuickShot.of(testView).setPath("QuickShotTestDirectory").setResultListener(new QuickShot.QuickShotListener() {
            @Override
            public void onQuickShotSuccess(String path) {
                if (QuickShotUtils.isAboveAPI29()) {
                    Assert.assertTrue(path.contains("QuickShotTestDirectory"));
                } else {
                    File file = new File(path);
                    File directory = new File(file.getParent());
                    boolean isDirectory = directory.exists() && directory.isDirectory();
                    Assert.assertTrue(isDirectory);
                }
            }

            @Override
            public void onQuickShotFailed(String path, String errorMsg) {

            }
        }).save();
        sleepThread();
    }


    @Test
    public void testIfFileExist() {
        QuickShot.of(testView).setPath("QuickShotTestDirectory").setResultListener(new QuickShot.QuickShotListener() {
            @Override
            public void onQuickShotSuccess(String path) {
                if (QuickShotUtils.isAboveAPI29()) {
                    Assert.assertTrue(path != null && path.length() > 0);
                } else {
                    File file = new File(path);
                    Assert.assertTrue(file.exists());
                }
            }

            @Override
            public void onQuickShotFailed(String path, String errorMsg) {

            }
        }).save();
        sleepThread();
    }

    private View generateTestView() {
        int width = 950;
        int height = 950;

        int widthMS = View.MeasureSpec.makeMeasureSpec(width, EXACTLY);
        int heightMS = View.MeasureSpec.makeMeasureSpec(height, EXACTLY);

        View view = new View(InstrumentationRegistry.getTargetContext());
        view.measure(widthMS, heightMS);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setBackgroundColor(Color.GRAY);

        return view;
    }

    private void sleepThread() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
