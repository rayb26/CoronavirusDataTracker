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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class ThreeAtOnceCountry extends AbstractCollective implements Runnable {
    List<String> recovered = new ArrayList<>();
    List<Timestamp> timestampList = new ArrayList<>();
    List<String> refactorList = new ArrayList<>();
    List<String> caseListValue = new ArrayList<>();
    List<String> caseListConsole = new ArrayList<>();
    List<Timestamp> timestampListCase = new ArrayList<>();
    List<String> DeathsListValue = new ArrayList<>();
    List<String> DeathsListConsole = new ArrayList<>();
    List<Timestamp> timeStampDeath = new ArrayList<>();
    private static final int classsId = 3;
    ReentrantLock lock = new ReentrantLock(true);
    ExecutorService serviceWork = Executors.newFixedThreadPool(1);
    public final String countryValue;
    public final String checkedCountryMatch;
    static int choice;
    public final String originalCountryValue;
    public final String locationNotSupportedString = "Location Not Supported";


    public static int getClasssId() {
        return classsId;
    }

    public ThreeAtOnceCountry(int choiceValueActual, String country) {
        this.originalCountryValue = country;
        this.countryValue = country.replaceAll("\\s", "").toLowerCase();
        this.choice = choiceValueActual;
        this.checkedCountryMatch = checkIfStateExists(countryValue);

    }

    @Override
    public void run() {
        System.out.println("Data of " + originalCountryValue);
        extractCases();
    }

    public void extractCases() {

        try {
            if (choice > 0) {


                boolean reOccur = true;
                while (reOccur) {
                    if (checkedCountryMatch.equals(locationNotSupportedString)) {
                        System.out.println("Location Not Supported");
                        break;
                    }
                    try {
                        String url = "https://www.worldometers.info/coronavirus/country/" + checkedCountryMatch + "/";
                        Document document = Jsoup.connect(url).userAgent("mozilla/17.0").get();
                        Elements temp = document.select("div#maincounter-wrap");

                        String notFoundErrorParser = document.title();
                        if (notFoundErrorParser.equals("404 Not Found")) {
                            System.out.println("Country's Data Is Unreachable");
                            break;
                        }
                        int i = 0;

                        for (Element values : temp) {
                            i++;

                            if (i == 3) {
                                Timestamp timestamp = new Timestamp(System.currentTimeMillis());


                                String RecoveredtoWrite = timestamp + " -> " + values.getElementsByTag("span").first().text();

                                String casesValueOf = values.getElementsByTag("span").first().text();
                                synchronized (this) {
                                    System.out.println(RecoveredtoWrite + " patients recovered");

                                }
                                recovered.add(RecoveredtoWrite);
                                timestampList.add(timestamp);
                                refactorList.add(values.getElementsByTag("span").first().text());

                                FileWriter pathFile = null;
                                try {
                                    pathFile = new FileWriter("Crecovered.txt");
                                    for (String elements : recovered) {
                                        pathFile.write(elements + "\n");
                                    }

                                    try {
                                        String fileName = "C:" + File.separator + "Users" + File.separator + "sport" + File.separator + "Desktop" + File.separator + "WebScraperData" + File.separator + "excel_documents" + File.separator + "Crecovered.xls";
                                        WritableWorkbook workbookRecovered = Workbook.createWorkbook(new File(fileName));
                                        WritableSheet sheet = workbookRecovered.createSheet("Deaths Data", 1);
                                        //   sheet.setName("Deaths data");

                                        Label countryValueLabel = new Label(2, 0, originalCountryValue);
//                                        Label timeStampLabel = new Label(0, 2, "TimeStamp");
//                                        Label recoveredLabel = new Label(1, 2, "Recovered");
                                        sheet.addCell(countryValueLabel);
//                                        sheet.addCell(timeStampLabel);
//                                        sheet.addCell(recoveredLabel);


                                        boolean isActive = true;


                                        String cases = values.getElementsByTag("span").first().text();

                                        int recoveredColumn = 1;
                                        int recoveredRow = -1;
                                        Label caseLabels;

                                        for (String casesValuesEach : refactorList) {
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
                                    System.out.println("Output Error");
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


                            } else if (i == 1) {
                                Timestamp timestampC = new Timestamp(System.currentTimeMillis());

                                String casesToWrite = timestampC + " -> " + values.getElementsByTag("span").first().text();
                                String casesValueOf = values.getElementsByTag("span").first().text();

                                System.out.println(casesToWrite + " cases");

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
                                        String fileName = "C:" + File.separator + "Users" + File.separator + "sport" + File.separator + "Desktop" + File.separator + "WebScraperData" + File.separator + "excel_documents" + File.separator + "Ccases.xls";

                                        WritableWorkbook workbookC = Workbook.createWorkbook(new File(fileName));
                                        WritableSheet sheetC = workbookC.createSheet("Cases Data", 1);

                                        Label countryValueLabel = new Label(2, 0, originalCountryValue);

                                        sheetC.addCell(countryValueLabel);


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
                                        System.out.println("IO Error");
                                        e.printStackTrace();
                                    }

                                }

                            } else if (i == 2) {
                                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                                String DeathsToWrite = timestamp + " -> " + values.getElementsByTag("span").first().text();
                                String casesValueOf = values.getElementsByTag("span").first().text();

                                System.out.println(DeathsToWrite + " deaths");


                                DeathsListConsole.add(DeathsToWrite);
                                timeStampDeath.add(timestamp);
                                DeathsListValue.add(values.getElementsByTag("span").first().text());

                                FileWriter pathFile = null;
                                try {
                                    pathFile = new FileWriter("CdeathsList.txt");
                                    for (String elements : DeathsListConsole) {
                                        pathFile.write(elements + "\n");
                                    }

                                    try {
                                        String fileName = "C:" + File.separator + "Users" + File.separator + "sport" + File.separator + "Desktop" + File.separator + "WebScraperData" + File.separator + "excel_documents" + File.separator + "Cdeaths.xls";
                                        WritableWorkbook workbookD = Workbook.createWorkbook(new File(fileName));
                                        WritableSheet sheetD = workbookD.createSheet("Deaths Data", 1);
                                        //   sheet.setName("Deaths data");

                                        Label countryValueLabel = new Label(2, 0, originalCountryValue);

                                        sheetD.addCell(countryValueLabel);


                                        boolean isActive = true;


                                        String cases = values.getElementsByTag("span").first().text();

                                        int casesColumnD = 1;
                                        int caseRowD = -1;
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
                                    System.out.println("IO Error");
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        if (pathFile != null) {
                                            pathFile.close();
                                        }
                                    } catch (IOException e) {
                                        System.out.println("IO Error");
                                        e.printStackTrace();
                                    }

                                }


                            }


                        }
                        try {
                            long chosenTime = 86400000 / choice;
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
    public String checkIfStateExists(String country) {
        for (Countries countries : Countries.values()) {
            if (country.equals(countries.toString().toLowerCase())) {
                String stateMatch = countries.getRealName();

                return stateMatch;
            }
        }
        return "Location Not Supported";

    }

}
