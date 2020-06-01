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

public class ThreeAtOnceState extends AbstractCollective implements Runnable {
    List<String> recovered = new ArrayList<>();
    List<Timestamp> timestampList = new ArrayList<>();
    List<String> recoveredList = new ArrayList<>();
    List<String> caseListValue = new ArrayList<>();
    List<String> caseListConsole = new ArrayList<>();
    List<Timestamp> timestampListCase = new ArrayList<>();
    List<String> DeathsListValue = new ArrayList<>();
    List<String> DeathsListConsole = new ArrayList<>();
    List<Timestamp> timeStampDeath = new ArrayList<>();
    public final String stateValue;
    public final String checkedStateMatch;
    static int choice;
    public final String orgininalStateValue;
    public final String locationNotSupportedString = "Location Not Supported";
    DataBaseStore dataBaseStore = new DataBaseStore();



    public ThreeAtOnceState(int choiceValueActual, String states) {
        this.orgininalStateValue = states;
        this.stateValue = states.replaceAll("\\s", "").toLowerCase();
        this.choice = choiceValueActual;
        this.checkedStateMatch = checkIfStateExists(stateValue);

    }

    @Override
    public void run() {
        extractData();
    }

    public void extractData() {

        try {
            if (choice > 0) {


                boolean reOccur = true;
                while (reOccur) {
                    if (checkedStateMatch.equals(locationNotSupportedString)) {
                        System.out.println("Location Not Supported");
                        break;
                    }
                    try {
                        String url = "https://www.worldometers.info/coronavirus/usa/" + checkedStateMatch + "/";
                        Document document = Jsoup.connect(url).userAgent("mozilla/17.0").get();
                        Elements temp = document.select("div#maincounter-wrap");

                        String notFoundErrorParser = document.title();
                        if (notFoundErrorParser.equals("404 Not Found")) {
                            System.out.println("Data Is Unreachable for "+orgininalStateValue);
                            break;
                        }
                        int i = 0;

                        for (Element values : temp) {
                            i++;


                            if (i == 3) {
                                Timestamp timestamp = new Timestamp(System.currentTimeMillis());


                                String RecoveredtoWrite = timestamp + " -> " + values.getElementsByTag("span").first().text();
                                String recoveredData = values.getElementsByTag("span").first().text();

                                //synchronized not needed, but added as a thread-safe precaution
                                synchronized (this) {
                                    System.out.println(RecoveredtoWrite + " patients recovered");

                                }
                                dataBaseStore.createStateTable(DataBaseStore.CREATE_TABLE_STATE_RECOVERED);
                                dataBaseStore.insertIntoStateData(timestamp.toString(), recoveredData, DataBaseStore.TABLE_STATE_RECOVERED);

                                recovered.add(RecoveredtoWrite);
                                timestampList.add(timestamp);
                                recoveredList.add(values.getElementsByTag("span").first().text());

                                FileWriter pathFile = null;
                                try {
                                    pathFile = new FileWriter("Srecovered.txt");
                                    for (String elements : recovered) {
                                        pathFile.write(elements + "\n");
                                    }

                                    try {
                                        //You can change file name to your needs
                                        //This file name is where the excel document is stored
                                        String fileName = "C:" + File.separator + "Users" + File.separator + "sport" + File.separator + "Desktop" + File.separator + "WebScraperData" + File.separator + "excel_documents" + File.separator + "Srecovered.xls";
                                        WritableWorkbook workbookRecovered = Workbook.createWorkbook(new File(fileName));
                                        WritableSheet sheet = workbookRecovered.createSheet("Recovered Data", 1);

                                        Label stateValueLabel = new Label(2, 0, orgininalStateValue);
                                        Label timeStampLabel = new Label(0, 0, "TimeStamp");
                                        Label recoveredLabelText = new Label(1, 0, "Recovered");
                                        sheet.addCell(stateValueLabel);
                                        sheet.addCell(timeStampLabel);
                                        sheet.addCell(recoveredLabelText);


                                        int recoveredColumn = 1;
                                        int recoveredRow = 0;




                                        for (String recoveredValueEach : recoveredList) {
                                            recoveredRow++;
                                           Label recoveredLabel = new Label(recoveredColumn, recoveredRow, recoveredValueEach);
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
                                    System.out.println("Error");
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


                            } else if (i == 1) {
                                Timestamp timestampC = new Timestamp(System.currentTimeMillis());

                                String casesToWrite = timestampC + " -> " + values.getElementsByTag("span").first().text();
                                String caseData = values.getElementsByTag("span").first().text();

                                //synchronized not needed, but added as a thread-safe precaution
                               synchronized (this){
                                   System.out.println(casesToWrite + " cases");
                               }
                               dataBaseStore.createStateTable(DataBaseStore.CREATE_TABLE_STATE_CASES);
                               dataBaseStore.insertIntoStateData(timestampC.toString(), caseData, DataBaseStore.TABLE_STATE_CASES);

                                caseListConsole.add(casesToWrite);
                                timestampListCase.add(timestampC);
                                caseListValue.add(values.getElementsByTag("span").first().text());

                                FileWriter pathFile = null;
                                try {
                                    pathFile = new FileWriter("ScasesList.txt");
                                    for (String elements : caseListConsole) {
                                        pathFile.write(elements + "\n");
                                    }


                                    try {
                                        //You can change file name to your needs
                                        //This file name is where the excel document is stored
                                        String fileName = "C:" + File.separator + "Users" + File.separator + "sport" + File.separator + "Desktop" + File.separator + "WebScraperData" + File.separator + "excel_documents" + File.separator + "Scases.xls";

                                        WritableWorkbook workbookC = Workbook.createWorkbook(new File(fileName));
                                        WritableSheet sheetC = workbookC.createSheet("Cases Data", 1);

                                        Label stateValueLabel = new Label(2, 0, orgininalStateValue);
                                        Label timeStampLabelText = new Label(0, 0, "TimeStamp");
                                        Label casesLabelText = new Label(1, 0, "Cases");
                                        sheetC.addCell(timeStampLabelText);
                                        sheetC.addCell(stateValueLabel);
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
                                } finally {
                                    try {
                                        if (pathFile != null) {
                                            pathFile.close();
                                        }
                                    } catch (IOException e) {
                                        System.out.println("Error");
                                        e.printStackTrace();
                                    }

                                }

                            } else if (i == 2) {
                                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                                String DeathsToWrite = timestamp + " -> " + values.getElementsByTag("span").first().text();
                                String deathsValue = values.getElementsByTag("span").first().text();

                                //synchronized not needed, but added as a thread-safe precaution
                                synchronized (this){
                                    System.out.println(DeathsToWrite + " deaths");

                                }
                                dataBaseStore.createStateTable(DataBaseStore.CREATE_TABLE_STATE_DEATHS);
                                dataBaseStore.insertIntoStateData(timestamp.toString(), deathsValue, DataBaseStore.TABLE_STATE_DEATHS);
                                DeathsListConsole.add(DeathsToWrite);
                                timeStampDeath.add(timestamp);
                                DeathsListValue.add(values.getElementsByTag("span").first().text());

                                FileWriter pathFile = null;
                                try {
                                    pathFile = new FileWriter("SdeathsList.txt");
                                    for (String elements : DeathsListConsole) {
                                        pathFile.write(elements + "\n");
                                    }

                                    try {
                                        //You can change file name to your needs
                                        //This file name is where the excel document is stored
                                        String fileName = "C:" + File.separator + "Users" + File.separator + "sport" + File.separator + "Desktop" + File.separator + "WebScraperData" + File.separator + "excel_documents" + File.separator + "Sdeaths.xls";
                                        WritableWorkbook workbookD = Workbook.createWorkbook(new File(fileName));
                                        WritableSheet sheetD = workbookD.createSheet("Deaths Data", 1);

                                        Label stateValueLabel = new Label(2, 0, orgininalStateValue);
                                        Label timeStampLabelText = new Label(0, 0, "TimeStamp");
                                        Label deathsLabelText = new Label(1, 0, "Deaths");
                                        sheetD.addCell(timeStampLabelText);
                                        sheetD.addCell(deathsLabelText);
                                        sheetD.addCell(stateValueLabel);


                                        int casesColumnD = 1;
                                        int caseRowD = 0;
                                        Label DeathsLabelReal;

                                        for (String deathsListEach : DeathsListValue) {
                                            caseRowD++;
                                            DeathsLabelReal = new Label(casesColumnD, caseRowD, deathsListEach);
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
                                    System.out.println("Error");
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        if (pathFile != null) {
                                            pathFile.close();
                                        }
                                    } catch (IOException e) {
                                        System.out.println("Error");
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
                        System.out.println("Error");
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

