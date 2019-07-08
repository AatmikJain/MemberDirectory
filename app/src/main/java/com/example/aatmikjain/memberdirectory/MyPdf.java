package com.example.aatmikjain.memberdirectory;

import android.content.Context;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import Database.EditLogTable;

public class MyPdf {
    //write method takes two parameter pdf name and content
    //return true if pdf successfully created
    public Boolean write(Context context, String fname,  ArrayList<EditLogTable> myProducts) {
        try {
            //Create file path for Pdf
            String fpath =  "/sdcard/Download/"+ fname;
            File file = new File(fpath);
            if (!file.exists()) {
                file.createNewFile();
            }
            // To customise the text of the pdf
            // we can use FontFamily
            // create an instance of itext document
            Document document = new Document();

            PdfPTable table = new PdfPTable(new float[] { 1, 1});
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Title");
            table.addCell("Des");
            table.setHeaderRows(1);
            PdfPCell[] cells = table.getRow(0).getCells();
            for (int j=0;j<cells.length;j++){
                cells[j].setBackgroundColor(BaseColor.GRAY);
            }
            for (int i=1;i<myProducts.size();i++){

                table.addCell(myProducts.get(i).getEmail());
                table.addCell(myProducts.get(i).getLastEdit());
            }
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
            document.open();
            //using add method in document to insert a paragraph
            document.add(table);
            // close document
            document.close();

            Toast.makeText(context,"pdf created.",Toast.LENGTH_LONG).show();


            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}


