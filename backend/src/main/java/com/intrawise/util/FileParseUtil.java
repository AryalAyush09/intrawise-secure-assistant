package com.intrawise.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.intrawise.exception.FileParseException;
import org.xml.sax.SAXException;

@Component
public class FileParseUtil {
	private final Tika tika = new Tika();
	
	public String extractText(MultipartFile file) {
	    try {
	        return tika.parseToString(file.getInputStream());
	    } catch (IOException | TikaException e) {
	        throw new FileParseException("Failed to parse file: " + file.getOriginalFilename());
	    }
	}
	
	public String extractTitle(MultipartFile file) {
		try(InputStream stream = file.getInputStream()){
			BodyContentHandler  handler = new BodyContentHandler();
			Metadata data = new Metadata();
			AutoDetectParser parser = new AutoDetectParser();
			parser.parse(stream, handler, data, new ParseContext());
			
			String title = data.get("title");
			
			return(title != null && !title.isEmpty()) ? title : "UnkownTitle";
			
		}catch (IOException | TikaException | SAXException e) {
			  throw new FileParseException
			      ("Failed to extract title from file: " + file.getOriginalFilename());
		}
	}
}	

