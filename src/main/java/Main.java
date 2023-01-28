
import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.math.WPIMathJNI;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.util.CombinedRuntimeLoader;
import edu.wpi.first.util.WPIUtilJNI;

import java.io.IOException;
import java.util.Scanner;


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
    waitForConnection(inst);
    new SubscribeTables(inst);

  }

  /** Set NT4 client IP based on chooser
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

  /** Waits for connection with NT4, checks every 25ms*/
  private static void waitForConnection(NetworkTableInstance inst) throws InterruptedException{

    if(!inst.isConnected()) {
      do {
        Thread.sleep(25);  
      } while (!inst.isConnected());
    }
    System.out.println("Connected");

  }








}
