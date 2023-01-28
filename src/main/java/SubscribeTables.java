import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class SubscribeTables {
  
  private NetworkTableInstance inst;

  public static NetworkTable sd;
  public static NetworkTable fms;

  static DoubleSubscriber leftDistance;
  static DoubleSubscriber rightDistance;
  static DoubleSubscriber fmsControlData;


  /** Subscribe to NT4 tables and values */
  public SubscribeTables(NetworkTableInstance inst){
    this.inst = inst;

    // Get tables
    sd = inst.getTable("SmartDashboard");
    fms = inst.getTable("FMSInfo");
    // TODO to be added more...
    //
    //


    // Get values
    leftDistance = sd.getDoubleTopic("Left Distance in xxxxx").subscribe(0);
    rightDistance = sd.getDoubleTopic("Right Distance in xxxxx").subscribe(0);
    fmsControlData = fms.getDoubleTopic("FMSControlData").subscribe(0);
    // TODO to be added more...
    //
    //

  }


  /** Get an array of newest values from NT4 */
  public static String[] gtNtValues() {

    String[] ntValues = {
      "0", // PLACEHOLDER FOR ID, so we don't need to use ArrayList
      Double.toString(leftDistance.get()), 
      Double.toString(rightDistance.get()),
      Double.toString(fmsControlData.get())
      // TODO to be added more...
      //
      //

    };
    return ntValues;
  }


}