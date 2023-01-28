import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.math.WPIMathJNI;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.util.CombinedRuntimeLoader;
import edu.wpi.first.util.WPIUtilJNI;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) throws IOException{
    
    // Load needed libraries
    loadLibraries();

    // Setup NT4 client and wait for connection
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    inst.startClient4("FRC Stat Track");
    selectNetworkTablesIP(inst, 5883);
    waitForConnection(inst, 100);
    new SubscribeTables(inst);

    // Create new .csv file
    File file = CustomWriter.createFile("logs");

    // Add .csv header
    String[] header = {"id", "left distance (m)", "right distance (m)", "FMS control data"};
    CustomWriter.addLine(file, header);
    
    // Get and set newest NT4 values to .csv file
    sleepFor(500);
    int id = 0;
    while (true) {
      String[] ntValues = SubscribeTables.gtNtValues();   // Get new values with [0] = 0
      ntValues[0] = String.valueOf(id);                   // Set [0] as id
      CustomWriter.addLine(file, ntValues);               // Add line to .csv file
      System.out.println(Arrays.toString(ntValues));      // Print values added
      id++;
      sleepFor(100);
    }

  }


  /** Set NT4 client IP based on chooser */
  private static void selectNetworkTablesIP(NetworkTableInstance inst, int teamNumber) {
    
    boolean isInputRight = true;

    try (Scanner sc = new Scanner(System.in)) {
      do {

        System.out.println("Type: \n0 - for simulation \n1 - for robot\n");
        String ipChoice = sc.nextLine();
        
        if(ipChoice.equals("0")) {
          inst.setServer("localhost"); // Connect to localhost for simulation
          System.out.println("Connecting to localhost");

        } else if(ipChoice.equals("1")) {
          inst.setServerTeam(teamNumber); // Connect to NT4 with RoboRIO IP from team number ex. 10.58.83.2 for 5883
          System.out.println("Connecting to RoboRIO");

        } else {
          System.out.println("\nWrong input. Try again! \n");
          isInputRight = false;
          
        }

      } while (!isInputRight);
    }

  }


  /** Wait for connection with NT4, checks once every <b>rate</b> <i>(in milliseconds)</i>*/
  private static void waitForConnection(NetworkTableInstance inst, int rate) {

    if(!inst.isConnected()) {
      do {

        try {
          Thread.sleep(rate);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }  

      } while (!inst.isConnected());
    }

    System.out.println("Connected");

  }


  /** Load needed libraries */
  private static void loadLibraries() throws IOException {
    NetworkTablesJNI.Helper.setExtractOnStaticLoad(false);
    WPIUtilJNI.Helper.setExtractOnStaticLoad(false);
    WPIMathJNI.Helper.setExtractOnStaticLoad(false);
    CameraServerJNI.Helper.setExtractOnStaticLoad(false);
    CombinedRuntimeLoader.loadLibraries(Main.class, "wpiutiljni", "wpimathjni", "ntcorejni", "cscorejnicvstatic");
  }


  /** Sleep thread for given time */
  public static void sleepFor(int time) {
    try {
      Thread.sleep(time);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


}