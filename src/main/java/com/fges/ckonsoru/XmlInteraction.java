package com.fges.ckonsoru;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlInteraction implements DatabaseInteraction {

    @Override
    public Collection findCrenauxDate(LocalDate date) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;
        List<RendezVous> rdvs = new LinkedList<>();
        String sdate = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        return null;
    }
}
