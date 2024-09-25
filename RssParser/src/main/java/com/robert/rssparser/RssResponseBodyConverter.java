package com.robert.rssparser;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class RssResponseBodyConverter<T> implements Converter<ResponseBody, RssFeed> {


    RssResponseBodyConverter() {
    }

    @Override
    public RssFeed convert(ResponseBody value) {
        //return parse(value);
        return new CustomXMLParser().parser(value);
    }

    private RssFeed parse(ResponseBody value) {
        ArrayList<RssItem> items = new ArrayList<RssItem>();
        try {
            XMLParser parser = new XMLParser();
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = parserFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(parser);
            InputSource inputSource = new InputSource(value.charStream());
            xmlReader.parse(inputSource);
            items.addAll(parser.getItems());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new RssFeed(items);
    }

}