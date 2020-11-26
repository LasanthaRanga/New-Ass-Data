package manual;

import Proces.Holder;
import Proces.NewData;
import conn.DB;

import java.sql.ResultSet;

/**
 * Created by Ranga Rathnayake on 2020-11-20.
 */
public class polgahawela {

    public static void main(String[] args) {

        try {

            ResultSet data = DB.getData("SELECT\n" +
                    "all_data_sheet.idAssessment,\n" +
                    "all_data_sheet.Allocation,\n" +
                    "all_data_sheet.quarter_value,\n" +
                    "all_data_sheet.`9.30 Balance`,\n" +
                    "all_data_sheet.piadQuater,\n" +
                    "all_data_sheet.`12.31_OverPayment`\n" +
                    "FROM\n" +
                    "all_data_sheet\n");

            while (data.next()) {
                int idAssessment = data.getInt("idAssessment");
                double allocation = data.getDouble("Allocation");
                double quarter_value = data.getDouble("quarter_value");
                double balance = data.getDouble("9.30 Balance");
                int paid = data.getInt("piadQuater");
                double over = data.getDouble("12.31_OverPayment");
                over = over * -1;


                System.out.println(idAssessment + " - " + allocation + " - " + quarter_value + "  -  " + balance + "  -  " + paid + "  -  " + over);

                Holder h = new Holder();
                h.setAssessment(idAssessment);
                h.setAssQstartQuaterNumber(4);
                h.setAssQstartProcessDate("2020-10-01");
                h.setProcessUpdateComment(" New Data To System -- ");
                h.setAssPayHistryComment(" New Data To System -- ");
                h.setAssQstartHaveToQpay(quarter_value);

                h.setAssQstartLycArreas(0.0);
                h.setAssQstartLyArreas(0.0);
                h.setAssQstartLyWarrant(0.0);
                h.setAssQstartLycWarrant(0.0);

                h.setAssQstartYear(2020);
                h.setAssQstartTyoldArrias(balance);

                h.setAssPayHistryQ1status(1);
                h.setAssPayHistryQ2status(1);
                h.setAssPayHistryQ3status(1);

                if (balance > 0) {
                    h.setAssQstartLqcArreas(balance);
                    h.setAssQstartLqArreas(balance);
                    h.setAssPayHistryQ4status(0);
                } else {
                    h.setAssQstartLqcArreas(0.0);
                    h.setAssQstartLqArreas(0.0);
                    h.setAssPayHistryQ4status(1);
                }


                if (paid == 0) {
                    h.setAssQstartHaveToQpay(quarter_value);
                    h.setAssPayHistryQ4status(0);
                } else {
                    h.setAssQstartHaveToQpay(0.0);
                    h.setAssPayHistryQ4status(1);
                }


                if (over > 0) {
                    h.setAssPayHistryOver(over);
                } else {
                    h.setAssPayHistryOver(0.0);
                }

                NewData.insertData(h);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
