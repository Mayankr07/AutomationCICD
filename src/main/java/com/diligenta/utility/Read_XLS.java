package com.diligenta.utility;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class Read_XLS
{

    public String filelocation;
    public  FileInputStream ipstr;
    public OutputStream opstr;
    private XSSFWorkbook wb;
    public XSSFSheet ws;

    public DataFormat dataFormat;

    public Read_XLS(String filelocation)
    {
        this.filelocation=filelocation;
        try
        {
             ipstr =  new FileInputStream(filelocation);
               wb  =  new XSSFWorkbook(ipstr);
               ws  =  wb.getSheetAt(0);
               ipstr.close();
        }catch (Exception e)
        {
                e.printStackTrace();
        }
    }

    //Retrieving  No of Rows
    public int retrieveNoOfRows(String wsName)
    {
            int sheetIndex= wb.getSheetIndex(wsName);
            if(sheetIndex==-1)
            {
                return 0;
            }
            else
            {
               ws= wb.getSheetAt(sheetIndex);
               int rowCount=  ws.getLastRowNum()+1; //+1 because this function has indexing from 0 so
               return  rowCount;
            }
    }

    //Retrieving No of Coloum
    public int retrieveNoOfColums(String wsName)
    {
           int SheetIndex=wb.getSheetIndex(wsName);
           if(SheetIndex==-1)
           {
               return 0;
           }
           else {
               ws= wb.getSheetAt(SheetIndex);
               int colCount= ws.getRow(0).getLastCellNum();
               return colCount;
           }
    }


    //To check that Suite or Test case is marked as Y or NOT
    public String  retrieveToRunFlag(String wsName,String colName,String rowName) throws IOException
    {

        System.out.println("-----------------");
        System.out.println("---Sheet Name "+wsName);
        System.out.println("---Col Name"+colName);
        System.out.println("--- Row Name "+rowName);


           int SheetIndex= wb.getSheetIndex(wsName);
           if(SheetIndex==-1)
           {
               return null;
           }
           else {
              int rowNum= retrieveNoOfRows(wsName);
              int colNum= retrieveNoOfColums(wsName);
              int colNumber=-1;
              int rowNumber=-1;

              XSSFRow SuiteRow=ws.getRow(0);

              //Traversing Colums of First Row
              for(int i=0;i<colNum;i++)
              {
                  if(SuiteRow.getCell(i).getStringCellValue().equalsIgnoreCase(colName.trim()))
                  {
                      colNumber=i;
                  }
              }

              if(colNumber==-1)
              {
                  return null;
              }

               //Traversing rows of colum index 0
              for(int j=0;j<rowNum;j++)
              {
                  XSSFRow suiteRow=ws.getRow(j);
                  if(suiteRow.getCell(0).getStringCellValue().equalsIgnoreCase(rowName.trim()))
                  {
                      rowNumber=j;
                  }
              }

               if(rowNumber==-1)
               {
                   return null;
               }

               XSSFRow row=ws.getRow(rowNumber);
               XSSFCell cell= row.getCell(colNumber);


               //Converting Cell Value To String
               String value=cellToString(cell);
               System.out.println("value of Cell"+value);
               return  value;
           }

    }


   //To Convert the data from Excel Cell in to String Format
    public static String cellToString(XSSFCell cell)
    {
        int type;
        Object result;  //Doing Autoboxing here primitive --->To Object getting it in object type because object accepts  any return type
        //it will give you the type of cell
        type= cell.getCellType();
        System.out.println("type "+type);
        switch (type){
            case 0 :
                result= cell.getNumericCellValue();
                break;
            case 1 :
                result= cell.getStringCellValue();
                break;
            default:
                throw new RuntimeException("Unsupported  cell.");
        }
        System.out.println("Cell Value "+result.toString());
        return result.toString();

    }


    public boolean writeResult(String wsName,String colName,String rowName,String result)
    {
        try
        {
            int SheetIndex= wb.getSheetIndex(wsName);
            if(SheetIndex==-1)
            {
                return false;
            }

                int rowNum = retrieveNoOfRows(wsName);
                int colNum = retrieveNoOfColums(wsName);
                int colNumber = -1;
                int rowNumber = -1;

                XSSFRow suiteRow=ws.getRow(0);
                for(int i=0;i<colNum;i++)
                {
                    if(suiteRow.getCell(i).getStringCellValue().equals(colName.trim()))
                    {
                        colNumber=i;
                    }
                }

                if(colNumber==-1)
                {
                    return false;
                }

                for(int i=0; i<rowNum-1;i++)
                {
                    XSSFRow row=ws.getRow(i+1);
                    XSSFCell cell=row.getCell(0);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String value=cellToString(cell);
                    System.out.println("CellValue to write"+value);
                    if(value.equals(rowName))
                    {
                        rowNumber=i+1;
                        break;
                    }
                }


                XSSFRow Row=ws.getRow(rowNumber);
                XSSFCell cell=Row.getCell(colNumber);
                if(cell==null)
                {
                    cell=Row.createCell(colNumber);
                }
                cell.setCellValue(result);

                opstr= new FileOutputStream(filelocation);
                wb.write(opstr);
                opstr.close();

        }
        catch(Exception e)
        {
            return false;
        }
        System.out.println("returning Result");
        return true;
    }



    //To retrieve DataToRun Flag of Test Case
    public String[] retrieveToRunFlagTestData(String wsName,String colName)
    {
         int sheetIndex=  wb.getSheetIndex(wsName);
         if(sheetIndex==-1)
         {
             return null;
         }else
         {
             int rowNum = retrieveNoOfRows(wsName);
             int colNum = retrieveNoOfColums(wsName);
             int colNumber = -1;
             int rowNumber = -1;

             XSSFRow Suiterow =  ws.getRow(0);
             String data[]=new String[rowNum-1];
             for(int i=0;i<colNum;i++)
             {
                 if(Suiterow.getCell(i).getStringCellValue().equals(colName.trim()))
                 {
                     colNumber=i;
                 }
             }

             if(colNumber==-1)
             {
                return null;
             }

             for(int j=0;j<rowNum;j++)
             {
                 XSSFRow Row=ws.getRow(j+1);
                 if(Row==null)
                 {
                     data[j]="";
                 }
                 else {
                     XSSFCell cell=Row.getCell(colNumber);
                     if(cell==null)
                     {
                         data[j]="";
                     }
                     else
                     {
                         String value=cellToString(cell);
                         data[j]=value;
                     }
                 }
             }
             System.out.println("Data Found "+data.toString());
             return data;
         }
    }
}
