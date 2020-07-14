/**
 * (C) Karros Technologies LLC 2020 Karros Technologies are the owner of
 * the entire copyright in this work. Any unauthorised use of this work will
 * constitute an infringement of Karros's copyright. Copying, reproduction,
 * publication, performance, broadcasting, transmission to subscribers to a
 * diffusion service or adaptation of the work, without the express written
 * consent of Karros Technologies is strictly prohibited.
 */
package com.karros.entity;

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
import javax.persistence.Table;

/**
 * @author chaul
 *
 */
@Entity
@Table (name = "TRACKSEGMENT")
public class GPXTrackSegment {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "fileupload_id")
    private FileUpload fileUpload;
    
    @OneToMany(mappedBy = "gpxTrackSegment", cascade = CascadeType.ALL)
    private List<GPXTrackPoint> gpxTrackPoints;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public FileUpload getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(FileUpload fileUpload) {
        this.fileUpload = fileUpload;
    }

    public List<GPXTrackPoint> getGpxTrackPoints() {
        return gpxTrackPoints;
    }

    public void setGpxTrackPoints(List<GPXTrackPoint> gpxTrackPoints) {
        this.gpxTrackPoints = gpxTrackPoints;
    }
}
