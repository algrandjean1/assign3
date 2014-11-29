package assign3.server;
import org.json.*;


/**
 * Purpose: demonstrate using the RMI API
 * a simple serializable employee class including name and id number.
 * @author Tim Lindquist (Tim@asu.edu), ASU Polytechnic
 * @version June 2014
 */


public class Waypoint implements java.io.Serializable {
    public double lat;
    public double lon;
    public double ele;
    public String address;
    public String name;
    
    public Waypoint(){
        lat = 0;
        lon = 0;
        ele = 0;
        name = "";
        address = "";
    }

   public Waypoint(double lati,double loni,double elev, String namea, String addressa) {
      lat = lati;
      lon = loni;
      ele = elev;
      name = namea;
      address = addressa;
   }
    public void setLat(double lat){
        this.lat = lat;
    }
    public double getLat(){
        return lat;
    }
    public void setLon(double lon){
        this.lon = lon;
    }
    public double getLon(){
        return lon;
    }
    public void setEle(double ele){
        this.ele = ele;
    }
    public double getEle(){
        return ele;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setAddr(String address){
        this.address = address;
    }
    public String getAddr(){
        return address;
    }
    public JSONObject toJson(){
        JSONObject obj = new JSONObject();
        obj.put("lat",lat);
        obj.put("lon",lon);
        obj.put("ele",ele);
        obj.put("name",name);
        obj.put("addr",address);
        return obj;
    }
    
}		
