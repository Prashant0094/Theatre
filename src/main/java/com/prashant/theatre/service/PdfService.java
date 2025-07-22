package com.prashant.theatre.service;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.prashant.theatre.dto.TicketSummaryDto;
@Service
public class PdfService {
	public byte[] generatePdf(TicketSummaryDto summary) throws BadElementException, Exception {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    Document doc = new Document();
	    PdfWriter.getInstance(doc, out);
	    doc.open();

	    // logo
	    Image logo = Image.getInstance(getClass().getClassLoader().getResource("logo.jpg"));
	    logo.scaleToFit(200, 200);
	    doc.add(logo);

	    // User + Show info
	    doc.add(new Paragraph("Name: " + summary.getUserName()));
	    doc.add(new Paragraph("Email: " + summary.getUserEmail()));
	    doc.add(new Paragraph("Show: " + summary.getShowName() + " (" + summary.getShowId() + ")"));
	    doc.add(new Paragraph("Duration: " + summary.getShowLength()));

	    // Seats
	    doc.add(new Paragraph("Seats: " + String.join(", ", summary.getSeatIds())));
	    doc.add(new Paragraph("Total: ₹" + summary.getTotalPrice()));

	    doc.close();
	    return out.toByteArray();
	}

}
