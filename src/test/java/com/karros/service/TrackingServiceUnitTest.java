/**
 * (C) Karros Technologies LLC 2020 Karros Technologies are the owner of
 * the entire copyright in this work. Any unauthorised use of this work will
 * constitute an infringement of Karros's copyright. Copying, reproduction,
 * publication, performance, broadcasting, transmission to subscribers to a
 * diffusion service or adaptation of the work, without the express written
 * consent of Karros Technologies is strictly prohibited.
 */
package com.karros.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.karros.app.DemoApplication;
import com.karros.dao.FileUploadRepository;
import com.karros.dto.FileUploadDTO;
import com.karros.dto.LatestTrackDTO;
import com.karros.dto.MetadataDTO;
import com.karros.dto.TrackPointDTO;
import com.karros.dto.WaypointDTO;
import com.karros.entity.FileUpload;
import com.karros.entity.GPXMetadata;
import com.karros.entity.GPXTrackPoint;
import com.karros.entity.GPXTrackSegment;
import com.karros.entity.GPXWaypoint;
import com.karros.entity.User;
import com.karros.service.impl.TrackingServiceImpl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * @author chaul
 *
 */
@SpringBootTest(classes=DemoApplication.class)
public class TrackingServiceUnitTest {
    @Mock
    private FileUploadRepository fileUploadRepo;
    
    @InjectMocks
    private TrackingService trackingService = new TrackingServiceImpl();
    
    @Test
    public void testSaveFileUpload_happyCase() throws IOException {
        trackingService.saveFileUpload(1L, "test-filename", getTestFile());
    }
    
    @Test
    public void testSaveFileUpload_exceptionWhileSaving() throws IOException {
        Mockito.when(fileUploadRepo.save(Mockito.any())).thenThrow(new IllegalArgumentException("Something wrong happened."));
        try {
            trackingService.saveFileUpload(1L, "test-filename", getTestFile());
            fail("IllegalArgumentException must be thrown!");
        }
        catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    @Test
    public void testGetLatestTrack_happyCase() {
        FileUpload entity = createDefaultFileUpload(1L);
        Page<FileUpload> searchResult = new PageImpl<FileUpload>(Arrays.asList(entity));
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by("id").descending());
        Mockito.when(fileUploadRepo.findAll(pageable)).thenReturn(searchResult);
        List<LatestTrackDTO> dtos = trackingService.getLatestTrack(null, null);
        assertEquals(1, dtos.size());
        compareValues(entity, dtos.get(0));
    }
    
    @Test
    public void testGetLatestTrack_testPaginator() {
        List<FileUpload> content = new ArrayList<>();
        content.add(createDefaultFileUpload(1L));
        content.add(createDefaultFileUpload(2L));
        content.add(createDefaultFileUpload(3L));
        content.add(createDefaultFileUpload(4L));
        content.add(createDefaultFileUpload(5L));
        Pageable page0 = PageRequest.of(0, 10, Sort.by("id").descending());
        Page<FileUpload> page0Result = new PageImpl<FileUpload>(content);
        Mockito.when(fileUploadRepo.findAll(page0)).thenReturn(page0Result);
        Pageable page1 = PageRequest.of(1, 10, Sort.by("id").descending());
        Page<FileUpload> page1Result = new PageImpl<FileUpload>(new ArrayList<>());
        Mockito.when(fileUploadRepo.findAll(page1)).thenReturn(page1Result);
        List<LatestTrackDTO> dtos = trackingService.getLatestTrack(0, 10);
        assertEquals(5, dtos.size());
        dtos = trackingService.getLatestTrack(1, 10);
        assertEquals(0, dtos.size());
    }
    
    @Test
    public void testGetFileDetail_happyCase() {
        FileUpload entity = createDefaultFileUpload(1L);
        Optional<FileUpload> getResult = Optional.of(entity);
        Mockito.when(fileUploadRepo.findById(1L)).thenReturn(getResult);
        FileUploadDTO dto = trackingService.getFileDetail(1L);
        assertNotNull(dto);
        compareValues(entity, dto);
    }
    
    @Test
    public void testGetFileDetail_ItemNotFound() {
        Optional<FileUpload> getResult = Optional.ofNullable(null);
        Mockito.when(fileUploadRepo.findById(1L)).thenReturn(getResult);
        FileUploadDTO dto = trackingService.getFileDetail(1L);
        assertNull(dto);
    }
    
