package com.example.test2;

import java.text.DecimalFormat;
import java.util.Vector;

public class params {
    public double mass,time,start_h,q;
    public Vector massVector = new Vector(0);
    public Vector timeVector = new Vector(0);
    public Vector start_hVector = new Vector(0);
    public Vector qVector = new Vector(0);

    public void add(double mass,long time, int start_h){
        massVector.add(mass/1000.);
        timeVector.add((double) (time/1000.));
        start_hVector.add((double)(start_h/100.));
        qVector.add((double) (mass/1000.*9.807*((double)(start_h/100.)-((9.807*(double)(time*time/1000000.))/(8.)))));
    }
    public String getQstring(){
        DecimalFormat df=new DecimalFormat("###.###");
        return df.format(qVector.get(qVector.size()-1));
    }
    public double getQ(){
        return Double.parseDouble(qVector.get(qVector.size()-1).toString());
    }
    public void clear(){
        massVector.removeAllElements();
        timeVector.removeAllElements();
        start_hVector.removeAllElements();
        qVector.removeAllElements();
    }
    public boolean isEmpty(){
        return massVector.isEmpty();
    }
    public void deleteLast(){
        massVector.remove(massVector.size() - 1);
        massVector.trimToSize();
        timeVector.remove(timeVector.size() - 1);
        timeVector.trimToSize();
        start_hVector.remove(start_hVector.size() - 1);
        start_hVector.trimToSize();
        qVector.remove(qVector.size() - 1);
        qVector.trimToSize();
    }

    public int getSize(){
        return massVector.size();
    }

    public double[] getAllMasses(){
        double[] masses = new double[massVector.size()];
        for(int i=0;i<massVector.size();i++){
            masses[i]=Double.parseDouble(massVector.get(i).toString())*1000.;
        }
        return masses;
    }

    public double[] getAllQ(){
        double[] q = new double[qVector.size()];
        for(int i=0;i<qVector.size();i++){
            q[i]=Double.parseDouble(qVector.get(i).toString());
        }
        return q;
    }
}
