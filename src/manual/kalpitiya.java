package manual;

import Proces.Holder;
import conn.DB;

import java.sql.ResultSet;

import static Proces.NewData.insertData;

/**
 * Created by Ranga Rathnayake on 2020-09-07.
 */
public class kalpitiya {
    public static void main(String[] args) {
//      getData();
    }

    public static void getData() {
        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "kalpitiya.all_data_sheet.idAssessment,\n" +
                    "kalpitiya.all_data_sheet.Allocation,\n" +
                    "kalpitiya.all_data_sheet.quarter_value,\n" +
                    "kalpitiya.all_data_sheet.balance1231\n" +
                    "FROM\n" +
                    "kalpitiya.all_data_sheet");

            while (data.next()) {

                int idAssessment = data.getInt("idAssessment");
                double allocation = data.getDouble("Allocation");
                double quarter_value = data.getDouble("quarter_value");
                double balance1231 = data.getDouble("balance1231");


                Holder h = new Holder();
                h.setAssessment(idAssessment);
                h.setAssQstartQuaterNumber(1);
                h.setAssQstartProcessDate("2020-01-01");
                h.setProcessUpdateComment(" New Data To System -- ");
                h.setAssPayHistryComment(" New Data To System -- ");
                h.setAssQstartHaveToQpay(quarter_value);

                if (balance1231 > 0) {
                    h.setAssQstartLycArreas(balance1231);
                    h.setAssQstartLyArreas(balance1231);

                    h.setAssQstartLyWarrant(0.0);
                    h.setAssQstartLycWarrant(0.0);
                } else {
                    double v = balance1231 * -1;
                    h.setProcessUpdateArrears(v);
                }

                insertData(h);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
