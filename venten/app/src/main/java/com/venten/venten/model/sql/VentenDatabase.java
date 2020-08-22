package com.venten.venten.model.sql;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.venten.venten.model.data.FilterResponseModel;
import com.venten.venten.model.data.VentenConverters;

@Database(entities = {FilterResponseModel.class}, version = 1, exportSchema = false)
@TypeConverters({VentenConverters.class})
public abstract class VentenDatabase extends RoomDatabase {

    private static VentenDatabase instance;

    public static VentenDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    VentenDatabase.class,"ventendatabase").build();
        }

        return instance;
    }

    public abstract VentenDao ventenDao();
}
