package ru.liga.graphic;

import lombok.SneakyThrows;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.model.Course;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class Graphic {

    private final static Logger logger = LoggerFactory.getLogger(Graphic.class);
    private static final long serialVersionUID = 1L;
    private final TimeSeriesCollection dataset;

    /**
     * Creates a new demo instance.
     */
    public Graphic() {
        this.dataset = new TimeSeriesCollection();
    }

    public XYDataset setDataset(Map<String, List<Course>> courses) {
        logger.debug("Подготовка данных для расчета графика курса валют");
        logger.info("Подготовка данных для расчета графика курса валют");
        for (Map.Entry<String, List<Course>> coursesEntity : courses.entrySet()) {
            TimeSeries s = new TimeSeries(coursesEntity.getKey());
            for (Course course : coursesEntity.getValue()) {
                s.add(new Day(Date.valueOf(course.getDay())), course.getOneCoinCourse());
            }
            dataset.addSeries(s);
        }
        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {
        logger.debug("Создание графика курса валют");
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Прогноз курсов валют",  // title
                "",                            // x-axis label
                "Курс",                      // y-axis label
                dataset,                       // data
                true,                          // create legend
                true,                          // generate tooltips
                false                          // generate URLs
        );

        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setDrawSeriesLineAsPath(true);
        }

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("dd.MM.yyyy"));

        return chart;
    }

    @SneakyThrows
    public File getCurrencyRatesAsGraph() {
        logger.debug("Формирование графика курса валют");
        logger.info("Формирование графика курса валют");
        JFreeChart chart = createChart(dataset);
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);

        File lineChart = new File("src/main/resources/lineChart.png");
        ImageIO.write(chart.createBufferedImage(1000, 400), "png", lineChart);
        return lineChart;
    }
}
