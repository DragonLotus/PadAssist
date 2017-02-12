package com.padassist.Util;

import com.padassist.Data.Element;
import com.padassist.Data.OrbMatch;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by DragonLotus on 2/12/2017.
 */

public class OrbMatchUtil {

    public static ArrayList<Element> getAllOrbMatchElements(RealmResults<OrbMatch> orbMatchList){
        ArrayList<Element> compareAllElements = new ArrayList<>();

        for (int i = 0; i < orbMatchList.size(); i++) {
            compareAllElements.add(orbMatchList.get(i).getElement());
        }
        return compareAllElements;


    }

}
