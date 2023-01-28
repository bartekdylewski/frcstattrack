import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class SubscribeTables {
  private NetworkTableInstance inst;

  NetworkTable sd;
  NetworkTable fms;

  static DoubleSubscriber leftDistance;
  static DoubleSubscriber rightDistance;
  static DoubleSubscriber fmsControlData;

  public SubscribeTables(NetworkTableInstance inst){
    this.inst = inst;

    // Get tables
    sd = inst.getTable("SmartDashboard");
    fms = inst.getTable("FMSInfo");
    
    // Get values
    leftDistance = sd.getDoubleTopic("Left Distance in xxxxx").subscribe(0);
    rightDistance = sd.getDoubleTopic("Right Distance in xxxxx").subscribe(0);
    fmsControlData = fms.getDoubleTopic("FMSControlData").subscribe(0);
    
    

  }

  public static String[] gtNtValues() {
    String[] ntValues = {
      Double.toString(leftDistance.get()), 
      Double.toString(rightDistance.get()),
      Double.toString(fmsControlData.get())

    };
    return ntValues;
  }
}