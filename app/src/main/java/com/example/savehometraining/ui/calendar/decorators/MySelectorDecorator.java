package com.example.savehometraining.ui.calendar.decorators;

import android.graphics.drawable.Drawable;

import com.example.savehometraining.R;
import com.example.savehometraining.ui.calendar.CalendarFragment;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class MySelectorDecorator implements DayViewDecorator {
    private final Drawable drawable;
    public MySelectorDecorator(CalendarFragment calendarFragment) {
        drawable=calendarFragment.getResources().getDrawable(R.drawable.my_selector);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}
