package PDFReader;

import java.beans.Statement;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;


public class PDFReader{

	//Method to open a connection to the DB
	private Connection dbConnect() {

		String url = "jdbc:sqlite:D:/Databases/DnDText.db";
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(url);
			System.out.println("Connection to SQLite has been established.");
		}catch(SQLException exc) {
			System.out.println(exc.getMessage());
		}
		return con;
	}
	
	//Method to create table within DB
	public static void createTable() {
		String url = "jdbc:sqlite:D:/Databases/DnDText.db";
		
		String sql = "CREATE TABLE IF NOT EXISTS DnD_Library (\n"
				+ " 'Reference Word' text NOT NULL UNIQUE PRIMARY KEY);";
		
		try(Connection con = DriverManager.getConnection(url); java.sql.Statement state = con.createStatement()){
			state.execute(sql);
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//Method to insert text to DB
	public void insertToDB(String word) {
		String sql = "INSERT INTO DnD_Library('Reference Word') VALUES(?)";
		
		try(Connection con = this.dbConnect(); PreparedStatement pstate = con.prepareStatement(sql)){
			pstate.setString(1, word);
			pstate.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void WriteFile (String output) throws IOException {
		
		//This is the name of the file to be written, but I would like to make this dynamic if possible?
		String FileName = "D:/My Documents/D&D/Book of Lost Spells (5E).txt";

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
		File file = new File("D:/My Documents/D&D/Book of Lost Spells (5E).pdf");
		PDDocument document = PDDocument.load(file);

		//Instantiate PDFTextStripper class
		PDFTextStripper pdfStripper = new PDFTextStripper();

		//Retrieving text from PDF document
		String text = pdfStripper.getText(document);
		
		//Creates DB Table if it does not exist
		createTable();
		
		//Initialize insert value
		String dbInsert = null;
		
		//Testing
		String testWord = "Necromancer";
		
		//Reading through text String, which is the entire document to find testWord and inserting substring
		//Investigating way to filter by word rather than by substring
		for(int i = 0; i < text.length(); i++) {
			//System.out.println(text);
			if(text.contains(testWord)) {
				dbInsert = text.substring(0,1);
			}
		}
		
		//Insert to DB
		//Would like to find way to associate page numbers with keywords
		//Would like to find way to associate book titles with keywords
		PDFReader addNew = new PDFReader();
		addNew.insertToDB(dbInsert);
		
		WriteFile(text);

		//Closing the document
		document.close();

	}
}
