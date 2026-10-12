package baseclass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot {

    public static void takeScreenshot(WebDriver driver, String screenshotName)throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File folder = new File("./ScreenShots");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File destination = new File(folder,screenshotName + ".png");
        Files.copy(source.toPath(),destination.toPath(),StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Screenshot saved: "+ destination.getAbsolutePath());
    }
}