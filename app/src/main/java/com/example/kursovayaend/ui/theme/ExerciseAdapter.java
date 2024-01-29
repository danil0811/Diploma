package com.example.kursovayaend.ui.theme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kursovayaend.Model.Exercise;

import java.util.List;

public class ExerciseAdapter extends BaseAdapter {

    private List<Exercise> exercises;
    private LayoutInflater inflater;

    public ExerciseAdapter(Context context, List<Exercise> exercises) {
        this.exercises = exercises;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public Object getItem(int position) {
        return exercises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Exercise exercise = (Exercise) getItem(position);

        if (exercise != null) {
            viewHolder.textView.setText(exercise.getName());
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
    }
}
