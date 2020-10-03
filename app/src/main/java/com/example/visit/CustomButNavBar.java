package com.example.visit;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomButNavBar extends BottomNavigationView {
    private boolean previousInList = false;
    private int menuSize = 0;
    private String a,b;
    Context context;
    MainActivity mainActivity;
    List<LinkedHashMap<String, Drawable>> menuNames = new ArrayList<>();


    public CustomButNavBar(@NonNull Context context, final MainActivity mainActivity, int menuId) {
        super(context);
        this.context = context;

        this.mainActivity = mainActivity;

        inflateMenu(menuId);
        grouping();

        setOnNavigationItemReselectedListener(new OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                Map.Entry<String,Drawable> menuItem = checkName(item.getTitle().toString());
                if(menuItem!=null)
                {
                    mainActivity.setFragment(menuItem.getKey());
                    item.setTitle(menuItem.getKey());
                    item.setIcon(menuItem.getValue());
                }
            }
        });
    }

    public CustomButNavBar(@NonNull final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    // Выбираем из меню все элементы, кроме первого по следующему правилу:
    // если 2 элемента имеют вид название_число и их названия совпадают,то мы добавляем из в отдельный ArrayList<MenuItem>
    // таким образом,собираем группы элементов с одним названием. Важно! В самом меню остаётся только 1ый элемент из каждой группы, остальные удаляются
    private void grouping()
    {
        menuSize = getMenu().size();
        for(int i=0;i<menuSize-1;i++)
        {
            a = context.getResources().getResourceName(getMenu().getItem(i).getItemId());
            a = a.substring(a.indexOf('/')+1);
            
            previousInList = false;

            for(int j=i+1;j<menuSize;j++)
            {
                b = context.getResources().getResourceName(getMenu().getItem(j).getItemId());
                b = b.substring(b.indexOf('/')+1);
                if(isTurn(a,b))
                {
                    if(previousInList) {
                        menuNames.get(menuNames.size() - 1).put(getMenu().getItem(j).getTitle().toString(),getMenu().getItem(j).getIcon());
                    }
                    else {
                        LinkedHashMap<String, Drawable> linkedHashMap = new LinkedHashMap<>();
                        linkedHashMap.put(getMenu().getItem(i).getTitle().toString(),getMenu().getItem(i).getIcon());
                        linkedHashMap.put(getMenu().getItem(j).getTitle().toString(),getMenu().getItem(j).getIcon());
                        menuNames.add(linkedHashMap);
                        previousInList =true;
                    }
                    getMenu().removeItem(getMenu().getItem(j).getItemId());
                    menuSize--;
                    j--;
                }
            }
        }
    }
    private boolean isTurn(String a, String b) {
        int aSeparator = a.lastIndexOf('_');
        int bSeparator = b.lastIndexOf('_');

        if (aSeparator ==-1 || bSeparator==-1 || aSeparator != bSeparator)
            return false;

        if (a.substring(0, aSeparator).equals(b.substring(0, bSeparator)))
            if (a.substring(aSeparator+1).matches("[-+]?\\d+") && b.substring(aSeparator+1).matches("[-+]?\\d+"))
                if (!a.substring(aSeparator+1).equals(b.substring(bSeparator+1)))
                    return true;

        return false;
    }
    private Map.Entry<String,Drawable> checkName(String name)
    {
        for(int i=0;i<menuNames.size();i++)
            if(menuNames.get(i).containsKey(name))
            {
                Set<Map.Entry<String, Drawable>> a = menuNames.get(i).entrySet();
                Iterator<Map.Entry<String, Drawable>> iterator = a.iterator();

                while (iterator.hasNext())
                {
                    Map.Entry<String,Drawable> c = iterator.next();
                    if(c.getKey().equals(name))
                        if(iterator.hasNext())
                            return iterator.next();
                        else
                        {
                            Iterator<Map.Entry<String, Drawable>> iteratorFromStart = a.iterator();
                            return iteratorFromStart.next();
                        }
                }
            }
        return null;
    }

}
