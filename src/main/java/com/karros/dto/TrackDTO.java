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
@JacksonXmlRootElement (localName = "trk")
public class TrackDTO {
    @JacksonXmlProperty (localName = "trkseg")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<TrackSegmentDTO> trackSegmentDTOs;

    public List<TrackSegmentDTO> getTrackSegmentDTOs() {
        return trackSegmentDTOs;
    }

    public void setTrackSegmentDTOs(List<TrackSegmentDTO> trackSegmentDTOs) {
        this.trackSegmentDTOs = trackSegmentDTOs;
    }
}
