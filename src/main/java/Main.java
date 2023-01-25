
import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.math.WPIMathJNI;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.util.CombinedRuntimeLoader;
import edu.wpi.first.util.WPIUtilJNI;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.opencsv.CSVWriter;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
      
      // Load libraries
      NetworkTablesJNI.Helper.setExtractOnStaticLoad(false);
      WPIUtilJNI.Helper.setExtractOnStaticLoad(false);
      WPIMathJNI.Helper.setExtractOnStaticLoad(false);
      CameraServerJNI.Helper.setExtractOnStaticLoad(false);
      CombinedRuntimeLoader.loadLibraries(Main.class, "wpiutiljni", "wpimathjni", "ntcorejni", "cscorejnicvstatic");

      // Setup NT4 Client
      NetworkTableInstance inst = NetworkTableInstance.getDefault();
      inst.startClient4("FRC Stat Track");
      selectNetworkTablesIP(inst, 5883);
      // Connects after ~100ms



      new SubscribeTables(inst);

      


/*       // POBIERANIE TABELEK
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
      } */

/* 
      // Print loop
      while (true) {
        try {
          Thread.sleep(100);
        } catch (InterruptedException ex) {
          System.out.println("interrupted");
          return;
        }

        System.out.println("leftD: " + leftDistance.get() + " rightD: " + rightDistance.get() + " FMSControlData: " + fmsControlData.get());
      }
 */
    }

    /** Set NT4 Client IP based on chooser
     * 
     * @param inst NetworkTableInstance
     * @param teamNumber
     */
    private static void selectNetworkTablesIP(NetworkTableInstance inst, int teamNumber) {
      
      boolean isInputRight = true;

      try (Scanner sc = new Scanner(System.in)) {
        do {

          System.out.println("Type: \n0 - for simulation \n1 - for robot\n");
          String ipChoice = sc.nextLine();
          
          if(ipChoice.equals("0")) {
            inst.setServer("localhost"); // Connect to localhost for simulation

          } else if(ipChoice.equals("1")) {
            inst.setServerTeam(teamNumber); // Connect to NT4 with RoboRIO IP from team number ex. 10.58.83.2 for 5883

          } else {
            System.out.println("\nWrong input. Try again! \n");
            isInputRight = false;
            
          }

        } while (!isInputRight);
      }
      System.out.println("\nNetworkTables IP chosen");
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
