/**
 * (C) Karros Technologies LLC 2020 Karros Technologies are the owner of the
 * entire copyright in this work. Any unauthorised use of this work will
 * constitute an infringement of Karros's copyright. Copying, reproduction,
 * publication, performance, broadcasting, transmission to subscribers to a
 * diffusion service or adaptation of the work, without the express written
 * consent of Karros Technologies is strictly prohibited.
 */
package com.karros.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.karros.app.DemoApplication;
import com.karros.dto.FileUploadDTO;
import com.karros.dto.LatestTrackDTO;
import com.karros.dto.LinkDTO;
import com.karros.dto.MetadataDTO;
import com.karros.dto.TrackDTO;
import com.karros.dto.TrackPointDTO;
import com.karros.dto.TrackSegmentDTO;
import com.karros.dto.WaypointDTO;
import com.karros.service.TrackingService;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author chaul
 *
 */
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class TrackingControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackingService service;

    @Test
    public void testUploadFile_happyCase() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile("testFile", getTestFile());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/{userId}/upload", 1).file("file", mockFile.getBytes())).andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isAccepted())
                        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(String.format("File %s is successfully uploaded!", mockFile.getOriginalFilename()))));
    }

    @Test
    public void testUploadFile_emptyFile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/{userId}/upload", 1).file("file", null)).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Please select a file!")));
    }

    @Test
    public void testGetLatestTrack_happyCase() throws Exception {
        List<LatestTrackDTO> response = Arrays.asList(createDefaultLatestTrackDTO());
        Mockito.when(service.getLatestTrack(0, Integer.MAX_VALUE)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.get("/latest")).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.blankString())));
    }

    @Test
    public void testGetFileDetail_happyCase() throws Exception {
        Long testFileId = 1L;
        FileUploadDTO mockDto = createDefaultFileUploadDTO();

        Mockito.when(service.getFileDetail(testFileId)).thenReturn(mockDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/detail").queryParam("id", testFileId.toString())).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().string(Matchers.not(Matchers.blankString())));
    }

    @Test
    public void testGetFileDetail_missingParam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/detail")).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Query parameter 'id' is required!")));
    }

    @Test
    public void testGetFileDetail_fileNotFound() throws Exception {
        Long testFileId = 1L;
        Mockito.when(service.getFileDetail(testFileId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/detail").queryParam("id", testFileId.toString())).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNotFound())
                        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(String.format("fileId=%s not found.", testFileId))));
    }

    private LatestTrackDTO createDefaultLatestTrackDTO() {
        LatestTrackDTO dto = new LatestTrackDTO();
        dto.setFileId(1L);
        dto.setFileName("filename");
        dto.setUsername("username");
        dto.setUploadTime(new Date());
        return dto;
    }

    private FileUploadDTO createDefaultFileUploadDTO() {
        FileUploadDTO dto = new FileUploadDTO();
        MetadataDTO metadata = new MetadataDTO();
        metadata.setName("metaname");
        metadata.setDescription("metadescr");
        metadata.setAuthor("metaauthor");

        LinkDTO link = new LinkDTO();
        link.setLinkHref("linkHref");
        link.setLinkText("linkText");
        metadata.setLink(link);
        metadata.setTime(new Date());
        dto.setMetadata(metadata);

        WaypointDTO waypoint = new WaypointDTO();
        waypoint.setLatitude(123.123);
        waypoint.setLongitude(123.123);
        waypoint.setName("waypoingname");
        waypoint.setSymbol("waypointsym");
        dto.setWaypoints(Arrays.asList(waypoint));

        TrackDTO track = new TrackDTO();
        TrackSegmentDTO trackSegmentDto = new TrackSegmentDTO();
        TrackPointDTO trackPointDto = new TrackPointDTO();
        trackPointDto.setElevation(444.555);
        trackPointDto.setLatitude(666.666);
        trackPointDto.setLongitude(777.777);
        trackPointDto.setTime(new Date());
        trackSegmentDto.setTrackPoints(Arrays.asList(trackPointDto));
        track.setTrackSegmentDTOs(Arrays.asList(trackSegmentDto));
        dto.setTrack(track);
        return dto;
    }

    private final InputStream getTestFile() throws IOException {
        return new ClassPathResource("testdata/testfile.gpx").getInputStream();
    }
}
