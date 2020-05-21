package com.example.sport;

import jxl.Workbook;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

public class ThreeAtOnce implements Runnable {
    //Initialization of array lists, scanners, and class id
    private static Scanner scanner = new Scanner(System.in);
    List<String> recovered = new ArrayList<>();
    List<Timestamp> timestampList = new ArrayList<>();
    List<String> recoveredList = new ArrayList<>();
    List<String> caseListValue = new ArrayList<>();
    private static final int classsId = 1;
    List<String> caseListConsole = new ArrayList<>();
    List<Timestamp> timestampListCase = new ArrayList<>();
    List<String> DeathsListValue = new ArrayList<>();
    List<String> DeathsListConsole = new ArrayList<>();
    List<Timestamp> timeStampDeath = new ArrayList<>();


    public static int getClasssId() {
        return classsId;
    }


    @Override
    public void run() {


        extractCases();
    }

    public void extractCases() {
        System.out.println("Enter how many times a day you want a number");

        try { //try block used in case inputmismatch exception is thrown


            int choice = scanner.nextInt();
            if (choice > 0) {


                boolean reOccur = true;


                int columnTimeStamp = 0;
                int row = 3;
                int columnCases = 1;


                while (reOccur) {


                    try {

                        Document document = Jsoup.connect("https://www.worldometers.info/coronavirus/").userAgent("mozilla/17.0").get(); //Target website
                        Elements temp = document.select("div#maincounter-wrap"); //fetching all numbers within div


                        int counter = 0;


                        for (Element values : temp) {
                            counter++;

                            if (counter == 3) {
                                //A counter value of 3 will result in the recovered data being fetched
                                Timestamp timestamp = new Timestamp(System.currentTimeMillis());


                                String RecoveredtoWrite = timestamp + " -> " + values.getElementsByTag("span").first().text();

                                //synchronized not needed, but added as a thread-safe precaution
                                synchronized (this) {
                                    System.out.println(RecoveredtoWrite + " patients recovered");

                                }
                                recovered.add(RecoveredtoWrite);
                                timestampList.add(timestamp);
                                recoveredList.add(values.getElementsByTag("span").first().text());

                                FileWriter pathFile = null;
                                try {
                                    pathFile = new FileWriter("recoveredList.txt");
                                    for (String elements : recovered) {
                                        pathFile.write(elements + "\n");
                                    }

                                    try {
                                        String fileName = "C:" + File.separator + "Users" + File.separator + "sport" + File.separator + "Desktop" + File.separator + "WebScraperData" + File.separator + "excel_documents" + File.separator + "recovered.xls";
                                        WritableWorkbook workbookRecovered = Workbook.createWorkbook(new File(fileName));
                                        WritableSheet sheet = workbookRecovered.createSheet("Deaths Data", 1);
                                        //   sheet.setName("Deaths data");


//                                        Label timeStampLabel = new Label(0, 1, "TimeStamp");
//                                        Label recoveredLabel = new Label(1, 1, "Recovered");
//                                        sheet.addCell(timeStampLabel);
//                                        sheet.addCell(recoveredLabel);


                                        boolean isActive = true;


                                        String cases = values.getElementsByTag("span").first().text();

                                        int recoveredColumn = 1;
                                        int recoveredRow = -1;
                                        Label caseLabels;

                                        for (String casesValuesEach : recoveredList) {
                                            recoveredRow++;
                                            Label recoveredLabel = new Label(recoveredColumn, recoveredRow, casesValuesEach);
                                            sheet.addCell(recoveredLabel);


                                        }
                                        int timeStampColumnR = 0;
                                        int timeStampRowR = -1;
                                        Label timeStampLabels;
                                        for (Timestamp timestampValues : timestampList) {
                                            String timeStampValueOf = timestampValues.toString();
                                            timeStampRowR++;
                                            timeStampLabels = new Label(timeStampColumnR, timeStampRowR, timeStampValueOf);
                                            sheet.addCell(timeStampLabels);


                                        }

                                        workbookRecovered.write();
                                        workbookRecovered.close();


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


                            } else if (counter == 1) {
                                Timestamp timestampC = new Timestamp(System.currentTimeMillis());

                                String casesToWrite = timestampC + " -> " + values.getElementsByTag("span").first().text();
                                String casesValueOf = values.getElementsByTag("span").first().text();

                                System.out.println(casesToWrite + " cases");

                                caseListConsole.add(casesToWrite);
                                timestampListCase.add(timestampC);
                                caseListValue.add(values.getElementsByTag("span").first().text());

                                FileWriter pathFile = null;
                                try {
                                    pathFile = new FileWriter("casesList.txt");
                                    for (String elements : caseListConsole) {
                                        pathFile.write(elements + "\n");
                                    }


                                    try {
                                        String fileName = "C:" + File.separator + "Users" + File.separator + "sport" + File.separator + "Desktop" + File.separator + "WebScraperData" + File.separator + "excel_documents" + File.separator + "cases.xls";

                                        WritableWorkbook workbookC = Workbook.createWorkbook(new File(fileName));
                                        WritableSheet sheetC = workbookC.createSheet("Cases Data", 1);

//                                        Label timeStampLabel = new Label(0, 1, "TimeStamp");
//                                        Label casesLabel = new Label(1, 1, "Cases");
//                                        sheetC.addCell(timeStampLabel);
//                                        sheetC.addCell(casesLabel);


                                        boolean isActive = true;


                                        String cases = values.getElementsByTag("span").first().text();

                                        int casesColumn = 1;
                                        int caseRow = -1;

                                        for (String casesValuesEach : caseListValue) {
                                            caseRow++;
                                            Label casesLabel = new Label(casesColumn, caseRow, casesValuesEach);
                                            sheetC.addCell(casesLabel);


                                        }
                                        int timeStampColumn = 0;
                                        int timeStampRow = -1;
                                        Label timeStampLabels;
                                        for (Timestamp timestampValues : timestampListCase) {
                                            String timeStampValueOf = timestampValues.toString();
                                            timeStampRow++;
                                            timeStampLabels = new Label(timeStampColumn, timeStampRow, timeStampValueOf);
                                            sheetC.addCell(timeStampLabels);


                                        }
                                        workbookC.write();
                                        workbookC.close();


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

                            } else if (counter == 2) {
                                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                                String DeathsToWrite = timestamp + " -> " + values.getElementsByTag("span").first().text();
                                String casesValueOf = values.getElementsByTag("span").first().text();

                                System.out.println(DeathsToWrite + " deaths");


                                DeathsListConsole.add(DeathsToWrite);
                                timeStampDeath.add(timestamp);
                                DeathsListValue.add(values.getElementsByTag("span").first().text());

                                FileWriter pathFile = null;
                                try {
                                    pathFile = new FileWriter("deathsList.txt");
                                    for (String elements : DeathsListConsole) {
                                        pathFile.write(elements + "\n");
                                    }

                                    try {
                                        String fileName = "C:" + File.separator + "Users" + File.separator + "sport" + File.separator + "Desktop" + File.separator + "WebScraperData" + File.separator + "excel_documents" + File.separator + "deaths.xls";
                                        WritableWorkbook workbookD = Workbook.createWorkbook(new File(fileName));
                                        WritableSheet sheetD = workbookD.createSheet("Deaths Data", 1);
                                        //   sheet.setName("Deaths data");


//                                        Label timeStampLabel = new Label(0, 1, "TimeStamp");
//                                        Label deathsLabel = new Label(1, 1, "Deaths");
//                                        sheetD.addCell(timeStampLabel);
//                                        sheetD.addCell(deathsLabel);


                                        boolean isActive = true;


                                        String cases = values.getElementsByTag("span").first().text();

                                        int casesColumnD = 1;
                                        int caseRowD = -1;
                                        Label caseLabels;

                                        Label DeathsLabelReal;

                                        for (String casesValuesEach : DeathsListValue) {
                                            caseRowD++;
                                            DeathsLabelReal = new Label(casesColumnD, caseRowD, casesValuesEach);
                                            sheetD.addCell(DeathsLabelReal);


                                        }
                                        int timeStampColumnD = 0;
                                        int timeStampRowD = -1;
                                        Label timeStampLabelD;
                                        for (Timestamp timestampValues : timeStampDeath) {
                                            String timeStampValueOf = timestampValues.toString();
                                            timeStampRowD++;
                                            timeStampLabelD = new Label(timeStampColumnD, timeStampRowD, timeStampValueOf);
                                            sheetD.addCell(timeStampLabelD);


                                        }

                                        workbookD.write();
                                        workbookD.close();


                                    } catch (IOException e) {
                                        System.out.println("IO Error");
                                        e.printStackTrace();
                                        reOccur = false;
                                        break;

                                    } catch (NumberFormatException e) {
                                        System.out.println("Number format Error");
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


                            }


                        }


                        try {
                            long chosenTime = 86400000 / choice; //milliseconds in a day divided by how many times a day the client wants to receive the data
                            Thread.sleep(chosenTime);
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

}
