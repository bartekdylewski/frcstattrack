
import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.math.WPIMathJNI;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.util.CombinedRuntimeLoader;
import edu.wpi.first.util.WPIUtilJNI;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
      
      // Load libraries
      NetworkTablesJNI.Helper.setExtractOnStaticLoad(false);
      WPIUtilJNI.Helper.setExtractOnStaticLoad(false);
      WPIMathJNI.Helper.setExtractOnStaticLoad(false);
      CameraServerJNI.Helper.setExtractOnStaticLoad(false);
      CombinedRuntimeLoader.loadLibraries(Main.class, "wpiutiljni", "wpimathjni", "ntcorejni", "cscorejnicvstatic");


      // Init
      NetworkTableInstance inst = NetworkTableInstance.getDefault();
      Scanner sc = new Scanner(System.in);
      
      
      // Config
      String networktablesIP = "localhost"; // Configured below
      int teamNumber = 5883;
      
      
      // Set NT instance IP depending on connection
      boolean ipConfigSuccesfull = false;
      do {
        System.out.println("Type: \n0 - for simulation \n1 - for robot\n");
        String ipChoice = sc.nextLine();
        
        if(ipChoice.equals("0")) {
          networktablesIP = "127.0.0.1";
          ipConfigSuccesfull = true;
        } else if(ipChoice.equals("1")) {
          networktablesIP = "10.58.83.2";
          ipConfigSuccesfull = true;
        } else {
          System.out.println("\nWrong input. Try again! \n");
        }
      } while (!ipConfigSuccesfull);
      System.out.println("\nNetworkTables IP chosen");
      
      
      // Init NT client
      inst.startClient4("FRC Stat Track");
      inst.setServer(networktablesIP); // Connect to IP
      inst.setServerTeam(teamNumber, 0); // Connect to a RoboRIO with teamNumber
      inst.startDSClient(); // Connects to RoboRIO IP, by getting IP from driver station
      
      // Get tables
      NetworkTable sd = inst.getTable("SmartDashboard");
      NetworkTable fms = inst.getTable("FMSInfo");
      
      // Get values
      DoubleSubscriber x = sd.getDoubleTopic("Left Distance in xxxxx").subscribe(0);
      DoubleSubscriber y = sd.getDoubleTopic("Right Distance in xxxxx").subscribe(0);
      DoubleSubscriber z = fms.getDoubleTopic("FMSControlData").subscribe(0);


      // Print loop
      while (true) {
        try {
          Thread.sleep(100);
        } catch (InterruptedException ex) {
          System.out.println("interrupted");
          return;
        }
        System.out.println("leftD: " + x.get() + " rightD: " + y.get() + " FMSControlData: " + z.get());
      }

    }
}
