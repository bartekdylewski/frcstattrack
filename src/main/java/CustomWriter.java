import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.opencsv.CSVWriter;

public class CustomWriter {

    /** Create new blank .csv file named by local date and time.*/
    public static File createFile(String directory) {

        // Set default directory to /logs/
        if(directory == null) {
            directory = "logs";
        }

        // Get current local date and time and format it
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm");
        
        // Create and return file
        File file = new File(directory, dateFormat.format(currentDate) +".csv");
        System.out.println("Created file \"" +dateFormat.format(currentDate) +".csv" +"\"");
        
        return file;
    }


    /** Add new line to the selected .csv file */
    public static void addLine(File file, String[] line, boolean writeAnnouncement) {

        try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) {

        writer.writeNext(line, true); // every value is quoted in .csv file

        if(writeAnnouncement) {
            System.out.println("line added!");
        }

        } catch (IOException e) {
        e.printStackTrace();
        }

    }


    /** Adds new line to the selected .csv file, sets writeAnnouncement to false*/
    public static void addLine(File file, String[] line) {
        addLine(file, line, false);
    }


}