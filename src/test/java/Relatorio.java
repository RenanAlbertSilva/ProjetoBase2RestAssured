import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Relatorio {
    private static ExtentReports extent;
    private static ExtentTest logger;

    public static ExtentReports getInstance() {
        if (extent == null) {
            extent = new ExtentReports();
            ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("Relatorio Evidencia Testes API.html");
            extent.attachReporter(htmlReporter);
        }
        return extent;
    }

    public static void startTest(String testName) {
        logger = extent.createTest(testName);
    }

    public static void logPass(String message) {
        logger.log(Status.PASS, MarkupHelper.createLabel(message, ExtentColor.GREEN));
    }

    public static void logFail(String message) {
        logger.log(Status.FAIL, MarkupHelper.createLabel(message, ExtentColor.RED));
    }

    public static void endTest() {
        extent.flush();
    }
}
