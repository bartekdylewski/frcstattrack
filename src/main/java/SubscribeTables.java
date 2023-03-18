import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class SubscribeTables {
  
  private NetworkTableInstance inst;

  public static NetworkTable smartDashboard;
  public static NetworkTable fms;

  static DoubleSubscriber values[] = new DoubleSubscriber[Strings.value_names.length];


  /** Subscribe to NT4 tables and values */
  public SubscribeTables(NetworkTableInstance inst){
    this.inst = inst;

    // Get tables
    smartDashboard = inst.getTable("SmartDashboard");
    fms = inst.getTable("FMSInfo");

    // Subscribe values[] to given topics
    for (int i = 0; i < values.length; i++) {
      values[i] = smartDashboard.getDoubleTopic(Strings.value_names[i]).subscribe(0);
    }

  }

  
  /** Get an array of newest values from NT4 */
  public static String[] gtNtValues() {

    String[] ntValues = new String[Strings.csvHeader.length]; // create string array with present values
    ntValues[0] = "0"; // set first column to 0
    for (int i = 0; i < ntValues.length-1; i++) {
      ntValues[i+1] = Double.toString(values[i].get()); // set next columns to values subscribed from NT
    }
   
    return ntValues;
  }


}