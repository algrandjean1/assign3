package assign3.server;

import assign3.client.*;
import assign3.server.*;
import java.rmi.server.*;
import java.text.NumberFormat;
import java.lang.Math;
import java.rmi.*;
import java.util.*;
import java.io.*;
import org.json.*;

/**
 * Purpose: demonstrate using the RMI API
 * Implementation of employee server - create a remote server object 
 * (with a couple of employees). Register the remote server object with the
 * rmi registry.
 * @author Tim Lindquist (Tim@asu.edu), ASU Polytechnic
 * @version June 2014
 */
public class WaypointServerImpl extends UnicastRemoteObject implements WaypointServer, java.io.Serializable {
    
    public final static double radiusE = 6371;
    
    protected Hashtable<String, Waypoint> wps;
    
    public WaypointServerImpl() throws RemoteException, Exception {
        try {
            wps = new Hashtable<String,Waypoint>();
            System.out.println("Finding samples");
            File f = new File("samples.txt");
            if(f.exists() && !f.isDirectory()) {
                System.out.println("samples.txt. located");
                BufferedReader fileIn = new BufferedReader(new FileReader(f));
                String inStr;
                String st[];
                double lat, lon, ele = 0;
                String name;
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
                while((inStr = fileIn.readLine())!=null) {
                    st = inStr.split(",");
                    lat = Double.parseDouble(st[0]);
                    lon = Double.parseDouble(st[1]);
                    ele = Double.parseDouble(st[2]);
                    name = st[3];
                    Waypoint wp = new Waypoint(lat,lon,ele,name,"");
                    System.out.println("Init with waypoint: "+name+" lat:"+nf.format(lat)+
                                       " lon:"+nf.format(lon)+" ele:"+nf.format(ele));
                    wps.put(name,wp);
                    System.out.println(wps);
                }
            }
            }
        catch(RemoteException e) {
            System.out.println("Error reading input file.");
        }
    }
    
    public String[] getNames() throws RemoteException {
        String ret[];
        ret = (wps.keySet()).toArray(new String[0]);
        return ret;
    }
    
    public Waypoint get(String name) throws RemoteException{
        try
		{
			if (wps.containsKey(name))
			{
                System.out.println("Returning: " + name);
				return wps.get(name);
			}
            else{
                System.out.println("Error Getting Name.");
                return null;
            }
		}
		catch (Exception e)
		{
			throw e;
		}
    }
    
    public void remove(String name) throws RemoteException{
        wps.remove(name);
    }
    public void add(String name, Waypoint wp) throws RemoteException{
        wps.put(name,wp);
    }
    
    public double distGC(String from, String to) throws RemoteException{
        double lat;
        double lon;
        double retDist = 0.0;
        
        Waypoint fromWP = wps.get(from);
        Waypoint toWP = wps.get(to);
        try {
            lat = fromWP.getLat();
            lon = fromWP.getLon();
            double dlatRad = Math.toRadians(lat - toWP.getLat()); //delta
            double dlonRad = Math.toRadians(lon - toWP.getLon());
            double latOrgRad = Math.toRadians(toWP.getLat());
            double lonOrgRad = Math.toRadians(toWP.getLon());
            double a = Math.sin(dlatRad/2) * Math.sin(dlatRad/2) +
            Math.sin(dlonRad/2) * Math.sin(dlonRad/2) * Math.cos(latOrgRad) *
            Math.cos(Math.toRadians(lat));
            retDist = radiusE * (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
            retDist = retDist * 0.62137119; // Make Statute
            //Complete Distance
        }catch(Exception e){
            System.out.println("Error in distance calculation.");
        }
        return retDist;
    }
    public double directionGC(String from, String to) throws RemoteException{
        double lat;
        double lon;
        double ele;
        double retBear = 0.0;
        
        Waypoint fromWP = wps.get(from);
        Waypoint toWP = wps.get(to);
        try{
        double fLat = fromWP.getLat();
        double fLon = fromWP.getLon();
        double tLat = toWP.getLat();
        double tLon = toWP.getLon();
        System.out.println("From Lat: "+fLat+" From Lon: "+fLon+"To Lat: "+tLat+" To Lon: "+tLon);
        double dlonRad = Math.toRadians(tLon - fLon);
        double latOrgRad = Math.toRadians(fLat);
            
        double y = Math.sin(dlonRad) * Math.cos(Math.toRadians(tLat));
        double x = Math.cos(latOrgRad) * Math.sin(Math.toRadians(tLat)) -
            Math.sin(latOrgRad) * Math.cos(Math.toRadians(tLat)) *
            Math.cos(dlonRad);
            
        retBear = Math.toDegrees(Math.atan2(y,x));
        retBear = (retBear+360.0) % 360.0;
            
    }catch(Exception e){
        System.out.println("Error in bearing calculation.");
    }
        
        return retBear;
    }

   public static void main(String args[]) {
      try {
         String hostId="192.168.2.3";
         String port="2222";
         if (args.length >= 2){
	    hostId=args[0];
            port=args[1];
         }
         WaypointServer obj = new WaypointServerImpl();
         Naming.rebind("rmi://"+hostId+":"+port+"/WaypointServer", obj);
         System.out.println("Server bound in registry as: "+
                            "rmi://"+hostId+":"+port+"/WaypointServer");
      }catch (Exception e) {
         e.printStackTrace();
      }
   }
}
