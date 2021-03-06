package com.rarasnet.rnp.desordens.profile.statistics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.rarasnet.rnp.desordens.R;
import com.rarasnet.rnp.desordens.profile.DisorderProfileActivity;
import com.rarasnet.rnp.desordens.search.models.DisorderProfile;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Farina on 21/10/2015.
 */
public class StatisticsFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private DisorderProfile disorderProfile = DisorderProfileActivity.mDisorderProfile;

    public static StatisticsFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        StatisticsFragment fragment = new StatisticsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout view  =
                (RelativeLayout) inflater.inflate(R.layout.fragment_disorder_statistics,
                        container, false);
        RelativeLayout chartFrame =
                (RelativeLayout) view.findViewById(R.id.frag_disorder_statistics_rl_mortalidadeGraphFrame);
        BarDataSet dataset = new BarDataSet(getGraphData(), "Número de óbitos no Brasil");

        dataset.setColor(getResources().getColor(R.color.material_palette_800));
        BarData data = new BarData(getGraphLabels(), dataset);
        dataset.setValueTextColor(getResources().getColor(R.color.material_grey_palette_800));
        dataset.setHighLightColor(getResources().getColor(R.color.primary_accent));

        BarChart chart = new BarChart(getActivity());
        chart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        chart.setDrawHighlightArrow(true);
        chart.setData(data);
        chart.setDrawGridBackground(false);
        chart.setDescription("");
        chart.animateY(3000);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(getResources().getColor(R.color.material_grey_palette_400));
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLimitLinesBehindData(true);

        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setTextColor(getResources().getColor(R.color.material_grey_palette_400));
        yAxisLeft.setDrawLimitLinesBehindData(true);

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setTextColor(getResources().getColor(R.color.material_grey_palette_400));
        yAxisRight.setDrawLimitLinesBehindData(true);

        chart.getLegend().setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);

        chartFrame.addView(chart);
        return view;
    }

    private ArrayList<BarEntry> getGraphData() {
        ArrayList<BarEntry> mortalidadeEntries = new ArrayList<>(Arrays.asList(new BarEntry[]{

                new BarEntry(Integer.parseInt(disorderProfile.getMortalidade().getAno2002()), 0),
                new BarEntry(Integer.parseInt(disorderProfile.getMortalidade().getAno2003()), 1),
                new BarEntry(Integer.parseInt(disorderProfile.getMortalidade().getAno2004()), 2),
                new BarEntry(Integer.parseInt(disorderProfile.getMortalidade().getAno2005()), 3),
                new BarEntry(Integer.parseInt(disorderProfile.getMortalidade().getAno2006()), 4),
                new BarEntry(Integer.parseInt(disorderProfile.getMortalidade().getAno2007()), 5),
                new BarEntry(Integer.parseInt(disorderProfile.getMortalidade().getAno2008()), 6),
                new BarEntry(Integer.parseInt(disorderProfile.getMortalidade().getAno2009()), 7),
                new BarEntry(Integer.parseInt(disorderProfile.getMortalidade().getAno2010()), 8),
                new BarEntry(Integer.parseInt(disorderProfile.getMortalidade().getAno2011()), 9),
                new BarEntry(Integer.parseInt(disorderProfile.getMortalidade().getAno2012()), 10)
        }));


        return mortalidadeEntries;
    }

    private ArrayList<String> getGraphLabels() {
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("2002");
        labels.add("2003");
        labels.add("2004");
        labels.add("2005");
        labels.add("2006");
        labels.add("2007");
        labels.add("2008");
        labels.add("2009");
        labels.add("2010");
        labels.add("2011");
        labels.add("2012");
        return labels;
    }
}
