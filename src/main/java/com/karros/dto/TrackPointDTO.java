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
@JacksonXmlRootElement (localName = "trkpt")
public class TrackPointDTO {
    @JacksonXmlProperty (localName = "lat", isAttribute = true)
    private Double latitude;
    @JacksonXmlProperty (localName = "lon", isAttribute = true)
    private Double longitude;
    @JacksonXmlProperty (localName = "ele")
    private Double elevation;
    @JacksonXmlProperty (localName = "time")
    private Date time;
    
    public Double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    public Double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    public Double getElevation() {
        return elevation;
    }
    
    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }
    
    public Date getTime() {
        return time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }
}
