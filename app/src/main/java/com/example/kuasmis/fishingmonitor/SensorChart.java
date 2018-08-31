package com.example.kuasmis.fishingmonitor;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Date;
import data.DataGetter;
import data.MiDataGetter;

public class SensorChart {

    private int chartLength = 50;
    private int count = chartLength;
    private MiDataGetter dataGetter;
    private XYSeries seriesX;
    private XYSeries seriesY;
    private XYSeries seriesZ;
    private XYMultipleSeriesDataset dataset;
    private XYMultipleSeriesRenderer renderer;
    private GraphicalView chart;

    public SensorChart(Context context, MiDataGetter dataGetter) {
        buildDataset();
        setChartSettings();
        this.chart = ChartFactory.getLineChartView(context, dataset, renderer);
        this.dataGetter = dataGetter;
    }

    private void buildDataset() {
        seriesX = getfilledSeries("X");
        seriesY = getfilledSeries("Y");
        seriesZ = getfilledSeries("Z");
        dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(seriesX);
        dataset.addSeries(seriesY);
        dataset.addSeries(seriesZ);
    }

    private XYSeries getfilledSeries(String title) {
        XYSeries series = new TimeSeries(title);
        for (int i = 0; i < chartLength; i++)
            series.add(i, 0);
        return series;
    }

    private void setChartSettings() {
        renderer = new XYMultipleSeriesRenderer();

        XYSeriesRenderer xRenderer = new XYSeriesRenderer();
        xRenderer.setColor(Color.rgb(255, 100, 100));
        xRenderer.setLineWidth(5);

        XYSeriesRenderer yRenderer = new XYSeriesRenderer();
        yRenderer.setColor(Color.rgb(0, 255, 0));
        yRenderer.setLineWidth(5);

        XYSeriesRenderer zRenderer = new XYSeriesRenderer();
        zRenderer.setColor(Color.rgb(0, 255, 255));
        zRenderer.setLineWidth(5);

        renderer.addSeriesRenderer(xRenderer);
        renderer.addSeriesRenderer(yRenderer);
        renderer.addSeriesRenderer(zRenderer);
        renderer.setBackgroundColor(Color.BLACK);
        renderer.setMarginsColor(Color.BLACK);
        renderer.setYAxisMax(2);
        renderer.setYAxisMin(-2);

    }

    public GraphicalView getChartView() {
        return chart;
    }

    public void update() {
        double newX = dataGetter.getX();
        double newY = dataGetter.getY();
        double newZ = dataGetter.getZ();
        updateSeries(seriesX, newX);
        updateSeries(seriesY, newY);
        updateSeries(seriesZ, newZ);
        chart.invalidate();
    }

    private void updateSeries(XYSeries series, double data) {
        series.remove(0);
        series.add(++count, data);
    }
}
