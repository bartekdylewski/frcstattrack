import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class SubscribeTables {
    private NetworkTableInstance inst;

    public SubscribeTables(NetworkTableInstance inst) throws InterruptedException, IOException {
        this.inst = inst;
        // use inst here
        System.out.println(inst.isConnected());
         // POBIERANIE TABELEK
        // Get tables
        NetworkTable sd = inst.getTable("SmartDashboard");
        NetworkTable fms = inst.getTable("FMSInfo");
        // Get values
        DoubleSubscriber leftDistance = sd.getDoubleTopic("Left Distance in xxxxx").subscribe(0);
        DoubleSubscriber rightDistance = sd.getDoubleTopic("Right Distance in xxxxx").subscribe(0);
        DoubleSubscriber fmsControlData = fms.getDoubleTopic("FMSControlData").subscribe(0);

         // MAIN LOOOOOP
      // Create writer
      // default all fields are enclosed in double quotes
      // default separator is a comma
      if(inst.isConnected()){
        try (CSVWriter writer = new CSVWriter(new FileWriter("test.csv", true))) {
          String[] header = {"id", "left Distance in xxxxx"};
          writer.writeNext(header, true); // wymagane true ttuu
          System.out.println("HEADER ADDED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
      }

      int id = 0;
      while(inst.isConnected()){
        try {
          Thread.sleep(1000);
        } catch (InterruptedException ex) {
          System.out.println("interrupted");
          return;
        }


        String ld = String.valueOf(leftDistance.get());
        try (CSVWriter writer = new CSVWriter(new FileWriter("test.csv", true))) {
        updateCsv(writer, id, ld);
        }
        
        System.out.println(ld);
        System.out.println("id: " + id);

        id++;
      }
        
    }

private static void setupCsvHeader(CSVWriter writer) {
    String[] header = {"siema", "elo"};
    writer.writeNext(header, false);
    System.out.println("HEADER ADDED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
  }

  private static void updateCsv(CSVWriter writer, int id, String ld) {

    String[] entry = {Integer.toString(id), ld};
    writer.writeNext(entry);
    System.out.println("entry updated");
  }
}