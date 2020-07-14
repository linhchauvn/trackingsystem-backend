/**
 * (C) Karros Technologies LLC 2020 Karros Technologies are the owner of
 * the entire copyright in this work. Any unauthorised use of this work will
 * constitute an infringement of Karros's copyright. Copying, reproduction,
 * publication, performance, broadcasting, transmission to subscribers to a
 * diffusion service or adaptation of the work, without the express written
 * consent of Karros Technologies is strictly prohibited.
 */
package com.karros.dto;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * @author chaul
 *
 */
@JacksonXmlRootElement (localName = "gpx")
public class FileUploadDTO {
    @JacksonXmlProperty (localName = "metadata")
    private MetadataDTO metadata;
    @JacksonXmlProperty (localName = "wpt")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<WaypointDTO> waypoints;
    @JacksonXmlProperty (localName = "trk")
    private TrackDTO track;
    
    public MetadataDTO getMetadata() {
        return metadata;
    }
    
    public void setMetadata(MetadataDTO metadata) {
        this.metadata = metadata;
    }
    
    public List<WaypointDTO> getWaypoints() {
        return waypoints;
    }
    
    public void setWaypoints(List<WaypointDTO> waypoints) {
        this.waypoints = waypoints;
    }
    
    public TrackDTO getTrack() {
        return track;
    }
    
    public void setTrack(TrackDTO track) {
        this.track = track;
    }
}
