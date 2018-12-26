import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFReader{

	public static void WriteFile (String output) throws IOException {
		
		//This is the name of the file to be pulled, but I would like to make this dynamic if possible?
		String FileName = "C:/Users/Matthew/Documents/D&D/Book of Lost Spells (5E).txt";

		FileWriter fw = new FileWriter(FileName);
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(output);

		System.out.println("Done");

		if(bw != null) {
			bw.close();
		}

		if(fw != null) {
			fw.close();
		}

	}

	public static void main(String args[]) throws IOException {

		//Loading an existing document
		File file = new File("C:/Users/Matthew/Documents/D&D/Book of Lost Spells (5E).pdf");
		PDDocument document = PDDocument.load(file);

		//Instantiate PDFTextStripper class
		PDFTextStripper pdfStripper = new PDFTextStripper();

		//Retrieving text from PDF document
		String text = pdfStripper.getText(document);

		WriteFile(text);

		//Closing the document
		document.close();

	}
}
