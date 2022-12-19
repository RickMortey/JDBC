package homework.java.service.query;

import java.awt.*;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;

import javax.swing.*;
import java.io.File;
import java.io.IOException;


public class VisualizeQuery {
    public void DrawBarChartQuery4(ArrayList<ExecuteQuery.Query4Result> data) throws IOException {
        String basePath = "./src/main/resources/homework/java/charts/";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (ExecuteQuery.Query4Result instance : data
        ) {
            dataset.setValue(instance.getCancelledFlightsCount(), "CancelledFlights", instance.getMonth());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Cancelled flights by month",
                "Month",
                "CancelledFlights",
                dataset
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        ChartUtils.saveChartAsPNG(new File(basePath + "Query4BarChart.png"), chart, 450, 400);
    }

    public void DrawBarChartQuery5(ArrayList<ExecuteQuery.Query5Result> data) throws IOException {
        String basePath = "./src/main/resources/homework/java/charts/";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (ExecuteQuery.Query5Result instance : data
        ) {
            dataset.setValue(instance.getArrivalFlightCount(), "ArrivedFlights", instance.getWeekDay());
            dataset.setValue(instance.getDepartureFlightCount(), "DepartedFlights", instance.getWeekDay());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Arrival/Departures by day",
                "Weekday",
                "Flights",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        ChartUtils.saveChartAsPNG(new File(basePath + "Query5BarChart.png"), chart, 450, 400);

    }

}
