package com.example.sport;

import jxl.NumberCell;
import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class TestClass extends AbstractCollective implements Runnable{
    private static Scanner scanner = new Scanner(System.in);
    Map<Timestamp, String> casesMap = new HashMap<>();
    List<String> casesList = new ArrayList<>();
    List<Timestamp> timestampList = new ArrayList<>();
    List<String> caseListValue = new ArrayList<>();
    ReentrantLock lock = new ReentrantLock(true);
    ExecutorService serviceWork = Executors.newFixedThreadPool(1);
    static int choiceValue;
    public final String locationNotSupportedString = "Location Not Supported";



    public TestClass (int choiceValueActual){
        this.choiceValue = choiceValueActual;

    }




    @Override
    public void run() {

        extractCases();
    }

    public void extractCases() {
        try {



            if (choiceValue > 0) {



                boolean reOccur = true;


                int columnTimeStamp = 0;
                int row = 3;
                int columnCases = 1;


                while (reOccur) {



                      /*  for(States states : States.values()){
                            if(stateValue.equals(states.toString())){
                                System.out.println(stateValue + " Matched ");
                            }

                        }
                    System.out.println("Location Not Supported");
                        */

                    try {


                        //    Document document = Jsoup.connect("https://www.worldometers.info/coronavirus/").userAgent("mozilla/17.0").get();
                        String url = "https://www.worldometers.info/coronavirus/";
                        Document document = Jsoup.connect(url).userAgent("mozilla/17.0").get();

                        Elements temp = document.select("div#maincounter-wrap"); //fetching all numbers

                        int i = 0;


                        for (Element values : temp) {
                            i++;

                            if (i == 1) {
                                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                                String casesToWrite = timestamp + " -> " + values.getElementsByTag("span").first().text();
                                String casesValueOf = values.getElementsByTag("span").first().text();

                                System.out.println(casesToWrite);

                                casesList.add(casesToWrite);
                                timestampList.add(timestamp);
                                caseListValue.add(values.getElementsByTag("span").first().text());



                                FileWriter pathFile = null;
                                try {
                                    pathFile = new FileWriter("casesList.txt");
                                    for (String elements : casesList) {
                                        pathFile.write(elements + "\n");
                                    }


                                    try {

                                        //Filename can be changed to any file path you want
                                        //The "filename" String variable stores the location of the excel document you want to write to

                                        String fileName = "C:" + File.separator + "Users" + File.separator + "sport" + File.separator + "Desktop" + File.separator + "WebScraperData" + File.separator + "excel_documents" + File.separator + "cases.xls";
                                        File file = new File(fileName);


                                        WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName));
                                        WritableSheet sheet = workbook.createSheet("Cases Data", 1);

                                        Label timeStampLabel = new Label(0, 1, "TimeStamp");
                                        Label casesLabel = new Label(1, 1, "Cases");
                                        sheet.addCell(timeStampLabel);
                                        sheet.addCell(casesLabel);


                                        boolean isActive = true;


                                        String cases = values.getElementsByTag("span").first().text();

                                        int casesColumn = 1;
                                        int caseRow = 2;
                                        Label caseLabels;

                                        for (String casesValuesEach : caseListValue) {
                                            caseRow++;
                                            caseLabels = new Label(casesColumn, caseRow, casesValuesEach);
                                            sheet.addCell(caseLabels);


                                        }
                                        int timeStampColumn = 0;
                                        int timeStampRow = 2;
                                        Label timeStampLabels;
                                        for (Timestamp timestampValues : timestampList) {
                                            String timeStampValueOf = timestampValues.toString();
                                            timeStampRow++;
                                            timeStampLabels = new Label(timeStampColumn, timeStampRow, timeStampValueOf);
                                            sheet.addCell(timeStampLabels);


                                        }
                                        workbook.write();
                                        workbook.close();


                                        Runtime.getRuntime().addShutdownHook(new Thread(){
                                            @Override
                                            public void run() {
                                                System.out.println("Closing");
                                            }
                                        });

                                    } catch (IOException e) {
                                        System.out.println("IO Error");
                                        e.printStackTrace();
                                        reOccur = false;
                                        break;

                                    } catch (NumberFormatException e) {
                                        System.out.println("Number format error");
                                        e.printStackTrace();
                                        reOccur = false;
                                        break;
                                    } catch (RowsExceededException e) {
                                        System.out.println("Rows Exceeded Error");
                                        e.printStackTrace();
                                        reOccur = false;
                                        break;
                                    } catch (WriteException e) {
                                        System.out.println("Write Exception");
                                        e.printStackTrace();
                                        reOccur = false;
                                        break;
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        if (pathFile != null) {
                                            pathFile.close();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }


                                // casesMap.put(timestamp, casesValueOf);
                            } else {
                                continue;
                            }


                        }


                        try {
                            long chosenTime = 86400000 / choiceValue;
                            Thread.sleep(chosenTime); //28,800
                        } catch (InterruptedException e) {
                            System.out.println("Error");
                            e.printStackTrace();
                            reOccur = false;
                            break;
                        }

                    } catch (IOException e) {
                        System.out.println("IO Exception");
                        System.out.println("Error");
                        reOccur = false;
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Number Format Exception");
                        reOccur = false;
                        break;
                    }

                }

            } else {
                System.out.println("Enter a value higher than 0");
            }


        } catch (InputMismatchException e) {
            System.out.println("Enter a valid number");
        }

    }
    public String checkIfStateExists(String state) {
        for (States states : States.values()) {
            if (state.equals(states.toString().toLowerCase())) {
                String stateMatch = states.getRealName();

                return stateMatch;
            }
        }
        return "Location Not Supported";

    }

}
