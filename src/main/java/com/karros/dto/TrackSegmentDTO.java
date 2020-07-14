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
@JacksonXmlRootElement (localName = "trkseg")
public class TrackSegmentDTO {
    @JacksonXmlProperty (localName = "trkpt")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<TrackPointDTO> trackPoints;

    public List<TrackPointDTO> getTrackPoints() {
        return trackPoints;
    }

    public void setTrackPoints(List<TrackPointDTO> trackPoints) {
        this.trackPoints = trackPoints;
    }
}
