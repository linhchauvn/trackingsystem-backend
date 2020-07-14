/**
 * (C) Karros Technologies LLC 2020 Karros Technologies are the owner of
 * the entire copyright in this work. Any unauthorised use of this work will
 * constitute an infringement of Karros's copyright. Copying, reproduction,
 * publication, performance, broadcasting, transmission to subscribers to a
 * diffusion service or adaptation of the work, without the express written
 * consent of Karros Technologies is strictly prohibited.
 */
package com.karros.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author chaul
 *
 */
@Entity
@Table(name = "METADATA")
public class GPXMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "gpxname", nullable = false)
    private String gpxName;

    @Column(name = "gpxdescription", length = Integer.MAX_VALUE)
    private String gpxDescription;

    @Column(name = "author")
    private String author;

    @Column(name = "linkhref")
    private String linkHref;

    @Column(name = "linktitle")
    private String linkTitle;

    @Column(name = "time")
    private Date time;

    @OneToOne
    @JoinColumn(name = "fileupload_id")
    private FileUpload fileUpload;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGpxName() {
        return gpxName;
    }

    public void setGpxName(String gpxName) {
        this.gpxName = gpxName;
    }

    public String getGpxDescription() {
        return gpxDescription;
    }

    public void setGpxDescription(String gpxDescription) {
        this.gpxDescription = gpxDescription;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLinkHref() {
        return linkHref;
    }

    public void setLinkHref(String linkHref) {
        this.linkHref = linkHref;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public FileUpload getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(FileUpload fileUpload) {
        this.fileUpload = fileUpload;
    }
}