    @Test
    public void testGetFileDetail_invalidId() {
        Mockito.when(fileUploadRepo.findById(null)).thenThrow(new IllegalArgumentException());
        try {
            trackingService.getFileDetail(null);
            fail("IllegalArgumentException must be thrown!");
        }
        catch (IllegalArgumentException e) {
            // expected
        }
    }
    
    private void compareValues(FileUpload entity, FileUploadDTO dto) {
        assertNotNull(dto);

        assertNotNull(dto.getMetadata());
        compareValues(entity.getMetadata(), dto.getMetadata());
        
        assertNotNull(dto.getWaypoints());
        assertEquals(entity.getGpxWaypoints().size(), dto.getWaypoints().size());
        compareValues(entity.getGpxWaypoints().get(0), dto.getWaypoints().get(0));
        
        assertNotNull(dto.getTrack());
        assertNotNull(dto.getTrack().getTrackSegmentDTOs());
        assertEquals(entity.getGpxTrackSegments().size(), dto.getTrack().getTrackSegmentDTOs().size());
        assertNotNull(dto.getTrack().getTrackSegmentDTOs().get(0).getTrackPoints());
        assertEquals(entity.getGpxTrackSegments().get(0).getGpxTrackPoints().size(), dto.getTrack().getTrackSegmentDTOs().get(0).getTrackPoints().size());
        compareValues(entity.getGpxTrackSegments().get(0).getGpxTrackPoints().get(0), dto.getTrack().getTrackSegmentDTOs().get(0).getTrackPoints().get(0));
    }

    private void compareValues(GPXTrackPoint entity, TrackPointDTO dto) {
        assertEquals(entity.getLatitude(), dto.getLatitude());
        assertEquals(entity.getLongitude(), dto.getLongitude());
        assertEquals(entity.getElevation(), dto.getElevation());
        assertEquals(entity.getTime(), dto.getTime());
    }

    private void compareValues(GPXWaypoint entity, WaypointDTO dto) {
        assertEquals(entity.getLatitude(), dto.getLatitude());
        assertEquals(entity.getLongitude(), dto.getLongitude());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getSymbol(), dto.getSymbol());
    }

    private void compareValues(GPXMetadata entity, MetadataDTO dto) {
        assertEquals(entity.getGpxName(), dto.getName());
        assertEquals(entity.getGpxDescription(), dto.getDescription());
        assertEquals(entity.getAuthor(), dto.getAuthor());
        if (entity.getLinkHref() != null || entity.getLinkTitle() != null) {
            assertEquals(entity.getLinkHref(), dto.getLink().getLinkHref());
            assertEquals(entity.getLinkTitle(), dto.getLink().getLinkText());
        }
        assertEquals(entity.getTime(), dto.getTime());
    }

    private void compareValues(FileUpload entity, LatestTrackDTO dto) {
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getFileId());
        assertEquals(entity.getFileName(), dto.getFileName());
        assertEquals(entity.getUploadTime(), dto.getUploadTime());
        assertEquals(entity.getUser().getUsername(), dto.getUsername());
    }

    private FileUpload createDefaultFileUpload(Long id) {
        FileUpload fileUpload = new FileUpload();
        fileUpload.setId(id);
        fileUpload.setFileName("filename");
        fileUpload.setUploadTime(new Date());
        User user = new User();
        user.setUsername("username");
        fileUpload.setUser(user);
        
        GPXMetadata metadata = new GPXMetadata();
        metadata.setGpxName("gpxname");
        metadata.setGpxDescription("gpxdescription");
        metadata.setAuthor("gpxauthor");
        fileUpload.setMetadata(metadata);
        
        GPXWaypoint waypoint = new GPXWaypoint();
        waypoint.setLatitude(-100.1);
        waypoint.setLongitude(100.1);
        waypoint.setName("waypointname");
        waypoint.setSymbol("waypointsym");
        fileUpload.setGpxWaypoints(Arrays.asList(waypoint));
        
        GPXTrackSegment trackSegment = new GPXTrackSegment();
        GPXTrackPoint trackpoint = new GPXTrackPoint();
        trackpoint.setLatitude(-200.2);
        trackpoint.setLongitude(200.2);
        trackpoint.setElevation(1234.1234);
        trackSegment.setGpxTrackPoints(Arrays.asList(trackpoint));
        fileUpload.setGpxTrackSegments(Arrays.asList(trackSegment));
        return fileUpload;
    }

    private final InputStream getTestFile() throws IOException {
        return new ClassPathResource("testdata/testfile.gpx").getInputStream();
    }
}
