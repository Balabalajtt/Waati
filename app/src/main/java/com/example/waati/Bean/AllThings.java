package com.example.waati.Bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 江婷婷 on 2017/10/28.
 */

public class AllThings {

    public static List<ThingListItem> sThingList = new ArrayList<>();
    public static List<Date> sDateList = new ArrayList<>();

    public static void addThing(ThingListItem thingListItem) {
        thingListItem.save();
        Date date = thingListItem.getmDate();
        if (con(date)) {//有同日期的其他事情存在
            int j = 0;
            for(int i = 0; i < sThingList.size(); i++) {
                if (sThingList.get(i).getmListType() == 0
                        && date.getDate() == sThingList.get(i).getmDate().getDate()) {

                    for (j = i + 1; j < sThingList.size(); j++) {
                        if (date.compareTo(sThingList.get(j).getmDate()) < 0) {
                            sThingList.add(j, thingListItem);
                            return;
                        }
                    }

                }
            }

            sThingList.add(j, thingListItem);

        } else {//无此日期
            if (sDateList.isEmpty()) {
                sDateList.add(0, date);
            } else {
                int i;
                for (i = 0; i < sDateList.size(); i++) {
                    if (date.compareTo(sDateList.get(i)) < 0) {
                        sDateList.add(i, date);
                        break;
                    }
                }
                if (i == sDateList.size()) {
                    sDateList.add(date);
                }
            }

            if(sThingList.isEmpty()) {
                sThingList.add(0, new ThingListItem(date));
                sThingList.add(1, thingListItem);
            } else {
                for (int i = 0; i < sThingList.size(); i++) {
                    if (sThingList.get(i).getmListType() == 0
                            && date.compareTo(sThingList.get(i).getmDate()) < 0) {
                        sThingList.add(i, new ThingListItem(date));
                        sThingList.add(i + 1, thingListItem);
                        return;
                    }
                }

                sThingList.add(new ThingListItem(date));
                sThingList.add(thingListItem);
            }


        }
    }

    public static void removeThing(int position) {
        if (position >= sThingList.size() || position <= 0) {
            return;
        }
        sThingList.get(position).delete();
        if (position == sThingList.size() - 1) {//最后一条事情
            if (sThingList.get(position - 1).getmListType() == 0) {
                sDateList.remove(sThingList.get(position - 1).getmDate());
                sThingList.remove(position);
                sThingList.remove(position - 1);
            } else {
                sThingList.remove(position);
            }
        } else {
            if (sThingList.get(position - 1).getmListType() == 0
                    && sThingList.get(position + 1).getmListType() == 0) {
                sDateList.remove(sThingList.get(position - 1).getmDate());
                sThingList.remove(position);
                sThingList.remove(position - 1);
            } else {
                sThingList.remove(position);
            }
        }

    }

    public static void changeDown(ThingListItem thingListItem, int position) {
        removeThing(position);
        addThing(thingListItem);
    }

    private static boolean con(Date date) {
        for (Date d : sDateList) {
            if (date.getYear() == d.getYear() && date.getMonth() == d.getMonth() && d.getDay() == date.getDay()) {
                return true;
            }
        }
        return false;
    }


}
