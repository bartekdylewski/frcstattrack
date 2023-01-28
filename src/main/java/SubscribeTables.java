import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class SubscribeTables {
  private NetworkTableInstance inst;

  public SubscribeTables(NetworkTableInstance inst) throws InterruptedException{
    this.inst = inst;
    
    // Get tables
    NetworkTable sd = inst.getTable("SmartDashboard");
    NetworkTable fms = inst.getTable("FMSInfo");

    // Get values
    DoubleSubscriber leftDistance = sd.getDoubleTopic("Left Distance in xxxxx").subscribe(0);
    DoubleSubscriber rightDistance = sd.getDoubleTopic("Right Distance in xxxxx").subscribe(0);
    DoubleSubscriber fmsControlData = fms.getDoubleTopic("FMSControlData").subscribe(0);


    File file = new File("logs", "test.csv");
    file.setReadable(true);
    file.setWritable(true);
    String[] header = {"id", "left Distance in m"};

  
      addHeader(file, header);

      int id = 0;
      while(true){

        String ld = String.valueOf(leftDistance.get());

        updateCsv(file, id, ld);

      
        System.out.println(ld);
        System.out.println("id: " + id);

        id++;
        Thread.sleep(100);
      }


    
    

  }

  private static void addHeader(File file, String[] header) {

    try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) {

      writer.writeNext(header, true); // every value is quoted in .csv file
      System.out.println("HEADER ADDED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  private static void updateCsv(File file, int id, String ld) {

    String[] entry = {Integer.toString(id), ld};

    try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) {
      writer.writeNext(entry, true); // must be true for something like {"id", "left Distance in xxxxx"}
      System.out.println("entry updated!");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



}