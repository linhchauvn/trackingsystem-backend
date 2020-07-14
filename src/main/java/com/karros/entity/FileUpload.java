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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author chaul
 *
 */
@Entity
@Table(name = "FILEUPLOAD")
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "filename", nullable = false)
    private String fileName;

    @Column(name = "uploadtime", nullable = false)
    private Date uploadTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "metadata_id")
    private GPXMetadata metadata;

    @OneToMany(mappedBy = "fileUpload", cascade = CascadeType.ALL)
    private List<GPXWaypoint> gpxWaypoints;

    @OneToMany(mappedBy = "fileUpload", cascade = CascadeType.ALL)
    private List<GPXTrackSegment> gpxTrackSegments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GPXMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(GPXMetadata metadata) {
        this.metadata = metadata;
    }

    public List<GPXWaypoint> getGpxWaypoints() {
        return gpxWaypoints;
    }

    public void setGpxWaypoints(List<GPXWaypoint> gpxWaypoints) {
        this.gpxWaypoints = gpxWaypoints;
    }

    public List<GPXTrackSegment> getGpxTrackSegments() {
        return gpxTrackSegments;
    }

    public void setGpxTrackSegments(List<GPXTrackSegment> gpxTrackSegments) {
        this.gpxTrackSegments = gpxTrackSegments;
    }
}
