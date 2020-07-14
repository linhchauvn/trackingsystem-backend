/**
 * (C) Karros Technologies LLC 2020 Karros Technologies are the owner of
 * the entire copyright in this work. Any unauthorised use of this work will
 * constitute an infringement of Karros's copyright. Copying, reproduction,
 * publication, performance, broadcasting, transmission to subscribers to a
 * diffusion service or adaptation of the work, without the express written
 * consent of Karros Technologies is strictly prohibited.
 */
package com.karros.dto.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.karros.dto.LinkDTO;
import com.karros.dto.MetadataDTO;
import com.karros.dto.TrackDTO;
import com.karros.dto.TrackPointDTO;
import com.karros.dto.TrackSegmentDTO;
import com.karros.dto.FileUploadDTO;
import com.karros.dto.LatestTrackDTO;
import com.karros.dto.WaypointDTO;
import com.karros.entity.FileUpload;
import com.karros.entity.GPXMetadata;
import com.karros.entity.GPXTrackPoint;
import com.karros.entity.GPXTrackSegment;
import com.karros.entity.GPXWaypoint;
import com.karros.entity.User;

/**
 * @author chaul
 *
 */
public final class DTOConverter {
    public static FileUpload convertObject(Long userId, String fileName, FileUploadDTO dto) {
        if (dto == null) {
            return null;
        }
        
        FileUpload fileUpload = new FileUpload();
        fileUpload.setFileName(fileName);
        fileUpload.setUploadTime(new Date());
        User user = new User();
        user.setId(userId);
        fileUpload.setUser(user);
        
        GPXMetadata metadata = convertObject(dto.getMetadata());
        metadata.setFileUpload(fileUpload);
        fileUpload.setMetadata(metadata);
        
        List<WaypointDTO> waypointDTOs = dto.getWaypoints();
        if (waypointDTOs != null && !waypointDTOs.isEmpty()) {
            List<GPXWaypoint> waypoints = new ArrayList<GPXWaypoint>();
            for (WaypointDTO item : waypointDTOs) {
                GPXWaypoint waypoint = convertObject(item);
                waypoint.setFileUpload(fileUpload);
                waypoints.add(waypoint);
            }
            fileUpload.setGpxWaypoints(waypoints);
        }
        
        TrackDTO trackDTO = dto.getTrack();
        if (trackDTO != null && trackDTO.getTrackSegmentDTOs() != null && !trackDTO.getTrackSegmentDTOs().isEmpty()) {
            List<GPXTrackSegment> trackSegments = new ArrayList<>();
            for (TrackSegmentDTO item : trackDTO.getTrackSegmentDTOs()) {
                GPXTrackSegment trackSegment = convertObjecT(item);
                trackSegment.setFileUpload(fileUpload);
                trackSegments.add(trackSegment);
            }
            fileUpload.setGpxTrackSegments(trackSegments);
        }
        
        return fileUpload;
    }
    
    public static LatestTrackDTO convertToLatestTrackDTO(FileUpload entity) {
        if (entity == null) {
            return null;
        }
        
        LatestTrackDTO dto = new LatestTrackDTO();
        dto.setFileId(entity.getId());
        dto.setFileName(entity.getFileName());
        dto.setUploadTime(entity.getUploadTime());
        dto.setUsername(entity.getUser().getUsername());
        return dto;
    }
    
    public static FileUploadDTO convertObject(FileUpload entity) {
        if (entity == null) {
            return null;
        }
        FileUploadDTO dto = new FileUploadDTO();
        
        dto.setMetadata(convertObject(entity.getMetadata()));
        if (entity.getGpxWaypoints() != null) {
            dto.setWaypoints(entity.getGpxWaypoints().stream().map(DTOConverter::convertObject).collect(Collectors.toList()));
        }
        if (entity.getGpxTrackSegments() != null) {
            TrackDTO trackDTO = new TrackDTO();
            trackDTO.setTrackSegmentDTOs(entity.getGpxTrackSegments().stream().map(DTOConverter::convertObject).collect(Collectors.toList()));
            dto.setTrack(trackDTO);
        }
        return dto;
    }

    private static GPXMetadata convertObject(MetadataDTO dto) {
        if (dto == null) {
            return null;
        }
        GPXMetadata metadata = new GPXMetadata();
        metadata.setGpxName(dto.getName());
        metadata.setGpxDescription(dto.getDescription());
        if (dto.getLink() != null) {
            metadata.setLinkHref(dto.getLink().getLinkHref());
            metadata.setLinkTitle(dto.getLink().getLinkText());
        }
        metadata.setTime(dto.getTime());
        return metadata;
    }

    private static GPXTrackSegment convertObjecT(TrackSegmentDTO dto) {
        if (dto == null) {
            return null;
        }
        
        GPXTrackSegment entity = new GPXTrackSegment();
        if (dto.getTrackPoints() != null && !dto.getTrackPoints().isEmpty()) {
            List<GPXTrackPoint> trackPoints = new ArrayList<>();
            for (TrackPointDTO item : dto.getTrackPoints()) {
                GPXTrackPoint trackPoint = convertObject(item);
                trackPoint.setGpxTrackSegment(entity);
                trackPoints.add(trackPoint);
            }
            entity.setGpxTrackPoints(trackPoints);
        }
        return entity;
    }

    private static GPXTrackPoint convertObject(TrackPointDTO dto) {
        if (dto == null) {
            return null;
        }
        
        GPXTrackPoint entity = new GPXTrackPoint();
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setElevation(dto.getElevation());
        entity.setTime(dto.getTime());
        return entity;
    }

    private static GPXWaypoint convertObject(WaypointDTO dto) {
        if (dto == null) {
            return null;
        }
        
        GPXWaypoint entity = new GPXWaypoint();
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setName(dto.getName());
        entity.setSymbol(dto.getSymbol());
        return entity;
    }
    
    private static MetadataDTO convertObject(GPXMetadata entity) {
        MetadataDTO dto = new MetadataDTO();
        dto.setName(entity.getGpxName());
        dto.setDescription(entity.getGpxDescription());
        dto.setAuthor(entity.getAuthor());
        dto.setTime(entity.getTime());
        if (entity.getLinkHref() != null && entity.getLinkHref().length() > 0) {
            LinkDTO linkDTO = new LinkDTO();
            linkDTO.setLinkHref(entity.getLinkHref());
            linkDTO.setLinkText(entity.getLinkTitle());
            dto.setLink(linkDTO);
        }
        return dto;
    }
    
    private static WaypointDTO convertObject(GPXWaypoint entity) {
        WaypointDTO dto = new WaypointDTO();
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setName(entity.getName());
        dto.setSymbol(entity.getSymbol());
        return dto;
    }
    
    private static TrackSegmentDTO convertObject(GPXTrackSegment entity) {
        TrackSegmentDTO dto = new TrackSegmentDTO();
        if (entity.getGpxTrackPoints() != null) {
            dto.setTrackPoints(entity.getGpxTrackPoints().stream().map(DTOConverter::convertObject).collect(Collectors.toList()));
        }
        return dto;
    }
    
    private static TrackPointDTO convertObject(GPXTrackPoint entity) {
        TrackPointDTO dto = new TrackPointDTO();
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        dto.setElevation(entity.getElevation());
        dto.setTime(entity.getTime());
        return dto;
    }
}
