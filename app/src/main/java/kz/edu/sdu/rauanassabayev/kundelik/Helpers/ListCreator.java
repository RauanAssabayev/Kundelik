package kz.edu.sdu.rauanassabayev.kundelik.Helpers;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;

import kz.edu.sdu.rauanassabayev.kundelik.Models.SpinnerSubjectItem;
import kz.edu.sdu.rauanassabayev.kundelik.R;

/**
 * Created by rauanassabayev on 11/4/17.
 */

public class ListCreator {
    public static ArrayList<SpinnerSubjectItem> createSubjectsList(Context context){
        ArrayList<SpinnerSubjectItem> list = new ArrayList<>();
        list.add(new SpinnerSubjectItem(context.getString(R.string.fragment_timetable_choose_time),R.drawable.ic_touch));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_custom),R.drawable.ic_subject_custom));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_algebra),R.drawable.ic_subject_algebra));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_english),R.drawable.ic_subject_english));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_astronomy),R.drawable.ic_subject_astronomy));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_biology),R.drawable.ic_subject_biology));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_world_history),R.drawable.ic_subject_world_history));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_geography),R.drawable.ic_subject_geography));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_geography),R.drawable.ic_subject_geometry));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_natural_science),R.drawable.ic_subject_natural_science));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_informatics),R.drawable.ic_subject_informatics));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_history_of_kazakhstan),R.drawable.ic_subject_history_of_kazakhstan));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_kazakh_language),R.drawable.ic_subject_kazakh_language));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_regional_studies),R.drawable.ic_subject_regional_studies));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_literature),R.drawable.ic_subject_literature));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_math),R.drawable.ic_subject_math));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_music),R.drawable.ic_subject_music));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_bmt),R.drawable.ic_subject_bmt));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_lsf),R.drawable.ic_subject_lsf));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_social_scince),R.drawable.ic_subject_social_scince));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_law),R.drawable.ic_subject_law));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_natural_history),R.drawable.ic_subject_natural_history));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_religion),R.drawable.ic_subject_religion)); //icon
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_art),R.drawable.ic_subject_art)); //icon
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_russian_literature),R.drawable.ic_subject_russian_literature)); //icon
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_russian_language),R.drawable.ic_subject_russian_language));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_self_knowledge),R.drawable.ic_subject_self_knowledge));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_technology),R.drawable.ic_subject_technology));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_labor),R.drawable.ic_subject_labor));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_physics),R.drawable.ic_subject_physics));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_physical_education),R.drawable.ic_subject_physical_education));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_philosophy),R.drawable.ic_subject_philosophy));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_chemistry),R.drawable.ic_subject_chemistry));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_individual_and_society),R.drawable.ic_subject_individual_and_society));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_drawing),R.drawable.ic_subject_drawing));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_calligraphy),R.drawable.ic_subject_calligraphy));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_reading),R.drawable.ic_subject_reading));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_ecology),R.drawable.ic_subject_ecology));
        list.add(new SpinnerSubjectItem(context.getString(R.string.subject_economy),R.drawable.ic_subject_economy));
        return list;
    }
}
