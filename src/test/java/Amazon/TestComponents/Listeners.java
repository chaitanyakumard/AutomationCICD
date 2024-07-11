package Amazon.TestComponents;

import java.io.IOException;

import javax.naming.ldap.ExtendedRequest;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Amazon.resoureces.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {
    ExtentTest test;
    
    ExtentReports extent = ExtentReporterNG.getReportObject();  
    
//    Amazon.resoureces.ExtentReporterNG extent = Amazon.resoureces.ExtentReporterNG.getReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); // Thread safe

    @Override
    public void onTestStart(ITestResult result) {
    	test =extent.createTest(result.getMethod().getMethodName());
//        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());

        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        
        String filePath = null;
        try {
            filePath = getScreenshot(result.getMethod().getMethodName(), driver);
        } catch (IOException e) {
            e.printStackTrace();
        }
        extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Implementation can be added here if needed
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Implementation can be added here if needed
    }

    @Override
    public void onStart(ITestContext context) {
        // Implementation can be added here if needed
    }

    @Override
    public void onFinish(ITestContext context) {
//        extent.flush();
    }
}
