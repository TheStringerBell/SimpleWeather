package mcanddev.minimalisticweather.UI;


import mcanddev.minimalisticweather.POJO.GetLocationPOJO.GetLocation;
import mcanddev.minimalisticweather.POJO.MainList;

public interface MainViewInterface {

    void fillListView(MainList mainList);
    void getLocationName(GetLocation getLocation);
//    void getText(String s);
}
