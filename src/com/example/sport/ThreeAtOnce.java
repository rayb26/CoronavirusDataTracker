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
    private static Scanner scanner = new Scanner(System.in);
    List<String> recovered = new ArrayList<>();
    List<Timestamp> timestampList = new ArrayList<>();
    List<String> recoveredList = new ArrayList<>();
    List<String> caseListValue = new ArrayList<>();
    List<String> caseListConsole = new ArrayList<>();
    List<Timestamp> timestampListCase = new ArrayList<>();
    List<String> DeathsListValue = new ArrayList<>();
    List<String> DeathsListConsole = new ArrayList<>();
    List<Timestamp> timeStampDeath = new ArrayList<>();




    @Override
    public void run() {


        extractData();
    }

    public void extractData() {
        System.out.println("Enter how many times a day you want a number");

        try {


            int choice = scanner.nextInt();
            if (choice > 0) {


                boolean reOccur = true;


                while (reOccur) {


                    try {

                        Document document = Jsoup.connect("https://www.worldometers.info/coronavirus/").userAgent("mozilla/17.0").get(); //Target website
                        Elements temp = document.select("div#maincounter-wrap"); //fetching all numbers within div


                        //Counter increments as program looks at next div element on webpage
                        int counter = 0;


                        for (Element values : temp) {
                            counter++;

                            if (counter == 3) {
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
                                        //You can change file name to your needs
                                        //This file name is where the excel document is stored
                                        String fileName = "C:" + File.separator + "Users" + File.separator + "sport" + File.separator + "Desktop" + File.separator + "WebScraperData" + File.separator + "excel_documents" + File.separator + "recovered.xls";
                                        File fileCheck = new File(fileName);
                                        if(fileCheck.exists() && !fileCheck.isDirectory()) {





                                        }
                                        WritableWorkbook workbookRecovered = Workbook.createWorkbook(new File(fileName));
                                        WritableSheet sheet = workbookRecovered.createSheet("Recovered Data", 1);


                                        Label timeStampLabel = new Label(0, 0, "TimeStamp");
                                        Label recoveredLabelText = new Label(1, 0, "Recovered");
                                        sheet.addCell(timeStampLabel);
                                        sheet.addCell(recoveredLabelText);

                                        int recoveredColumn = 1;
                                        int recoveredRow = 0;

                                        for (String casesValuesEach : recoveredList) {
                                            recoveredRow++;
                                            Label recoveredLabel = new Label(recoveredColumn, recoveredRow, casesValuesEach);
                                            sheet.addCell(recoveredLabel);


                                        }
                                        int timeStampColumnR = 0;
                                        int timeStampRowR = 0;
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
                                        System.out.println("Error");
                                        e.printStackTrace();
                                        reOccur = false;
                                        break;
                                    } catch (RowsExceededException e) {
                                        System.out.println("Error");
                                        e.printStackTrace();
                                        reOccur = false;
                                        break;
                                    } catch (WriteException e) {
                                        System.out.println("Error");
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

                            } else if (counter == 1) {
                                Timestamp timestampC = new Timestamp(System.currentTimeMillis());

                                String casesToWrite = timestampC + " -> " + values.getElementsByTag("span").first().text();

                                //synchronized not needed, but added as a thread-safe precaution
                                synchronized (this){
                                    System.out.println(casesToWrite + " cases");
                                }

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
                                        //You can change file name to your needs
                                        //This file name is where the excel document is stored
                                        String fileName = "C:" + File.separator + "Users" + File.separator + "sport" + File.separator + "Desktop" + File.separator + "WebScraperData" + File.separator + "excel_documents" + File.separator + "cases.xls";

                                        WritableWorkbook workbookC = Workbook.createWorkbook(new File(fileName));
                                        WritableSheet sheetC = workbookC.createSheet("Cases Data", 1);

                                        Label timeStampLabel = new Label(0, 0, "TimeStamp");
                                        Label casesLabelText = new Label(1, 0, "Cases");
                                        sheetC.addCell(timeStampLabel);
                                        sheetC.addCell(casesLabelText);



                                        int casesColumn = 1;
                                        int caseRow = 0;

                                        for (String casesValuesEach : caseListValue) {
                                            caseRow++;
                                            Label casesLabel = new Label(casesColumn, caseRow, casesValuesEach);
                                            sheetC.addCell(casesLabel);


                                        }
                                        int timeStampColumn = 0;
                                        int timeStampRow = 0;
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
                                        System.out.println("Error");
                                        e.printStackTrace();
                                        reOccur = false;
                                        break;

                                    } catch (NumberFormatException e) {
                                        System.out.println("Error");
                                        e.printStackTrace();
                                        reOccur = false;
                                        break;
                                    } catch (RowsExceededException e) {
                                        System.out.println("Error");
                                        e.printStackTrace();
                                        reOccur = false;
                                        break;
                                    } catch (WriteException e) {
                                        System.out.println("Error");
                                        e.printStackTrace();
                                        reOccur = false;
                                        break;
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    System.out.println("Error");
                                } finally {
                                    try {
                                        if (pathFile != null) {
                                            pathFile.close();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        System.out.println("Error");
                                    }

                                }

                            } else if (counter == 2) {
                                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                                String DeathsToWrite = timestamp + " -> " + values.getElementsByTag("span").first().text();

                                //synchronized not needed, but added as a thread-safe precaution
                                synchronized (this){
                                    System.out.println(DeathsToWrite + " deaths");
                                }


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
                                        //You can change file name to your needs
                                        //This file name is where the excel document is stored
                                        String fileName = "C:" + File.separator + "Users" + File.separator + "sport" + File.separator + "Desktop" + File.separator + "WebScraperData" + File.separator + "excel_documents" + File.separator + "deaths.xls";
                                        WritableWorkbook workbookD = Workbook.createWorkbook(new File(fileName));
                                        WritableSheet sheetD = workbookD.createSheet("Deaths Data", 0);


                                        Label timeStampLabel = new Label(0, 0, "TimeStamp");
                                        Label deathsLabel = new Label(1, 0, "Deaths");
                                        sheetD.addCell(timeStampLabel);
                                        sheetD.addCell(deathsLabel);


                                        int casesColumnD = 1;
                                        int caseRowD = 0;

                                        Label DeathsLabelReal;

                                        for (String casesValuesEach : DeathsListValue) {
                                            caseRowD++;
                                            DeathsLabelReal = new Label(casesColumnD, caseRowD, casesValuesEach);
                                            sheetD.addCell(DeathsLabelReal);


                                        }
                                        int timeStampColumnD = 0;
                                        int timeStampRowD = 0;
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
                                        System.out.println("Error");
                                        e.printStackTrace();
                                        reOccur = false;
                                        break;

                                    } catch (NumberFormatException e) {
                                        System.out.println("Error");
                                        e.printStackTrace();
                                        reOccur = false;
                                        break;
                                    } catch (RowsExceededException e) {
                                        System.out.println("Error");
                                        e.printStackTrace();
                                        reOccur = false;
                                        break;
                                    } catch (WriteException e) {
                                        System.out.println("Error");
                                        e.printStackTrace();
                                        reOccur = false;
                                        break;
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    System.out.println("Error");
                                } finally {
                                    try {
                                        if (pathFile != null) {
                                            pathFile.close();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        System.out.println("Error");
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
                        System.out.println("Error");
                        reOccur = false;
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Error");
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
