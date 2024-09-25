package com.robert.rssparser;

import org.w3c.dom.Element;

import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import android.text.TextUtils;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;

public class CustomXMLParser {
    static final String EMPTY_STRING = "";
    static final String ITEM = "item";
    static final String TITLE = "title";
    static final String MEDIA = "media";
    static final String DESCRIPTION = "description";
    static final String LINK = "link";
    static final String ATOM_LINK = "atom:link";
    static final String URL = "url";
    static final String IMAGE = "image";
    static final String ENCLOSURE = "enclosure";
    static final String DC_DATE = "dc:date";
    static final String PUBLISH_DATE = "pubdate";

    static final String CONTENT_ENCODED = "content:encoded";
    static final String MEDIA_CONTENT = "media:content";
    static final String MEDIA_THUMBNAIL = "media:thumbnail";
    static final String THUMBNAIL = "thumbnail";// get image tag inside content use for thumbnail of item
    static final String THUMBNAIL_TAG = "img";// image tag inside content
    static final String THUMBNAIL_SRC = "src";// image source inside content

    public RssFeed parser(ResponseBody value) {
        //LogUtil.e("--->value=" + value);
        ArrayList<RssItem> rssItems = new ArrayList<RssItem>();
        try {
            InputStream stream = value.byteStream();
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            documentFactory.setIgnoringComments(false);
            Document document = documentFactory.newDocumentBuilder().parse(stream);
            document.getDocumentElement().normalize();

            LinkedList<HashMap<String, String>> entries = new LinkedList<HashMap<String, String>>();

            Element root = document.getDocumentElement();
            NodeList items = root.getElementsByTagName(ITEM);

            HashMap<String, String> entry;

            if (items != null) {
                for (int idx = 0; idx < items.getLength(); idx++) {
                    Node item = items.item(idx);
                    NodeList properties = item.getChildNodes();

                    RssItem rssItem = new RssItem();
                    entry = new HashMap<String, String>();

                    for (int cni = 0; cni < properties.getLength(); cni++) {
                        Node property = properties.item(cni);
                        String nodeName = property.getNodeName();
                        String nodeValue = property.getFirstChild() != null ? property.getFirstChild().getNodeValue() : property.getNodeValue();

                        LogUtil.e("--->nodeName=" + nodeName + " | nodeValue=" + nodeValue);

                        if (TITLE.equalsIgnoreCase(nodeName)) {
                            nodeValue = nodeValue != null ? nodeValue.replace("&#39;", "'").replace("&#039;", "'") : EMPTY_STRING;
                            rssItem.title = nodeValue;
                            entry.put(TITLE, nodeValue);
                        } else if (DESCRIPTION.equalsIgnoreCase(nodeName)) {
                            rssItem.description = nodeValue;
                            entry.put(DESCRIPTION, nodeValue);

                        } else if (LINK.equalsIgnoreCase(nodeName)) {
                            nodeValue = nodeValue != null ? nodeValue.trim() : EMPTY_STRING;
                            rssItem.link = nodeValue;
                            entry.put(DESCRIPTION, nodeValue);

                        } else if (URL.equalsIgnoreCase(nodeName)) {
                            rssItem.link = nodeValue;
                            entry.put(URL, nodeValue);

                        } else if (MEDIA_CONTENT.equalsIgnoreCase(nodeName)) {
                            rssItem.media = nodeValue;
                            entry.put(MEDIA, nodeValue);

                        } else if (DC_DATE.equalsIgnoreCase(nodeName) || PUBLISH_DATE.equalsIgnoreCase(nodeName)) {
                            rssItem.publishDate = nodeValue;
                            entry.put(PUBLISH_DATE, nodeValue);
                        } else if (IMAGE.equalsIgnoreCase(nodeName)) {
                            rssItem.image = nodeValue;
                            entry.put(IMAGE, nodeValue);

                        } else if (MEDIA_THUMBNAIL.equalsIgnoreCase(nodeName)) {
                            String thumbnail = nodeValue;
                            if (TextUtils.isEmpty(thumbnail) && property.getAttributes() != null && property.getAttributes().getNamedItem(URL) != null) {
                                thumbnail = property.getAttributes().getNamedItem(URL).getTextContent();
                            }

                            rssItem.thumbnail = thumbnail;
                            entry.put(THUMBNAIL, thumbnail);

                        } else if (CONTENT_ENCODED.equalsIgnoreCase(nodeName)) {
                            rssItem.contentEncoded = nodeValue;
                            entry.put(CONTENT_ENCODED, nodeValue);

                            // Parsing article thumbnail inside 'content:encoded'
                            org.jsoup.nodes.Document doc = Jsoup.parse(nodeValue);
                            org.jsoup.select.Elements elements = doc.select(THUMBNAIL_TAG);

                            for (org.jsoup.nodes.Element element: elements) {
                                if (element.hasAttr(THUMBNAIL_SRC)) {
                                    if (TextUtils.isEmpty(rssItem.thumbnail)) {
                                        rssItem.thumbnail = element.attribute(THUMBNAIL_SRC).getValue();
                                    }
                                    if (TextUtils.isEmpty(rssItem.image)) {
                                        rssItem.image = element.attribute(THUMBNAIL_SRC).getValue();
                                    }
                                    rssItem.images.add(element.attribute(THUMBNAIL_SRC).getValue());
                                }
                            }

                        } else if (ENCLOSURE.equalsIgnoreCase(nodeName) && TextUtils.isEmpty(rssItem.thumbnail) && property.getAttributes() != null) {

                            // Parsing article thumbnail inside 'enclosure' tag
                            NamedNodeMap attr = property.getAttributes();

                            if (attr.getNamedItem(URL) != null) {
                                nodeValue = attr.getNamedItem(URL).getNodeValue();
                                rssItem.thumbnail = nodeValue;
                                entry.put(IMAGE, nodeValue);
                            }

                        }
                    }
                    rssItems.add(rssItem);
                    entries.add(entry);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RssFeed(rssItems);
    }
}
