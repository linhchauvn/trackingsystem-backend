/**
 * (C) Karros Technologies LLC 2020 Karros Technologies are the owner of
 * the entire copyright in this work. Any unauthorised use of this work will
 * constitute an infringement of Karros's copyright. Copying, reproduction,
 * publication, performance, broadcasting, transmission to subscribers to a
 * diffusion service or adaptation of the work, without the express written
 * consent of Karros Technologies is strictly prohibited.
 */
package com.karros.dto;

import java.util.Date;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * @author chaul
 *
 */
@JacksonXmlRootElement (localName = "metadata")
public class MetadataDTO {
    @JacksonXmlProperty (localName = "name")
    private String name;
    @JacksonXmlProperty (localName = "desc")
    private String description;
    @JacksonXmlProperty (localName = "author")
    private String author;
    @JacksonXmlProperty (localName = "link")
    private LinkDTO link;
    @JacksonXmlProperty (localName = "time")
    private Date time;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public LinkDTO getLink() {
        return link;
    }
    
    public void setLink(LinkDTO link) {
        this.link = link;
    }
    
    public Date getTime() {
        return time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }
}
