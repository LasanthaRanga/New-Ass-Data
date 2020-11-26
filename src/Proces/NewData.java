package Proces;

import conn.DB;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;

/**
 * Created by Ranga Rathnayake on 2020-08-20.
 */
public class NewData {

    public static void getData() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new java.util.Date());

        try {
            ResultSet data = DB.getData("SELECT\n" +
                    "new_ass_data.AssID,\n" +
                    "new_ass_data.`Year`,\n" +
                    "new_ass_data.Quarter_Start_Number,\n" +
                    "new_ass_data.`Quarter_Start_Date`,\n" +
                    "new_ass_data.Allocation,\n" +
                    "new_ass_data.Quarter_Amount,\n" +
                    "new_ass_data.Arreas_12_31,\n" +
                    "new_ass_data.Warrant_12_31,\n" +
                    "new_ass_data.Overpaymnet_12_31,\n" +
                    "new_ass_data.Arrears,\n" +
                    "new_ass_data.Warrant,\n" +
                    "new_ass_data.Q2_PAY,\n" +
                    "new_ass_data.Q2_DIS,\n" +
                    "new_ass_data.Q3_PAY,\n" +
                    "new_ass_data.Q3_DIS,\n" +
                    "new_ass_data.Q4_PAY,\n" +
                    "new_ass_data.Q4_DIS,\n" +
                    "new_ass_data.Next_Year_Overpayment\n" +
                    "FROM\n" +
                    "new_ass_data\n");

            while (data.next()) {

                Holder h = new Holder();

                int assID = data.getInt("AssID");
                h.setAssessment(assID);

                int year = data.getInt("year");
                h.setAssQstartYear(year);

                int quarter_start_number = data.getInt("Quarter_Start_Number");
                h.setAssQstartQuaterNumber(quarter_start_number);

                String quarter_start_date = data.getString("Quarter_Start_Date");
                h.setAssQstartProcessDate(quarter_start_date);

                double allocation = data.getDouble("Allocation");

                h.setProcessUpdateComment(" New Data To System -- " + format);
                h.setAssPayHistryComment(" New Data To System -- " + format);

                double quarter_amount = data.getDouble("Quarter_Amount");

                if (quarter_start_number == 1) {


                    h.setAssQstartHaveToQpay(quarter_amount);

                    double arreas_12_31 = data.getDouble("Arreas_12_31");
                    h.setAssQstartLycArreas(arreas_12_31);
                    h.setAssQstartLyArreas(arreas_12_31);

                    double warrant_12_31 = data.getDouble("Warrant_12_31");
                    h.setAssQstartLyWarrant(warrant_12_31);
                    h.setAssQstartLycWarrant(warrant_12_31);

                    double overpaymnet_12_31 = data.getDouble("Overpaymnet_12_31");
                    h.setProcessUpdateArrears(overpaymnet_12_31);


                } else if (quarter_start_number > 1) {

                    double arrears = data.getDouble("Arrears");
                    double warrant = data.getDouble("Warrant");

                    if (arrears > 0) {
                        h.setAssQstartLqArreas(arrears);
                        h.setAssQstartLycArreas(arrears);

                        h.setAssQstartLqWarrant(warrant);
                        h.setAssQstartLqcWarrant(warrant);

                    } else {

                        double q2_pay = data.getDouble("Q2_PAY");
                        double q2_dis = data.getDouble("Q2_DIS");

                        double q3_pay = data.getDouble("Q3_PAY");
                        double q3_dis = data.getDouble("Q3_DIS");

                        double q4_pay = data.getDouble("Q4_PAY");
                        double q4_dis = data.getDouble("Q4_DIS");

                        double next_year_overpayment = data.getDouble("Next_Year_Overpayment");

                        if (quarter_start_number == 2) {

                            h.setAssPayHistryQ2(q2_pay);
                            h.setAssPayHistryDrq2(q2_dis);
                            if (q3_pay > 0 || q2_dis > 0 || q2_pay >= (quarter_amount - 1)) {
                                h.setAssPayHistryQ3status(1);
                            }

                            h.setAssPayHistryQ3(q3_pay);
                            h.setAssPayHistryDrq3(q3_dis);
                            if (q4_pay > 0 || q3_dis > 0 || q3_pay >= (quarter_amount - 1)) {
                                h.setAssPayHistryQ3status(1);
                            }

                            h.setAssPayHistryQ4(q4_pay);
                            h.setAssPayHistryDrq4(q4_dis);
                            if (next_year_overpayment > 0 || q4_dis > 0 || q4_pay >= (quarter_amount - 1)) {
                                h.setAssPayHistryQ4status(1);
                            }

                        }

                        if (quarter_start_number == 3) {

                            h.setAssPayHistryQ3(q3_pay);
                            h.setAssPayHistryDrq3(q3_dis);
                            if (next_year_overpayment > 0 || q3_dis > 0 || q3_pay >= (quarter_amount - 1)) {
                                h.setAssPayHistryQ3status(1);
                            }

                            h.setAssPayHistryQ4(q4_pay);
                            h.setAssPayHistryDrq4(q4_dis);
                            if (next_year_overpayment > 0 || q4_dis > 0 || q4_pay >= (quarter_amount - 1)) {
                                h.setAssPayHistryQ4status(1);
                            }

                        }

                        if (quarter_start_number == 4) {
                            h.setAssPayHistryQ4(q4_pay);
                            h.setAssPayHistryDrq4(q4_dis);
                            if (next_year_overpayment > 0 || q4_dis > 0 || q4_pay >= (quarter_amount - 1)) {
                                h.setAssPayHistryQ4status(1);
                            }
                        }

                        h.setAssPayHistryOver(next_year_overpayment);

                    }
                }

                insertData(h);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void insertData(Holder h) {
        try {

            conn.DB.setData("INSERT INTO  `ass_qstart` (\t\n" +
                    "\t`ass_Qstart_QuaterNumber`,\n" +
                    "\t`ass_Qstart_process_date`,\n" +
                    "\t`ass_Qstart_LY_Arreas`,\n" +
                    "\t`ass_Qstart_LY_Warrant`,\n" +
                    "\t`ass_Qstart_LYC_Arreas`,\n" +
                    "\t`ass_Qstart_LYC_Warrant`,\n" +
                    "\t`ass_Qstart_LQ_Arreas`,\n" +
                    "\t`ass_Qstart_LQC_Arreas`,\n" +
                    "\t`ass_Qstart_LQ_Warrant`,\n" +
                    "\t`ass_Qstart_LQC_Warrant`,\n" +
                    "\t`ass_Qstart_HaveToQPay`,\n" +
                    "\t`ass_Qstart_QPay`,\n" +
                    "\t`ass_Qstart_QDiscont`,\n" +
                    "\t`ass_Qstart_QTot`,\n" +
                    "\t`ass_Qstart_FullTotal`,\n" +
                    "\t`ass_Qstart_status`,\n" +
                    "\t`Assessment_idAssessment`,\n" +
                    "\t`ass_Qstart_year`,\n" +
                    "\t`process_update_warant`,\n" +
                    "\t`process_update_arrears`,\n" +
                    "\t`process_update_comment`,\n" +
                    "\t`ass_Qstart_tyold_arrias`,\n" +
                    "\t`ass_Qstart_tyold_warant` \n" +
                    ")\n" +
                    "VALUES\n" +
                    "\t(\n" +
                    "\t\t" + h.getAssQstartQuaterNumber() + ",\n" +
                    "\t\t'" + h.getAssQstartProcessDate() + "',\n" +
                    "\t\t" + h.getAssQstartLyArreas() + ",\n" +
                    "\t\t" + h.getAssQstartLyWarrant() + ",\n" +
                    "\t\t" + h.getAssQstartLycArreas() + ",\n" +
                    "\t\t" + h.getAssQstartLycWarrant() + ",\n" +
                    "\t\t" + h.getAssQstartLqArreas() + ",\n" +
                    "\t\t" + h.getAssQstartLqcArreas() + ",\n" +
                    "\t\t" + h.getAssQstartLqWarrant() + ",\n" +
                    "\t\t" + h.getAssQstartLqcWarrant() + ",\n" +
                    "\t\t" + h.getAssQstartHaveToQpay() + ",\n" +
                    "\t\t" + h.getAssQstartQpay() + ",\n" +
                    "\t\t" + h.getAssQstartQdiscont() + ",\n" +
                    "\t\t" + h.getAssQstartQtot() + ",\n" +
                    "\t\t" + h.getAssQstartFullTotal() + ",\n" +
                    "\t\t" + h.getAssQstartStatus() + ",\n" +
                    "\t\t" + h.getAssessment() + ",\n" +
                    "\t\t" + h.getAssQstartYear() + ",\n" +
                    "\t\t" + h.getProcessUpdateWarant() + ",\n" +
                    "\t\t" + h.getProcessUpdateArrears() + ",\n" +
                    "\t\t'" + h.getProcessUpdateComment() + "',\n" +
                    "\t\t" + h.getAssQstartTyoldArrias() + ",\n" +
                    "" + h.getAssQstartTyoldWarant() + " \n" +
                    "\t)");

            conn.DB.setData("INSERT INTO `ass_payhistry` (\n" +
                    "\t`ass_PayHistry_Qcunt`,\n" +
                    "\t`ass_PayHistry_year`,\n" +
                    "\t`ass_PayHistry_Date`,\n" +
                    "\t`ass_PayHistry_status`,\n" +
                    "\t`ass_PayHistry_comment`,\n" +
                    "\t`ass_PayHistry_TotalPayid`,\n" +
                    "\t`ass_PayHistry_Q1`,\n" +
                    "\t`ass_PayHistry_Q2`,\n" +
                    "\t`ass_PayHistry_Q3`,\n" +
                    "\t`ass_PayHistry_Q4`,\n" +
                    "\t`ass_PayHistry_Over`,\n" +
                    "\t`Assessment_idAssessment`,\n" +
                    "\t`ass_PayHistry_DRQ1`,\n" +
                    "\t`ass_PayHistry_DRQ2`,\n" +
                    "\t`ass_PayHistry_DRQ3`,\n" +
                    "\t`ass_PayHistry_DRQ4`,\n" +
                    "\t`ass_PayHistry_Q1Status`,\n" +
                    "\t`ass_PayHistry_Q2Status`,\n" +
                    "\t`ass_PayHistry_Q3Status`,\n" +
                    "\t`ass_PayHistry_Q4Status` \n" +
                    ")\n" +
                    "VALUES\n" +
                    "\t(\n" +
                    "\t\t" + h.getAssQstartQuaterNumber() + ",\n" +
                    "\t\t" + h.getAssQstartYear() + ",\n" +
                    "\t\t'" + h.getAssQstartProcessDate() + "',\n" +
                    "\t\t" + h.getAssPayHistryStatus() + ",\n" +
                    "\t\t'" + h.getAssPayHistryComment() + "',\n" +
                    "\t\t" + h.getAssPayHistryTotalPayid() + ",\n" +
                    "\t\t" + h.getAssPayHistryQ1() + ",\n" +
                    "\t\t" + h.getAssPayHistryQ2() + ",\n" +
                    "\t\t" + h.getAssPayHistryQ3() + ",\n" +
                    "\t\t" + h.getAssPayHistryQ4() + ",\n" +
                    "\t\t" + h.getAssPayHistryOver() + ",\n" +
                    "\t\t" + h.getAssessment() + ",\n" +
                    "\t\t" + h.getAssPayHistryDrq1() + ",\n" +
                    "\t\t" + h.getAssPayHistryDrq2() + ",\n" +
                    "\t\t" + h.getAssPayHistryDrq3() + ",\n" +
                    "\t\t" + h.getAssPayHistryDrq4() + ",\n" +
                    "\t\t" + h.getAssPayHistryQ1status() + ",\n" +
                    "\t\t" + h.getAssPayHistryQ2status() + ",\n" +
                    "\t\t" + h.getAssPayHistryQ3status() + ",\n" +
                    "" + h.getAssPayHistryQ4status() + " \n" +
                    "\t)");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.gc();
        }

    }


}
