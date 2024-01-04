package by.kihtenkoolga.util.factory.impl;

import by.kihtenkoolga.exception.WriterException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import by.kihtenkoolga.util.factory.UtilWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static by.kihtenkoolga.util.FilesUtil.getFileFromResources;

@Component
public class PdfUtil implements UtilWriter {

    private final String BASE_PDF = getFileFromResources("/Clevertec_Template.pdf");

    @Override
    public void writeObjectToFile(Object object, File fileOutput) {
        String htmlPage = "<html><pre>" + object.toString() + "</pre></html>";
        try {
            Document document = new Document(PageSize.A4, 50, 50, 280, 10);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileOutput.getAbsolutePath()));
            document.open();
            PdfContentByte cb = writer.getDirectContent();

            PdfReader reader = new PdfReader(new FileInputStream(BASE_PDF));

            PdfImportedPage page = writer.getImportedPage(reader, 1);

            document.newPage();
            cb.addTemplate(page, 0, 0);

            InputStream htmlInputStream = new ByteArrayInputStream(htmlPage.getBytes(StandardCharsets.UTF_8));
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, htmlInputStream);
            document.close();

        } catch (DocumentException | IOException e) {
            throw new WriterException();
        }
    }

}
